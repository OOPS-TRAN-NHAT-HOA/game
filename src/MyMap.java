import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class MyMap extends Entity {
	// TODO: làm 3 phase, mỗi phase ra một loại quái mới, phase cuối có con boss

    private List<Monster> monsters;
	private List<DropItem> dropItems;
	private Random rand = new Random();
	
	public MyMap(double x, double y){
		this.setImage("file:images/Space/space.png", x, y);
		this.monsters = new ArrayList<>();
		this.dropItems = new ArrayList<>();
		this.move();
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		gc.drawImage(this.getImage(), this.getX(), this.getY() - this.getHeight(), this.getWidth(), this.getHeight());

		Iterator<Monster> it = this.monsters.iterator();
		while (it.hasNext()) {
			Monster monster = it.next();
			if (monster.isAlive()) {
				monster.draw(gc);
			}
			else {
				DropItem dropItem = monster.dropSomething();
				if (dropItem != null) {
					this.dropItems.add(dropItem);
				}
				it.remove();
			}
		}
		Iterator<DropItem> it2 = this.dropItems.iterator();
		while (it2.hasNext()) {
			DropItem dropItem = it2.next();
			if (dropItem.isMoving()) {
				dropItem.draw(gc);
			}
			else {
				it2.remove();
			}
		}
	}

	public void spawn(GamePane gamePane) {
		for (int i = 0; i < rand.nextInt(1, 10); i++) {
			Monster monster = new Monster("file:images/Invader/chicken.png");
			this.monsters.add(monster);
		}
	}
	
	public void move() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e-> {
			if (this.getY() + 3 > this.getHeight()) {
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
	
	public List<DropItem> getDropItems() {
		return this.dropItems;
	}
}