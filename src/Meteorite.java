import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Meteorite extends Entity {
    // TODO: chưa có warningline
    private Timeline warningTimeline, movingTimeline;
    private boolean moving, drawWarning=false;
    private Image warningSign = new Image("file:images/entity/warning.png");
    Meteorite(double xPos) {
        this.setImage(new Image("file:images/entity/entity2.png"));
        this.setX(xPos);
        this.setY(-100);
        this.setCollidable(true);
        this.moving = true;
        this.setColliBox(-100,-100,1,1);
        // 1.5 seconds
        warningTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
        }));
        warningTimeline.setCycleCount(15);

        // 1 seconds
        movingTimeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            this.setY(this.getY() + 1);
            this.setColliBox(this.getX() + 20, this.getY() + 25, this.getWidth() - 45, this.getHeight() - 40);
        }));
        movingTimeline.setCycleCount(1000);
        movingTimeline.setOnFinished(e -> this.moving = false);

        warningTimeline.setOnFinished(e ->{ 
            drawWarning = false;
            movingTimeline.play();
        });
        warningTimeline.play();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(warningSign, this.getX(), 0, warningSign.getWidth(), warningSign.getHeight());
        gc.fillRect(this.getColliBox().getX(), this.getColliBox().getY(), this.getColliBox().getWidth(), this.getColliBox().getHeight());
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public boolean isStop() {
        return !this.moving;
    }

    public void stop() {
        warningTimeline.stop();
        movingTimeline.stop();
        moving = false;
    }
}