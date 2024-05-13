import java.util.ArrayList;
import java.awt.image.*;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javafx.scene.image.*;
import java.awt.Graphics2D;

public class Sprite {

	private int currentSpriteNum = 0;
	private int totalSprite;

	private BufferedImage spriteSheet;
	private ArrayList<Image> spriteArrayList = new ArrayList<Image>();
	

	private void addSprite(Image newSprite){
		spriteArrayList.add(newSprite);
		totalSprite = spriteArrayList.size() - 1;
	}

	public Sprite(String path){
		try{

			this.spriteSheet = ImageIO.read(new File(path));

		}catch(IOException e)	{e.printStackTrace();}

		//các sprite xếp theo hàng ngang
		int x = 0;
		int index = 0;
		BufferedImage bufferedImageTemp = null;
		if(this.spriteSheet != null) {
			int tileSize = spriteSheet.getHeight();
			while(x < this.spriteSheet.getWidth()){
				bufferedImageTemp = this.spriteSheet.getSubimage( x, 0, tileSize, tileSize);
				javafx.scene.image.Image tmp = SwingFXUtils.toFXImage(rotate(bufferedImageTemp,-90),null);
				addSprite(tmp);
				x += tileSize;
			}
		}
	}

	//rotate Image
	private BufferedImage rotate(BufferedImage bimg, double angle) {
	    int w = bimg.getWidth();    
	    int h = bimg.getHeight();

	    BufferedImage rotated = new BufferedImage(w, h, bimg.getType());  
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawImage(bimg, null, 0, 0);
	    graphic.dispose();
	    return rotated;
	}

	public void getCurrentSpriteNum( int spriteCounter){
		if( spriteCounter == 0){
			if(this.currentSpriteNum < this.totalSprite){
				this.currentSpriteNum++;
			}
			else{
				this.currentSpriteNum = 0;
			}
		}
	}

	public Image getCurrentSprite(){
		if(!this.spriteArrayList.isEmpty()){
			return this.spriteArrayList.get(currentSpriteNum);
		}
		return null;
	}
}