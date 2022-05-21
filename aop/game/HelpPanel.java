package aop.game;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gen.GameButton;
import gen.ImageLoader;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelpPanel extends JPanel {
    private BufferedImage BG_IMG, CONTENT_IMG;
    private AOP aop;
    private boolean isPlay;

    public HelpPanel(AOP aop, boolean isPlay){
        this.aop = aop;
        this.isPlay = isPlay;
        loadElements();
    }

    public void loadElements(){
        setLayout(null);
        setBounds(0,0,700,500);
        setOpaque(false);
        ImageLoader il = new ImageLoader("aop/src/helpPanelOuter.png", "bgHelp");
        BG_IMG = il.getBuffImage();
        il.reloadImage("aop/src/helpContent.png", "content");
        CONTENT_IMG = il.getBuffImage();

        GameButton back = new GameButton(308, 397, 84, 28);

        back.setIcons(
            "aop/src/normal/back.png",
            "aop/src/hilite/h_back.png",
            "BACK"
        );

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.setVisible(true);   
                aop.getMainClass().getContentPane().remove(getPanel());
                aop.getMainClass().revalidate();
                if(isPlay)
                    aop.playingStatus(true);
            }
        });

        JPanel contentPanel = new JPanel(null){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(CONTENT_IMG, 0, 0, null);
            }
        };
        contentPanel.setSize(CONTENT_IMG.getWidth(), CONTENT_IMG.getHeight());
        contentPanel.setPreferredSize(new Dimension(CONTENT_IMG.getWidth(), CONTENT_IMG.getHeight()));

        JScrollPane jsp = new JScrollPane(contentPanel);
        jsp.setBounds(131, 25, 439, 364);
        jsp.setBorder(BorderFactory.createEmptyBorder());
        add(jsp);
        add(back);
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
