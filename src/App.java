import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.awt.*;

public class App extends Application {
    static protected Pane menuPane;
    static protected Scene menuScene;

    static protected Pane secondMenuPane;
    static protected Scene secondMenuScene;

    static protected double screenWidth;
    static protected double screenHeight;
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
    }

    @Override
    public void start(Stage stage) {
        // get your screenSize

        // icon
        Image icon = new Image("file:images/Invader/chicken.png");
        stage.getIcons().add(icon);

        //Main menu -------------------------------------------------------------------------------------------
        App.menuPane = new Pane();
        App.menuScene = new Scene(menuPane, screenWidth, screenHeight);

        ImageView background = new ImageView("file:images/Space/space.png");
        background.setFitWidth(screenWidth);

        ImageView title = new ImageView("file:images/title.png");
        title.setX(283);
        title.setY(30);        

        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/exit.png");
        exitButton.setTranslateX(30);
        exitButton.setTranslateY(630);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);

        Button startButton = new Button();
        ImageView start = new ImageView("file:images/start.png");
        startButton.setTranslateX(930);
        startButton.setTranslateY(630);
        startButton.setPrefSize(start.getFitWidth(), start.getFitHeight());
        startButton.setGraphic(start);
        startButton.setStyle("-fx-background-color: Transparent");
        startButton.setCursor(Cursor.HAND);

        menuPane.getChildren().addAll(background, title, exitButton, startButton);
        // ----------------------------------------------------------------------------------------------------
        
        // Secondy menu -------------------------------------------------------------------------------------
        ImageView background2 = new ImageView("file:images/Space/space.png");
        background2.setFitWidth(screenWidth);

        App.secondMenuPane = new Pane(); 
        Button continueButton = new Button("Continue"); 
        continueButton.setTranslateX(500); 
        continueButton.setTranslateY(100);
        Button newGameButton = new Button("New game");
        newGameButton.setTranslateX(500);
        newGameButton.setTranslateY(200);

        Button backButton = new Button("Back");
        backButton.setTranslateX(500);
        backButton.setTranslateY(300);
        App.secondMenuPane.getChildren().addAll(background2, continueButton, newGameButton, backButton);

        App.secondMenuScene = new Scene(secondMenuPane, screenWidth, screenHeight);




        // ----------------------------------------------------------------------------------------------------
        
        // thirdMenu-------------------------------------------------------------------------------------------
        // TODO: chỗ nãy để chọn máy bay nhưng chưa làm gì cả
        // ----------------------------------------------------------------------------------------------------

        
        exitButton.setOnAction(e-> stage.close());
        startButton.setOnAction(e-> {
            stage.setScene(secondMenuScene);
        });

        continueButton.setOnAction(e-> {
            GamePane battle = new GamePane();
            stage.setScene(battle.getScene());
        });
        newGameButton.setOnAction(e -> {
            GamePane battle = new GamePane();
            stage.setScene(battle.getScene());
        });
        backButton.setOnAction(e -> stage.setScene(menuScene));

        //stage
        stage.setTitle("Chicken Invaders");
        stage.setScene(menuScene);
        stage.setFullScreen(false);
        // stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}
