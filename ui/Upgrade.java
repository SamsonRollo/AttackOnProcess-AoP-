package ui;

import exception.*;
import java.awt.Rectangle;

public class Upgrade {
    private int tokens;
    private int speedPowerup;
    private int numberOfCores;
    private int bulletLag;
    private int processLag;
    private int level;
    private int bulletLevel;
    private int starvation;

    public Upgrade(int numProc, int speed, int bulletLag, int level){
        this.speedPowerup = speed;
        this.numberOfCores = numProc;
        this.bulletLag = bulletLag;
        this.level = level;
        this.tokens = 0;
        this.bulletLevel = 1;
        this.starvation = 0;
    }

    public void incrementNumCore(AOP aop) throws CoreIncrementException{
        if(numberOfCores>=4)
            throw new CoreIncrementException("Max core reached!");
        
        this.numberOfCores++;
        Rectangle loc = selectCPULocation(aop);
        if(loc==null)
            throw new CoreIncrementException("Cannot add core!");
        
        Processor newCPU = new Processor(aop, aop.getUpgrade(), (int)loc.getX()+18, (int)loc.getY()+3);
        aop.addProcessor(newCPU);
    }

    private Rectangle selectCPULocation(AOP aop){
        Rectangle r = null;
        for(int i = 0; i<aop.getDropPoints().length; i++){
            if(!aop.getHasProcessor()[i]){
                r = aop.getDropPoints()[i];
                aop.getHasProcessor()[i] = true;
                break;
            }
        }
        return r;
    }

    public void updateLevel(int score){
        int curlevel = (int)Math.floor(score/52)+1;
        this.level = curlevel > this.level ? curlevel : this.level; 
    }

    public int getNumCore(){
        return this.numberOfCores;
    }

    public void decrementBulletLag(){
        this.bulletLag--;
    }

    public void decrementBulletLag(int bulletLag){
        this.bulletLag+=bulletLag;
    }

    public int getBulletLag(){
        return this.bulletLag;
    }

    public void incrementStarvation(){
        this.starvation++;
    }

    public int getStarvationCount(){
        return (int)Math.floor(this.starvation/23);
    }

    public void incrementSpeed(){
        this.speedPowerup++;
    }

    public void incrementSpeed(int speed){
        this.speedPowerup+=speed;
    }

    public int getSpeed(){
        return this.speedPowerup;
    }

    public void incrementLevel(){
        this.level++;
    }

    public int getLevel(){
        return this.level;
    }

    public int getProcessLag(){
        return (int)Math.floor(400/this.level);
    }

    public int getLevelVariable(){ //edit
        return this.level;
    }

    public int getBulletLevel(){
        return this.bulletLevel;
    }

    public void incrementBulletLevel() throws BulletUpgradeException{
        if(this.bulletLevel==3)
            throw new BulletUpgradeException("Max Bullet reached!");
        this.bulletLevel++;
    }

    public void setToken(int token){
        this.tokens = token;
    }

    public void incrementToken(int token){
        this.tokens+=token;
    }

    public void decrementToken(int token) throws TokenException{
        if(this.tokens-token<0)
            throw new TokenException("Not enough Token!");
        this.tokens-=token;
    }

    public int getToken(){
        return this.tokens;
    }

    public int getCoreCost(){
        if(numberOfCores>=4)
            return 0;
        return this.numberOfCores * 421 + 12;
    }

    public int getBulletCost(){
        if(bulletLevel>=3)
            return 0;
        return this.bulletLevel * 512 + 13;
    }

    public String getCoreCostStr(){
        int cost = getCoreCost();
        if(cost==0)
            return "";
        return String.valueOf(cost);
    }

    public String getBulletCostStr(){
        int cost = getBulletCost();
        if(cost==0)
            return "";
        return String.valueOf(cost);
    }
}
