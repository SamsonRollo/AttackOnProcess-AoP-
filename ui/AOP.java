package ui;
//package game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;

import exception.ErrorReport;

public class AOP extends JPanel{
    private Upgrade upgrade;
    private Score score;
    private final int MENU_LOC_X = 16;
    private final int MENU_LOC_Y = 67;
    private final int MENU_LOC_MUL = 32;
    private MainClass mainClass;
    private BufferedImage BG_IMG;
    private ArrayList<Process> processes;
    private ArrayList<Processor> processors;
    private ArrayList<Bullet> bullets;
    private Rectangle[] dropPoints;
    private boolean[] hasProcessor;
    private boolean playBoolean = false;
    private SpriteSheet procBody;
    private SpriteSheet procTail;
    private AOPButton playBut;
    private Font font;

    public AOP(MainClass mainClass){ //Score score
        this.mainClass = mainClass;
        setPreferredSize(new Dimension(700,500));
        setLayout(null);
        setBounds(0,0,700,500);
        loadElements();
    }

    public void loadElements(){
        ImageLoader il = new ImageLoader("src/panel.png", "bg");

        font = new Font("sans_serif", Font.BOLD, 18);
        score = new Score();
        upgrade = new Upgrade(1, 5, 1000, 1);
        BG_IMG = il.getBuffImage();
        procBody = new SpriteSheet("src/pbodysprite.png", 55, 44);
        procTail = new SpriteSheet("src/ptailsprite.png", 55, 44);

        processes = new ArrayList<Process>();
        bullets = new ArrayList<Bullet>();
        processors = new ArrayList<Processor>();
        hasProcessor = new boolean[7];
        dropPoints = new Rectangle[7];

        processors.add(new Processor(this, 135, 70));
        hasProcessor[0] = true;

        for(int i=0, mult=55; i<7; i++)
            dropPoints[i] = new Rectangle(117, 67+mult*i, 81, 53);

        playBut = new AOPButton(MENU_LOC_X, MENU_LOC_Y, 84, 28);
        AOPButton pauseBut = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL, 84, 28);
        AOPButton upgradeBut = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*2, 84, 28);
        AOPButton helpBut = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*3, 84, 28);
        AOPButton quitBut = new AOPButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*4, 84, 28);

        playBut.setIcons(
            "src/normal/play.png",
            "src/hilite/h_play.png",
            "PLAY"
        );
        pauseBut.setIcons(
            "src/normal/pause.png",
            "src/hilite/h_pause.png",
            "PAUSE"
        );
        upgradeBut.setIcons(
            "src/normal/upgrade.png",
            "src/hilite/h_upgrade.png",
            "UPGRADE"
        );
        helpBut.setIcons(
            "src/normal/help.png",
            "src/hilite/h_help.png",
            "HELP"
        );
        quitBut.setIcons(
            "src/normal/quit.png",
            "src/hilite/h_quit.png",
            "QUIT"
        );

        playBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playingStatus(true);
            }
        });

        pauseBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playingStatus(false);
            }
        });

        upgradeBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                UpgradePanel ugPanel = new UpgradePanel(getAOP(), upgrade, font, isPlay());
                setPlay(false);
                getAOP().setVisible(false);
                mainClass.add(ugPanel);
            }
        });

        helpBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //pop up help menu
            }
        });

        quitBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //record score and back to main menu
            }
        });

        add(processors.get(0));
        add(playBut);
        add(pauseBut);
        add(upgradeBut);
        add(helpBut);
        add(quitBut);       
    }

    public void playingStatus(boolean status){
        setPlay(status);
        playBut.setEnabled(!status);
        if(status){
            produceBullets();
            produceProcesses();
        }
    }

    public boolean isPlay(){
        return this.playBoolean;
    }

    public void setPlay(boolean playBoolean){
        this.playBoolean = playBoolean;
    }

    private void produceProcesses(){
        Thread processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int accumLagProc = upgrade.getProcessLag();
                while(isPlay()){
                    updateProcess();
                    if(accumLagProc >= upgrade.getProcessLag()){
                        createProcesses();
                        accumLagProc=0;
                    }
                    removeProcess();
                    updateUI();
                    accumLagProc += upgrade.getSpeed();

                    try{
                        Thread.sleep(80);
                    } catch (InterruptedException e) {}
                }
            }
        });  
        processThread.start(); 
    }

    public void updateProcess(){
        for(Process p : processes)
            p.update();
    }

    public void createProcesses(){
        int spawnX = 640;
        int idx = new Random().nextInt(7);
        Rectangle lane = dropPoints[idx];
        
        if(!intersectProcess((int)lane.getY()+3)){
            Process p = new Process(getAOP(), upgrade.getLevel(), spawnX, (int)lane.getY()+3, 44);
            processes.add(p);
            add(p);
        }
    }

    public boolean intersectProcess(int spawnY){
        for(Process p : processes)
            if(p.getRectangle().contains(640, spawnY))
                return true;
        return false;
    }

    public void removeProcess(){
        for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            if(!process.isAlive()){
                iterator.remove();
                remove(process);
            }
        }
    }

    private void produceBullets(){
        Thread bulletThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int accumLagBul = upgrade.getBulletLag();
                while(isPlay()){
                    updateBullets();
                    if(accumLagBul>= upgrade.getBulletLag()){
                        createBullet();
                        accumLagBul = 0;
                    }
                    removeBullet();
                    updateUI();
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
            b.updateBullet();
            processHit(b);
        }
    }

    public void createBullet(){
        for(int i=0, mult=55; i<dropPoints.length; i++){
            Rectangle curDrop = dropPoints[i];

            if(hasProcessor[i] && !intersectBullet((int)curDrop.getY())){
                Processor p = getProcessorAt((int)curDrop.getY()+3);
                if(p!=null && !p.isDragged()){
                    Bullet b = new Bullet(upgrade.getBulletLevel(), 198, 85+mult*i); 
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

    public void removeBullet(){
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if(!bullet.isAlive()){
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

    public void processHit(Bullet b){
        for(Process p : processes){
            if(b.getRectangle().intersects(p.getRectangle())){
                b.setAlive(false);
                p.decrementBurstTime(upgrade.getBulletLevel()*3);
                if(!p.isAlive())
                    score.incrementScore(p.getProcessScore());
                    upgrade.incrementToken(p.getProcessScore());
                return;
            }
        }
    }

    public int getSpeed(){
        return this.upgrade.getSpeed();
    }

    public void incrementSpeed(int speed){
        this.upgrade.incrementSpeed(speed);
    }

    public SpriteSheet getBodySprite(){
        return this.procBody;
    }

    public SpriteSheet getTailSprite(){
        return this.procTail;
    }

    public Rectangle[] getDropPoints(){
        return this.dropPoints;
    }

    public boolean[] getHasProcessor(){
        return this.hasProcessor;
    }

    public ArrayList<Process> getProcesses(){
        return this.processes;
    }

    public void addProcessor(Processor cpu){
        processors.add(cpu);
        add(cpu);
    }

    public AOP getAOP(){
        return this;
    }

    public MainClass getMainClass(){
        return this.mainClass;
    }

    public void reportError(String message, String title){
        new ErrorReport(mainClass, message, title);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG, 0, 0, null);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(String.valueOf(score.getScore()), 583, 45);
    }
}
