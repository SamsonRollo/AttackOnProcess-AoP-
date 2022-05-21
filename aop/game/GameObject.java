package aop.game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gen.ImageLoader;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject extends JLabel {
    protected AOP aop;
    protected String IMG_PATH;
    protected Point currentPoint;
    protected Point initPoint;
    protected int w, h;
    protected int velocity = 2;
    protected boolean alive = true;

    public void setGameObject(String alt, int x, int y){
        ImageLoader il = new ImageLoader(IMG_PATH, alt);
        setGameObjectAux(il.getBuffImage(), x, y);
    }

    public void setGameObjectAux(BufferedImage img, int x, int y){
        setIcon(new ImageIcon(img));
        setCurrentPoint(x, y);
        setW(img.getWidth());
        setH(img.getHeight());
        setBounds(getX(), getY(), getW(), getH());
    }

    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), getW(), getH());
    }

    public void updatePosition(int x, int y){
        setCurrentPoint(x, y);
    }
    
    public void moveCurrentPoint(int x, int y){
        this.currentPoint.move(x, y);
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isAlive(){
        if(!calculateAlive() || !this.alive)
            return false; 
        return true;
    }

    protected int getVelocity(){
        return this.velocity;
    }

    protected void incrementVelocity(){
        this.velocity++;
    }

    protected void incrementVelocity(int velocity){
        this.velocity += velocity;
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

    public Point getInitPoint(){
        return this.initPoint;
    }

    public int getInitX(){
        return (int)this.initPoint.getX();
    }

    public int getInitY(){
        return (int)this.initPoint.getY();
    }

    public void setInitPoint(int x, int y){
        this.initPoint = new Point(x,y);
    }

    public void setInitPoint(Point p){
        this.initPoint = p;
    }

    abstract protected boolean calculateAlive();

}
