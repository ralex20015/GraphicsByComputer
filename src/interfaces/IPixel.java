package interfaces;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IPixel {

    default void putPixel(int x, int y, BufferedImage bufferedImage, Color c) {
        bufferedImage.setRGB(x, y, c.getRGB());
    }

    default void changeColor(Color color){
    }
//    void draw(int x,int y,int centerX, int centerY, Color c, JFrame frame);
}
