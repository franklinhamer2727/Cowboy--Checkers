package cowboycheckers.vistas.tableroJuego;

import cowboycheckers.usuarios.Jugador;

import javax.swing.*;
import java.awt.*;

public class JugadorPanel extends JPanel{


	private static final long serialVersionUID = -3043526960369929645L;
	private static final int COLS = 1;
	private static final int CELDA_SIZE = 59;
	private Jugador player;



	public JugadorPanel(Jugador p1) {
		this.player = p1;

		this.setPreferredSize(
                new Dimension(CELDA_SIZE * COLS, 525));
         this.setBackground(Color.WHITE);
	}


	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer pieces = 9 - this.player.getPiezasJugadas();
        for (int r=0; r<pieces; r++) {
            for (int c=0; c<COLS; c++) {
                int x = c * CELDA_SIZE;
                int y = r * CELDA_SIZE + 30;

            	g.setColor(this.player.getColor());
            	g.fillOval(x+10, y+10, CELDA_SIZE-20, CELDA_SIZE-20);
            }
        }
	}

}
