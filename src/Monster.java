
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Monster extends Entity {

    protected double Vx = 0;
    protected double Vy = 1;
    protected boolean alive;
    private Timeline timeline;

    private Random rand = new Random();

    Monster(String path) {
        super(path);
        this.setX(rand.nextDouble(0, 1100));
        this.setY(rand.nextDouble(0, 300));

        this.setCollidable(true);
        this.getColliBox().setVisible(false);
        this.alive = true;
        this.move(1200, 720);
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
        Timeline VelocityTimeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            this.newVelocity();
        }));

        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            double nx = this.getX() + Vx;
            double ny = this.getY() + Vy;
            if (0 <= nx && nx < Width) {
                this.setX(nx);
            }
            if (0 <= ny && ny + this.getHeight() + 20 < Height) {
                this.setY(ny);
            }
        }));

        VelocityTimeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setCycleCount(Timeline.INDEFINITE);

        VelocityTimeline.play();
        timeline.play();
    }
}
