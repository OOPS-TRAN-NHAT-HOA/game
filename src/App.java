import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        cai lon
        Application.launch(args);
    }

    

    @Override
    public void start(Stage stage) throws Exception {
        // icon
        Image icon = new Image("file:images\\chicken.png");
        stage.getIcons().add(icon);

        // frame
        Pane pane = new Pane();
        Scene scene = new Scene(pane);

        // scene nodes
        // background
        ImageView background = new ImageView("file:images\\space.png"); 
        // title
        ImageView title = new ImageView("file:images\\title.png");
        title.setX(280);
        title.setY(50);
        // start button
        ImageView start = new ImageView("file:images\\start.png");
        start.setX(930);
        start.setY(630);
        start.setCursor(Cursor.HAND);
        // exit button
        ImageView exit = new ImageView("file:images\\exit.png");
        exit.setX(50);
        exit.setY(630);
        exit.setCursor(Cursor.HAND);

        pane.getChildren().addAll(background, title, start, exit);



        
        





        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
        stage.setResizable(false);
        stage.show();
    }
}
