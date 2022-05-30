package cowboycheckers.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cowboycheckers.controladores.ControladorJuego;
import cowboycheckers.modelo.Tablero;
import cowboycheckers.usuarios.AIJugador;
import cowboycheckers.usuarios.Jugador;
import cowboycheckers.vistas.tableroJuego.JuegoTablero;
import cowboycheckers.vistas.newJuego.NewVentanaJuego;

public class VentanaPrincipal extends JFrame implements WindowListener{


	private static final long serialVersionUID = 3638794002119631337L;
	private CardLayout cards;

	private JuegoTablero gb;
	private VentanaInicio ws;
	private NewVentanaJuego sd;
	private VentanaGanador vs;
	private ControladorJuego nmm;

	private JPanel cardPanel;

	private JMenuBar jMenuBar1;

	private JMenu jMenu1;
	private JMenu jMenu3;
	private JMenu jMenu2;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem6;
	private JMenuItem jMenuItem7;


	public VentanaPrincipal(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Cowboy Checkers");
		this.setSize(600,600);

		this.createMenuBar();
		this.setLayout(new BorderLayout());

		this.ws = new VentanaInicio(this);

		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);

		cardPanel.add(this.ws, "VentanaInicio");
		cards.show(cardPanel, "VentanaInicio");

		this.add(cardPanel);

		BufferedImage Icon = null;
		try {
			Icon = ImageIO.read(new File("resources/taskBarIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		};

		this.setIconImage(Icon);
		this.setSize(700,700);
		this.setLocation(0,0);
		this.setResizable(false);
		this.setVisible(true);
	}


	private void createMenuBar() {
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		jMenuItem1 = new JMenuItem();
		jMenuItem2 = new JMenuItem();
		jMenuItem3 = new JMenuItem();
		jMenu2 = new JMenu();
		jMenu3 = new JMenu();
		jMenuItem5 = new JMenuItem();
		jMenuItem6 = new JMenuItem();
		jMenuItem7 = new JMenuItem();

		jMenu1.setText("Archivo");
		jMenu2.setText("Editar");
		jMenu3.setText("Ayuda");

		jMenuItem1.setText("Nuevo juego");
		jMenuItem2.setText("Modo trampa");
		jMenuItem3.setText("Salir");
		jMenuItem5.setText("Cómo jugar");
		jMenuItem6.setText("Acerca de");
		jMenuItem7.setText("Opciones de color");

		jMenuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				reset(evt);
			}
		});
		jMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				quit(evt);
			}
		});
		jMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				cheatMode(evt);
			}
		});

		jMenuItem6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				showAbout(evt);
			}
		});
		jMenuItem7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				showColors(evt);
			}
		});

		jMenu1.add(jMenuItem1);
		jMenu2.add(jMenuItem2);
		jMenu1.add(jMenuItem3);
		jMenu3.add(jMenuItem5);
		jMenu3.add(jMenuItem6);
		jMenu3.add(jMenuItem7);

		jMenuBar1.add(jMenu1);
		jMenuBar1.add(jMenu2);
		jMenuBar1.add(jMenu3);

		this.setJMenuBar(jMenuBar1);
	}

	private void showColors(ActionEvent evt) {
		String colors[] = AIJugador.colores;
		JLabel colours = new JLabel();
		String s= "<HTML> ";
		for(int i=0; i<colors.length; i++)
			s = s + "<br>" + colors[i] + "</br>";
		s = s + "</html>";
		colours.setText(s);
		JOptionPane.showMessageDialog(this, colours);

	}


	private void showAbout(ActionEvent evt) {
		JLabel about = new JLabel();
		about.setText("<html>Creado por:<br>"+"" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Franklin Jara</br><br>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diego Vasquez</br><br>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Abraham Berrospi</br><br>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Joel Jimenez</br>" +
				"<br>Version 1.0</html>");
		JOptionPane.showMessageDialog(this, about);
	}





	private void cheatMode(ActionEvent evt) {
		if(this.nmm == null){
			JOptionPane.showMessageDialog(this, "Un juego aún no ha comenzado.");
			return;
		}
		if(this.nmm.getTablero().getFaseActual(this.nmm.getCurrPlayer()) == Tablero.FASE_COLOCACION){
			JOptionPane.showMessageDialog(this, "Espere hasta después de la fase de colocación\r\npara entrar en modo trampa");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(this, "¿Quieres entrar en el modo trampa?");
		if(confirm == 0){
			this.nmm.getTablero().setModoTrampa();
		}
		else
			return;

	}


	public void quit(ActionEvent evt) {
		int confirm = JOptionPane.showConfirmDialog(this, "Estas seguro?");
		if(confirm == 0){
			this.dispose();
			System.exit(NORMAL);
		}
		else
			return;
	}


	public void changeCard(String card){
		this.cards.show(cardPanel, card);
	}


	public void reset(ActionEvent evt){
		if(evt.getSource() == this.jMenuItem1){
			int confirm = JOptionPane.showConfirmDialog(this, "Estas seguro?");
			if(confirm == 0){
				this.clear();

			}
			else
				return;
		}
		this.clear();
	}


	private void clear() {
		this.changeCard("VentanaInicio");
		this.nmm = null;
		this.sd = null;
		this.gb = null;
		this.vs = null;
	}


	public void newGame(Integer mode){
		this.sd = new NewVentanaJuego(this, mode);
		cardPanel.add(this.sd, "NewVentanaJuego");
		this.changeCard("NewVentanaJuego");
		this.setSize(700,700);
		this.setLocation(0,0);
	}


	public void startGame(Jugador p1, Jugador p2, Integer mode){
		this.nmm = new ControladorJuego(mode, p1, p2, this);
		this.nmm.setPlayer1(p1);
		this.nmm.setPlayer2(p2);
		this.gb = new JuegoTablero(nmm, this);
		cardPanel.add(this.gb, "JuegoTablero");
		this.changeCard("JuegoTablero");
		this.setSize(700,700);
		this.setLocation(0,0);
	}


	public void showEnd() {
		this.vs = new VentanaGanador(this, this.nmm.getVictor(), this.nmm.getLoser());
		cardPanel.add(this.vs, "EndGame");
		this.changeCard("EndGame");
		this.setLocationRelativeTo(null);
		this.setSize(700,700);
		this.setLocation(0,0);
	}

	public JuegoTablero getTableroJuego() {
		return this.gb;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.dispose();
		System.exit(0);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
