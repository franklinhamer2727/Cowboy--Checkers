package cowboycheckers.modelo;

public class Arista implements Comparable<Arista>
{
	private String etiqueta;
	private Localizacion localizacion1;
	private Localizacion localizacion2;
	private int alineacion;

	public Arista()
	{
		etiqueta = "NA";
		localizacion1 = null;
		localizacion2 = null;
		alineacion = -1;
	}

	public Arista(String etiq, Localizacion loc1, Localizacion loc2, int alineacion)
	{
		this.etiqueta = etiq;
		this.localizacion1 = loc1;
		this.localizacion2 = loc2;
		this.alineacion = alineacion;
	}

	public Arista(Arista e) {}

	public String getEtiqueta()
	{
		return this.etiqueta;
	}

	public int getAlineacion()
	{
		return this.alineacion;
	}

	public boolean tieneUbicacion(Localizacion loc)
	{
		if (localizacion1 == loc || localizacion2 == loc)
			return true;
		else
			return false;
	}

	public Localizacion getOpuesto(Localizacion loc)
	{
		if (loc == localizacion1)
			return localizacion2;
		else if (loc == localizacion2)
			return localizacion1;
		else
			return null;
	}

	@Override
	public int compareTo(Arista arista) {
		return this.getEtiqueta().compareTo(arista.getEtiqueta());
	}

	public String toString()
	{
		return String.format("%s[%s:(%s,%s)]", this.getEtiqueta(), (this.alineacion == 0) ? "|" : "-", this.localizacion1.toString(), this.localizacion2.toString());
	}
}
