
import javafx.scene.canvas.GraphicsContext;

public class Map1 extends Entity {

	public Map1(double x, double y){
		setImage("file:images/space.png",x,y);
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(image, getX(), getY(), this.image.getWidth(), this.image.getHeight());
    }
}