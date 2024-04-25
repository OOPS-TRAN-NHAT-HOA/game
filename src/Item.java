import java.fx.scene.image.ImageView;// Declare class java.fx...

import javafx.scene.ImageCursor;

import java.fx.scene.image.ImageView;//Declare class java.fx...

public class Item {  // Dlare class Item
    protected ImageView shape; // Declare object shape 

    public Item(String path, double x, double y){ //Declare Item method
        this.shape = new ImageView(path); // Create shape variable
        this.shape.setX(x); // Take x in shape
        this.shape.setY(y); // Take y in shape


    }

    public ImageView getShape(){
        return shape;        // return shape variable with getShape() method
    }

    public void setShape(ImageView shape){
        this.shape = shape;
    }

    public double getX(){
        return shape.getX();
    }
     public double getY(){
        retrun shape.getY();
     }
     
}

