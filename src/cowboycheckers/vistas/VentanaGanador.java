package cowboycheckers.vistas;

import cowboycheckers.usuarios.Jugador;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class VentanaGanador extends JComponent{


	private static final long serialVersionUID = 4655939260856856256L;
    private JButton JugarBoton;
    private JButton SalirBoton;
    private VentanaPrincipal mw;
    

	public VentanaGanador(VentanaPrincipal mw, Jugador victor, Jugador loser) {
		this.mw = mw;
        JPanel winnerPanel = new JPanel();
        JLabel winnerText = new JLabel();
        JPanel loserPanel = new JPanel();
        JLabel loserText = new JLabel();
        JPanel buttonPanel = new JPanel();
        JugarBoton = new JButton();
        SalirBoton = new JButton();

        setLayout(new java.awt.GridLayout(3, 1));

        winnerPanel.setLayout(new java.awt.BorderLayout());

        winnerText.setFont(new java.awt.Font("Arial", 0, 30));
        winnerText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String s = "Felicidades %s, Buen juego";
        s = String.format(s, victor.getNombre());
        winnerText.setText(s);
        winnerPanel.add(winnerText, java.awt.BorderLayout.CENTER);

        add(winnerPanel);

        loserPanel.setLayout(new java.awt.BorderLayout());

        loserText.setFont(new java.awt.Font("Arial", 0, 30));
        loserText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String t = "Lo siento, %s. Mejor suerte la próxima vez";
        t = String.format(t, loser.getNombre());
        loserText.setText(t);
        loserPanel.add(loserText, java.awt.BorderLayout.CENTER);

        add(loserPanel);

        buttonPanel.setLayout(new java.awt.GridLayout(1, 2));

        JugarBoton.setFont(new java.awt.Font("Arial", 0, 48));
        JugarBoton.setText("Desea Jugar de Nuevo");
        buttonPanel.add(JugarBoton);

        SalirBoton.setFont(new java.awt.Font("Arial", 0, 48));
        SalirBoton.setText("Salir");
        buttonPanel.add(SalirBoton);

        add(buttonPanel);

        JugarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newGame(evt);
            }
        });
        SalirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                end(evt);
            }
        });
        
	}

	private void end(ActionEvent evt) {
		this.mw.quit(evt);		
	}

	private void newGame(ActionEvent evt) {
		this.mw.reset(evt);
	}
}
