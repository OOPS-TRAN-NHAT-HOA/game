import java.util.Iterator;
import java.util.Random;

public class InfiniteMap extends MyMap {
	InfiniteMap(){
		this.currentMap = infiniteMap;
		this.hasBoss = true;
		this.boss = new ChickenBoss(500, 200);
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

        if(currentMap == infiniteMap){
			// 0.5% per frame
			if (rand.nextDouble(0, 1) < 0.005) {
				meteorites.add(new Meteorite(rand.nextDouble(0, App.screenWidth)));
			}

			this.boss.update();
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
				this.boss.resetBoss();
			}
	    }
	}
}
