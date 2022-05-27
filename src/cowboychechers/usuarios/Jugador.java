package cowboychechers.usuarios;

public class Jugador {
    private String name;

    final Integer MAXPIECES = 9;
    /**
     * Constructor for the player
     * class.  Takes in the name
     * and color choice of the player
     * @param name
     */
    public Jugador(String name){
        this.name = name;

    }
    public String getName() {
        return this.name;
    }

    /**
     * Will return the number
     * of pieces played by the player
     * @return
     */
    public int getPiecesPlayed() {
        int played = 0;

        return played;
    }
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
