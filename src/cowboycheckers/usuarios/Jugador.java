package cowboycheckers.usuarios;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * Autor: @FranklinHamer
 * Descripción: Cowboy Checker implementa una clase llamada Jugador la cual nos permite crear los perfiles
 * de jugadores, esta clase implementa un método getPieza la cual nos permite asignar las piezas al jugador nuevo
 *
 * Clase padre de Jugador
 * Usa los paquetes
 * 		java.awt.Color
 * 		java.lang.Integer
 * Atributos Publicos:
 * Atributos Privados:
 * 		MAXPIEZAS
 * 		piezas
 * 		color
 * 		nombre
 *Métodos implementados
 * 		initPiezas()
 * 		getPiezas(): JugarPiezas()
 * 		setColor():void
 * 		getPuntuación() Integer
 * 		getPiezasJugadas(): int
 * 		esHumano() :boolean
 *
 */
import cowboycheckers.modelo.JugarPieza;

public class Jugador {
	private String nombre;
	private Color color;
	private ArrayList<JugarPieza> piezas;
	final Integer MAXPIEZAS = 9;

	public Jugador(String nombre, String color) {
		this.nombre = nombre;
		this.setColor(color);
		this.initPiezas();
	}

	private void initPiezas() {
		this.piezas = new ArrayList<JugarPieza>(MAXPIEZAS);
		for (Integer i = 0; i < MAXPIEZAS; i++) {
			JugarPieza p = new JugarPieza(this.color, this, i);
			this.piezas.add(p);
		}
	}

	public JugarPieza getPieza(int id) {
		for (int i = 0; i < piezas.size(); i++)
			if (piezas.get(i).getID() == id)
				return piezas.get(i);

		return null;
	}

	private void setColor(String color) {
		Color c = null;
		try {
			Field field = Color.class.getField(color.toLowerCase());
			c = (Color) field.get(null);
		} catch (Exception e) {
			c = Color.BLACK; // Not defined
		}

		this.color = c;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Color getColor() {
		return this.color;
	}

	public ArrayList<JugarPieza> getPiezas() {
		return this.piezas;
	}

	public Integer getPuntuacion() {
		int curscore = 0;

		for (int i = 0; i < piezas.size(); i++)
			if (piezas.get(i).getEstado() != JugarPieza.MUERTO)
				curscore++;

		return curscore;
	}

	public int getPiezasJugadas() {
		int played = 0;
		for (int i = 0; i < piezas.size(); i++) {
			if (piezas.get(i).getEstado() != JugarPieza.NOPOSICIONADO)
				played++;
		}

		return played;
	}

	public boolean esHumano() {
		return true;
	}
}
