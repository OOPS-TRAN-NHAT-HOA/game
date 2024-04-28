
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;
import javafx.scene.canvas.GraphicsContext;

public class MyMap extends Entity {

    private List<Monster> monsters;
	private Random rand = new Random();

	public MyMap(double x, double y){
		setImage("file:images/space.png", x, y);
		monsters = new ArrayList<>();
	}

	public void draw(GraphicsContext gc){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gc.drawImage(this.getImage(), this.getX(), this.getY(), screenSize.getWidth(), screenSize.getHeight());
    }

	public void spawn(GamePane gamePane) {
		for (int i = 0; i < rand.nextInt(1, 10); i++) {
			Monster monster = new Monster("file:images/chicken.png");
			this.monsters.add(monster);
		}
	}

	public List<Monster> getMonsters() {
		return this.monsters;
	}
}