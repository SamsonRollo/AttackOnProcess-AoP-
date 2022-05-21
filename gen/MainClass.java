package gen;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

import aop.game.AOP;

public class MainClass extends JFrame{
    //private BufferedImage ICON_PATH = "src/tray_icon.png"

    public MainClass(){
    	//setTrayIcon();
        setMinimumSize(new Dimension(700,500));
        setPreferredSize(new Dimension(700,500));
        setResizable(false);
		getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadPanels();
        pack();
        setLocationRelativeTo(null);
    }

    public void loadPanels(){
        AOP aop = new AOP(this);
        getContentPane().add(aop);
    }

    // private void setTrayIcon(){
	// 	ImageLoader il = new ImageLoader(ICON_PATH, "icon");
    //     setIconImage(il.getBuffImage());
	// }

    public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainClass().setVisible(true);
            }
        });
    }
}