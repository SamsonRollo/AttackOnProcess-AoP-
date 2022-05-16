package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ProcessBody extends JLabel{
    private Point currentPoint;
    private int w, h;
    private int currentSpriteIdx = 0;
    private AOP aop;

    public ProcessBody(AOP aop, int currentSpriteIdx, int x, int y){
        this.aop = aop;
        this.currentSpriteIdx = currentSpriteIdx;
        BufferedImage sprite = aop.getBodySprite().getSprites()[currentSpriteIdx++];
        setIcon(new ImageIcon(sprite));
        setH(sprite.getHeight());
        setW(sprite.getWidth());
        setCurrentPoint(x, y);
        setBounds(getX(), getY(), getW(), getH());
    }

    public void updateSprite(){
        BufferedImage sprite = aop.getBodySprite().getSprites()[(currentSpriteIdx++)%6];
        setIcon(new ImageIcon(sprite));
    }

    public void updatePosition(int x, int y){
        setCurrentPoint(x, y);
    }

    public Point getCurrentPoint(){
        return this.currentPoint;
    }

    public int getX(){
        return (int)this.currentPoint.getX();
    }

    public int getY(){
        return (int)this.currentPoint.getY();
    }

    public int getW(){
        return this.w;
    }

    public int getH(){
        return this.h;
    }

    public void setCurrentPoint(int x, int y){
        this.currentPoint = new Point(x,y);
    }

    public void setCurrentPoint(Point p){
        this.currentPoint = p;
    }

    public void setW(int w){
        this.w = w;
    }

    public void setH(int h){
        this.h = h;
    }

}
