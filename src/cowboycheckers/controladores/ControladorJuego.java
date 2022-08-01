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
     * <p>
     * Los jugadores son creados por la interfaz gráfica de usuario
     * el modo será determinar si IA
     * está presente
     * <p>
     * Mw se trae simplemente para pasar al tablero
     *
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

    /***
     * La función principal de ControladorJuego
     *
     * Esto verificará en qué fase se encuentra el juego
     * y determinar la acción correcta de ahí en adelante
     *
     * Devolverá falso si ocurre un error
     * Actualizará el estado del tablero.
     * @param label
     * @return
     */
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
            case Tablero.FASE_COLOCACION: //colocación
                // Si la colocación es exitosa, pasa al siguiente jugador.
                if (PlacementPhase(label))
                    nextPlayer();
                else
                    return false;
                break;

            case Tablero.FASE_MOVIMIENTO: //movimiento
                // Siguiente jugador en movimiento exitoso.
                // Devolverá falso si un movimiento da como resultado un molino, para que el jugador no se salte.
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

            case Tablero.FASE_ELIMINACION: //eliminación
                boolean passed = RemovalPhase(label);
                if (passed)
                    nextPlayer();
                else if (this.currTablero.getFaseActual(this.inactivePlayer()) == Tablero.FASE_FIN_JUEGO) {
                    this.Victor = this.curPlayer;
                    this.Loser = this.inactivePlayer();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Bloque catch generado automáticamente
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

    /***
     * Se usa para determinar qué tipo de movimiento debe tomar la IA
     * basado en la fase actual del juego
     * @return
     */
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

    /***
     * Seleccionará la pieza actual y actualizará su
     * estado
     *
     * Se utiliza para que la GUI seleccione una pieza
     * y luego mueve esa pieza
     * @param label
     * @return
     */
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

    /***
     * Determinará la ubicación y pieza a
     * eliminar dada la etiqueta
     *
     * Luego intentará quitar esa pieza
     * @param label
     * @return
     */
    private boolean RemovalPhase(String label) {
        Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
        boolean v = true;
        if (t.getPieza().getOwner() != this.inactivePlayer()) {
            v = false;
        }

        return this.currTablero.removerPieza(this.inactivePlayer(), t.getPieza().getID(), v);
    }

    /***
     * Determinará la ubicación y pieza a
     * lugar dado la etiqueta
     *
     * Luego intentará colocar esa pieza
     * @param label
     * @return
     */
    private boolean PlacementPhase(String label) {
        Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
        return this.currTablero.lugarPieza(this.curPlayer, 8 - this.curPlayer.getPiezasJugadas(), t.getEtiqueta());
    }

    /***
     *Se determinará la ubicación y pieza a mover
     * de la etiqueta dada
     *
     * Luego intentará mover esa pieza
     * @param label
     * @return
     */
    private boolean MovementPhase(String label) {
        if (this.currTablero.numMovimientosDisponibles(this.curPlayer) == 0 && this.curPlayer.getPuntuacion() > 3) {
            return true;
        } else {
            Localizacion t = this.currTablero.getLocacionByEtiqueta(label);
            return this.currTablero.moverPieza(this.curPlayer, this.pieceSelected.getID(), t.getEtiqueta());
        }
    }

    /***
     * Devolverá el ganador del juego
     * Utilizado en la pantalla de victoria de la interfaz gráfica de usuario
     * @return
     */
    public Jugador getVictor() {
        return this.Victor;
    }

    /***
     * Devolverá el perdedor del juego
     * Utilizado en la pantalla de victoria de la interfaz gráfica de usuario
     * @return
     */
    public Jugador getLoser() {
        return this.Loser;
    }

    /**
     * Establecerá el siguiente jugador
     */
    private void nextPlayer() {
        this.curPlayer = this.inactivePlayer();
    }

    /**
     * Regresará el jugador inactivo
     *
     * @return
     */
    private Jugador inactivePlayer() {
        if (this.curPlayer == this.p1)
            return this.p2;
        else
            return this.p1;
    }

    /***
     * Regresará el jugador actual
     * @return
     */
    public Jugador getCurrPlayer() {
        return this.curPlayer;
    }

    /**
     * Establecerá la fase actual
     * al modo pasado
     *
     * @param mode
     */
    public void setGamePhase(Integer mode) {
        this.currTablero.setFaseActual(mode);
    }

    /**
     * Obtener el modo de juego
     * Actualmente sin usar.
     * Se usará para el modo de juego pve
     * cuando se agregue
     *
     * @return
     */
    public Integer getMode() {
        return this.gameMode;
    }

    /***
     * regreso de Jugador 1
     * @return
     */
    public Jugador getJugador1() {
        return this.p1;
    }

    /***
     * regreso de Jugador 1
     * @return
     */
    public Jugador getPlayer2() {
        return this.p2;
    }

    /***
     * Devuelve el tablero actual que se está utilizando
     * @return
     */
    public Tablero getTablero() {
        return this.currTablero;
    }

    /**
     * Establecer p1 en el jugador pasado
     *
     * @param p
     */
    public void setPlayer1(Jugador p) {
        this.p1 = p;
    }

    /**
     * Establecer p2 en el jugador pasado
     *
     * @param p
     */
    public void setPlayer2(Jugador p) {
        this.p2 = p;
    }

    /***
     * Esta función devolverá un mensaje
     * basado en la fase actual del juego
     *
     * Se utiliza en la GUI para mostrar que
     * hacer en la parte superior de la pantalla
     * @return
     */
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