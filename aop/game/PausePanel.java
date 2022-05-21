package aop.game;

import javax.swing.JPanel;

import gen.GameButton;
import gen.ImageLoader;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PausePanel extends JPanel {
    private BufferedImage BG_IMG;
    private AOP aop;

    public PausePanel(AOP aop){
        this.aop = aop;
        loadElements();
    }

    public void loadElements(){
        setLayout(null);
        setBounds(0,0,700,500);
        setOpaque(false);
        ImageLoader il = new ImageLoader("aop/src/pausePanel.png", "pause");
        BG_IMG = il.getBuffImage();

        GameButton resume = new GameButton(308, 296, 84, 28);

        resume.setIcons(
            "aop/src/normal/resume.png",
            "aop/src/hilite/h_resume.png",
            "RESUME"
        );

        resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.setVisible(true);   
                aop.getMainClass().getContentPane().remove(getPanel());
                aop.getMainClass().revalidate();
                aop.playingStatus(true);
            }
        });

        add(resume);
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
