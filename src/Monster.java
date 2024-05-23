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
    private double xOffset = 10, yOffset = 5;//offset of the colliBox from the Image

    private int currentHitPoint, totalHitPoint;

    private Random rand = new Random();

    Monster(){}

    Monster(String path, int totalHP) {
        super(path);
        this.setX(rand.nextDouble(0, 1100));
        this.setY(rand.nextDouble(0, 300));

        this.totalHitPoint = totalHP;
        this.currentHitPoint = totalHP;
        this.alive = true;
        this.setCollidable(true);
        this.move(App.screenWidth, App.screenHeight);
    }

    @Override
    public void draw(GraphicsContext gc) {
        // debug the colliBox  
        gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());

        //draw HP bar
        gc.setFill(Color.WHITE);
        gc.fillRect(this.getX()+20, this.getY()-20, this.getWidth()-40, 10);
        gc.setFill(Color.RED);
        double remainingHealth = (double) currentHitPoint/totalHitPoint;
        gc.fillRect(this.getX()+20, this.getY()-20, (this.getWidth()-40)*remainingHealth, 10);
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    // public void update(){
    //     this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);  
    // }

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
            this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);  
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
        if (p < 0.05) { // 5% for heart
            return new DropItem(DropItem.Items.HEART, this.getX(), this.getY());
        }
        p = rand.nextDouble(0, 1);
        if (p < 0.10) { // 10% for bullet
            return new DropItem(DropItem.Items.UPGRADGEBULLETS, this.getX(), this.getY());
        }
        p = rand.nextDouble(0, 1);
        if (p < 0.05) { // 5% for shield
            return new DropItem(DropItem.Items.SHIELD, this.getX(), this.getY());
        }
        return null;
    }

    public void takeDamage(int dmg) {
        currentHitPoint -= dmg;
        if (currentHitPoint <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setOffset(double x, double y){
        this.xOffset=x;
        this.yOffset=y;
    }
}
