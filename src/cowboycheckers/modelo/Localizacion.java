package cowboycheckers.modelo;

public class Localizacion implements Comparable<Localizacion> {

	private String etiqueta;
	private JugarPieza pieza;

	public Localizacion()
	{
		etiqueta = "NA";
		pieza = null;
	}

	public Localizacion(String name)
	{
		etiqueta = name;
		pieza = null;
	}

	public Localizacion(Localizacion l) {
		this.etiqueta = l.etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public boolean ContainsPieza(JugarPieza pieza)
	{
		if (this.getPieza() == pieza)
			return true;
		else
			return false;

	}

	public void RemovePieza()
	{
		this.setPieza(null);
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public boolean equal(char etiqueta){
		if(this.etiqueta.charAt(0) == etiqueta)
			return true;
		return false;
	}

	public JugarPieza getPieza() {
		return pieza;
	}

	public void setPieza(JugarPieza pieza) {
		this.pieza = pieza;
	}

	@Override
	public int compareTo(Localizacion loc) {
		return this.getEtiqueta().compareTo(loc.getEtiqueta());
	}

	public String toString()
	{
		int id = (this.pieza == null) ? -1 : this.pieza.getID();
		return this.etiqueta + "<" + id + ">";
	}

}
