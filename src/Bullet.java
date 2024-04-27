
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Bullet extends Entity {
    private double Vx;
    private double Vy;
    private boolean moving;
    private Timeline timeline;

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
        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            this.setX(this.getX() + Vx);
            this.setY(this.getY() + Vy);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }

    public void stop(){
        // System.out.println("bullet stop!");
        moving = false;
        timeline.stop();
    }

    public boolean isStop() {
        return !moving;
    }
}
