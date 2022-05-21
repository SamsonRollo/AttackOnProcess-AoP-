package aop.game;

import javax.swing.JPanel;

import gen.GameButton;
import gen.ImageLoader;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpgradePanel extends JPanel {
    private Upgrade upgrade;
    private BufferedImage IMAGE, coreImage, bulletImage;
    private Font font;
    private AOP aop;
    private boolean isPlay;

    public UpgradePanel(AOP aop, Upgrade upgrade, Font font, boolean isPlay){
        this.aop = aop;
        this.upgrade = upgrade;
        this.font = font;
        this.isPlay = isPlay;
        loadElements();
    }

    public void loadElements(){
        setLayout(null);
        setBounds(0,0,700,500);
        setOpaque(false);
        ImageLoader il = new ImageLoader("aop/src/upgradePanel.png", "upgradePanel");
        IMAGE = il.getBuffImage();
        il.reloadImage("aop/src/cpu.png", "cpu");
        coreImage = il.getBuffImage();
        il.reloadImage("aop/src/bullet"+upgrade.getBulletLevel()+".png", "bullet");
        bulletImage = il.getBuffImage();

        GameButton buyCPU = new GameButton(340, 172, 84, 28);
        GameButton buyBullet = new GameButton(389, 298, 84, 28);
        GameButton back = new GameButton(308, 392, 84, 28);

        buyCPU.setIcons(
            "aop/src/normal/buy.png",
            "aop/src/hilite/h_buy.png",
            "BUYCPU"
        );

        buyBullet.setIcons(
            "aop/src/normal/buy.png",
            "aop/src/hilite/h_buy.png",
            "BUYBullet"
        );

        back.setIcons(
            "aop/src/normal/back.png",
            "aop/src/hilite/h_back.png",
            "BACK"
        );

        buyCPU.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int initToken = upgrade.getToken();
                try{
                    upgrade.decrementToken(upgrade.getCoreCost());
                    upgrade.incrementNumCore(aop);
                    updateUI();
                }catch(Exception ex){
                    if(upgrade.getToken()!=initToken)
                        upgrade.setToken(initToken);;
                    aop.reportError(ex.getMessage(), "Cannot purchase!");
                }
            }
        });

        buyBullet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int initToken = upgrade.getToken();
                try{
                    upgrade.decrementToken(upgrade.getBulletCost());
                    upgrade.incrementBulletLevel();
                    updateBulletImage();
                    updateUI();
                }catch(Exception ex){
                    if(upgrade.getToken()!=initToken)
                        upgrade.setToken(initToken);
                    aop.reportError(ex.getMessage(), "Cannot purchase!");
                }
            }
        });

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.setVisible(true);   
                aop.getMainClass().getContentPane().remove(getPanel());
                aop.getMainClass().revalidate();
                if(isPlay)
                    aop.playingStatus(true);
            }
        });

        add(buyCPU);
        add(buyBullet);
        add(back);
    }

    public void updateBulletImage(){
        ImageLoader il = new ImageLoader("aop/src/bullet"+upgrade.getBulletLevel()+".png", "bullet");
        bulletImage = il.getBuffImage();
        repaint();
    }

    public JPanel getPanel(){
        return this;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(IMAGE,0,0, null);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(String.valueOf(upgrade.getToken()), 265, 127);
        g.drawString(upgrade.getCoreCostStr(), 247, 219);
        g.drawString(upgrade.getBulletCostStr(), 247, 345);

        for(int i=0; i<upgrade.getNumCore(); i++)
            g.drawImage(coreImage, 197+(55*i), 235, null);

        g.drawImage(bulletImage, 197, 365, null);
    }
}
