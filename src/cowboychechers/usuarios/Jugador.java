package cowboycheckers.usuarios;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

import cowboycheckers.modelo.JugarPieza;

public class Jugador {
	private String name;
	private Color color;
	private ArrayList<JugarPieza> pieces;
	final Integer MAXPIECES = 9;
	
	/**
	 * Constructor for the player
	 * class.  Takes in the name
	 * and color choice of the player
	 * @param name
	 * @param color
	 */
	public Jugador(String name, String color){
		this.name = name;
		this.setColor(color);
		this.initPieces();
	}
	
	/**
	 * Init pieces
	 */
	private void initPieces() {
		this.pieces = new ArrayList<JugarPieza>(MAXPIECES);
		for(Integer i=0; i<MAXPIECES; i++){			
			JugarPieza p = new JugarPieza(this.color, this, i);
			this.pieces.add(p);
		}
	}
	
	/***
	 * Will return a certain
	 * game piece given its id
	 * @param id
	 * @return
	 */
	public JugarPieza getPiece(int id)
	{
		for(int i=0; i < pieces.size(); i++)
			if (pieces.get(i).getID() == id)
				return pieces.get(i);
		
		return null;
	}
	
	/**
	 * Will create A color object from a given
	 * string
	 * @param color
	 */
	private void setColor(String color){
		Color c = null;	
		try {
		    Field field = Color.class.getField(color.toLowerCase());
		    c = (Color)field.get(null);
		} catch (Exception e) {
		    c = Color.BLACK; // Not defined
		}
		
		this.color = c;
	}
	
	/***
	 * Will return the players name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/***
	 * Will return the players color
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}
	
	/***
	 * Will return a list of the players pieces
	 * @return
	 */
	public ArrayList<JugarPieza> getPieces() {
		return this.pieces;
	}
	
	/***
	 * Will return the currentScore
	 * of the player
	 * @return
	 */
	public Integer getScore() {
		int curscore = 0;
		
		for(int i=0; i < pieces.size(); i++)
			if (pieces.get(i).getStatus() != JugarPieza.DEAD)
				curscore++;
				
		return curscore;
	}
	
	/**
	 * Will return the number
	 * of pieces played by the player
	 * @return
	 */
	public int getPiecesPlayed() {
		int played = 0;
		for (int i=0; i < pieces.size(); i++)
		{
			if (pieces.get(i).getStatus() != JugarPieza.UNPLACED)
				played++;
		}
		
		return played;
	}

	/**
	 * A function to determine is human
	 * Will be used in pve mode
	 * @return
	 */
	public boolean isHuman() {
		return true;
	}
}
