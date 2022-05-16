package ui;

import java.util.ArrayList;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Process extends JPanel{
    private final String[] PROC1_IMG_PATH = {"src/p1.png","src/p2.png"};
    private Point currentPoint;
    private ArrayList<ProcessBody> body;
    private ProcessTail tail;
    private int h, w;
    private int burstTime;
    private AOP aop;
    private int speed = 1;

    public Process(AOP aop, int level, int x, int y, int h){
        this.aop = aop;
        setLayout(null);
        setOpaque(false);
        loadElements(level);
        //randomize the length of process and add thewidth to be the width of the panel
        setH(h);
        setCurrentPoint(x, y);
        setBounds(getX(), getY(), getW(), getH());
    }

    public void loadElements(int level){
        int curWidth = 0;
        ImageLoader il = new ImageLoader(PROC1_IMG_PATH[randomizeSelection(1, 0)], "process");
        JLabel head = new JLabel();
        head.setIcon(new ImageIcon(il.getBuffImage()));
        head.setBounds(0, 0, il.getBuffImage().getWidth(), il.getBuffImage().getHeight());
        add(head);

        this.burstTime = randomizeSelection((level+1)*5, 5);

        curWidth += head.getWidth();
        int numBody = (int)Math.floor(burstTime/5);
        body = new ArrayList<ProcessBody>();

        int i;
        for(i=0; i<numBody-1; i++){
            ProcessBody pb = new ProcessBody(aop, (i%2)*3, curWidth, 0);
            body.add(pb);
            add(pb);
            curWidth+=55;
        }
        tail = new ProcessTail(aop, (i%2)*3, curWidth, 0);
        add(tail);
        curWidth+=55;
        setW(curWidth);
    }

    public void update(){
        moveCurrentPoint(getX()-speed, getY());
        
        for(ProcessBody pb: body)
            pb.updateSprite();
        tail.updateSprite();
            //update position when burst time decrease by factor of 5
    }

    public int randomizeSelection(int max, int min){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    public void decrementBurstTime(int decrement){
        this.burstTime -= decrement;
    }

    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), getW(), getH());
    }
    
    public void moveCurrentPoint(int x, int y){
        this.currentPoint.move(x, y);
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
