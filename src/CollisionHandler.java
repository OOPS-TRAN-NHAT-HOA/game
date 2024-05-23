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

    // plane - meteorite
    public boolean checkCollision(Plane plane, Meteorite meteorite) {
        return plane.isCollidable && checkCollision(plane.getColliBox(), meteorite.getColliBox());
    }

    // monster - meteorite
    public boolean checkCollision(Monster monster, Meteorite meteorite) {
        return checkCollision(monster.getColliBox(), meteorite.getColliBox());
    }

    //plane - boss
    public boolean checkCollision(Plane plane, ChickenBoss boss) {
        if(plane.isCollidable){
            if(checkCollision(plane.getColliBox(),boss.getColliBox())){
                return true;
            }
            for(BossBullet bullet : boss.getBossBullets()){
                if(checkCollision(plane.getColliBox(), bullet.getColliBox())){
                    return true;
                }
            }
        }
        return false;
    }

    // bullets - boss
    public boolean checkCollision(Bullet bullet, ChickenBoss boss){
        return checkCollision(bullet.getColliBox(), boss.getColliBox());
    }

    // boss - meteorite
    public boolean checkCollision(ChickenBoss boss, Meteorite meteorite) {
        return checkCollision(boss.getColliBox(), meteorite.getColliBox());
    }
}