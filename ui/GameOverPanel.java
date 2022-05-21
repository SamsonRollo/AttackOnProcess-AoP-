package ui;

import javax.swing.JPanel;
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
        ImageLoader il = new ImageLoader("src/gameoverPanel.png", "bggameover");
        BG_IMG = il.getBuffImage();

        AOPButton back = new AOPButton(600, 34, 84, 28);

        back.setIcons(
            "src/normal/back.png",
            "src/hilite/h_back.png",
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
