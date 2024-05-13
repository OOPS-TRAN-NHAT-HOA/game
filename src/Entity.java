import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract public class Entity {

	protected boolean isCollidable = false;
    protected Rectangle collisionBox;
    
    protected Image image;
    protected double xPos = -1000, yPos = -1000;

    Entity() {}
    
    Entity(String path) {
        this.image = new Image(path);
        this.setColliBox(this.xPos, this.yPos, this.getWidth(), this.getHeight());
    }

    //setImage 
    public void setImage(String path, double x, double y) {
        this.image = new Image(path);
        this.setColliBox(this.xPos, this.yPos, this.getWidth(), this.getHeight());
        this.setX(x);
        this.setY(y);
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
        this.getColliBox().setX(x);
    }
    
    public void setY(double y) {
        this.yPos = y;
        this.getColliBox().setY(y);
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
    
    abstract public void draw(GraphicsContext gc);
}