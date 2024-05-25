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
    static protected Pane startPane;
    static protected Scene startMenu;

    static protected Pane menuPane;
    static protected Scene menuScene;

    static protected Pane thirdMenuPane;
    static protected Scene thirdMenuScene;

    static protected Pane winPane;
    static protected Scene winScene;

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
        
        // Menu -------------------------------------------------------------------------------------
        ImageView background2 = new ImageView("file:images/UI/background2.jpg");
        background2.setFitWidth(screenWidth);
        background2.setFitHeight(screenHeight);

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

        App.menuPane.getChildren().addAll(background2, gameName, playButton, aboutButton, backButton);
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
            GamePane battle = new GamePane();
            stage.setScene(battle.getScene());
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

        // ----------------------------------------------------------------------------------------------------
        
        //stage
        stage.setTitle("StarWing");
        stage.setScene(menuScene);
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
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
}
