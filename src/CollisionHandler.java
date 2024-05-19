import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

public class CollisionHandler {

    public boolean checkCollision(Rectangle rhs, Rectangle lhs) {
        Shape intersect = Shape.intersect((Shape) rhs, (Shape) lhs); 
        return intersect.getBoundsInLocal().getWidth() != -1;
    }

    // plane - monster
    public boolean checkCollision(Plane plane, Monster monster) {
        return plane.isCollidable && checkCollision(plane.getColliBox(), monster.getColliBox());
    }

    // bullet - monster
    public boolean checkCollision(Bullet bullet, Monster monster) {
        return checkCollision(bullet.getColliBox(), monster.getColliBox());
    }

    // plane - drop item
    public boolean checkCollision(Plane plane, DropItem dropItem) {
        return checkCollision(plane.getColliBox(), dropItem.getColliBox());
    }

    // plane - Egg
    public boolean checkCollision(Plane plane, Egg egg) {
        return plane.isCollidable && checkCollision(plane.getColliBox(), egg.getColliBox());
    }
    
    // plane - meteorite
    public boolean checkCollision(Plane plane, Meteorite meteorite) {
        return plane.isCollidable && checkCollision(plane.getColliBox(), meteorite.getColliBox());
    }

    // monster - meteorite
    public boolean checkCollision(Monster monster, Meteorite meteorite) {
        return checkCollision(monster.getColliBox(), meteorite.getColliBox());
    }
}