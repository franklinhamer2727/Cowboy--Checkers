package cowboycheckers.modelo;

public class Localizacion implements Comparable<Localizacion> {
	
	private String label;
	private JugarPieza piece;
	
	/***
	 * Default location constructor
	 */
	public Localizacion()
	{
		label = "NA";
		piece = null;
	}
	
	/***
	 * Creates a location with the passed
	 * in name
	 * @param name
	 */
	public Localizacion(String name)
	{
		label = name;
		piece = null;
	}

	public Localizacion(Localizacion l) {
		this.label = l.label;
	}

	/***
	 * returns the locations label
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Determines if the location contains
	 * a piece
	 * @param inPiece
	 * @return
	 */
	public boolean ContainsPiece(JugarPieza inPiece)
	{
		if (this.getPiece() == inPiece)
			return true;
		else
			return false;
		
	}
	
	/***
	 * Removes the piece from the location
	 */
	public void RemovePiece()
	{
		this.setPiece(null);
	}
	
	/***
	 * Changes the label of the location
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean equal(char Label){
		if(this.label.charAt(0) == Label)
			return true;
		return false;
	}

	/***
	 * Returns the piece at the location
	 * @return
	 */
	public JugarPieza getPiece() {
		return piece;
	}

	/***
	 * Changes the piece at location
	 * to the passed in piece
	 * @param piece
	 */
	public void setPiece(JugarPieza piece) {
		this.piece = piece;
	}

	/***
	 * Compares to locations
	 */
	@Override
	public int compareTo(Localizacion otherLoc) {
		return this.getLabel().compareTo(otherLoc.getLabel());
	}
	
	public String toString()
	{
		int id = (this.piece == null) ? -1 : this.piece.getID();
		return this.label + "<" + id + ">";
	}
	
	

}
