import java.awt.*;
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

    public Canvas canvas;
    public GraphicsContext gc;
    public Scene gameScene;
    private AnimationTimer gameloop;
    
    GamePane() {
        // get your screenSize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameWidth = screenSize.getWidth();
        gameHeight = screenSize.getHeight();

        this.collisionHandler = new CollisionHandler();

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

        // monster update
        for (Monster monster : this.map.getMonsters()) {
            if (collisionHandler.checkCollision(this.plane, monster)) {
                this.plane.die();
            }
            for (Bullet bullet : this.plane.getBullets()) {
                if (collisionHandler.checkCollision(bullet, monster)) {
                    monster.takeDamage(bullet.getDamage());
                    bullet.stop();
                }
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
    }

    private void draw(GraphicsContext gc){
        if(plane.isAlive()){
            gc.clearRect(0, 0, gameWidth, gameHeight);
            this.map.draw(gc);
            this.plane.draw(gc);
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
