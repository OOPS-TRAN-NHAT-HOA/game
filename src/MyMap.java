

import java.util.*;
import javafx.scene.canvas.GraphicsContext;

public class MyMap extends Entity {

    private List<Monster> monsters;
    private GamePane gp;

	public MyMap(double x, double y, GamePane _gp){
		setImage("file:images/space.png", x, y);
		monsters = new ArrayList<>();
		gp = _gp;
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), gp.getScreenWidth(), gp.getScreenHeight());
    }

	public void spawn(GamePane gamePane) {
		for (int i = 0; i < 10; i++) {
			Monster monster = new Monster("file:images/chicken.png");
			this.monsters.add(monster);
		}
	}

	public List<Monster> getMonsters() {
		return this.monsters;
	}
}