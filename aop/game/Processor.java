package aop.game;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class Processor extends GameObject{
    private boolean dragged = false;
    private ArrayList<Bullet> bullets;
    private Upgrade upgrade;
    private int accumLagBul;
    private int lastLevelBulletUpdate;
    private int bulletSpeed = 2;
    private int lane;

    public Processor(AOP aop, Upgrade upgrade, int x, int y){
        this.aop = aop;
        this.upgrade = upgrade;
        this.lastLevelBulletUpdate = 0;
        this.lane = calculateLane(y);
        accumLagBul = upgrade.getBulletLag();
        IMG_PATH = "aop/src/cpu.png";
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
                setLane(calculateLane(getY()));
            }
        });

        bullets = new ArrayList<Bullet>();
        produceBullets();
    }

    public void produceBullets(){
        Thread bulletThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(aop.isPlay()){
                    updateBullets();
                    if(accumLagBul>= upgrade.getBulletLag() && aop.getProcessesLane()[lane]>0){
                        createBullet();
                        accumLagBul = 0;
                    }
                    removeBullet();
                    aop.updateUI();
                    accumLagBul+=upgrade.getSpeed();

                    try{
                        Thread.sleep(upgrade.getSpeed());
                    } catch (InterruptedException e) {}
                }
            }
        });  
        bulletThread.start();   
    }

    public void updateBullets(){
        for(Bullet b : bullets){
            b.setVelocity(bulletSpeed);
            b.updateBullet();
            aop.processHit(b);
        }
        if(this.bulletSpeed<6 && upgrade.getLevel()%3==0 && upgrade.getLevel()>this.lastLevelBulletUpdate){
            this.bulletSpeed++;
            if(upgrade.getLevel()%6==0)
                upgrade.decrementBulletLag(2);
            this.lastLevelBulletUpdate = upgrade.getLevel();
        }
    }

    public void createBullet(){
            if(!intersectBullet(getY()-3) && !isDragged()){
                Bullet b = new Bullet(upgrade.getBulletLevel(), 198, getY()+18); 
                bullets.add(b);
                aop.add(b);
            }
    }

    public void removeBullet(){
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if(!bullet.isAlive()){
                iterator.remove();
                aop.remove(bullet);
            }
        }
    }

    public boolean intersectBullet(int dropY){
        Rectangle bulletDropPoint = new Rectangle(198, dropY+18, 30, 17); //need change if change bullet dimension
        for(Bullet b: bullets)
            if((b.getRectangle()).intersects(bulletDropPoint))
                return true;
        return false;
    }

    public void setAllBulletsDead(){
        for(Bullet b : bullets)
            b.setAlive(false);
    }

    public int calculateLane(int y){
        return (int)Math.floor((y-88)/56);
    }

    public int getLane(){
        return this.lane;
    }

    public void setLane(int lane){
        this.lane = lane;
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
