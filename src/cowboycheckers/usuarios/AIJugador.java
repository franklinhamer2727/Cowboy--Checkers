package cowboycheckers.usuarios;

import java.util.Random;

import cowboycheckers.controladores.ControladorJuego;
import cowboycheckers.modelo.Tablero;
import cowboycheckers.modelo.JugarPieza;
import cowboycheckers.modelo.Localizacion;

/**
 * Autor: @FranklinHamer
 * Descripción: Cowboy Checker implementa una clase llamada IaJugador la cual
 * desarrolla
 * una funcionalidad de simular el comportamiento de un jugador virtual para el
 * inicio de
 * una nueva partida
 * clase HIJA IAJugador de Jugador
 * Clase que es llamada desde Controlador llamado ControladorJuego
 * Usa los paquetes
 * java.util.Random()
 * java.lang.String()
 * Atributos:
 * name
 * color
 * Métodos que retornan valores Boleanos
 * esHumano()
 * placeMove()
 * moveMove()
 * remoMove()
 * Método que captura nuevo movimiento:
 * newMovimiento(): String
 * Peticiones get and set del controlador son:
 * setControlador(): void
 * getControladorJuego() ControladorJuego
 */
public class AIJugador extends Jugador {

    public static final String[] colores = {"Black", "Red", "Blue", "Gray", "Green"};
    public static Random R;
    private ControladorJuego controladorJuego;


    public AIJugador(String name, String color) {
        super(name, escogerColorAleatorio(color));

    }

    public String newMovimiento() {
        return "A";
    }

    @Override
    public boolean esHumano() {
        return false;
    }

    public boolean placeMove() {
        int i = R.nextInt() % 24;
        while (i < 0 || i > 24) {
            i = R.nextInt() % 24;
        }
        char move = Tablero.ALFABETO[i];
        while (!this.controladorJuego.newMovimiento(String.valueOf(move))) {
            i = R.nextInt() % 24;
            while (i < 0 || i > 24) {
                i = R.nextInt() % 24;
            }
            move = Tablero.ALFABETO[i];
            ;
        }
        return true;
    }

    public boolean moveMove() {
        for (JugarPieza p : this.getPiezas()) {
            Localizacion t = this.controladorJuego.getTablero().getLocalizacionPieza(p);

            for (char lab : Tablero.ALFABETO) {
                Localizacion newLoc = this.controladorJuego.getTablero().getLocacionByEtiqueta(String.valueOf(lab));
                if (t == newLoc)
                    continue;
                if (!newLoc.contienePieza(null))
                    continue;
                if (this.controladorJuego.getTablero().esVecino(t, newLoc)) {
                    this.controladorJuego.setSelecccionado(p);
                    if (this.controladorJuego.newMovimiento(newLoc.getEtiqueta())) {
                        return true;
                    } else {
                        this.controladorJuego.limpiarSeleccionado();
                    }
                }

            }
        }
        return false;
    }

    public boolean remoMove() {
        Jugador p = this.controladorJuego.getJugador1();
        for (JugarPieza gp : p.getPiezas()) {
            if (gp.estaVivo() && gp.getEstado() != JugarPieza.NOPOSICIONADO) {
                if (this.controladorJuego
                        .newMovimiento(this.controladorJuego.getTablero().getLocalizacionPieza(gp).getEtiqueta())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setControlador(ControladorJuego controladorJuego) {
        this.controladorJuego = controladorJuego;
    }

    public ControladorJuego getControladorJuego() {
        return controladorJuego;
    }

    private static String escogerColorAleatorio(String color) {
        R = new Random();
        int i = R.nextInt() % 5;
        while (i < 0 || i > 4)
            i = R.nextInt() % 5;
        if (AIJugador.colores[i] != null) {
            return AIJugador.colores[i].toLowerCase();
        }
        return color;
    }
}
