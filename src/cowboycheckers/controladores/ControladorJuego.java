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
    }
  
    public boolean newMovimiento(String label) {
    }
  
    public boolean newAIMove() {
    }
  
    private boolean selectPiece(String label) {
    }
  
    private boolean RemovalPhase(String label) {
    }
  
    private boolean PlacementPhase(String label) {
    }
  
    private boolean MovementPhase(String label) {
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
