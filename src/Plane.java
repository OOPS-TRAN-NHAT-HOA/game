import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.*; 

enum PlaneState{
    MOVING,
    SHOOTING,
    EXPLODING
}

public class Plane extends Entity {
    static final int TYPE1 = 1;
    static final int TYPE2 = 2;

    protected boolean alive;
    private Timeline shootingTimeline;
    private List<Bullet> planeBullets;
    protected int bulletLevel = 1;

    private final double xOffset = 85, yOffset = 85;//offset of the colliBox from the Image
    private Sprite currentSprite, movingPlane, shootingPlane, explodingPlane;
    private final int framePerSprite = 6;
    private int spriteCounter = 0;
    private PlaneState state;

    private Image shield;
    private int shieldState = 1; // 1 is visible, 0 otherwise
    private Timeline invisibleTimeline, flickerTimeline;
    

    Plane(int x, int y) {
        this.invisible(5);
        this.alive = true;
        this.planeBullets = new ArrayList<>();
        this.shield = new Image("file:images/items/Shield/item-shield-on100.png");
        this.state = PlaneState.MOVING;
        movingPlane = new Sprite("images/SpacePlane/MovingPlane.png", -90);
        shootingPlane = new Sprite("images/SpacePlane/ShootingPlane.png", -90);
        explodingPlane = new Sprite("images/SpacePlane/ExplodingPlane.png", -90);
    }


    public void update(Scene scene){

        //spriteCounter becomes 0 after each time it reachs framePerSprite
        this.spriteCounter++;
        if( this.spriteCounter > framePerSprite){
            this.spriteCounter = 0;
        }

        if(this.state == PlaneState.EXPLODING){
            this.stopShooting();
            scene.setOnMouseMoved(e->{});
            scene.setOnMouseDragged(e->{});
            scene.setOnMousePressed(e->{});
            scene.setOnMouseReleased(e->{});
            currentSprite = explodingPlane;
            if(currentSprite.getCurrentSpriteNum() == currentSprite.getTotalSprite()){
                this.die();
            }
        }
        else{
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

            switch(state){
                case PlaneState.MOVING:
                    currentSprite = movingPlane;
                    break;
                case PlaneState.SHOOTING:
                    currentSprite = shootingPlane;
                    break;
                default:
            }
        }

        currentSprite.setCurrentSpriteNum(spriteCounter);
        this.image = currentSprite.getCurrentSprite();
        this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);  
    }

    @Override
    public void draw(GraphicsContext gc){


        if (this.isCollidable == false && this.shieldState == 1) {
            gc.drawImage(this.shield, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        //draw bullet
        Iterator<Bullet> it = getBullets().iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.isStop()) {
                it.remove();
            }
            else {
                bullet.draw(gc);
            }
        }
        //debug the colliBox
        gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
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
        Bullet bullet = new Bullet("file:images/Bullets/bullet1.png");
        bullet.setSpeed(0, -10);
        double x = this.getX() + this.getWidth() / 2 - bullet.getWidth() / 2;
        double y = this.getY();
        bullet.setX(x);
        bullet.setY(y);
        switch (bulletLevel) {
            case 1:
                planeBullets.add(bullet.clone());
                break;
            case 2:
                bullet.setX(x - 10);
                planeBullets.add(bullet.clone());
                bullet.setX(x + 10);
                planeBullets.add(bullet.clone());
                break;
            case 3:
                bullet.setX(x - 20);
                planeBullets.add(bullet.clone());
                bullet.setX(x + 20);
                planeBullets.add(bullet.clone());
                bullet.setX(x);
                bullet.setY(y - 10);
                planeBullets.add(bullet.clone());
                break;
        }
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

    public void invisible(double t) {
        this.setCollidable(false);
        if (this.flickerTimeline != null) {
            this.flickerTimeline.stop();
            this.shieldState = 1;
        }
        if (this.invisibleTimeline != null) {
            this.invisibleTimeline.stop();
        }
        this.invisibleTimeline = new Timeline(new KeyFrame(Duration.seconds(2 * t / 3), e -> {
        }));
        this.invisibleTimeline.setOnFinished(e -> {
            this.flickerTimeline = new Timeline(new KeyFrame(Duration.seconds(t / 3 / 15), E -> {
                this.shieldState ^= 1;
            }));
            this.flickerTimeline.setCycleCount(15);
            this.flickerTimeline.setOnFinished(E -> {
                this.setCollidable(true);
                this.shieldState = 1;
            });
            this.flickerTimeline.play();
        });
        this.invisibleTimeline.play();
    }

    // exploding animation
    public void exploding(){
        this.state = PlaneState.EXPLODING;
    }

    private void die(){
        alive = false;
    }

    public boolean isAlive(){
        if(alive){
            return true;
        }
        return false;
    }
}