import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

public class CollisionHandler {

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

    public boolean checkCollision(Plane plane, DropItem dropItem) {
        return checkCollision(plane.getColliBox(), dropItem.getColliBox());
    }
}