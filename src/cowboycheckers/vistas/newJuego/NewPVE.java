package cowboycheckers.vistas.newJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class NewPVE extends JPanel {

	private static final long serialVersionUID = 6048218193452708272L;
	private NewVentanaJuego ngs;
	private JTextField p1Nombre;
	private JTextField p1Color;
	private JButton startBut;


	public NewPVE(NewVentanaJuego ngs) {
		this.ngs = ngs;
        JPanel top = new JPanel();
        JLabel message = new JLabel();
        JPanel middle = new JPanel();
        JPanel p1BackPanel = new JPanel();
        JLabel p1NameLabel = new JLabel();
        p1Nombre = new JTextField();
        JLabel p1ColorLabel = new JLabel();
        p1Color = new JTextField();
        JPanel bot = new JPanel();
        startBut = new JButton();

        setLayout(new java.awt.GridLayout(3, 1, 0, 150));

        top.setLayout(new java.awt.BorderLayout());

        top.add(message, java.awt.BorderLayout.CENTER);

        add(top);

        middle.setLayout(new java.awt.GridLayout());

        p1BackPanel.setLayout(new java.awt.GridLayout(4, 0));

        p1NameLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        p1NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1NameLabel.setText("Nombre Jugador 1: ");
        p1BackPanel.add(p1NameLabel);

        p1Nombre.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Nombre);

        p1ColorLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        p1ColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1ColorLabel.setText("Elija un color (En ingles)");
        p1BackPanel.add(p1ColorLabel);

        p1Color.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Color);

        middle.add(p1BackPanel);

        add(middle);

        bot.setLayout(new java.awt.BorderLayout());

        startBut.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        startBut.setText("Empezar juego");
        bot.add(startBut, java.awt.BorderLayout.CENTER);

        add(bot);
        startBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                start(evt);
            }
        });
        this.setSize(700,700);
	}


	private void start(ActionEvent evt){
		if(this.getP1Name().length() < 1){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), " Introduce un nombre válido para Jugador 1\r\n (Debe tener al menos 1 carácter)");
			return;
		}
		if(this.getP1Color().length() < 1){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Introduce un color para Jugador 1");
			return;
		}
		int check = checkColor(this.getP1Color());
		if(check == 1)
			return;
		if(getColor(this.getP1Color()).equals(this.ngs.getComputer().getColor())){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Elija un color diferente al que eligió la computadora");
			return;
		}
		if(this.getP1Color().equalsIgnoreCase("white")){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Por favor, elija un color diferente al blanco.");
			return;
		}
		this.ngs.startGame();
	}
	

	private int checkColor(String c) {
		Color color;
		try {
		    Field field = Color.class.getField(c.toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		if(color == null){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), c + " No es un color valido");
			return 1;
		}
		if(color == Color.white){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Por favor elija otro color");
			return 1;
		}
		return 0;
	}
	
	public Color getColor(String color){
		Color c = null;	
		try {
		    Field field = Color.class.getField(color.toLowerCase());
		    c = (Color)field.get(null);
		} catch (Exception e) {
		    c = null;
		}
		
		return c;
	}


	public String getP1Name() {
		return this.p1Nombre.getText();
	}
	

	public String getP1Color() {
		return this.p1Color.getText();
	}

}
