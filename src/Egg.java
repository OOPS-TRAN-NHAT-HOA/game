
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Egg extends Item {
    
    public Egg(double x, double y, Pane pane, Plane plane) {
        super("file:images/egg.png", x, y);//Call superclass constructor to initialize the image and position
        shape.setX(x);
        shape.setY(y);
        pane.getChildren().add(1,this.shape);//// Add the shape to the pane at index 1
        // start move


        Timeline animation = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.millis(80), e -> {
          // Move the egg downwards
           this.shape.setY(this.shape.getY() + 10);
           if (this.shape.getY() > pane.getHeight())// Check if the egg is out of the pane's bounds
               animation.stop();// Stop the animation if egg is out of bounds

           if (this.shape.getY() >= plane.getShape().getY() - 15 &&
                   this.shape.getY() <= plane.getShape().getY() + 80 &&
                   this.shape.getX() >= plane.getShape().getX() &&
                   this.shape.getX() <= plane.getShape().getX() + 75) {
                                // Check for collision with the plane

               animation.stop();
               // Stop the animation if collision occurs
               plane.die();
               // Call the plane's die method
               this.shape.setVisible(false);
               // Make the egg shape invisible
           }
        });
        animation.getKeyFrames().add(frame);
        // Add the frame to the animation
        animation.setCycleCount(Timeline.INDEFINITE);
         // Set the animation to repeat indefinitely
        animation.play();
         // Start the animation
    }
    
}