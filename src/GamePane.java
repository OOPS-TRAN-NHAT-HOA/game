

import java.util.*;
import java.awt.*;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Cursor;

public class GamePane extends Pane {

    private final int fps = 60;
    private double gameWidth;
    private double gameHeight;

    private MyMap map;
    private Plane plane;
    private CollisionHandler planeHandler,bulletHandler;

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;
    private AnimationTimer gameloop;
    
    GamePane() {
        // get your screenSize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameWidth = screenSize.getWidth();
        gameHeight = screenSize.getHeight();

        // setup
        this.canvas = new Canvas(gameWidth, gameHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.gameScene = new Scene(this);
        this.getChildren().add(canvas);

        this.start();
    }

    private void update(Scene scene){
        plane.update(scene);
        if (this.map.getMonsters().size() == 0 && this.plane.isAlive()) {
            this.map.spawn(this);
        }

        ArrayList<Rectangle> monsterColBox = new ArrayList<Rectangle>();
        this.map.getMonsters().forEach(monster -> {
            monsterColBox.add(monster.getColliBox());
        });

        planeHandler = new CollisionHandler(this.plane.getColliBox(), monsterColBox);
        if(planeHandler.checkCollision()){
            this.plane.die();
            this.map.getMonsters().clear();
        }

        for(Bullet currentBullet : this.plane.getBullets()){
            bulletHandler = new CollisionHandler(currentBullet.getColliBox(), monsterColBox);
            if(bulletHandler.indexOfCollisionBlock() != -1){
                currentBullet.stop();
                this.plane.getBullets().remove(currentBullet);
                this.map.getMonsters().get(bulletHandler.indexOfCollisionBlock()).stop();
                this.map.getMonsters().remove(bulletHandler.indexOfCollisionBlock());
            }
        }

    }

    private void draw(GraphicsContext gc){
        if(plane.isAlive()){
            gc.clearRect(0, 0, gameWidth, gameHeight);
            map.draw(gc);
            plane.draw(gc);
            this.map.getMonsters().forEach(monster -> monster.draw(gc));
        }
        else{
            gameOver();
        }
    }

    private void start() {
        
        this.gameScene.setCursor(Cursor.NONE);
        this.plane = new Plane(505, 550, this);
        this.map = new MyMap(0, 0, this);

        gameloop = new AnimationTimer(){
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

    private void gameOver(){
        gc.clearRect(0, 0, gameWidth, gameHeight);
        gameloop.stop();
        gameScene.setCursor(Cursor.DEFAULT);
        ImageView gameOverBg = new ImageView("file:images/game-over.png");
        gameOverBg.setFitWidth(gameWidth);
        gameOverBg.setFitHeight(gameHeight);
        // exit button
        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/exit.png");
        exitButton.setTranslateX(30);
        exitButton.setTranslateY(630);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(e-> {
            Stage stage = (Stage) this.gameScene.getWindow();
            stage.close();
        });

        // restart button
        Button restartButton = new Button();
        ImageView restart = new ImageView("file:images/start.png");
        restartButton.setTranslateX(940);
        restartButton.setTranslateY(630);
        restartButton.setPrefSize(restart.getFitWidth(), restart.getFitHeight());
        restartButton.setGraphic(restart);
        restartButton.setStyle("-fx-background-color: Transparent");
        restartButton.setCursor(Cursor.HAND);
        restartButton.setOnAction(e-> {
            this.getChildren().removeAll(gameOverBg,exitButton,restartButton);
            this.start();
        });

        this.getChildren().addAll(gameOverBg,exitButton,restartButton);
    }

    public double getScreenWidth(){
        return gameWidth;
    }

    public double getScreenHeight(){
        return gameHeight;
    }
}
