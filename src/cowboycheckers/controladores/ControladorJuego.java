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
    }
  
    public Jugador getLoser() {
    }
  
    private void nextPlayer() {
    }
  
    private Jugador inactivePlayer() {
    }
  
    public Jugador getCurrPlayer() {
    }
    
    public void setGamePhase(Integer mode) {
    }
  
    public Integer getMode() {
    }
  
    public Jugador getJugador1() {
    }  

    public Jugador getPlayer2() {
    }
  
    public Tablero getTablero() {
    }
  
    public void setPlayer1(Jugador p) {
    }  

    public void setPlayer2(Jugador p) {
    }  

    public String getPhaseText() {
    }
  
    public boolean isMoving() {
    }
  
    public void setMoving(boolean mov) {
    }
  
    public void setSelecccionado(JugarPieza p) {
    }
  
    public void limpiarSeleccionado() {
    }    
}
