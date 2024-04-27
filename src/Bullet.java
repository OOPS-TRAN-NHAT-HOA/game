import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Bullet extends Entity {
    private double Vx;
    private double Vy;
    private boolean moving;

    Bullet(String path) {
        super(path);
        this.setCollidable(true);
        this.getColliBox().setVisible(false);
        moving = true;
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
        timeline.setOnFinished(e -> {
            moving = false;
        });
        timeline.play();
    }

    public boolean isStop() {
        return !moving;
    }
}
