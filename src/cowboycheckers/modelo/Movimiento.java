package cowboycheckers.modelo;

import cowboycheckers.vistas.tableroJuego.GamePanel;

public class Movimiento {
    private Localizacion origen;
    private Localizacion destino;

    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float curX;
    private float curY;
    private float dx;
    private float dy;

    public Movimiento(Localizacion A, Localizacion B) {
        this.origen = A;
        this.setDestino(B);
        this.convToCart(A);
        this.convToCart(B);
        this.calcCambio();
    }

    private void convToCart(Localizacion loc) {
        char[] labels = Tablero.ALFABETO;
        String[] puntos = Tablero.REFERENCIA_TABLA;

        int buscado = 0;
        for (int i = 0; i < 24; i++) {
            if (loc.equal(labels[i])) {
                buscado = i;
            }
        }

        String t[] = puntos[buscado].split(",");
        int row = Integer.parseInt(t[0]);
        int col = Integer.parseInt(t[1]);

        if (loc == this.origen) {
            this.x1 = col * GamePanel.CELDA_SIZE + 25;
            this.y1 = row * GamePanel.CELDA_SIZE + 25;
            this.curX = this.x1;
            this.curY = this.y1;
        } else {
            this.x2 = col * GamePanel.CELDA_SIZE + 25;
            this.y2 = row * GamePanel.CELDA_SIZE + 25;
        }

    }

    private void calcCambio() {
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

    public boolean update() {
        this.curX += this.dx;
        this.curY += this.dy;

        if (this.dx < 0) {
            if (this.curX < this.x2)
                return true;
        } else if (this.dx > 0)
            if (this.curX > this.x2)
                return true;

        if (this.dy < 0) {
            if (this.curY < this.y2)
                return true;
        } else if (this.dy > 0)
            if (this.curY > this.y2)
                return true;

        return false;
    }

    public Localizacion getDestino() {
        return destino;
    }

    public void setDestino(Localizacion destino) {
        this.destino = destino;
    }

}
