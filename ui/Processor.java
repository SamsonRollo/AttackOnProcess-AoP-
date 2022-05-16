package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Processor extends JLabel{
    private final String PROC_IMG_PATH = "src/cpu.png";
    private AOP aop;
    private Point initPoint;
    private Point currentPoint;
    private int w, h;
    private boolean dragged = false;

    public Processor(AOP aop, int x, int y){
        this.aop = aop;
        ImageLoader il = new ImageLoader(PROC_IMG_PATH, "processor");

        setIcon(new ImageIcon(il.getBuffImage()));
        setCurrentPoint(x, y);
        setW(il.getBuffImage().getWidth());
        setH(il.getBuffImage().getHeight());
        setBounds(getX(), getY(), getW(), getH());
        setInitPoint(x, y);

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                if(aop.isPlay()){
                    setDragged(true);
                    setCurrentPoint(getInitX() + e.getX()-22, getInitY() + e.getY()-22);
                    aop.updateUI();
                }
            }
        });

        addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                //if valid position then setbounds
                Point drop = getDropLocation(getX()+22,getY()+22);

                if(drop.getX()==-1 && drop.getY()==-1)
                    drop = getInitPoint();

                if(!drop.equals(getInitPoint()))
                    aop.setHasProcessorAt(getInitY(), (int)drop.getY());

                setLocation(drop);
                setInitPoint(drop);
                setCurrentPoint(drop);
                setDragged(false);
                aop.updateUI();
            }
        });
    }

    public void setDragged(boolean dragged){
        this.dragged = dragged;
    }

    public boolean isDragged(){
        return this.dragged;
    }

    private Point getDropLocation(int x, int y){
        return aop.getDropLocation(new Point(x,y));
    }

    public Point getInitPoint(){
        return this.initPoint;
    }

    public Point getCurrentPoint(){
        return this.currentPoint;
    }

    public int getInitX(){
        return (int)this.initPoint.getX();
    }

    public int getInitY(){
        return (int)this.initPoint.getY();
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

    public void setInitPoint(int x, int y){
        this.initPoint = new Point(x,y);
    }

    public void setInitPoint(Point p){
        this.initPoint = p;
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
