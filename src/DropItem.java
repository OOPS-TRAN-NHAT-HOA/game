import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class DropItem extends Entity {

    static enum Items {
        UPGRADGEBULLETS,
        SHIELD
    }
    private Items Item;
    private boolean moving;
    private final double xOffset = 5, yOffset = 5;//offset of the colliBox from the Image

    DropItem(Items name, double x, double y) {
        switch (name) {
            case Items.UPGRADGEBULLETS:
                this.Item = name;
                this.setImage("file:images/items/bulletitem.png", x, y);
                break;
            case Items.SHIELD:
                this.Item = name;
                this.setImage("file:images/items/Shield/item-shield-on50.png", x, y);
            default:
        }
        if (this.getImage() != null) {
            this.moving = true;
            this.setCollidable(true);
            this.drop();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        //debug the colliBox
        // gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private void drop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), e-> {
            this.setY(this.getY() + 1.5);
             this.setColliBox(this.getX()+xOffset, this.getY()+yOffset, this.getWidth()-2*xOffset, this.getHeight()-2*yOffset);
        }));
        timeline.setCycleCount(200);
        timeline.setOnFinished(e -> {
            this.moving = false;
        });
        timeline.play();
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void stop() {
        this.moving = false;
    }

    public void itemEffect(Plane plane) {
        switch (this.Item) {
            case Items.UPGRADGEBULLETS:
                if (plane.bulletLevel == 3) {
                    plane.bulletdmg = Math.min(plane.bulletdmg + 1, 3);
                }
                plane.bulletLevel = Math.min(plane.bulletLevel + 1, 3);
                break;
            case Items.SHIELD:
                plane.invisible(7);
        }
    }
}
