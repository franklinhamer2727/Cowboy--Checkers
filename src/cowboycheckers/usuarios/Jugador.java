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

	public Jugador(String name, String color){
		this.name = name;
		this.setColor(color);
		this.initPieces();
	}

	private void initPieces() {
		this.pieces = new ArrayList<JugarPieza>(MAXPIECES);
		for(Integer i=0; i<MAXPIECES; i++){
			JugarPieza p = new JugarPieza(this.color, this, i);
			this.pieces.add(p);
		}
	}

	public JugarPieza getPiece(int id)
	{
		for(int i=0; i < pieces.size(); i++)
			if (pieces.get(i).getID() == id)
				return pieces.get(i);

		return null;
	}

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

	public String getName() {
		return this.name;
	}

	public Color getColor() {
		return this.color;
	}

	public ArrayList<JugarPieza> getPieces() {
		return this.pieces;
	}

	public Integer getScore() {
		int curscore = 0;

		for(int i=0; i < pieces.size(); i++)
			if (pieces.get(i).getEstado() != JugarPieza.MUERTO)
				curscore++;

		return curscore;
	}

	public int getPiecesPlayed() {
		int played = 0;
		for (int i=0; i < pieces.size(); i++)
		{
			if (pieces.get(i).getEstado() != JugarPieza.NOPOSICIONADO)
				played++;
		}

		return played;
	}

	public boolean isHuman() {
		return true;
	}
}
