package aop.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class PowerUp extends GameObject{
    private int selectedPowerUp = 0;
    private AOP aop;

    public PowerUp(AOP aop){
        this.aop = aop;
        IMG_PATH = "aop/src/powerup.png";
        setGameObject("powerup", randomizeXPos(), 63);
        
        selectedPowerUp = new Random().nextInt(101);
        
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(aop.isPlay())
                    executePowerUp(selectedPowerUp);
            }
        });
    }

    public void executePowerUp(int selectedPowerUp){
        //insert question pop up here
        if(selectedPowerUp%11==0){
            aop.setAllProcessDead();
        } else if(selectedPowerUp%5==0){
            int curStarvation = aop.getUpgrade().getStarvationRealCount();
            curStarvation = (int)Math.floor(curStarvation - (curStarvation*0.13));
            aop.getUpgrade().setStarvation(curStarvation);
            aop.updateAngerIMG();
        }else{
            randomKillProcess(aop.getProcesses());
        }
        setAlive(false);
    }

    public void randomKillProcess(ArrayList<Process> processes){
        try{
            int victim = new Random().nextInt(processes.size());
            int iter = 0;
            for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
                Process process = iterator.next();
                if(iter==victim){
                    iterator.remove();
                    aop.remove(process);
                }
                iter++;
            }
        }catch(Exception e){};
    }

    private int randomizeXPos(){
        return (new Random().nextInt(638 + 1 - 198)+198);
    }

    public void updatePowerUp(){
        int y = getY()+velocity;
        moveCurrentPoint(getX(), y);
        setLocation(getX(), y);
    }

    protected boolean calculateAlive() {
        if(getY()+getH()>=452)
            return false;
        return true;
    }

}
