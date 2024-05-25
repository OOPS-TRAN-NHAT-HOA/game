import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.*;

public class App extends Application {
    static protected Pane menuPane;
    static protected Scene menuScene;

    static protected Pane secondMenuPane;
    static protected Scene secondMenuScene;

    static protected Pane thirdMenuPane;
    static protected Scene thirdMenuScene;

    static protected double screenWidth;
    static protected double screenHeight;
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
    }
    static public int planeType = -1;

    @Override
    public void start(Stage stage) {
        Image icon = new Image("file:images/Invader/chicken.png");
        stage.getIcons().add(icon);

        //Main menu -------------------------------------------------------------------------------------------
        
        ImageView background = new ImageView("file:images/UI/background2.jpg");
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);

        ImageView gameName = new ImageView("file:images/UI/gameName.png");
        gameName.setTranslateX(0);
        gameName.setTranslateY(50);
        gameName.setFitWidth(screenWidth);
        App.menuPane = new Pane(); 

        ImageView play = new ImageView("file:images/UI/play.png");
        Button playButton = new Button();
        play.setFitWidth(216);
        play.setFitHeight(108);
        playButton.setTranslateX(600);
        playButton.setTranslateY(570);
        playButton.setPrefSize(play.getFitWidth(), play.getFitHeight());
        playButton.setGraphic(play);
        playButton.setStyle("-fx-background-color: Transparent");
        playButton.setCursor(Cursor.HAND);

        ImageView about = new ImageView("file:images/UI/about.png");
        Button aboutButton = new Button();
        about.setFitWidth(216);
        about.setFitHeight(108);
        aboutButton.setTranslateX(200);
        aboutButton.setTranslateY(570);
        aboutButton.setPrefSize(about.getFitWidth(), about.getFitHeight());
        aboutButton.setGraphic(about);
        aboutButton.setStyle("-fx-background-color: Transparent");
        aboutButton.setCursor(Cursor.HAND);

        Button backButton = new Button();
        ImageView back = new ImageView("file:images/UI/back.png");
        back.setFitWidth(216);
        back.setFitHeight(108);
        backButton.setTranslateX(1000);
        backButton.setTranslateY(570);
        backButton.setPrefSize(back.getFitWidth(), back.getFitHeight());
        backButton.setGraphic(back);
        backButton.setStyle("-fx-background-color: Transparent");
        backButton.setCursor(Cursor.HAND);

        App.menuPane.getChildren().addAll(background, gameName, playButton, aboutButton, backButton);
        App.menuScene = new Scene(menuPane, screenWidth, screenHeight);

        Button backButton2 = new Button();
        ImageView back2 = new ImageView("file:images/UI/back2.png");
        back2.setFitWidth(80);
        back2.setFitHeight(83);
        backButton2.setTranslateX(20);
        backButton2.setTranslateY(20);
        backButton2.setPrefSize(back2.getFitWidth(), back2.getFitHeight());
        backButton2.setGraphic(back2);
        backButton2.setStyle("-fx-background-color: Transparent");
        backButton2.setCursor(Cursor.HAND);

        playButton.setOnAction(e -> {
             stage.setScene(secondMenuScene);
        });

        aboutButton.setOnAction(e->{
            Canvas canvas = new Canvas(App.screenWidth, App.screenHeight);
            menuPane.getChildren().add(canvas);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            int xOffset = 150, yOffset = 100;
            drawAboutWindow(xOffset, yOffset, (int) this.screenWidth-2*xOffset, (int) this.screenHeight-2*yOffset, gc);
            menuPane.getChildren().addAll(backButton2);
            backButton2.setOnAction(ie->{
                menuPane.getChildren().removeAll(canvas,backButton2);   
            });
        });
        backButton.setOnAction(e -> stage.close());

        // Secondy menu -------------------------------------------------------------------------------------

        App.secondMenuPane = new Pane(); 
        ImageView background2 = new ImageView("file:images/UI/background2.jpg");
        background2.setFitWidth(screenWidth);
        background2.setFitHeight(screenHeight);

        ImageView gameSelect = new ImageView("file:images/UI/gameSelect.png");
        gameSelect.setTranslateX(350); 
        gameSelect.setTranslateY(50);

        ImageView stageMode = new ImageView("file:images/UI/stageMode.png");
        stageMode.setFitWidth(216);
        stageMode.setFitHeight(108);
        Button newGameButton= new Button();
        newGameButton.setPrefSize(stageMode.getFitWidth(), stageMode.getFitHeight());
        newGameButton.setGraphic(stageMode);
        newGameButton.setStyle("-fx-background-color: Transparent"); 
        newGameButton.setTranslateX(100); 
        newGameButton.setTranslateY(570);
        newGameButton.setCursor(Cursor.HAND);

        ImageView infiniteMode = new ImageView("file:images/UI/infiniteMode.png");
        infiniteMode.setFitWidth(216);
        infiniteMode.setFitHeight(108);
        Button infiniteButton= new Button();
        infiniteButton.setPrefSize(infiniteMode.getFitWidth(), infiniteMode.getFitHeight());
        infiniteButton.setGraphic(infiniteMode);
        infiniteButton.setStyle("-fx-background-color: Transparent"); 
        infiniteButton.setTranslateX(450); 
        infiniteButton.setTranslateY(570);
        infiniteButton.setCursor(Cursor.HAND);

        ImageView ninjaMode = new ImageView("file:images/UI/ninjaMode.png");
        ninjaMode.setFitWidth(216);
        ninjaMode.setFitHeight(108);
        Button ninjaButton = new Button();
        ninjaButton.setPrefSize(ninjaMode.getFitWidth(), ninjaMode.getFitHeight());
        ninjaButton.setGraphic(ninjaMode);
        ninjaButton.setStyle("-fx-background-color: Transparent"); 
        ninjaButton.setTranslateX(800); 
        ninjaButton.setTranslateY(570);
        ninjaButton.setCursor(Cursor.HAND);

        Button backButton3 = new Button();
        ImageView back3 = new ImageView("file:images/UI/back3.png");
        back3.setFitWidth(216);
        back3.setFitHeight(108);
        backButton3.setTranslateX(1150);
        backButton3.setTranslateY(570);
        backButton3.setPrefSize(back3.getFitWidth(), back3.getFitHeight());
        backButton3.setGraphic(back3);
        backButton3.setStyle("-fx-background-color: Transparent");
        backButton3.setCursor(Cursor.HAND);

        App.secondMenuPane.getChildren().addAll(background2, gameSelect, newGameButton, infiniteButton, ninjaButton, backButton3);
        App.secondMenuScene = new Scene(secondMenuPane, screenWidth, screenHeight);

        newGameButton.setOnAction(e -> {
            GamePane battle = new GamePane(MyMap.mainMap);
            stage.setScene(battle.getScene());
        });

        infiniteButton.setOnAction(e -> {
            GamePane battle = new GamePane(MyMap.infiniteMap);
            stage.setScene(battle.getScene());
        });

        ninjaButton.setOnAction(e -> {
            GamePane battle = new GamePane(MyMap.ninjaleadMap);
            stage.setScene(battle.getScene());
        });

        backButton3.setOnAction(e->{
            stage.setScene(menuScene);
        });


        // ----------------------------------------------------------------------------------------------------
        
        // ----------------------------------------------------------------------------------------------------

        
        // exitButton.setOnAction(e-> stage.close());
        // startButton.setOnAction(e-> {
        //     stage.setScene(secondMenuScene);
        // });

        
        
        // backButton.setOnAction(e -> stage.setScene(menuScene));

        //stage
        stage.setTitle("Chicken Invaders");
        stage.setScene(menuScene);
        stage.setFullScreen(false);
        stage.show();
    }

    public void drawAboutWindow(int x, int y, int width, int height, GraphicsContext gc){
        Color c = new Color(1, 1, 1, 0.5);
        gc.setFill(c);
        gc.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(0, 0, 0, 0.8);
        gc.setFill(c);
        gc.fillRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
        String message = "Hãy sẵn sàng cho một trải nghiệm chiến đấu không gian đầy kịch tính \nvà hồi hộp! Trong \"StarWing: Máy Bay Bắn Quái\", bạn sẽ nhập vai phi \ncông của chiếc phi thuyền chiến đấu hiện đại nhất của Liên Minh Liên \nSao, đối mặt với những đợt tấn công khốc liệt từ lũ quái vật không gian \nhung ác. Trong tương lai xa xôi, khi nhân loại đã khám phá và định cư \ntrên nhiều hành tinh, xuất hiện những sinh vật ngoài hành tinh khổng lồ \nvà tàn ác, được gọi là Quái Không Gian, bắt đầu xâm chiếm các thuộc \nđịa của con người. Để bảo vệ sự sống còn của Liên Minh Liên Sao, đội \nphi công tinh nhuệ StarWing được thành lập. Với vai trò là phi công xuất \nsắc của đội StarWing, hãy sử dụng kỹ năng của mình để tiêu diệt kẻ thù \nvà bảo vệ hòa bình cho thiên hà!";
        int textXOffset = 15, textYOffset = 60;
        int textX = x+textXOffset, textY = y+textYOffset;
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Verdana", 30F));
        for(String line : message.split("\n")){
            gc.fillText(line, textX , textY);
            textY += 50;
        }
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}
