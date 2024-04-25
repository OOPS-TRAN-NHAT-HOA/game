
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract public class Entity {

	protected boolean isCollidable = false;
    protected Rectangle collisionBox;
    
    protected Image image;
    protected double xPos,yPos;

    //setImage 
    public void setImage(String path, double x, double y) {
        this.image = new Image(path);
        setX(x);
        setY(y);
    }
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }
    
    public void setX(double x) {
        this.xPos = x;
    }
    
    public void setY(double y) {
        this.yPos = y;
    }

    public void setCollidable(boolean tmp){
    	isCollidable = tmp;
    }

    public void setColliBox(double x, double y, double width, double height){
    	collisionBox = new Rectangle(x, y, width, height);
    }

    public Rectangle getColliBox(){
    	return collisionBox;
    }
}