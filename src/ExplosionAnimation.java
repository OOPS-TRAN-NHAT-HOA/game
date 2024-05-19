import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class ExplosionAnimation {
    static protected int TYPE1 = 1;
    static protected int TYPE2 = 2;
    static private List<Image> ExplosionFrame = new ArrayList<>();
    static private List<Image> MeteoriteExplosionFrame = new ArrayList<>();
    static {
        for (int i = 2; i >= 1; i--) {
            ExplosionFrame.add(new Image("file:images/collision/collision" + i + ".png"));
        }
        for (int i = 3; i >= 1; i--) {
            MeteoriteExplosionFrame.add(new Image("file:images/collision/collision" + i + ".png"));
        }
    }
    private Iterator<Image> it;
    private Image img;
    private double xPos, yPos;
    private boolean stop = false;
    private Timeline timeline;
    
    ExplosionAnimation(int type, double x, double y) {
        this.xPos = x;
        this.yPos = y;
        if (type == TYPE1) {
            it = ExplosionFrame.iterator();
            img = it.next();
            timeline = new Timeline(new KeyFrame(Duration.millis(70), e -> {
                if (it.hasNext()) {
                    img = it.next();
                }
            }));
            timeline.setCycleCount(2);
        }
        else if (type == TYPE2) {
            it = MeteoriteExplosionFrame.iterator();
            img = it.next();
            timeline = new Timeline(new KeyFrame(Duration.millis(120), e -> {
                if (it.hasNext()) {
                    img = it.next();
                }
            }));
            timeline.setCycleCount(3);
        }
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
