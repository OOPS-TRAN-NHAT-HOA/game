import java.util.*;
import javafx.scene.canvas.GraphicsContext;

public class MyMap extends Entity {

    private List<Monster> monsters;

	public MyMap(double x, double y){
		setImage("file:images/space.png", x, y);
		monsters = new ArrayList<>();
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

	public void spawn(GamePane gamePane) {
		for (int i = 0; i < 5; i++) {
			Monster monster = new Monster("file:images/chicken.png");

			gamePane.getChildren().add(monster.getColliBox());
			this.monsters.add(monster);
		}
	}

	public List<Monster> getMonsters() {
		return this.monsters;
	}
}