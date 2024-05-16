import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class ExplosionAnimation {

    static private List<Image> ExplosionFrame = new ArrayList<>();
    static {
        for (int i = 2; i >= 1; i--) {
            ExplosionFrame.add(new Image("file:images/collision/collision" + i + ".png"));
        }
    }
    private Iterator<Image> it = ExplosionFrame.iterator();
    private Image img;
    private double xPos, yPos;
    private boolean stop = false;
    
    ExplosionAnimation(double x, double y) {
        this.xPos = x;
        this.yPos = y;
        img = it.next();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            if (it.hasNext()) {
                img = it.next();
            }
        }));
        timeline.setCycleCount(2);
        timeline.setOnFinished(e -> {
            this.stop = true;
        });
        timeline.play();
    }
    
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, this.xPos, this.yPos, img.getWidth(), img.getHeight());
    }

    public boolean isStop() {
        return this.stop;
    }
}
