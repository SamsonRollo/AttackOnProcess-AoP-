package aop.game;

import javax.swing.JPanel;

import gen.GameButton;
import gen.ImageLoader;
import gen.Score;

import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuitPanel extends JPanel {
    private BufferedImage BG_IMG;
    private AOP aop;
    private boolean isPlay;

    public QuitPanel(AOP aop, boolean isPlay){
        this.aop = aop;
        this.isPlay = isPlay;
        loadElements();
    }

    public void loadElements(){
        setLayout(null);
        setBounds(0,0,700,500);
        setOpaque(false);
        ImageLoader il = new ImageLoader("aop/src/quitPanel.png", "quit");
        BG_IMG = il.getBuffImage();

        GameButton no = new GameButton(366, 305, 84, 28);
        GameButton yes = new GameButton(240, 305, 84, 28);

        no.setIcons(
            "aop/src/normal/no.png",
            "aop/src/hilite/h_no.png",
            "NO"
        );

        yes.setIcons(
            "aop/src/normal/yes.png",
            "aop/src/hilite/h_yes.png",
            "YES"
        );

        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.setVisible(true);   
                aop.getMainClass().getContentPane().remove(getPanel());
                aop.getMainClass().revalidate();
                if(isPlay)
                    aop.playingStatus(true);
            }
        });

        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
        });

        add(no);
        add(yes);
    }

    public JPanel getPanel(){
        return this;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG,0,0, null);
    }
}
