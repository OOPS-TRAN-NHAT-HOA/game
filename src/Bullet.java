import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Bullet extends Entity {
    private double Vx;
    private double Vy;
    private boolean moving;
    private Timeline timeline;
    private int damage;
        

    Bullet(String path) {
        super(path);
        this.setCollidable(true);
        this.getColliBox().setVisible(false);
        this.moving = true;
        this.damage = 1;
    }
    public void setDamage(int dmg) {
        this.damage = dmg;
    }
    public int getDamage() {
        return this.damage;
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
        timeline.setOnFinished(e -> stop());
        timeline.play();
    }

    public void stop(){
        moving = false;
        timeline.stop();
    }

    public boolean isStop() {
        return !moving;
    }
}
