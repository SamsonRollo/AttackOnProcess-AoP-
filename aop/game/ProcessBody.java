package aop.game;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

public class ProcessBody extends GameObject{
    private int currentSpriteIdx = 0;

    public ProcessBody(AOP aop, int currentSpriteIdx, int x, int y){
        this.aop = aop;
        this.currentSpriteIdx = currentSpriteIdx;
        BufferedImage sprite = aop.getBodySprite().getSprites()[currentSpriteIdx++];
        setGameObjectAux(sprite, x, y);
    }

    public void updateSprite(){
        BufferedImage sprite = aop.getBodySprite().getSprites()[(currentSpriteIdx++)%6];
        setIcon(new ImageIcon(sprite));
    }

    @Override
    protected boolean calculateAlive() {
        return true;
    }
}
