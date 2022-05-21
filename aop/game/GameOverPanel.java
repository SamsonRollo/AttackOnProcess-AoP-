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

public class GameOverPanel extends JPanel {
    private BufferedImage BG_IMG;
    private AOP aop;
    private Score score;
    private Font font;

    public GameOverPanel(AOP aop, Score score, Font font){
        this.aop = aop;
        this.score = score;
        this.font = font;
        loadElements();
    }

    public void loadElements(){
        setLayout(null);
        setBounds(0,0,700,500);
        setOpaque(false);
        ImageLoader il = new ImageLoader("aop/src/gameoverPanel.png", "bggameover");
        BG_IMG = il.getBuffImage();

        GameButton back = new GameButton(308, 320, 84, 28);

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
                aop.resetGame();
            }
        });

        add(back);
    }

    public JPanel getPanel(){
        return this;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG,0,0, null);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(String.valueOf(score.getScore()), 275, 271);

    }
}
