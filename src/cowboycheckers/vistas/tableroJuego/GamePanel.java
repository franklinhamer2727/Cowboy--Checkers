package cowboycheckers.vistas.tableroJuego;

import cowboycheckers.controladores.ControladorJuego;
import cowboycheckers.modelo.Glow;
import cowboycheckers.modelo.JugarPieza;
import cowboycheckers.modelo.Movimiento;
import cowboycheckers.modelo.Tablero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements MouseListener, ActionListener{


	private static final long serialVersionUID = 9076559530700021419L;
	private static final int FILAS = 7;
	private static final int COLS = 7;
	public static final int CELDA_SIZE = 75;
	private BufferedImage board;
	private ControladorJuego gameModel;
	private JuegoTablero gb;

	// agregando como algunos comentarios

	public GamePanel(ControladorJuego game, JuegoTablero gb){
		this.gb = gb;
		this.gameModel = game;
		try {
			this.board = ImageIO.read(new File("resources/nmmBoard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(
                new Dimension(CELDA_SIZE * COLS+50, CELDA_SIZE * FILAS+50));
         this.setBackground(Color.WHITE);
         this.addMouseListener(this);
         
         int delay = 500;
         new Timer(delay, this).start();
	}	
	

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        JugarPieza[][] gameBoard = this.gameModel.getTablero().getTablaArray();
    	drawBackground(g);
        for (int r=0; r<FILAS; r++) {
            for (int c=0; c<COLS; c++) {
            	int x = c * CELDA_SIZE+25;
            	int y = r * CELDA_SIZE+25;

				if(gameBoard[r][c] == null || gameBoard[r][c].getID() == -1){
					continue;
            	}
				
				else if(gameBoard[r][c].isMoviendo()){
					Movimiento mv = gameBoard[r][c].getMv();
					if(!mv.update()){
						g.setColor(gameBoard[r][c].getColor());
	            		g.fillOval((int)mv.getCurX()+10, (int)mv.getCurY()+10, CELDA_SIZE-20, CELDA_SIZE-20);
					}
					else{
						this.gameModel.getTablero().setPieza(r, c, gameBoard[r][c]);
						gameBoard[r][c].setMoviendo(false);
						this.gameModel.setMoving(false);
					}
				}
				
				else if(gameBoard[r][c].getSeleccionado()){
            		g.setColor(gameBoard[r][c].getColor());
            		g.fillOval(x+10, y+10, CELDA_SIZE-20, CELDA_SIZE-20);
            		
            		Glow gl = gameBoard[r][c].getGl();
            		gl.update();
            		int newx = (int) gl.getX();
            		int newy = (int) gl.getY();
            		
            		g.setColor(gl.getColor());
            		g.fillOval(x+newx, y+newy, CELDA_SIZE-(newx*2), CELDA_SIZE-(newy*2));
            			
				}
				
				else{
					g.setColor(gameBoard[r][c].getColor());
        			g.fillOval(x+10, y+10, CELDA_SIZE-20, CELDA_SIZE-20);
				}
            }
        }
	}


	private void drawBackground(Graphics g) {
		g.drawImage(board, 25, 25, 725, 725, 0, 0, 700, 700, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.gameModel.getCurrPlayer().esHumano() && !this.gameModel.isMoving()){
        	this.gameModel.newAIMove();
        }
		this.revalidate();
		this.gb.repaint();
	}	
	
	@Override

	public void mouseClicked(MouseEvent e) {
		int col = e.getX()/CELDA_SIZE;
        int row = e.getY()/CELDA_SIZE;
        if(this.gameModel.isMoving())
        	return;
        if(row < 0 || col < 0 || row > 6 || col > 6)
        	return;
        
        if(!this.convertToLabel(row, col) && this.gameModel.getCurrPlayer().esHumano()){
        	return;
        }
        
        this.revalidate();
        this.gb.repaint();
	}
	

	private boolean convertToLabel(int row, int col) {
		char[] labels = Tablero.ALFABETO;
		String[] points = Tablero.REFERENCIA_TABLA;
		String label = "Z";
		
		for(int i=0; i<24; i++){
			String t[] = points[i].split(",");
			int row2 = Integer.parseInt(t[0]);
			int col2 = Integer.parseInt(t[1]);
			if(row2 == row && col2 == col)
				label = String.valueOf(labels[i]);
		}
		if(label == "Z")
			return false;
			
		if(!this.gameModel.newMovimiento(label)){
			return false;
		}
		
		return true;
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}


}
