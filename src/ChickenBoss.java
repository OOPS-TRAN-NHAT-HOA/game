import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.*;
import javafx.scene.paint.Color;
public class ChickenBoss extends Entity {
    enum BossState{
        SPAWN,
        MOVING,
        SHOOTING_EGG,
        SPAWN_SMALLER_CHICKEN,
        DO_NOTHING,
        GET_OUT,
        END
    } 
    private Sprite chickenBossSprite = new Sprite();
    private int totalHP = 500, currentHP = 500;
    private final int framePerSprite = 8;
    private int spriteCounter = -1, restCounter = 1;
    private final double xOffset = 60, yOffset = 45;//offset of the colliBox from the Image
    private double vX = 10, vY = 10; // boss velocity
    private double spawnXPos, spawnYPos, x, y;
    private BossState currentState;
    private boolean switchState = false, winBoss = false;
    private ArrayList<BossBullet> bullets = new ArrayList<BossBullet>();
    private ArrayList<Monster> smallerMonster = new ArrayList<Monster>();
    private Random rand = new Random();

    public ChickenBoss(double x, double y){
        this.setCollidable(true);
        this.setX(rand.nextInt(0,800));
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
        case BossState.SHOOTING_EGG:
            double p = rand.nextInt(12,24);
            for(int i =0; i<=p; i++){
                this.bullets.add(new BossBullet(1, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2, 360/p*i));
            }
            switchState = true;
            break;
        case BossState.MOVING:
            if(switchState){
                x = rand.nextDouble(0,App.screenWidth-this.getWidth()); 
                y = rand.nextDouble(10,App.screenHeight-this.getHeight()-400);
            }
            switchState = false;
            if(moveTo(x,y,vX,vY)){
                switchState = true;
            }
            break;
        case BossState.SPAWN_SMALLER_CHICKEN:
            for(int i = 0; i<10; i++){
                Monster tempMonster ;
                if(rand.nextDouble(0,1)<0.5){
                    tempMonster = new Monster("file:images/Invader/chicken.png",10);
                }
                else{
                    tempMonster = new Monster("file:images/Invader/chicken1.1.png",30);
                }
                tempMonster.setX( randomInRange(this.getX(),this.getX()+this.getWidth()-tempMonster.getWidth()));
                tempMonster.setY( randomInRange(this.getY(),this.getY()+this.getHeight()-tempMonster.getHeight()));
                smallerMonster.add(tempMonster);
            }
            switchState = true;
            break;
        case BossState.DO_NOTHING:
            switchState = false;
            if(rest()){
                switchState = true;
            }
            break;
        case BossState.GET_OUT:
            switchState = false;
            if(moveTo(-500,-500,vX,vY)){
                switchState = true;
            }
            break;
        case BossState.END:
            switchState = false;
            winBoss = true;
            System.out.println("END GAME!");
            break;
        }

        chooseState();

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
        for(Monster monster : smallerMonster){
            monster.draw(gc);

        }
        for(BossBullet bossBullet : bullets){
            bossBullet.draw(gc);
        }
        Iterator<BossBullet> it = this.bullets.iterator();
        while (it.hasNext()){
            BossBullet tmp = it.next();
            if (tmp.isStopped()) {
                it.remove();
            }
            else {
                tmp.draw(gc);
            }
        }
        //debug the colliBox
        gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
        
        //draw HP bar
        gc.setFill(Color.WHITE);
        gc.fillRect(this.getX()+20, this.getY()-20, 270, 10);
        gc.setFill(Color.RED);
        double remainingHealth = (double) currentHP/totalHP;
        gc.fillRect(this.getX()+20, this.getY()-20, 270*remainingHealth, 10);

        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight()); 
    }
    
    public void spawn(double XPos,double YPos){
        if(moveTo(XPos,YPos,vX,vY)){
            switchState = true;
        }
    }

    private boolean moveTo(double XPos, double YPos, double vX, double vY){
        if(this.getX() == XPos && this.getY() == YPos){
            System.out.println("MOVED!");
            return true;
        }
        if(this.getX()<XPos){
            vX = Math.min(vX, XPos - this.getX());
        }
        else if(this.getX()>XPos){
            vX = - Math.min(vX, this.getX() - XPos);
        }
        else{
            vX = 0;
        }
        if(this.getY()<YPos){
            vY = Math.min(vY, YPos - this.getY());
        }
        else if(this.getY()>YPos){
            vY = - Math.min(vY, this.getY() - YPos);
        }
        else{
            vY = 0;
        }
        double coorX = this.getX()+vX, coorY = this.getY()+vY;
        this.setX(coorX);
        this.setY(coorY);
        System.out.println(coorX + " " + coorY);
        return false;
    }

    public ArrayList<Monster> getSmallerMonster(){
        return this.smallerMonster;
    }

    public ArrayList<BossBullet> getBossBullets(){
        return this.bullets;
    }

    private void chooseState(){
        if(switchState){
            if(currentHP > 0){
            switch(currentState){
            case BossState.SPAWN:
                currentState = BossState.DO_NOTHING;
                break;
            case BossState.SHOOTING_EGG:
                currentState = BossState.DO_NOTHING;
                break;
            case BossState.MOVING:
                currentState = BossState.DO_NOTHING;
                break;
            case BossState.SPAWN_SMALLER_CHICKEN:
                currentState = BossState.DO_NOTHING;
                break;
            case BossState.DO_NOTHING:
                if(this.currentHP < this.totalHP/2){
                    double p = rand.nextDouble(0,1);
                    if(p<0.5){
                        currentState = BossState.SHOOTING_EGG;
                    }
                    else if(p<0.75){
                        currentState = BossState.SPAWN_SMALLER_CHICKEN;
                    }
                    else{
                        currentState = BossState.MOVING;
                    }
                }
                else{
                    double p = rand.nextDouble(0,1);
                    if(p<0.5){
                        currentState = BossState.SHOOTING_EGG;
                    }
                    else{
                        currentState = BossState.MOVING;
                    }
                }
                break;
                default:
                    break;
            }}
            else{
                switch(currentState){
                case BossState.GET_OUT:
                    currentState = BossState.END;
                    break;
                default :
                    currentState = BossState.GET_OUT;
                    break;
                }
            }
        }
    }

    // Generating a random double number of a certain range 
    private double randomInRange(double rangeMin, double rangeMax){
        return rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
    }

    //boss rest in 1sec = 60 frames
    private boolean rest(){
        if(restCounter<60){
            restCounter ++;
            return false;
        }
        else{
            restCounter = 0;
            System.out.println("DONE REST!");
            return true;
        }
    }

    public void takeDMG(int dmg){
        if(currentHP<=0){
            currentHP = 0;
            return;
        }
        this.currentHP -= dmg;
    }

    public boolean isWinning(){
        return winBoss;
    }
}

