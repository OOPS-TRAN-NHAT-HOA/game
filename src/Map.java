
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Map extends Entity {

	public Map(double x, double y){
		setImage("images/space.png",x,y);
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(image, getX(), getY(), this.image.getWidth(), this.image.getHeight());
    }
}