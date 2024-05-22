import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class BossBullet extends Entity{ 

    private boolean isStopped = false; 
    private double xPos, yPos, vX, vY, angle;
    private final double velocity = 5;
    private Timeline movingTimeline;
    BossBullet(int type, double x, double y, double angle){
        this.setCollidable(true);
        this.setX(x);
        this.setY(y);
        switch(type){ // egg bullet type 1, 
        case 1:
            this.setImage(new Image("file:images/chickenboss/BossBulletEgg.png"));  
            double xOffset = 5, yOffset = 5;
            this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);
            this.vX = velocity * Math.sin(angle);
            this.vY = - velocity * Math.cos(angle);
            movingTimeline = new Timeline(new KeyFrame(Duration.millis(20), e-> {
                doMovement();
            }));
            movingTimeline.setCycleCount(200);
            movingTimeline.setOnFinished(e->{
                isStopped = true;
            });
            movingTimeline.play();
            break;  
        }
    }

    public void doMovement(){
        this.setX(getX()+vX);
        this.setY(getY()+vY);
        this.setColliBox(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public boolean isStopped(){
        return isStopped;
    }

    public void draw(GraphicsContext gc){
        //debug the colliBox
        // gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
