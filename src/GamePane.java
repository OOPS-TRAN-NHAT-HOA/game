import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Cursor;

public class GamePane extends Pane {
    static enum GameState{
        PLAY,
        PAUSE
    }
    private final int fps = 60;
    private double gameWidth;
    private double gameHeight;

    private MyMap map;
    private Plane plane;
    private CollisionHandler collisionHandler;
    private List<ExplosionAnimation> explosion = new ArrayList<>();
    private GameState currentState;

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;
    private AnimationTimer gameloop;
    
    GamePane() {
        // setup
        this.canvas = new Canvas(App.screenWidth, App.screenHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.gameScene = new Scene(this);
        this.getChildren().add(canvas);
        this.collisionHandler = new CollisionHandler();
        this.start();
    }

    private void update(Scene scene){
        plane.update(scene);
        if(this.plane.isAlive()){
            this.map.update();
        }

        // boss check
        if(this.map.hasBoss()){
            if(collisionHandler.checkCollision(this.plane, this.map.getBoss())){
                this.plane.exploding();
            }
            for (Bullet bullet : this.plane.getBullets()){
                if(collisionHandler.checkCollision(bullet,this.map.getBoss())){
                    bullet.stop();
                    explosion.add(new ExplosionAnimation(1, bullet.getX() - 30, bullet.getY() - 25));
                    this.map.getBoss().takeDMG(bullet.getDamage());
                }
                for (Monster smallerMonster : this.map.getBoss().getSmallerMonster()){
                    if(collisionHandler.checkCollision(bullet,smallerMonster)){
                        bullet.stop();
                        explosion.add(new ExplosionAnimation(1, bullet.getX() - 30, bullet.getY() - 25));
                        smallerMonster.takeDamage(bullet.getDamage());
                    }
                    if(collisionHandler.checkCollision(this.plane, smallerMonster)){
                        this.plane.exploding();
                    }
                }
            }
        }

        // monster update
        for (Monster monster : this.map.getMonsters()) {
            if (collisionHandler.checkCollision(this.plane, monster)) {
                this.plane.exploding();
            }
            for (Bullet bullet : this.plane.getBullets()) {
                if (collisionHandler.checkCollision(bullet, monster)) {
                    monster.takeDamage(bullet.getDamage());
                    explosion.add(new ExplosionAnimation(1, bullet.getX() - 30, bullet.getY() - 25));
                    bullet.stop();
                }
            }
            for (Meteorite meteorite : this.map.getMeteorites()) {
                if (collisionHandler.checkCollision(monster, meteorite)) {
                    monster.takeDamage(7);
                    explosion.add(new ExplosionAnimation(2, meteorite.getX() + meteorite.getWidth() / 3, meteorite.getY() + meteorite.getHeight() / 3));
                    meteorite.stop();
                }
            }
        }

        // Meteorite update
        for (Meteorite meteorite : this.map.getMeteorites()) {
            if (collisionHandler.checkCollision(plane, meteorite)) {
                this.plane.exploding();
                explosion.add(new ExplosionAnimation(2, meteorite.getX() + meteorite.getWidth() / 3, meteorite.getY() + meteorite.getHeight() / 3));
                meteorite.stop();
            }
        }

        // dropItem update
        for (DropItem dropItem : this.map.getDropItems()) {
            if (collisionHandler.checkCollision(this.plane, dropItem)) {
                dropItem.stop();
                Platform.runLater(() -> { // the main thread will be block without this
                    dropItem.itemEffect(this.plane);
                });
            }
        }

        // explosion
        Iterator<ExplosionAnimation> it = explosion.iterator();
        while (it.hasNext()) {
            ExplosionAnimation ex = it.next();
            if (ex.isStop()) {
                it.remove();
            }
        }

        //winning
        if(this.map.isWinning()){
            gameWin();
        }
    }

    private void draw(GraphicsContext gc){
        if(plane.isAlive()){
            gc.clearRect(0, 0, App.screenWidth, App.screenHeight);
            this.map.draw(gc);
            this.plane.draw(gc);
            for (ExplosionAnimation ex : explosion) {
                ex.draw(gc);
            }
        }
        else{
            gameOver();
        }
    }

    private void start() {
        this.gameScene.setCursor(Cursor.NONE);
        try {
            Robot robot = new Robot();
            robot.mouseMove(650, 550);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        
        this.plane = new Plane(650, 550);
        this.map = new MyMap(0, 0);

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
        gc.clearRect(0, 0, App.screenWidth, App.screenHeight);
        gameloop.stop();
        gameScene.setCursor(Cursor.DEFAULT);
        ImageView gameOverBg = new ImageView("file:images/game-over.png");
        gameOverBg.setFitWidth(App.screenWidth);
        gameOverBg.setFitHeight(App.screenHeight);
        // exit button
        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/UI/back.png");
        exit.setFitWidth(216);
        exit.setFitHeight(108);
        exitButton.setTranslateX(200);
        exitButton.setTranslateY(570);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(e-> {
            Stage stage = (Stage) this.gameScene.getWindow();
            stage.setScene(App.menuScene);
        });

        // restart button
        Button restartButton = new Button();
        ImageView restart = new ImageView("file:images/UI/restart.png");
        restart.setFitWidth(216);
        restart.setFitHeight(108);
        restartButton.setTranslateX(900);
        restartButton.setTranslateY(570);
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

    public void gameWin(){
        gameloop.stop();
        gc.clearRect(0, 0, App.screenWidth, App.screenHeight);
        System.out.println("Game WIN!");
        Stage stage = (Stage) this.gameScene.getWindow();
        this.getChildren().remove(canvas);
        gameScene.setCursor(Cursor.DEFAULT);
        ImageView gameOverBg = new ImageView("file:images/UI/youWin.png");
        gameOverBg.setFitWidth(App.screenWidth);
        gameOverBg.setFitHeight(App.screenHeight);
        // exit button
        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/UI/back.png");
        exit.setFitWidth(216);
        exit.setFitHeight(108);
        exitButton.setTranslateX(200);
        exitButton.setTranslateY(570);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(e-> {
            stage.setScene(App.menuScene);
        });

        // restart button
        Button restartButton = new Button();
        ImageView restart = new ImageView("file:images/UI/restart.png");
        restart.setFitWidth(216);
        restart.setFitHeight(108);
        restartButton.setTranslateX(900);
        restartButton.setTranslateY(570);
        restartButton.setPrefSize(restart.getFitWidth(), restart.getFitHeight());
        restartButton.setGraphic(restart);
        restartButton.setStyle("-fx-background-color: Transparent");
        restartButton.setCursor(Cursor.HAND);
        restartButton.setOnAction(e-> {
            this.getChildren().removeAll(gameOverBg,exitButton,restartButton);
            this.getChildren().add(canvas);
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
