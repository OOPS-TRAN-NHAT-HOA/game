import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {

    private final int screenWidth = 1366;
    private final int screenHeigth = 768;
    private final int fps = 60;
    private MyMap map;
    private Plane plane;
    private List<Monster> monster;

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;


    GamePane() {
        this.canvas = new Canvas(screenWidth, screenHeigth);
        this.gc = canvas.getGraphicsContext2D();
        this.gameScene = new Scene(this);

        this.plane = new Plane(505, 550, gc, this);
        this.map = new MyMap(0, 0);

        this.getChildren().add(canvas);
        this.start();
    }

    private void update(Scene scene){
        plane.update(scene);
    }

    private void draw(GraphicsContext gc){
        map.draw(gc);
        plane.draw(gc);
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
