import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import javafx.scene.paint.Color;

public class Monster extends Entity {

    protected double Vx = 0;
    protected double Vy = 1;
    protected boolean alive;
    private Timeline timeline;

    private int hitPoint;

    private Random rand = new Random();

    Monster(String path) {
        super(path);
        this.setX(rand.nextDouble(0, 1100));
        this.setY(rand.nextDouble(0, 300));

        this.hitPoint = 10;
        this.alive = true;
        this.setCollidable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.move(screenSize.getWidth(), screenSize.getHeight());
    }

    public void setHitPoint(int hitpoint) {
        this.hitPoint = hitpoint;
    }
    
    public int getHitPoint() {
        return this.hitPoint;
    }

    @Override
    public void draw(GraphicsContext gc) {
        //debug the colliBox
        // gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());

        //draw HP bar
        gc.setFill(Color.WHITE);
        gc.fillRect(this.getX()+20, this.getY()-20, this.getWidth()-40, 10);
        gc.setFill(Color.RED);
        double remainingHealth = (double) hitPoint/10;
        gc.fillRect(this.getX()+20, this.getY()-20, (this.getWidth()-40)*remainingHealth, 10);

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
            if (0 <= ny && ny + this.getHeight() + 50 < Height) {
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

    public void stop(){
        timeline.stop();
        alive = false;
        System.out.println("Chicken die!");
    }

    public DropItem dropSomething() {
        double p = rand.nextDouble(0, 1);
        // System.out.println(p);
        if (p < 0.1) { // 10% for heart
            return new DropItem(DropItem.Items.HEART, this.getX(), this.getY());
        }
        else if (p < 0.2) { // 10% for bullet
            return new DropItem(DropItem.Items.UPGRADGEBULLETS, this.getX(), this.getY());
        }
        return null;
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
