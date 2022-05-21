package gen;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButton extends JButton{
 
    public GameButton(){
        setBorders();
    }

    public GameButton(int x, int y, int w, int h){
        setBorders();
        setBounds(x,y,w,h); 
    }

    public void setBorders(){
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public void setIcons(String selectAddress, String unselectAddress, String alt){
        ImageLoader imgLoader = new ImageLoader(selectAddress, alt);
        setIcon(new ImageIcon(imgLoader.getBuffImage()));
        imgLoader.reloadImage(unselectAddress, alt);
        setRolloverIcon(new ImageIcon(imgLoader.getBuffImage()));

        if(imgLoader.isNull())
            setText(alt);
    }

    public JButton getButton(){
        return this;
    }
}
