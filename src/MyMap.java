import java.util.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class MyMap extends Entity {
	final static int mainMap = 1;
	final static int infiniteMap = 2;
	final static int ninjaleadMap = 3;

	enum MonsterType{
		CHICKEN1,
		CHICKEN2
	}

	protected boolean winningMap = false;
	protected List<Monster> monsters;
	protected List<DropItem> dropItems;
	protected List<Meteorite> meteorites;
	protected Sprite background = new Sprite();
	protected Random rand = new Random();
	protected ChickenBoss boss;
	protected int currentWave;
	protected int spriteCounter = -1;
	protected final int framePerSprite = 2;
	protected boolean hasBoss = false;

	public MyMap(){
		this.setImage("file:images/Space/space.png", 0, 0);
		this.monsters = new ArrayList<>();
		this.dropItems = new ArrayList<>();
		this.meteorites = new ArrayList<>();
		boss = new ChickenBoss(500.0,200.0);
		currentWave = 0;
		for(int i=1;i<=10;i++){
			background.addSprite(new Image("file:images/Space/Image" + i +".jpg"));
		}
		this.image = background.getCurrentSprite();
	}

	public void update(){
		//update background
		//spriteCounter becomes 0 after each time it reachs framePerSprite
        this.spriteCounter++;
        if( this.spriteCounter > framePerSprite){
            this.spriteCounter = 0;
        }
        background.setCurrentSpriteNum(spriteCounter);
        this.image = background.getCurrentSprite();

		// 0.5% per frame
		if (rand.nextDouble(0, 1) < 0.005) {
			meteorites.add(new Meteorite(rand.nextDouble(0, App.screenWidth)));
		}
		if(currentWave < 3){
			if(monsters.isEmpty()){
				currentWave++;
				switch(currentWave){
				case 1:
					spawn(MonsterType.CHICKEN1);
					break;
				case 2:
					spawn(MonsterType.CHICKEN2);
					break;
				case 3:
					this.hasBoss = true;					
					break;
				}
			}
		}
		else{
			boss.update();
			Iterator<Monster> it = this.boss.getSmallerMonster().iterator();
			while (it.hasNext()) {
				Monster monster = it.next();
				if (!monster.isAlive()) {
					DropItem dropItem = monster.dropSomething();
					if (dropItem != null) {
						this.dropItems.add(dropItem);
					}
					it.remove();
				}
			}
			if(boss.isWinning()){
				this.winningMap = true;
			}
		}
	}

	public void draw(GraphicsContext gc){
        gc.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		Iterator<Meteorite> meteoIterator = this.meteorites.iterator();
		while (meteoIterator.hasNext()) {
			Meteorite m = meteoIterator.next();
			if (m.isStop()) {
				meteoIterator.remove();
			}
			else {
				m.draw(gc);
			}
		}
		if(currentWave<3){
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
		}
		else{
			boss.draw(gc);
		}

		Iterator<DropItem> it2 = this.dropItems.iterator();
		while (it2.hasNext()){
			DropItem dropItem = it2.next();
			if (dropItem.isMoving()) {
				dropItem.draw(gc);
			}
			else {
				it2.remove();
			}
		}
	}

	public void spawn(MonsterType monsterType) {
		for (int i = 0; i < rand.nextInt(10, 20); i++){
			Monster monster = new Monster();
			switch(monsterType){
			case CHICKEN1:
				monster = new Monster("file:images/Invader/chicken.png",10);
				break;
			case CHICKEN2:
				monster = new Monster("file:images/Invader/chicken1.1.png",30);
				monster.setOffset(10,10);
				break;
			}
			this.monsters.add(monster);
		}
	}
	
	// public void move() {
	// 	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e-> {
	// 		if (this.getY() + 3 > this.getHeight()) {
	// 			this.setY(0);
	// 		}
	// 		else {
	// 			this.setY(this.getY() + 1);
	// 		}
	// 	}));
	// 	timeline.setCycleCount(Timeline.INDEFINITE);
	// 	timeline.play();
	// }

	public boolean isWinning(){
		return winningMap;
	}

	public boolean hasBoss(){
		return this.hasBoss;
	}

	public ArrayList<Monster> getBossSmallerMonster() {
		return this.boss.getSmallerMonster();
	}

	public ChickenBoss getBoss(){
		return this.boss;
	}

	public List<Monster> getMonsters() {
		return this.monsters;
	}
	
	public List<DropItem> getDropItems() {
		return this.dropItems;
	}

	public List<Meteorite> getMeteorites() {
		return this.meteorites;
	}

	//utility
	/* Convenience method to convert RGB values (in the range 0-255) into a single integer */
    private static int colour(int r, int g, int b) {
        return (r*65536) + (g*256) + b;
    }
}