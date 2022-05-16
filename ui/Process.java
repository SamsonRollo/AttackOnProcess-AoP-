package ui;

import java.awt.image.BufferedImage;

public class Process {
    private final String PROC1_IMG_PATH = "src/p1.png";
    private final String PROC2_IMG_PATH = "src/p2.png";
    private BufferedImage PROC_IMGS;

    public Process(){
        ImageLoader il = new ImageLoader(PROC1_IMG_PATH, "process1");
        PROC_IMGS = il.getBuffImage();
    }

    
}
