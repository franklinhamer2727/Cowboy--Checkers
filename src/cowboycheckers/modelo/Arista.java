package cowboycheckers.modelo;

public class Arista implements Comparable<Arista> {
    private String etiqueta;
    private Localizacion localizacion1;
    private Localizacion localizacion2;
    private int alineacion;

    /***
     * Constructor por defecto
     */
    public Arista() {
        etiqueta = "NA";
        localizacion1 = null;
        localizacion2 = null;
        alineacion = -1;
    }

    /***
     * Constructor de la clase Arista que utiliza los parámetros pasados
     * @param etiq
     * @param loc1
     * @param loc2
     * @param alineacion
     */
    public Arista(String etiq, Localizacion loc1, Localizacion loc2, int alineacion) {
        this.etiqueta = etiq;
        this.localizacion1 = loc1;
        this.localizacion2 = loc2;
        this.alineacion = alineacion;
    }

    public Arista(Arista e) {
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }

    /***
     * Retorna la alineacion de la arista
     * @return
     */
    public int getAlineacion() {
        return this.alineacion;
    }

    /***
     * Determina si la localización dada esta contenida en uno de
     * los dos extremos
     * @param loc
     * @return
     */
    public boolean tieneUbicacion(Localizacion loc) {
        if (localizacion1 == loc || localizacion2 == loc)
            return true;
        else
            return false;
    }

    /***
     * Retorna la localización opuesta a la localización dada
     * @param loc
     * @return
     */
    public Localizacion getOpuesto(Localizacion loc) {
        if (loc == localizacion1)
            return localizacion2;
        else if (loc == localizacion2)
            return localizacion1;
        else
            return null;
    }

    /***
     * Compora las etiquetas de las aristas para ordenarlos
     */
    @Override
    public int compareTo(Arista arista) {
        return this.getEtiqueta().compareTo(arista.getEtiqueta());
    }

    /***
     * Retorna un string a ser mostrado por el objeto
     */
    public String toString() {
        return String.format("%s[%s:(%s,%s)]", this.getEtiqueta(), (this.alineacion == 0) ? "|" : "-", this.localizacion1.toString(), this.localizacion2.toString());
    }
}
