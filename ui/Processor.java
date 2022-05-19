package ui;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Rectangle;

public class Processor extends GameObject{
    private boolean dragged = false;

    public Processor(AOP aop, int x, int y){
        this.aop = aop;
        IMG_PATH = "src/cpu.png";
        setGameObject("cpu", x, y);
        setInitPoint(x, y);

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                if(aop.isPlay()){
                    setDragged(true);
                    setCurrentPoint(getInitX() + e.getX()-22, getInitY() + e.getY()-22);
                }
            }
        });

        addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                Point drop = getDropLocation(getX()+22,getY()+22);

                if(drop.getX()==-1 && drop.getY()==-1)
                    drop = getInitPoint();

                if(!drop.equals(getInitPoint()))
                    aop.setHasProcessorAt(getInitY(), (int)drop.getY());

                setLocation(drop);
                setInitPoint(drop);
                setCurrentPoint(drop);
                setDragged(false);
            }
        });
    }

    public void setDragged(boolean dragged){
        this.dragged = dragged;
    }

    public boolean isDragged(){
        return this.dragged;
    }

    public Point getDropLocation(int x, int y){
        Point point = new Point(-1,-1);
        Rectangle rect = new Rectangle(x-8, y-9, 16, 18); //need edit if change in ui
        for(int i=0; i<aop.getDropPoints().length; i++){
            Rectangle r = aop.getDropPoints()[i];
            if(r.intersects(rect) && !aop.getHasProcessor()[i]){
                Point temp = r.getLocation();
                point = new Point((int)temp.getX()+18, (int)temp.getY()+3);
                break;
            }
        }
        return point;
    }

    @Override
    protected boolean calculateAlive() {
        return true;
    }
}
