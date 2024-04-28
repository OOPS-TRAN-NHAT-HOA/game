
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Monster extends Entity {

    protected double Vx = 0;
    protected double Vy = 1;
    private Random rand = new Random();

    protected boolean alive;
    private int hitPoint = 0;

    private Timeline timeline;


    Monster(String path) {
        super(path);
        this.setX(rand.nextDouble(0, 1100));
        this.setY(rand.nextDouble(0, 300));

        this.alive = true;
        this.hitPoint = 10;
        this.setCollidable(true);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.move(screenSize.getWidth(), screenSize.getHeight());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void newVelocity() {
        Vx = rand.nextDouble(-3, 3);
        Vy = rand.nextDouble(-3, 3);
    }

    public void move(double Width, double Height) {
        Timeline VelocityTimeline = new Timeline(new KeyFrame(Duration.millis(rand.nextDouble(2000, 7000)), e -> {
            this.newVelocity();
        }));

        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            double nx = this.getX() + Vx;
            double ny = this.getY() + Vy;
            if (0 <= nx && nx + this.getWidth() < Width) {
                this.setX(nx);
            }
            else {
                Vx = -Vx;
            }
            if (0 <= ny && ny + 3 * this.getHeight() < Height) {
                this.setY(ny);
            }
            else {
                Vy = -Vy;
            }
        }));

        VelocityTimeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setCycleCount(Timeline.INDEFINITE);

        VelocityTimeline.play();
        timeline.play();
    }

    public void takeDamage(int dmg) {
        hitPoint -= dmg;
        if (hitPoint <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
