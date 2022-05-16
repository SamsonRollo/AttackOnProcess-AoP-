package ui;

import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLoader {
    BufferedImage img = null;
    String alt = null;

	   public ImageLoader(){}

    public ImageLoader(String path, String alt){
        reloadImage(path, alt);
    }

    public boolean reloadImage(String path, String alt){
        try{
            img = ImageIO.read(this.getClass().getClassLoader().getResource(path));
            return true;
        }catch(IOException ioe){
            img = null;
            this.alt = alt;
            return false; 
        }
    }

    public BufferedImage getBuffImage(){
        return img;
    }

    public String getAlt(){
        return alt;
    }
    
    public boolean isNull(){
        return img == null;
    }
}

