

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
// import java.util.ArrayList;

public class CollisionHandler {

  // private Rectangle currentBlock;
  // private ArrayList<Rectangle> other;
  
  // public CollisionHandler(Rectangle _currentBlock, ArrayList<Rectangle> _other){
  //   currentBlock = _currentBlock;
  //   other = _other;
  // }

  // //return boolean value
	// public boolean checkCollision(){
  //   for (Shape otherBlock : other){
  //     Shape intersect = Shape.intersect((Shape)currentBlock, (Shape)otherBlock);
  //     if (intersect.getBoundsInLocal().getWidth() != -1) {
  //       return true;
  //     }
  //   }
	// 	return false;
	// }

  // //return index of collision block in ArrayList
  // public int indexOfCollisionBlock(){
  //   for (Shape otherBlock : other){
  //     Shape intersect = Shape.intersect((Shape)currentBlock, (Shape)otherBlock);
  //     if (intersect.getBoundsInLocal().getWidth() != -1) {
  //       return other.indexOf(otherBlock);
  //     }
  //   }
  //   return -1;
  // }

    public boolean checkCollision(Rectangle rhs, Rectangle lhs) {
        Shape intersect = Shape.intersect((Shape) rhs, (Shape) lhs); 
        return intersect.getBoundsInLocal().getWidth() != -1;
    }

    public boolean checkCollision(Plane plane, Monster monster) {
        return checkCollision(plane.getColliBox(), monster.getColliBox());
    }

    public boolean checkCollision(Bullet bullet, Monster monster) {
        return checkCollision(bullet.getColliBox(), monster.getColliBox());
    }
}