
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.*;

public class Plane extends Entity {

    private boolean winner;
    private boolean alive;
    private int speed = 15;

    public Plane(double x, double y) {
        setImage("images/plane.png", x, y);
        setCollidable(true);
        winner = false;
        alive = true;
    }

    public void update(Scene scene){
        final List<KeyCode> acceptedCodes = Arrays.asList(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
        final Set<KeyCode> codes = new HashSet<>();
        scene.setOnKeyReleased(e -> {
                System.out.println("Release!");
                if (acceptedCodes.contains(e.getCode())){ 
                codes.remove(e.getCode()); 
                System.out.println("\nBREAK\n");
            }});
            scene.setOnKeyPressed(e -> {
                System.out.println("Pressed!");
                if (acceptedCodes.contains(e.getCode())) {
                    codes.add(e.getCode());
                    if (codes.contains(KeyCode.LEFT)) {
                        moveLeft();    
                    }
                    else if (codes.contains(KeyCode.RIGHT)) {
                        moveRight();
                    }
                    else if (codes.contains(KeyCode.UP)) {
                        moveUp();
                    }
                    else if (codes.contains(KeyCode.DOWN)) {
                        moveDown();
                    }
                }
            });
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, getX(), getY(), this.image.getWidth(), this.image.getHeight());
    }

    public void moveLeft() {
       setX(getX() - speed);
    }
    
    public void moveRight() {
        setX(getX() + speed);
    }
    
    public void moveUp() {
        setY(getY() - speed);
    }
    
    public void moveDown() {
        setY(getY() + speed);
    }
}
