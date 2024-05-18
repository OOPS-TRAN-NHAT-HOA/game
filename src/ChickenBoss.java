import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.*;
public class ChickenBoss extends Entity {
    enum BossState{
        SPAWN,
        MOVING,
        LINKING,
        SHOOTING
    } // sau sẽ hoàn thiện các state khác
    private Sprite chickenBossSprite = new Sprite();
    private int totalHP = 500, currentHP = 500;
    private final int framePerSprite = 8;
    private int spriteCounter = -1;
    private final double xOffset = 65, yOffset = 45;//offset of the colliBox from the Image
    private double aX = 0, aY = 0;
    private double spawnXPos, spawnYPos;
    private BossState currentState;

    private Random rand = new Random();

    public ChickenBoss(double x, double y){
        this.setCollidable(true);
        this.setX(rand.nextInt(0,1000));
        this.setY(-getX());
        this.spawnXPos=x;
        this.spawnYPos=y;
        this.currentState = BossState.SPAWN;
        for(int i=1;i<=8;i++){
            if(i<=5){
                chickenBossSprite.addSprite(new Image("file:images/chickenboss/chickenboss" + i +".png"));
            }
            else{
                chickenBossSprite.addSprite(new Image("file:images/chickenboss/chickenboss5.png"));
            }
        }
        this.image = chickenBossSprite.getCurrentSprite();
    }

    public void update(){
        switch(currentState){
            case BossState.SPAWN:
                spawn(spawnXPos, spawnYPos);
                break;
            default:
        }
        //spriteCounter becomes 0 after each time it reachs framePerSprite
        this.spriteCounter++;
        if( this.spriteCounter > framePerSprite){
            this.spriteCounter = 0;
        }

        chickenBossSprite.setCurrentSpriteNum(spriteCounter);
        this.image = chickenBossSprite.getCurrentSprite();
        this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);
    }

    @Override
    public void draw(GraphicsContext gc) {
        //debug the colliBox
        gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    public void spawn(double XPos,double YPos){
        if(this.getX() == XPos && this.getY() == YPos){
            this.currentState = BossState.MOVING;
        }
        else{
            moveTo(XPos,YPos);
        }
    }

    private void moveTo(double XPos, double YPos){
        double aX = aY = 0.0;
        if(this.getX()<XPos){
            aX=1.0;
        }
        if(this.getX()>XPos){
            aX=-1.0;
        }
        if(this.getY()<YPos){
            aY=1.0;
        }
        if(this.getY()>YPos){
            aY=-1.0;
        }
        double coorX = this.getX()+aX, coorY = this.getY()+aY;
        this.setX(coorX);
        this.setY(coorY);
        System.out.println(coorX+"/"+coorY);
    }
}
