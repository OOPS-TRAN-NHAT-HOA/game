import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class DropItem extends Entity {

    static enum Items {
        HEART,
        UPGRADGEBULLETS,
    }
    private Items Item;
    private boolean moving;

    DropItem(Items name, double x, double y) {
        switch (name) {
            case Items.HEART:
                this.Item = name;
                this.setImage("file:images/items/heartitem.png", x, y);
               break;
            case Items.UPGRADGEBULLETS:
                this.Item = name;
                this.setImage("file:images/items/bulletitem.png", x, y);
                break;
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
            case Items.HEART:
                // todo
                break;
            case Items.UPGRADGEBULLETS:
                // todo
                break;
        }
        System.out.println("Effected!!!");
    }
}
