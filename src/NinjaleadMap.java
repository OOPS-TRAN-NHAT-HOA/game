import java.util.Iterator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class NinjaleadMap extends MyMap {
    private double ratio;

    NinjaleadMap() {
        this.ratio = 0.1;
        this.currentMap = ninjaleadMap;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> ratio = ratio + 0.001));
        timeline.setCycleCount(400); // which mean max(ratio) == 50% per frame :)
        timeline.play();
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

        if(currentMap == ninjaleadMap){
    		// 0.5% per frame
    		if (rand.nextDouble(0, 1) < ratio) {
    			meteorites.add(new Meteorite(rand.nextDouble(0, App.screenWidth)));
    		}
            
            if (rand.nextDouble(0, 1) < 0.0001) {
                this.spawn(MonsterType.CHICKEN1);
                this.spawn(MonsterType.CHICKEN2);
            }
        }
    }
}
