import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Bullet extends Entity {
    private double Vx;
    private double Vy;
    private boolean moving;
    private Timeline timeline;
    private int damage = 100;
    private final double xOffset = 7, yOffset = 2;//offset of the colliBox from the Image

    Bullet() {
        this.setCollidable(true);
        this.moving = true;
        this.damage = 100;
    }

    Bullet(String path) {
        super(path);
        this.setCollidable(true);
        this.moving = true;
        this.damage = 100;
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
        //debug the colliBox
        // gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());

        gc.drawImage(this.getImage(), this.xPos, this.yPos, this.getWidth(), this.getHeight());
    }

    public void move() {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            this.setX(this.getX() + Vx);
            this.setY(this.getY() + Vy);
            this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);  
        }));
        timeline.setCycleCount(75);
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

    public Bullet clone() {
        Bullet cloneBullet = new Bullet();
        cloneBullet.setImage(this.getImage());
        cloneBullet.setSpeed(this.Vx, this.Vy);
        cloneBullet.setX(this.getX());
        cloneBullet.setY(this.getY());
        cloneBullet.setDamage(100);

        cloneBullet.move();
        return cloneBullet;
    }
}
