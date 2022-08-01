package cowboycheckers.vistas.newJuego;

import javax.swing.JPanel;

import cowboycheckers.usuarios.AIJugador;
import cowboycheckers.usuarios.Jugador;
import cowboycheckers.vistas.VentanaPrincipal;

public class NewVentanaJuego extends JPanel {

    /**
     * Nueva pantalla de juego
     * para Juego Mil
     */

    private static final long serialVersionUID = 2841820904085389198L;
    private VentanaPrincipal mw;
    private NewPVP pvp;
    private NewPVE pve;
    private Integer mode;
    private AIJugador computer;

    /***
     * Constructor para NewGameScreen
     *
     * Determina si el juego es PVE o PVP
     * de pasado en modo
     *
     * Crea AI para el juego si es necesario
     */

    public NewVentanaJuego(VentanaPrincipal mw, Integer mode) {
        this.setSize(700, 700);
        this.mw = mw;
        this.mode = mode;
        if (mode == 0) {
            this.pve = new NewPVE(this);
            this.add(this.pve);
        } else {
            this.pvp = new NewPVP(this);
            this.add(this.pvp);
        }
        this.setVisible(true);
        this.computer = new AIJugador("PC", "Black");
    }

    /***
     * Devuelve el objeto ventana principal
     * a las clases NewPVP y NewPVE
     *
     * Se usa solo para imprimir el diálogo en la pantalla principal
     */

    public VentanaPrincipal getMainWindow() {
        return this.mw;
    }

    /***
     * Comenzará el juego después
     * recibir una buena entrada del usuario
     */

    public void startGame() {
        if (this.mode == 0) {
            Jugador p1 = new Jugador(this.pve.getP1Name(), this.pve.getP1Color());
            this.mw.startGame(p1, this.computer, this.mode);
        } else if (this.mode == 1) {
            Jugador p1 = new Jugador(this.pvp.getP1Name(), this.pvp.getP1Color());
            Jugador p2 = new Jugador(this.pvp.getP2Name(), this.pvp.getP2Color());
            this.mw.startGame(p1, p2, this.mode);
        }
    }

    /**
     * Devolverá el componente AI del juego.
     * Se crea antes para evitar choques de color.
     * con el jugador
     */

    public AIJugador getComputer() {
        return computer;
    }
}
