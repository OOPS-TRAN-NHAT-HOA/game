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
    private final int fps = 60;
    private double gameWidth;
    private double gameHeight;

    private MyMap map;
    private Plane plane;
    private CollisionHandler collisionHandler;
    private List<ExplosionAnimation> explosion = new ArrayList<>();

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;
    private AnimationTimer gameloop;
    
    GamePane(int typeOfMap) {
        // setup
        this.canvas = new Canvas(App.screenWidth, App.screenHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.gameScene = new Scene(this);
        this.getChildren().add(canvas);
        
        this.collisionHandler = new CollisionHandler();
        this.start(typeOfMap);
    }

    private void update(Scene scene){
        plane.update(scene);
        // if (this.map.getMonsters().size() == 0 && this.plane.isAlive()) 
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
        if (this.map.hasBoss()) {
            for (Monster monster : this.map.getBossSmallerMonster()) {
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
        }
        for (Monster monster : this.map.getMonsters()) {
            if (collisionHandler.checkCollision(this.plane, monster)) {
                this.plane.exploding();
            }
            for (Bullet bullet : this.plane.getBullets()) {
                if (collisionHandler.checkCollision(bullet, monster)) {
                    System.out.println(bullet.getDamage());
                    monster.takeDamage(bullet.getDamage());
                    explosion.add(new ExplosionAnimation(1, bullet.getX() - 30, bullet.getY() - 25));
                    bullet.stop();
                }
            }
            for (Meteorite meteorite : this.map.getMeteorites()) {
                if (collisionHandler.checkCollision(monster, meteorite)) {
                    monster.takeDamage(monster.totalHitPoint / 2);
                    explosion.add(new ExplosionAnimation(2, meteorite.getX() + meteorite.getWidth() / 3, meteorite.getY() + meteorite.getHeight() / 3));
                    meteorite.stop();
                }
            }
        }

        // Meteorite update
        for (Meteorite meteorite : this.map.getMeteorites()) {
            if (this.map.hasBoss() && collisionHandler.checkCollision(this.map.getBoss(), meteorite)) {
                this.map.getBoss().takeDMG(20);
                explosion.add(new ExplosionAnimation(2, meteorite.getX() + meteorite.getWidth() / 3, meteorite.getY() + meteorite.getHeight() / 3));
                meteorite.stop();
            }
            else if (collisionHandler.checkCollision(plane, meteorite)) {
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

    private void start(int typeOfMap) {
        this.gameScene.setCursor(Cursor.NONE);
        try {
            Robot robot = new Robot();
            robot.mouseMove(650, 550);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        
        this.plane = new Plane(650, 550);
        switch (typeOfMap) {
            case MyMap.mainMap:
                this.map = new MyMap();
                break;
            case MyMap.infiniteMap:
                this.map = new InfiniteMap();
                break;
            case MyMap.ninjaleadMap:
                this.map = new NinjaleadMap();
                break;
            default:
                break;
        }

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
        ImageView exit = new ImageView("file:images/UI/back3.png");
        exit.setFitWidth(216);
        exit.setFitHeight(108);
        exitButton.setTranslateX(600);
        exitButton.setTranslateY(570);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(e-> {
            App.planeType = -1;
            Stage stage = (Stage) this.gameScene.getWindow();
            stage.setScene(App.menuScene);
        });

        this.getChildren().addAll(gameOverBg,exitButton);
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
        ImageView exit = new ImageView("file:images/UI/back3.png");
        exit.setFitWidth(216);
        exit.setFitHeight(108);
        exitButton.setTranslateX(600);
        exitButton.setTranslateY(570);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(e-> {
            App.planeType = -1;
            stage.setScene(App.menuScene);
        });

        this.getChildren().addAll(gameOverBg,exitButton);
    }    

    public double getScreenWidth(){
        return gameWidth;
    }

    public double getScreenHeight(){
        return gameHeight;
    }
}
