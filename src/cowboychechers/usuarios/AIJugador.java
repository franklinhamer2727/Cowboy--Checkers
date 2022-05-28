package cowboycheckers.usuarios;

import java.util.Random;

import cowboycheckers.Controladores.ControladorJugador;
import cowboycheckers.modelo.Tablero;
import cowboycheckers.modelo.JugarPieza;
import cowboycheckers.modelo.Localizacion;

public class AIJugador extends Jugador {
	
	public static final String[] colors = {"Black", "Red", "Blue", "Gray", "Green"};
	private ControladorJugador nmm;
	public static Random R;
	/***
	 * Default constructor for an
	 * AI player
	 * @param name
	 * @param color
	 */
	public AIJugador(String name, String color) {
		super(name, chooseRandomColor(color));
		
	}
	
	/***
	 * Will choose a random color for the AI
	 * @param color
	 * @return
	 */
	private static String chooseRandomColor(String color) {
		R = new Random();
		int i = R.nextInt() % 5;
		while(i < 0 || i > 4)
			i = R.nextInt() % 5;
		if(AIJugador.colors[i] != null){
			return AIJugador.colors[i].toLowerCase();
		}
		return color;
	}
	
	
	public String newMove(){
		return "A";
	}
	

	/**
	 * Will be in used in PVE mode
	 * 
	 * Tells the model that this is a
	 * computer
	 */
	@Override
	public boolean isHuman(){
		return false;
	}

	public boolean placeMove() {
		int i = R.nextInt() % 24;
		while(i < 0 || i > 24){
			i = R.nextInt() % 24;
		}
		char move = Tablero.ALPHABET[i];
		while(!this.nmm.newMove(String.valueOf(move))){
			i = R.nextInt() % 24;
			while(i < 0 || i > 24){
				i = R.nextInt() % 24;
			}
			move = Tablero.ALPHABET[i];;
		}
		return true;
	}

	public boolean moveMove() {		
		for(JugarPieza p : this.getPieces()){
			Localizacion t = this.nmm.getBoard().GetPieceLocation(p);

			for(char lab: Tablero.ALPHABET){
				Localizacion newLoc = this.nmm.getBoard().GetLocationByLabel(String.valueOf(lab));
				if(t == newLoc)
					continue;
				if(!newLoc.ContainsPiece(null))
					continue;
				if(this.nmm.getBoard().AreNeighbors(t, newLoc)){
					this.nmm.setSelected(p);
					if(this.nmm.newMove(newLoc.getLabel())){
						return true;
					}
					else{
						this.nmm.clearSelected();
					}
				}
				
			}
		}
		return false;
	}

	public boolean remoMove() {
		Jugador p = this.nmm.getPlayer1();
		for(JugarPieza gp: p.getPieces()){
			if(gp.IsAlive() && gp.getStatus() != JugarPieza.UNPLACED){
				if(this.nmm.newMove(this.nmm.getBoard().GetPieceLocation(gp).getLabel())){
					return true;
				}
			}
		}
		return false;
	}

	public void setNmm(ControladorJugador nmm) {
		this.nmm = nmm;
	}

	public ControladorJugador getNmm() {
		return nmm;
	}

}
