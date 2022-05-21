package aop.game;

public class Bullet extends GameObject{
    
    public Bullet(int bulletLevel, int x, int y){
        IMG_PATH = "aop/src/bullet"+bulletLevel+".png";
        setGameObject("bullet", x, y);
    }

    public void updateBullet(){
        moveCurrentPoint(getX()+velocity, getY());
    }

    @Override
    protected boolean calculateAlive() {
        if(getX()+getW() >= 638)
            return false;
        return true;
    }
}
