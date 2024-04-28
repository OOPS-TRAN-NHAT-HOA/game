
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.*;

public class Plane extends Entity {

    protected boolean alive;
    private Timeline shootingTimeline;

    private List<Bullet> planeBullets;


    Plane(int x, int y) {
        this.setImage("file:images/plane.png", x, y);

        this.setCollidable(true);
        this.alive = true;
        this.planeBullets = new ArrayList<>();

    }


    public void update(Scene scene){

        // control by mouse
        scene.setOnMouseMoved(e -> {
            this.moveTo(e.getX(), e.getY());
        });

        scene.setOnMouseDragged(e -> {
            this.moveTo(e.getX(), e.getY());
        });

        scene.setOnMousePressed(e -> {
            this.startShooting();
        });

        scene.setOnMouseReleased(e -> {
            this.stopShooting();
        });

    }

    public void die(){
        alive = false;
        // System.out.println("Die");
    }

    public boolean isAlive(){
        if(alive){
            return true;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void moveTo(double x, double y) {
        this.setX(x - this.getWidth() / 2);
        this.setY(y - this.getHeight() / 2);
    }

    public List<Bullet> getBullets() {
        return this.planeBullets;
    }

    // plane attack
    private void shoot() {
        Bullet bullet = new Bullet("file:images/shot.png");
        bullet.setSpeed(0, -10);
        bullet.setX(this.getX() + this.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY());

        planeBullets.add(bullet);
        bullet.move();
    }

    private void startShooting() {
        if (shootingTimeline == null || shootingTimeline.getStatus() != Timeline.Status.RUNNING) {
            shootingTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
                this.shoot();
            }));
            shootingTimeline.setCycleCount(100);
            shootingTimeline.play();
        }
    }

    private void stopShooting() {
        if (shootingTimeline != null) {
            shootingTimeline.stop();
        }
    }

}