import javafx.scene.canvas.GraphicsContext;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.*; 

enum PlaneState{
    MOVING,
    SHOOTING
}

public class Plane extends Entity {

    protected boolean alive;
    private Timeline shootingTimeline;
    private List<Bullet> planeBullets;
    private final double xOffset = 65, yOffset = 45;//offset of the colliBox from the Image
    private Sprite movingPlane, shootingPlane, currentSprite;
    private final int framePerSprite = 6;
    private int spriteCounter = 0;
    private PlaneState state;

    Plane(int x, int y) {
        this.setImage("file:images/plane.png", x, y);
        this.setCollidable(true);
        this.alive = true;
        this.planeBullets = new ArrayList<>();
        this.state = PlaneState.MOVING;
        movingPlane = new Sprite("images/MovingPlane.png");
        shootingPlane = new Sprite("images/ShootingPlane.png");
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
            this.state = PlaneState.SHOOTING; 
        });

        scene.setOnMouseReleased(e -> {
            this.stopShooting();
            this.state = PlaneState.MOVING;
        });

        //spriteCounter becomes 0 after each time it reachs framePerSprite
        this.spriteCounter++;
        if( this.spriteCounter > framePerSprite){
            this.spriteCounter = 0;
        }

        switch(state){
            case PlaneState.MOVING:
                currentSprite = movingPlane;
                break;
            case PlaneState.SHOOTING:
                currentSprite = shootingPlane;
                break;
        }
        currentSprite.getCurrentSpriteNum(spriteCounter);
        this.image = currentSprite.getCurrentSprite();
        this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);
    }

    public void die(){
        alive = false;
    }

    public boolean isAlive(){
        if(alive){
            return true;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc){

        //debug the colliBox
        // gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());

        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        //draw bullet
        Iterator<Bullet> it = getBullets().iterator();
        while (it.hasNext()){
            Bullet bullet = it.next();
            if (bullet.isStop()) {
                it.remove();
            }
            else {
                bullet.draw(gc);
            }
        }

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