package cowboycheckers.controladores;

import cowboycheckers.modelo.Tablero;
import cowboycheckers.modelo.JugarPieza;
import cowboycheckers.modelo.Localizacion;
import cowboycheckers.usuarios.AIJugador;
import cowboycheckers.usuarios.Jugador;

import cowboycheckers.vistas.VentanaPrincipal;

public class ControladorJuego {
    private Tablero currTablero;
    private Jugador p1;
    private Jugador p2;
    private AIJugador comp;
    private Jugador curPlayer;
    private Jugador Victor;
    private Jugador Loser;
    private Integer gameMode = -1;
    private JugarPieza pieceSelected;
    private boolean moving;

    /**
     * Constructor para ControladorJuego
     * Toma un modo, dos jugadores, y
     * una ventana principal
     *
     * Los jugadores son creados por la interfaz gráfica de usuario
     * el modo sera determinar si IA
     * está presente
     *
     * Mw se trae simplemente para pasar a la Junta
     * @param mode
     * @param p1
     * @param p2
     * @param mw
     */
    public ControladorJuego(Integer mode, Jugador p1, Jugador p2, VentanaPrincipal mw) {
        this.gameMode = mode;
        this.currTablero = new Tablero(mw);
        this.p1 = p1;
        if (this.gameMode == 0) {
            this.comp = (AIJugador) p2;
            this.comp.setControlador(this);
            this.p2 = p2;
        } else
            this.p2 = p2;

        double t;
        t = Math.random() * 50;
        if (t <= 25.000)
            this.curPlayer = this.p1;
        else
            this.curPlayer = this.p2;

        if (this.gameMode == 0) {
            this.curPlayer = this.p1;
        }
        this.Victor = p1;
        this.Loser = p2;
    }

    /***
     * Devolverá el estado del tablero
     *
     * Utilizado por la interfaz gráfica de usuario
     * @return
     */
    public int getStatus() {
        return this.currTablero.getFaseActual(this.curPlayer);
    }


    public boolean newMovimiento(String label) {
        int gamephase = this.currTablero.getFaseActual(this.curPlayer);
        if (gamephase == Tablero.FASE_FIN_JUEGO) {
            return true;
        }
        if (this.currTablero.numMovimientosDisponibles(this.curPlayer) <= 0 && gamephase != Tablero.FASE_COLOCACION) {
            this.Victor = this.inactivePlayer();
            this.Loser = this.curPlayer;
            this.currTablero.setFaseActual(Tablero.FASE_FIN_JUEGO);
            this.currTablero.newMensajeError(this.Loser + " no tiene más movimientos disponibles, pierde el juego", 1500);
        }
        switch (gamephase) {
            case Tablero.FASE_COLOCACION:
                if (PlacementPhase(label))
                    nextPlayer();
                else
                    return false;
                break;

            case Tablero.FASE_MOVIMIENTO:
                if (this.pieceSelected == null)
                    selectPiece(label);
                else if (this.pieceSelected != null) {
                    if (this.pieceSelected == this.currTablero.getLocacionByEtiqueta(label).getPieza()) {
                        this.pieceSelected.seleccionar(false);
                        this.pieceSelected = null;
                    } else if (MovementPhase(label)) {
                        nextPlayer();
                        this.pieceSelected.seleccionar(false);
                        this.pieceSelected = null;
                        this.moving = true;
                    } else {
                        if (this.currTablero.getFaseActual(this.curPlayer) == Tablero.FASE_ELIMINACION) {
                            this.pieceSelected.seleccionar(false);
                            this.pieceSelected = null;
                            this.moving = true;
                        }
                    }
                } else
                    return false;
                break;

            case Tablero.FASE_ELIMINACION:
                boolean passed = RemovalPhase(label);
                if (passed)
                    nextPlayer();
                else if (this.currTablero.getFaseActual(this.inactivePlayer()) == Tablero.FASE_FIN_JUEGO) {
                    this.Victor = this.curPlayer;
                    this.Loser = this.inactivePlayer();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return true;
                } else
                    return false;
                break;

            default:
                System.out.println("Fase de juego no válida. Saliendo");
                System.exit(1);
                break;
        }

        this.currTablero.updateTabla();
        return true;
    }

    public boolean newAIMove() {
        if (this.curPlayer.esHumano())
            return false;
        AIJugador p = (AIJugador) this.curPlayer;

        int gamephase = this.currTablero.getFaseActual(this.curPlayer);
        if (gamephase == Tablero.FASE_FIN_JUEGO) {
            return true;
        }
        boolean success = true;
        switch (gamephase) {
            case Tablero.FASE_COLOCACION:
                success = p.placeMove();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case Tablero.FASE_MOVIMIENTO:
                success = p.moveMove();
                break;
            case Tablero.FASE_ELIMINACION:
                success = p.remoMove();
                break;
            default:
                System.out.println("Fase de movimiento no válido");
                break;
        }
        this.currTablero.updateTabla();
        return success;
    }

    private boolean selectPiece(String label) {
        Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
        if (t.getPieza() != null && t.getPieza().getOwner() == this.curPlayer) {
            this.pieceSelected = t.getPieza();
            this.pieceSelected.seleccionar(true);
            return true;
        } else {
            return false;
        }
    }

    private boolean RemovalPhase(String label) {
        Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
        boolean v = true;
        if (t.getPieza().getOwner() != this.inactivePlayer()) {
            v = false;
        }

        return this.currTablero.removerPieza(this.inactivePlayer(), t.getPieza().getID(), v);
    }

    private boolean PlacementPhase(String label) {
        Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
        return this.currTablero.lugarPieza(this.curPlayer, 8 - this.curPlayer.getPiezasJugadas(), t.getEtiqueta());
    }

    private boolean MovementPhase(String label) {
        if (this.currTablero.numMovimientosDisponibles(this.curPlayer) == 0 && this.curPlayer.getPuntuacion() > 3) {
            return true;
        } else {
            Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
            return this.currTablero.moverPieza(this.curPlayer, this.pieceSelected.getID(), t.getEtiqueta());
        }
    }

    public Jugador getVictor() {
        return this.Victor;
    }

    public Jugador getLoser() {
        return this.Loser;
    }

    private void nextPlayer() {
        this.curPlayer = this.inactivePlayer();
    }

    private Jugador inactivePlayer() {
        if (this.curPlayer == this.p1)
            return this.p2;
        else
            return this.p1;
    }

    public Jugador getCurrPlayer() {
        return this.curPlayer;
    }

    public void setGamePhase(Integer mode) {
        this.currTablero.setFaseActual(mode);
    }

    public Integer getMode() {
        return this.gameMode;
    }

    public Jugador getJugador1() {
        return this.p1;
    }

    public Jugador getPlayer2() {
        return this.p2;
    }

    public Tablero getTablero() {
        return this.currTablero;
    }

    public void setPlayer1(Jugador p) {
        this.p1 = p;
    }

    public void setPlayer2(Jugador p) {
        this.p2 = p;
    }

    public String getPhaseText() {
        if (this.gameMode == 0 && !this.curPlayer.esHumano()) {
            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_COLOCACION)
                return "La computadora está colocando una pieza en el tablero.";
            else if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_MOVIMIENTO)
                return "La computadora está moviendo una pieza en el tablero.";
            else if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_ELIMINACION)
                return "La computadora está quitando una de tu pieza";
        } else {
            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_COLOCACION)
                return "Coloca una pieza en el tablero";
            else if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_MOVIMIENTO)
                return "Mueve una de tus piezas en el tablero";
            else if (this.currTablero.getFaseActual(this.getCurrPlayer()) == Tablero.FASE_ELIMINACION)
                return "Quita una de las piezas de tu oponente";
        }
        return "";
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void setMoving(boolean mov) {
        this.moving = mov;
    }

    public void setSelecccionado(JugarPieza p) {
        this.pieceSelected = p;
    }

    public void limpiarSeleccionado() {
        this.pieceSelected = null;
    }
}