package cowboycheckers.modelo;

import java.awt.Color;

public class Glow {
    private double x;
    private double y;
    private Color c;
    private boolean decrecer;

    public Glow() {
        this.x = 35;
        this.y = 35;
        this.c = Color.WHITE;
        this.decrecer = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return c;
    }

    public void update() {
        checkDirection();
        if (this.decrecer) {
            this.x -= .1;
            this.y -= .1;
        } else {
            this.x += .1;
            this.y += .1;
        }
    }

    private void checkDirection() {
        if (this.x <= 12 || this.y <= 12)
            this.decrecer = false;
        else if (this.x >= 35 || this.y >= 35)
            this.decrecer = true;
    }
}
