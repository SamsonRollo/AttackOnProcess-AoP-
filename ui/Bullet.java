package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.Rectangle;

public class Bullet extends JLabel{
    private final String BULLET_IMG_PATH = "src/bullet.png";
    private Point currentPoint;
    private int w, h;
    private final int velocity = 2;
    private boolean alive = true;

    public Bullet(int x, int y){
        ImageLoader il = new ImageLoader(BULLET_IMG_PATH, "bullet");

        setIcon(new ImageIcon(il.getBuffImage()));
        setCurrentPoint(x, y);
        setW(il.getBuffImage().getWidth());
        setH(il.getBuffImage().getHeight());
        setBounds(getX(), getY(), getW(), getH());
    }

    public void updateBullet(){
        moveCurrentPoint(getX()+velocity, getY());
    }

    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), getW(), getH());
    }
    
    public void moveCurrentPoint(int x, int y){
        this.currentPoint.move(x, y);
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isAlive(){
        return this.alive;
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
