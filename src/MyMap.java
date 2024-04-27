
import javafx.scene.canvas.GraphicsContext;

public class MyMap extends Entity {

	public MyMap(double x, double y){
		setImage("file:images/space.png", x, y);
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}