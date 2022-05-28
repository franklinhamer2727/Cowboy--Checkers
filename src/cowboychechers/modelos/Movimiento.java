package cowboycheckers.modelo;

import cowboycheckers.vistas.TableroJuego.GamePanel;

public class Movimiento {
	private Localizacion origin;
	private Localizacion dest;
	
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	private float curX;
	private float curY;
	private float dx;
	private float dy;
	
	public Movimiento(Localizacion A, Localizacion B){
		this.origin = A;
		this.setDest(B);
		this.convToCart(A);
		this.convToCart(B);
		this.calcChange();
	}

	private void convToCart(Localizacion loc) {
		char[] labels = Tablero.ALPHABET;
		String[] points = Tablero.BOARDREFERENCE;

		int found = 0;
		for(int i=0; i<24; i++){
			if(loc.equal(labels[i])){
				found = i;
			}
		}
		
		String t[] = points[found].split(",");
		int row = Integer.parseInt(t[0]);
		int col = Integer.parseInt(t[1]);
		
		if(loc == this.origin){
			this.x1 = col * GamePanel.CELL_SIZE+25;
			this.y1 = row * GamePanel.CELL_SIZE+25;
			this.curX = this.x1;
			this.curY = this.y1;
		}
		else{
			this.x2 = col * GamePanel.CELL_SIZE+25;
			this.y2 = row * GamePanel.CELL_SIZE+25;
		}
		
	}

	private void calcChange() {
		this.dx = (this.x2 - this.x1) / 50.f;
		this.dy = (this.y2 - this.y1) / 50.f;
	}

	public float getCurX() {
		return curX;
	}

	public void setCurX(int curX) {
		this.curX = curX;
	}

	public float getCurY() {
		return curY;
	}

	public void setCurY(int curY) {
		this.curY = curY;
	}
	
	public boolean update(){
		this.curX += this.dx;
		this.curY += this.dy;
		
		if(this.dx < 0){
			if(this.curX < this.x2)
				return true;
		}
		else if(this.dx > 0)
			if(this.curX > this.x2)
				return true;
		
		if(this.dy < 0){
			if(this.curY < this.y2)
				return true;
		}
		else if(this.dy > 0)
			if(this.curY > this.y2)
				return true;
		
		return false;	
	}

	public Localizacion getDest() {
		return dest;
	}

	public void setDest(Localizacion dest) {
		this.dest = dest;
	}
	
}
