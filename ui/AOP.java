package ui;
//package game.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class AOP extends JPanel{
    //private Score score;
    private final String BG_IMG_PATH = "src/panel.png";
    private final int MENU_LOC_X = 16;
    private final int MENU_LOC_Y = 67;
    private final int MENU_LOC_MUL = 32;
    private MainClass mainClass;
    private BufferedImage BG_IMG;
    private ArrayList<Processor> processors;
    private ArrayList<Bullet> bullets;
    private Rectangle[] dropPoints;
    private boolean[] hasProcessor;
    private Thread bulletThread, processorThread;
    private int speed = 50;
    private boolean playBoolean = false;

    public AOP(MainClass mainClass){ //Score score
        this.mainClass = mainClass;
        setPreferredSize(new Dimension(700,500));
        setLayout(null);
        setBounds(0,0,700,500);
        loadElements();
    }

    public void loadElements(){
        ImageLoader il = new ImageLoader(BG_IMG_PATH, "bg");
        BG_IMG = il.getBuffImage();

        bullets = new ArrayList<Bullet>();
        processors = new ArrayList<Processor>();
        hasProcessor = new boolean[7];
        dropPoints = new Rectangle[7];

        processors.add(new Processor(this, 135, 70));
        hasProcessor[0] = true;

        for(int i=0, mult=55; i<7; i++)
            dropPoints[i] = new Rectangle(117, 67+mult*i, 81, 53);

        AOPButton play = new AOPButton(MENU_LOC_X, MENU_LOC_Y, 84, 28);
        AOPButton pause = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL, 84, 28);
        AOPButton upgrade = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*2, 84, 28);
        AOPButton help = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*3, 84, 28);
        AOPButton quit = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*4, 84, 28);

        play.setIcons(
            "src/normal/play.png",
            "src/hilite/h_play.png",
            "PLAY"
        );
        pause.setIcons(
            "src/normal/pause.png",
            "src/hilite/h_pause.png",
            "PAUSE"
        );
        upgrade.setIcons(
            "src/normal/upgrade.png",
            "src/hilite/h_upgrade.png",
            "UPGRADE"
        );
        help.setIcons(
            "src/normal/help.png",
            "src/hilite/h_help.png",
            "HELP"
        );
        quit.setIcons(
            "src/normal/quit.png",
            "src/hilite/h_quit.png",
            "QUIT"
        );

        play.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setPlay(true);
                produceBullets();
            }
        });

        pause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setPlay(false);
            }
        });

        upgrade.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //pop up upgrade menu
            }
        });

        help.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //pop up help menu
            }
        });

        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //back to main menu
            }
        });

        add(processors.get(0));
        add(play);
        add(pause);
        add(upgrade);
        add(help);
        add(quit);
        //load the snake sprite her

        
    }

    private void produceBullets(){
        bulletThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isPlay()){
                    updateBullets();
                    createBullet();
                    removeBullet();
                    updateUI();
                
                    try{
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {}
                }
            }
        });  
        bulletThread.start();   
    }

    public boolean isPlay(){
        return this.playBoolean;
    }

    public void setPlay(boolean playBoolean){
        this.playBoolean = playBoolean;
    }

    public ArrayList<Processor> getProcessors(){
        return this.processors;
    }

    public void updateBullets(){
        for(Bullet b : bullets)
            b.updateBullet();
    }

    public void createBullet(){
        for(int i=0, mult=55; i<dropPoints.length; i++){
            Rectangle curDrop = dropPoints[i];

            if(hasProcessor[i] && !intersectBullet((int)curDrop.getY())){ //&& has process
                Processor p = getProcessorAt((int)curDrop.getY()+3);
                if(p!=null && !p.isDragged()){
                    Bullet b = new Bullet(198, 85+mult*i); 
                    bullets.add(b);
                    add(b);
                }
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

    public void removeBullet(){ //rely on alive method on bullet
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if(!bullet.isAlive()){ //end of the lane
                iterator.remove();
                remove(bullet);
            }
        }
    }

    public Processor getProcessorAt(int y){
        for(Processor p : processors)
            if(p.getInitY()==y)
                return p;
        return null;
    }

    public void setHasProcessorAt(int oldY, int newY){
        for(int i=0; i<dropPoints.length; i++){
            if((int)dropPoints[i].getY()+3 == oldY){
                hasProcessor[i] = false;
            }
            if((int)dropPoints[i].getY()+3 == newY){
                hasProcessor[i] = true;
            }
        }
    }

    public Point getDropLocation(Point p){
        Point point = new Point(-1,-1);
        Rectangle rect = new Rectangle((int)p.getX()-8, (int)p.getY()-9, 16, 18); //need edit if change in ui
        for(Rectangle r : dropPoints)
            if(r.intersects(rect)){
                Point temp = r.getLocation();
                point = new Point((int)temp.getX()+18, (int)temp.getY()+3);
                break;
            }

        return point;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG, 0, 0, null);

    }
}
