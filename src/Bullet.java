import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Bullet extends Entity {
    private double Vx;
    private double Vy;

    Bullet(String path) {
        super(path);
        this.setCollidable(true);
    }
    
    public void setSpeed(double vx, double vy) {
        this.Vx = vx;
        this.Vy = vy;
    }
    
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), this.xPos, this.yPos, this.getWidth(), this.getHeight());
    }

    public void move() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            this.setX(this.getX() + Vx);
            this.setY(this.getY() + Vy);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }
}
