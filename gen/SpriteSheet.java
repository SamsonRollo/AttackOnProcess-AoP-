package gen;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage[] sprites;

	public SpriteSheet(String path, int w, int h) {
		ImageLoader il = new ImageLoader(path, "sprite");
		loadSprite(il.getBuffImage(), w, h);
	}

	private void loadSprite(BufferedImage image, int w, int h){
		int size = (int)Math.floor(image.getWidth()/w);
		sprites = new BufferedImage[size];
		
		for(int i=0; i<size; i++)
			sprites[i] = image.getSubimage(w*i, 0, w, h);
	}

	public BufferedImage[] getSprites(){
		return this.sprites;
	}
}