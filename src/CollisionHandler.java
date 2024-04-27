
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class CollisionHandler {

  private Rectangle currentBlock;
  private ArrayList<Rectangle> other;
  
  public CollisionHandler(Rectangle _currentBlock, ArrayList<Rectangle> _other){
    currentBlock = _currentBlock;
    other = _other;
  }

	public boolean checkCollision(){
    for (Shape otherBlock : other){
      Shape intersect = Shape.intersect((Shape)currentBlock, (Shape)otherBlock);
      if (intersect.getBoundsInLocal().getWidth() != -1) {
        return true;
      }
    }
		return false;
	}
}