package graphs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DropmanDust {

    public static void main(String[] args) throws IOException {
        //75_500_2000_22_-47.5_20151015_1452110000
        
        BufferedImage bufferedImage = ImageIO.read(new File("src/graphs/data/75_500_2000_22_-47.5_20151015_1452110000.png")); 
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        System.out.println("pixels: " + pixels.length);
        System.out.println("h: " + bufferedImage.getHeight());
        System.out.println("w: " + bufferedImage.getWidth());
    }

}
