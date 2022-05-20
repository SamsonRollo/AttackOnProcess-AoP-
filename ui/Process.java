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
    private int score;
    private AOP aop;
    private int speed = 1; //variable speed
    private boolean alive = true, starving = false;
    private JLabel head;
    private int spriteIdx = 0;
    private int spriteLag = 0;

    public Process(AOP aop, int level, int x, int y, int h){
        this.aop = aop;
        setLayout(null);
        setOpaque(false);
        loadElements(level);
        setH(h);
        setCurrentPoint(x, y);
        setBounds(getX(), getY(), getW(), getH());
    }

    public void loadElements(int level){
        int curWidth = 0;
        ImageLoader il = new ImageLoader(PROC1_IMG_PATH[randomizeSelection(1, 0)], "process");
        head = new JLabel();
        head.setIcon(new ImageIcon(il.getBuffImage()));
        head.setBounds(0, 0, il.getBuffImage().getWidth(), il.getBuffImage().getHeight());
        add(head);

        this.burstTime = randomizeSelection((level+2)*5, 5);
        this.score = this.burstTime;

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

        if(isStarving()){
            aop.getUpgrade().incrementStarvation();
            if(spriteLag%20==0)
                head.setIcon(new ImageIcon(aop.getStarveSprite().getSprites()[(spriteIdx++)%2]));
            spriteLag++;
            aop.updateAngerIMG();
        }

        try{
            for(ProcessBody pb: body)
                pb.updateSprite();
            tail.updateSprite();
        }catch(Exception e){};
    }

    public int randomizeSelection(int max, int min){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    public void decrementBurstTime(int decrement){
        this.burstTime -= decrement;
        
        if(burstTime<0)
            burstTime = 0;
        int bodCount = (int)Math.floor(burstTime/5);
        if(bodCount<body.size()){
            ProcessBody last = body.get(body.size()-1);
            tail.updatePosition(last.getX(), last.getY());
            remove(last);
            body.remove(last);
            setW(getW()-55);
        }
    }

    public int getProcessScore(){
        return this.score;
    }

    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), getW(), getH());
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isAlive(){
        if(burstTime<=0 || !this.alive)
            return false; 

        return true;
    }
    
    public void moveCurrentPoint(int x, int y){ //dont move if there is someone starving
        if(x>198 && validProcessMove(this, x, y))
            this.currentPoint.move(x, y);
        if(!isStarving() && x<=198)
            setStarving(true);
    }

    public boolean validProcessMove(Process p, int x, int y){
        for(Process proc : aop.getProcesses()){
            if(proc==p)
                continue;
            Rectangle movedP = new Rectangle(x,y,p.getW(), p.getH());
            if(proc.getRectangle().intersects(movedP))
                return false; 
        }
        return true;
    }

    public boolean isStarving(){
        return this.starving;
    }

    public void setStarving(boolean starving){
        this.starving = starving;
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

    public void sleep(){
        try{
            Thread.sleep(burstTime);
        }catch(Exception e){}
    }
}
