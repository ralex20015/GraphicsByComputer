package Lines;

import Fill.ScanLine;
import Forms.OutlineShape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyCanva extends JPanel {
    private BufferedImage bufferedImage;
    private boolean isPainted = false;

    public MyCanva(int width, int height){
        setSize(width,height);
        setDoubleBuffered(true);
        bufferedImage = new BufferedImage(1900,1200,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
        OutlineShape shape = new OutlineShape();
//        shape.drawMidPointCircle(200,200,100,bufferedImage,g);

        ScanLine.apply(shape.drawBasicCircle(300,300,100,bufferedImage,g));
        ScanLine.apply(shape.drawRectangle(100,100,200,100,bufferedImage,g));
        shape.changeColor(Color.GREEN);
//        ScanLine.apply(shape.drawMidPointCircle(300,300,100,bufferedImage,g));
//        ScanLine.apply(shape.drawEllipse(300,300,100,50,bufferedImage,g));
//        Flood.apply(shape.drawRectangle(100,100,200,100,bufferedImage,g));
//        shape.drawMidPointCircle(300,300,100,bufferedImage,g);
//        shape.changeColor(Color.BLACK);
//        ScanLine.apply(shape.drawCircleWithPolarCoordinates(300,300,100,bufferedImage,g));

//        Flood.apply(shape.drawEllipse(300,300,100,50,bufferedImage,g));
//        ScanLine.apply(shape.drawCircleWithPolarCoordinates(200,200,100,bufferedImage,g));
    }
}
