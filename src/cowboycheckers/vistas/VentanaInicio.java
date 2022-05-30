package cowboycheckers.vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaInicio extends JComponent{

    private static final long serialVersionUID = 5219597153026471980L;
    private VentanaPrincipal mw;
    private JLabel welcome;
    private JButton pve;
    private JButton pvp;
    private JPanel top;
    private JPanel bot;


    public VentanaInicio(VentanaPrincipal mw){
        this.mw = mw;

        welcome = new JLabel();
        pve = new JButton();
        pvp = new JButton();

        welcome.setFont(new Font("Arial", 1, 30));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        bot = new JPanel();
        pve = new JButton();
        top = new JPanel();
        welcome = new JLabel();

        pvp = new JButton();

        setLayout(new GridLayout(2, 2));

        top.setLayout(new BorderLayout());

        welcome.setFont(new Font("new times roman", 1, 40));
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome.setText("Cowboy Checkers");
        top.add(welcome, BorderLayout.CENTER);

        add(top);

        bot.setLayout(new GridLayout());

        pve.setFont(new Font("Arial", 1, 24));
        pve.setText(" Jugador 1 vs PC");
        bot.add(pve);

        pvp.setFont(new Font("Arial", 1, 24));
        pvp.setText("Jugador VS Jugador");

        pve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                CambioVentana(evt);
            }
        });
        pvp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                CambioVentana(evt);
            }
        });

        bot.add(pvp);

        add(bot);
    }

    private void CambioVentana(ActionEvent evt) {
        if((JButton)evt.getSource() == this.pve)
            this.mw.newGame(0);
        else if((JButton)evt.getSource() == this.pvp)
            this.mw.newGame(1);
    }
}
