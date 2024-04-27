import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    private final int screenWidth = 1366;
    private final int screenHeigth = 768;
    private final int fps = 60;

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // icon
        Image icon = new Image("file:images/chicken.png");
        stage.getIcons().add(icon);

        //Main menu
        Pane menuPane = new Pane();
        ImageView bg = new ImageView("file:images/space.png");
        Scene menuScene = new Scene(menuPane, screenWidth, screenHeigth);

        // title
        ImageView title = new ImageView("file:images/title.png");
        title.setX(308);
        title.setY(50);        

        // exit button
        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/exit.png");
        exitButton.setTranslateX(50);
        exitButton.setTranslateY(630);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);

        exitButton.setOnAction(e-> stage.close());

        // start button
        Button startButton = new Button();
        ImageView start = new ImageView("file:images/start.png");
        startButton.setTranslateX(1010);
        startButton.setTranslateY(630);
        startButton.setPrefSize(start.getFitWidth(), start.getFitHeight());
        startButton.setGraphic(start);
        startButton.setStyle("-fx-background-color: Transparent");
        startButton.setCursor(Cursor.HAND);

        startButton.setOnAction(e-> {
            GamePane battle = new GamePane();
            battle.getScene().setCursor(Cursor.NONE);
            stage.setScene(battle.getScene());
        });

        menuPane.getChildren().addAll(bg,title,exitButton,startButton);

        //stage
        stage.setScene(menuScene);
        stage.setTitle("Chicken Invaders");
        stage.setFullScreen(false);
        // stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
        stage.show();
    }
}
