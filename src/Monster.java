import java.util.*;

import javafx.scene.canvas.GraphicsContext;
public class Monster extends Entity {

    protected double Vx = 1;
    protected double Vy = 1;
    protected boolean alive;

    private Random rand = new Random();

    Monster(String path) {
        super(path);
        this.xPos = rand.nextDouble(50, 1200);
        this.yPos = rand.nextDouble(30, 400);

        this.setCollidable(true);

        this.alive = true;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), this.xPos, this.yPos, this.getWidth(), this.getHeight());
    }

    public void update() {
        this.setX(this.getX() + Vx);
        this.setY(this.getY() + Vy);
    }
}
