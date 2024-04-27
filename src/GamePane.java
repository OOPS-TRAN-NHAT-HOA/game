
import java.util.*;
import java.awt.*;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class GamePane extends Pane {

    private final int fps = 60;
    private double gameWidth;
    private double gameHeight;

    private MyMap map;
    private Plane plane;
    private CollisionHandler planeHandler,bulletHandler;
    private Image gameOver = new Image("file:images/game-over.png");

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
        this.map = new MyMap(0, 0, this);

        this.getChildren().add(canvas);
        this.start();
    }

    private void update(Scene scene){
        plane.update(scene);
        if (this.map.getMonsters().size() == 0) {
            this.map.spawn(this);
        }

        ArrayList<Rectangle> monsterColBox = new ArrayList<Rectangle>();
        this.map.getMonsters().forEach(monster -> {
            monsterColBox.add(monster.getColliBox());
        });

        planeHandler = new CollisionHandler(this.plane.getColliBox(), monsterColBox);
        if(planeHandler.checkCollision()){
            this.plane.die();
        }

        for(Bullet currentBullet : this.plane.getBullets()){
            bulletHandler = new CollisionHandler(currentBullet.getColliBox(), monsterColBox);
            if(bulletHandler.checkCollision()){
                currentBullet.stop();
            }
        }
    }

    private void draw(GraphicsContext gc){
        if(plane.isAlive()){
            gc.clearRect(0, 0, gameWidth, gameHeight);
            map.draw(gc);
            plane.draw(gc);

            Iterator<Bullet> it = this.plane.getBullets().iterator();
            while (it.hasNext()) {
                Bullet bullet = it.next();
                if (bullet.isStop()) {
                    it.remove();
                }
                else {
                    bullet.draw(gc);
                }
            }
            this.map.getMonsters().forEach(monster -> monster.draw(gc));
        }
        else{
            gc.clearRect(0, 0, gameWidth, gameHeight);
            gc.drawImage(gameOver,0, 0, gameWidth, gameHeight );
        }
    }

    private void start() {
        AnimationTimer gameloop = new AnimationTimer(){
            private long prevTime = 0;
            private long timePerFrame = (long) 1e9/fps;
            private double delta = 0;
            public void handle(long now){
                delta += (now-prevTime)/timePerFrame;
                if( delta >= 1){
                    prevTime = now;
                    delta--;
                    //actual game loop
                    update(gameScene);
                    draw(gc);
                }
            }
        };
        gameloop.start();
    }

    public double getScreenWidth(){
        return gameWidth;
    }

    public double getScreenHeight(){
        return gameHeight;
    }
}
