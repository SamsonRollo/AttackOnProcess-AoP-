package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class PowerUp extends GameObject{
    private int selectedPowerUp = 0;

    public PowerUp(AOP aop){
        IMG_PATH = "src/powerup.png";
        this.aop = aop;
        setGameObject("powerup", 640, randomizeYPos());
        
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                executePowerUp(selectedPowerUp);
            }
        });
    }

    private int randomizeYPos(){
        return (new Random().nextInt(7)*55)+70;
    }

    public void executePowerUp(int selectedPowerUp){ //on click execute power up and show question
        switch(selectedPowerUp){
            case 1:
                //aop.rPopProcesss();
                break;
            case 2:
               // aop.
            default://case 0 and default is the same
                //aop.slowDownProcess();
                break;
        }
    }

    public void updatePowerUp(){
        moveCurrentPoint(getX()+velocity, getY());
    }

    @Override
    protected boolean calculateAlive() {
        if(getX()>=198)
            return false;
        return true;
    }

}
