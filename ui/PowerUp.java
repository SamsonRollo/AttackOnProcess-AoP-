package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class PowerUp extends GameObject{
    private int selectedPowerUp = 0;

    public PowerUp(AOP aop){
        IMG_PATH = "src/powerup.png";
        this.aop = aop;
        setGameObject("powerup", randomizeXPos(), 63);
        
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                executePowerUp(selectedPowerUp);
            }
        });
    }

    private int randomizeXPos(){
        return (new Random().nextInt(638 + 1 - 198)+198);
    }

    public void executePowerUp(int selectedPowerUp){ //on click execute power up and show question
        //insert question pop up here 
        switch(selectedPowerUp){
            case 1:
                System.out.println("Hello");
                break;
            case 2:
                System.out.println("Bulugon");
                break;
            default://case 0 and default is the same
                //aop.slowDownProcess();
                break;
        }
    }

    public void updatePowerUp(){
        moveCurrentPoint(getX(), getY()+velocity);
    }

    @Override
    protected boolean calculateAlive() {
        if(getY()+getH()>=452)
            return false;
        return true;
    }

}
