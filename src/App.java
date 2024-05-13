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
    private double screenWidth;
    private double screenHeight;

    @Override
    public void start(Stage stage) {
        // get your screenSize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();

        // icon
        Image icon = new Image("file:images/chicken.png");
        stage.getIcons().add(icon);

        //Main menu -------------------------------------------------------------------------------------------
        Pane menuPane = new Pane();
        Scene menuScene = new Scene(menuPane, screenWidth, screenHeight);

        // background
        ImageView background = new ImageView("file:images/space.png");
        background.setFitWidth(screenWidth);

        // title
        ImageView title = new ImageView("file:images/title.png");
        title.setX(283);
        title.setY(30);        

        // exit button
        Button exitButton = new Button();
        ImageView exit = new ImageView("file:images/exit.png");
        exitButton.setTranslateX(30);
        exitButton.setTranslateY(630);
        exitButton.setPrefSize(exit.getFitWidth(), exit.getFitHeight());
        exitButton.setGraphic(exit);
        exitButton.setStyle("-fx-background-color: Transparent");
        exitButton.setCursor(Cursor.HAND);

        // start button
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
        ImageView background2 = new ImageView("file:images/space.png");
        background2.setFitWidth(screenWidth);

        Pane secondMenuPane = new Pane();
        Button continueButton = new Button("Continue");
        continueButton.setTranslateX(500);
        continueButton.setTranslateY(100);

        Button newGameButton = new Button("New game");
        newGameButton.setTranslateX(500);
        newGameButton.setTranslateY(200);

        Button backButton = new Button("Back");
        backButton.setTranslateX(500);
        backButton.setTranslateY(300);
        secondMenuPane.getChildren().addAll(background2, continueButton, newGameButton, backButton);

        Scene secondScene = new Scene(secondMenuPane, screenWidth, screenHeight);




        // ----------------------------------------------------------------------------------------------------
        
        // thirdMenu-------------------------------------------------------------------------------------------
        // chỗ nãy để chọn máy bay nhưng chưa làm gì cả
        // ----------------------------------------------------------------------------------------------------

        
        exitButton.setOnAction(e-> stage.close());
        startButton.setOnAction(e-> {
            stage.setScene(secondScene);
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
