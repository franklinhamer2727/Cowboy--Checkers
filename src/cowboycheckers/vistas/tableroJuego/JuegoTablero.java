package cowboycheckers.vistas.tableroJuego;

import cowboycheckers.controladores.ControladorJuego;
import cowboycheckers.modelo.Tablero;
import cowboycheckers.vistas.VentanaPrincipal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JuegoTablero extends JPanel implements ActionListener {


    private static final long serialVersionUID = 2364448613335062368L;
    private ControladorJuego gameModel;
    private VentanaPrincipal mw;
    private JugadorPanel p1;
    private JugadorPanel p2;
    private GamePanel gp;
    private JLabel p1Name;
    private JLabel p2Name;
    private JLabel p1Score;
    private JLabel p2Score;
    private JLabel currentPlayer;
    private JLabel error;
    private JPanel topPanel;
    private JPanel scorePanel;
    private Timer timer;
    private BufferedImage board;

    public JuegoTablero(ControladorJuego nmm, VentanaPrincipal mw) {
        this.gameModel = nmm;
        this.mw = mw;
        this.p1 = new JugadorPanel(nmm.getJugador1());
        this.p2 = new JugadorPanel(nmm.getPlayer2());
        this.gameModel = nmm;
        this.gp = new GamePanel(this.gameModel, this);

        this.setSize(950, 800);

        this.topPanel = new JPanel();
        this.scorePanel = new JPanel();
        try {
            this.board = ImageIO.read(new File("resources/boardBG.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        p1Name = new JLabel(this.gameModel.getJugador1().getNombre());
        p2Name = new JLabel(this.gameModel.getPlayer2().getNombre());
        p1Score = new JLabel("Score: " + String.valueOf(this.gameModel.getJugador1().getPuntuacion()));
        p2Score = new JLabel("Score: " + String.valueOf(this.gameModel.getJugador1().getPuntuacion()));
        currentPlayer = new JLabel(this.gameModel.getCurrPlayer().getNombre().trim() + ", " + this.gameModel.getPhaseText().trim());
        error = new JLabel("               ");

        p1Name.setFont(new Font("Arial", Font.BOLD, 24));
        p2Name.setFont(new Font("Arial", Font.BOLD, 24));
        p1Name.setForeground(nmm.getJugador1().getColor());
        p2Name.setForeground(nmm.getPlayer2().getColor());
        currentPlayer.setFont(new Font("Arial", Font.BOLD, 30));
        error.setFont(new Font("Arial", Font.BOLD, 26));
        error.setForeground(Color.BLACK);
        p1Name.setHorizontalAlignment(JLabel.CENTER);
        currentPlayer.setHorizontalAlignment(JLabel.CENTER);
        p2Name.setHorizontalAlignment(JLabel.CENTER);
        p1Score.setHorizontalAlignment(JLabel.CENTER);
        p2Score.setHorizontalAlignment(JLabel.CENTER);
        error.setHorizontalAlignment(JLabel.CENTER);

        this.topPanel.setLayout(new BorderLayout());
        this.topPanel.add(this.p1Name, BorderLayout.WEST);
        this.topPanel.add(this.p2Name, BorderLayout.EAST);
        this.topPanel.add(this.currentPlayer, BorderLayout.NORTH);


        this.scorePanel.setLayout(new BorderLayout());
        this.scorePanel.add(p1Score, BorderLayout.WEST);
        this.scorePanel.add(p2Score, BorderLayout.EAST);
        this.scorePanel.add(this.error, BorderLayout.CENTER);
        this.topPanel.add(this.scorePanel, BorderLayout.SOUTH);

        this.topPanel.setBackground(Color.WHITE);
        this.scorePanel.setBackground(Color.WHITE);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(this.p1, BorderLayout.WEST);
        this.add(this.gp, BorderLayout.CENTER);
        this.add(this.p2, BorderLayout.EAST);

        this.topPanel.setOpaque(false);
        this.scorePanel.setOpaque(false);
        this.gp.setOpaque(false);
        this.p1.setOpaque(false);
        this.p2.setOpaque(false);

        this.setVisible(true);
        this.setOpaque(false);
        int delay = 1500;
        this.timer = new Timer(delay, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.gameModel.getStatus() == Tablero.FASE_FIN_JUEGO) {
            this.mw.showEnd();
        }

        if (this.gameModel.getCurrPlayer().esHumano())
            this.currentPlayer.setText(this.gameModel.getCurrPlayer().getNombre() + ", " + this.gameModel.getPhaseText());
        else
            this.currentPlayer.setText(this.gameModel.getPhaseText());

        this.p1Score.setText("Score: " + String.valueOf(this.gameModel.getJugador1().getPuntuacion()));
        this.p2Score.setText("Score: " + String.valueOf(this.gameModel.getPlayer2().getPuntuacion()));

        this.p1.repaint();
        this.p2.repaint();
        this.gp.repaint();
        int steps = 0;
        while (this.gameModel.isMoving() && steps < 10) {
            this.gp.repaint();
            drawBackground(g);
            steps++;
        }

        this.gp.repaint();
        drawBackground(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(board, 0, 0, 700, 700, 0, 0, 700, 700, null);
    }


    public void setError(String s, int delay) {
        this.timer.setDelay(delay);
        this.error.setText(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.error.setText("                 ");
        this.timer.stop();
    }

    public void iniciarTimer() {
        this.timer.start();
    }
}
