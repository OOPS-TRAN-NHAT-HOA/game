

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class MyMap extends Entity {

    private List<Monster> monsters;
	private Random rand = new Random();
	
	public MyMap(double x, double y){
		setImage("file:images/space.png", x, y);
		monsters = new ArrayList<>();
		this.move();
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		gc.drawImage(this.getImage(), this.getX(), this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
	}

	public void spawn(GamePane gamePane) {
		for (int i = 0; i < rand.nextInt(1, 10); i++) {
			Monster monster = new Monster("file:images/chicken.png");
			this.monsters.add(monster);
		}
	}
	
	public void move() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e-> {
			if (this.getY() + 1 > this.getHeight()) {
				this.setY(0);
			}
			else {
				this.setY(this.getY() + 1);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public List<Monster> getMonsters() {
		return this.monsters;
	}
}