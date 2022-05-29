package cowboycheckers.controladores;

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

    public ControladorJuego(Integer mode, Jugador p1, Jugador p2, VentanaPrincipal mw) {
    }        
    
    public int getStatus() {
        return this.currTablero.getFaseActual(this.curPlayer);
    }
  
    public boolean newMovimiento(String label) {
    }
  
    public boolean newAIMove() {
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
        return this.curPlayer == this.p1 ? this.p2 : this.p1;
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
            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 0) {
                return "La computadora está colocando una pieza en el tablero.";
            }

            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 1) {
                return "La computadora está moviendo una pieza en el tablero.";
            }

            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 2) {
                return "La computadora está quitando una de tu pieza";
            }
        } else {
            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 0) {
                return "Coloca una pieza en el tablero";
            }

            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 1) {
                return "Mueve una de tus piezas en el tablero";
            }

            if (this.currTablero.getFaseActual(this.getCurrPlayer()) == 2) {
                return "Quita una de las piezas de tu oponente";
            }
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
