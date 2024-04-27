
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract public class Entity {

	protected boolean isCollidable = false;
    protected Rectangle collisionBox;
    
    protected Image image;
    protected double xPos,yPos;

    Entity() {}
    
    Entity(String path) {
        this.image = new Image(path);
    }

    //setImage 
    public void setImage(String path, double x, double y) {
        this.image = new Image(path);
        setX(x);
        setY(y);
    }

    public Image getImage() {
        return this.image;
    }

    public double getWidth() {
        return this.image.getWidth();
    }

    public double getHeight() {
        return this.image.getHeight();
    }
    
    // getter
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
        this.setColliBox(xPos, yPos, this.getWidth(), this.getHeight());
    }

    public void setColliBox(double x, double y, double width, double height){
    	collisionBox = new Rectangle(x, y, width, height);
    }

    public Rectangle getColliBox(){
    	return collisionBox;
    }
    
    public boolean checkCollision(Rectangle other) throws Exception {
        if (isCollidable == false) {
            throw new Exception();
        }
        return this.getColliBox().intersects(other.getBoundsInParent());
    }


    abstract public void draw(GraphicsContext gc);
}