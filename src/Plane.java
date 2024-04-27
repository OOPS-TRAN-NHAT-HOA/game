import javafx.scene.canvas.GraphicsContext;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.*;

public class Plane extends Entity {

    private double speed = 30;
    protected boolean alive;
    private Timeline shootingTimeline;

    private GamePane gamePane;
    private GraphicsContext gc;


    Plane(int x, int y, GraphicsContext _gc, GamePane _pane) {
        this.setImage("file:images/plane.png", x, y);
        this.setCollidable(true);
        this.alive = true;
        this.gamePane = _pane;
        this.gc = _gc;
    }


    public void update(Scene scene){
        final List<KeyCode> acceptedCodes = Arrays.asList(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
        final Set<KeyCode> codes = new HashSet<>();

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

        // control by key
        scene.setOnKeyReleased(e -> {
                System.out.println("Release!");
                if (acceptedCodes.contains(e.getCode())){ 
                codes.remove(e.getCode()); 
                System.out.println("\nBREAK\n");
            }});
            scene.setOnKeyPressed(e -> {
                System.out.println("Pressed!");
                if (acceptedCodes.contains(e.getCode())) {
                    codes.add(e.getCode());
                    if (codes.contains(KeyCode.LEFT)) {
                        moveLeft();    
                    }
                    else if (codes.contains(KeyCode.RIGHT)) {
                        moveRight();
                    }
                    else if (codes.contains(KeyCode.UP)) {
                        moveUp();
                    }
                    else if (codes.contains(KeyCode.DOWN)) {
                        moveDown();
                    }
                }
            });
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void moveTo(double x, double y) {
        this.setX(x - this.getWidth() / 2);
        this.setY(y - this.getHeight() / 2);
    }

    public void moveLeft() {
       setX(getX() - speed);
    }
    
    public void moveRight() {
        setX(getX() + speed);
    }
    
    public void moveUp() {
        setY(getY() - speed);
    }
    
    public void moveDown() {
        setY(getY() + speed);
    }

    // plane attack
    private void shoot() {
        Bullet bullet = new Bullet("file:images/shot.png");
        bullet.setSpeed(0, -10);
        bullet.setX(this.getX() + this.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY());

        this.gamePane.getChildren().add(bullet.getColliBox());
        bullet.move(gc);
    }

    private void startShooting() {
        if (shootingTimeline == null || shootingTimeline.getStatus() != Timeline.Status.RUNNING) {
            shootingTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
                this.shoot();
            }));
            shootingTimeline.setCycleCount(Timeline.INDEFINITE);
            shootingTimeline.play();
        }
    }

    private void stopShooting() {
        if (shootingTimeline != null) {
            shootingTimeline.stop();
        }
    }

}