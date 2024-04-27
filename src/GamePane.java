import java.util.*;
import java.awt.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {

    private final int fps = 60;
    private double gameWidth;
    private double gameHeight;

    private MyMap map;
    private Plane plane;

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;


    GamePane() {
        // get your screenSize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameWidth = screenSize.getWidth();
        gameHeight = screenSize.getHeight();

        // setup
        this.canvas = new Canvas(gameWidth, gameHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.gameScene = new Scene(this);

        this.plane = new Plane(505, 550, this);
        this.map = new MyMap(0, 0);

        this.getChildren().add(canvas);
        this.start();
    }

    private void update(Scene scene){
        plane.update(scene);
        if (this.map.getMonsters().size() == 0) {
            this.map.spawn(this);
        }
        // please handle collision
        
    }

    private void draw(GraphicsContext gc){
        map.draw(gc);
        plane.draw(gc);

        // please handle collision
        Iterator<Bullet> it = this.plane.getBullets().iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.isStop()) {
                this.getChildren().remove(bullet.getColliBox());
                it.remove();
            }
            else {
                bullet.draw(gc);
            }
        }
        this.map.getMonsters().forEach(monster -> monster.draw(gc));
    }

    private void start() {
        AnimationTimer gameloop = new AnimationTimer(){
            
            private long prevTime = 0;
            private long timePerFrame = (long) 1e9/fps;
            
            public void handle(long now){
                long dt = now - prevTime;
                if( dt > timePerFrame){
                    prevTime = now;
                    
                    //actual game loop
                    update(gameScene);
                    draw(gc);
                }
            }
        };
        gameloop.start();
    }
}
