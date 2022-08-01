package cowboycheckers.vistas.newJuego;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPVP extends JPanel {

    /**
     * Nueva pantalla PVP para
     * juego MIL
     */

    private static final long serialVersionUID = -4800315689039990773L;
    private NewVentanaJuego ngs;
    private JTextField p1Name;
    private JTextField p2Name;
    private JTextField p1Color;
    private JTextField p2Color;
    private JButton startBut;

    /***
     * Constructor predeterminado para
     * un nuevo juego PVP

     */

    public NewPVP(NewVentanaJuego ngs) {
        JPanel top = new JPanel();
        JLabel message = new JLabel();
        JPanel middle = new JPanel();
        JPanel p1BackPanel = new JPanel();
        JLabel p1NameLabel = new JLabel();
        p1Name = new JTextField();
        JLabel p1ColorLabel = new JLabel();
        p1Color = new JTextField();
        JPanel p2BackPanel = new JPanel();
        JLabel p2NameLabel = new JLabel();
        p2Name = new JTextField();
        JLabel p2ColorLabel = new JLabel();
        p2Color = new JTextField();
        JPanel bot = new JPanel();
        startBut = new JButton();

        setLayout(new java.awt.GridLayout(3, 1, 500, 150));

        top.setLayout(new java.awt.BorderLayout());

        message.setFont(new java.awt.Font("Arial", 1, 36));

        top.add(message, java.awt.BorderLayout.CENTER);

        add(top);

        middle.setLayout(new java.awt.GridLayout());

        p1BackPanel.setLayout(new java.awt.GridLayout(4, 0));

        p1NameLabel.setFont(new java.awt.Font("Arial", 0, 18));
        p1NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1NameLabel.setText("Nombre Jugador 1");
        p1BackPanel.add(p1NameLabel);

        p1Name.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Name);

        p1ColorLabel.setFont(new java.awt.Font("Arial", 0, 18));
        p1ColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1ColorLabel.setText("Elija un color(En Ingles) ");
        p1BackPanel.add(p1ColorLabel);

        p1Color.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Color);

        middle.add(p1BackPanel);

        p2BackPanel.setLayout(new java.awt.GridLayout(4, 0));

        p2NameLabel.setFont(new java.awt.Font("Arial", 0, 18));
        p2NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p2NameLabel.setText("Nombre Jugador 2");
        p2BackPanel.add(p2NameLabel);

        p2Name.setHorizontalAlignment(JTextField.CENTER);
        p2BackPanel.add(p2Name);

        p2ColorLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        p2ColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p2ColorLabel.setText("Elija un color(En Ingles) ");
        p2BackPanel.add(p2ColorLabel);

        p2Color.setHorizontalAlignment(JTextField.CENTER);
        p2BackPanel.add(p2Color);

        middle.add(p2BackPanel);

        add(middle);

        bot.setLayout(new java.awt.BorderLayout());

        startBut.setFont(new java.awt.Font("Arial", 0, 36));
        startBut.setText("Empezar Juego");
        bot.add(startBut, java.awt.BorderLayout.CENTER);

        add(bot);
        startBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                start(evt);
            }
        });

        this.ngs = ngs;
        this.setSize(700, 700);
    }

    /**
     * Determina si la entrada del usuario es válida
     * si es así comienza el juego
     */

    private void start(ActionEvent evt) {
        if (this.getP1Name().length() < 2) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "    Introduce un nombre válido para Jugador 1\r\n (Debe tener más de 1 carácter)");
            return;
        }
        if (this.getP2Name().length() < 2) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "    Introduce un nombre válido para Jugador 2\r\n (Debe tener más de 1 carácter)");
            return;
        }
        if (this.getP1Color().toLowerCase().equalsIgnoreCase(this.getP2Color())) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Elija colores separados para cada jugador");
            return;
        }
        if (this.getP1Color().toLowerCase().equalsIgnoreCase("white")) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Por favor, elija un color diferente al blanco.");
            return;
        }
        if (this.getP2Color().toLowerCase().equalsIgnoreCase("white")) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Por favor, elija un color diferente al blanco.");
            return;
        }
        if (this.getP1Color().length() < 2) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Introduce un color para Jugador 1");
            return;
        }
        int check = checkColor(this.getP1Color());
        if (check == 1)
            return;

        if (this.getP2Color().length() < 2) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Introduce un color para Jugador 2");
            return;
        }
        check = checkColor(this.getP2Color());
        if (check == 1)
            return;

        this.ngs.startGame();
    }

    /**
     * Determina si los jugadores ingresaron
     * el color es válido
     */

    private int checkColor(String c) {
        Color color;
        try {
            java.lang.reflect.Field field = Color.class.getField(c.toLowerCase());
            color = (Color) field.get(this.ngs.getMainWindow());
        } catch (Exception e) {
            color = null; // Not defined
        }
        if (color == null) {
            JOptionPane.showMessageDialog(this.ngs.getMainWindow(), c + " no es un color valido");
            return 1;
        }
        return 0;
    }

    /***
     * Devuelve el nombre ingresado del jugador 1
     */

    public String getP1Name() {
        return this.p1Name.getText();
    }

    /***
     * Devuelve el color ingresado por el jugador 1
     */

    public String getP1Color() {
        return this.p1Color.getText();
    }

    /***
     * Devuelve el nombre ingresado del jugador 2
     */

    public String getP2Name() {
        return this.p2Name.getText();
    }

    /***
     * Devuelve el color ingresado por el jugador 2
     */

    public String getP2Color() {
        return this.p2Color.getText();
    }
}
