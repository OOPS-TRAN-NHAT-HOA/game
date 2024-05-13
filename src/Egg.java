import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Egg extends Entity {

    // ChickensBoss will lay eggs to summon a random monster
    private boolean hatched = false;

    Egg() {
        super("file:iamges/InvaderBullet/egg.png");
        this.setCollidable(true);
        Timeline hatches = new Timeline(new KeyFrame(Duration.millis(100), e-> {
            if (this.getY() + 1 < 400) {
                this.setY(this.getY() + 1);
            }
        }));
        hatches.setCycleCount(200);
        hatches.setOnFinished(e -> {
            this.hatching();
            hatched = true;
        });
        hatches.play();
    }

    private Monster hatching() {
        // TODO
        return null;
    }

    public boolean isHatched() {
        return this.hatched;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
