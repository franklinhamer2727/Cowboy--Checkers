package cowboycheckers.main;

import cowboycheckers.vistas.VentanaPrincipal;

import java.awt.*;

public class CowboyCheckers {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
}
