package cowboycheckers.modelo;

import java.awt.Color;

import cowboycheckers.usuarios.Jugador;

public class JugarPieza implements Comparable<JugarPieza>
{
	private Color color;
	private Jugador owner;
	private Integer estado;
	private int id;
	private boolean seleccionado;
	private Glow gl;
	private boolean moviendo;
	private Movimiento mv;

	public static final int NOPOSICIONADO = 0;
	public static final int POSICIONADO = 1;
	public static final int MOVIDO = 2;
	public static final int MUERTO = 3;

	public JugarPieza(Color color, Jugador owner){
		this(color, owner, -1);
	}

	public JugarPieza(Color color, Jugador owner, int id)
	{
		this.color = color;
		this.owner = owner;
		this.estado = 0;
		this.id = id;
		this.gl = new Glow();
	}

	public JugarPieza(JugarPieza gamePiece) {
		this.color = gamePiece.color;
		this.owner = gamePiece.owner;
		this.estado = gamePiece.estado;
		this.id = gamePiece.id;
		this.gl = gamePiece.gl;
	}

	public boolean getSeleccionado(){
		return this.seleccionado;
	}

	public void seleccionar(boolean seleccionado){
		this.seleccionado = seleccionado;
		if(!seleccionado){
			this.gl = new Glow();
		}
	}

	public boolean estaVivo()
	{
		if (this.estado != MUERTO)
			return true;
		else
			return false;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Color getColor() {
		return color;
	}

	public Jugador getOwner() {
		return owner;
	}

	@Override
	public int compareTo(JugarPieza otherPiece)
	{
		return this.id - otherPiece.getID();
	}

	public int getID() {
		return id;
	}

	public boolean inPlay()
	{
		if (estado == POSICIONADO || estado == MOVIDO)
			return true;
		else
			return false;
	}

	public void setGl(Glow gl) {
		this.gl = gl;
	}

	public Glow getGl() {
		return gl;
	}

	public boolean isMoviendo() {
		return moviendo;
	}

	public void setMoviendo(boolean moviendo) {
		this.moviendo = moviendo;
	}

	public Movimiento getMv() {
		return mv;
	}

	public void setMv(Movimiento mv) {
		if(mv == null)
			this.mv = null;
		else
			this.mv = mv;
	}
}
