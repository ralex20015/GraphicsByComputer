package _3D.projection;

import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CubeApplication extends JPanel {
    private final BufferedImage bufferedImage;
    private final MyPoint[]pointsOfCube;
    private final MyPoint[]pointsOfCube2;
    private final int ambientLightIntensity = 120;
    private final MyPoint[]pointsOfCubeWithAmbientLight;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Cube");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CubeApplication());
        frame.setVisible(true);
    }

    public CubeApplication(){
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
        pointsOfCube = new MyPoint[]{new MyPoint(50,150,150), new MyPoint(150,150,150),
                new MyPoint(50, 250, 150), new MyPoint(150,250,150), new MyPoint(50,150,250),
                new MyPoint(150,150,250), new MyPoint(50,250,250), new MyPoint(150,250, 250)};
//        pointsOfCube = new MyPoint[]{new MyPoint(50,150,150), new MyPoint(150,150,150),
//                new MyPoint(50  , 250, 150), new MyPoint(150,250,150), new MyPoint(10,190,250),
//                new MyPoint(110,190,250), new MyPoint(10,250,250), new MyPoint(110,250, 250)};
        pointsOfCube2 = new MyPoint[]{new MyPoint(220,150,150), new MyPoint(320,150,150),
                new MyPoint(220, 250, 150), new MyPoint(320,250,150), new MyPoint(220,150,250),
                new MyPoint(320,150,250), new MyPoint(220,250,250), new MyPoint(320,250, 250)};

        pointsOfCubeWithAmbientLight = new MyPoint[]{new MyPoint(400,150,150), new MyPoint(500,150,150),
                new MyPoint(400, 250, 150), new MyPoint(500,250,150), new MyPoint(400,150,250),
                new MyPoint(500,150,250), new MyPoint(400,250,250), new MyPoint(500,250, 250)};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = Color.BLUE;
        g.drawImage(bufferedImage,0,0,null);
        //Cube normal sin aplicar luz ambiental
        Cube cube = new Cube(bufferedImage, pointsOfCube);
//        cube.setLineColor(Color.BLACK);
        cube.setBackgroundColor(color);
//        cube.draw(new int[]{15, -15, 75});
        cube.draw(new int[]{-32, 28, 80});

        Color colorOfCube2 = new Color(122,55,122);
        Cube cube2 = new Cube(bufferedImage, pointsOfCube2);
        cube2.setLineColor(cube2.applyAmbientLight(colorOfCube2, ambientLightIntensity));
//        cube.setLineColor(Color.BLACK);
        cube2.setBackgroundColor(cube2.applyAmbientLight(colorOfCube2, ambientLightIntensity));
        cube2.draw(new int[]{-32, 28, 80});

        Cube cubeWithAmbientLight = new Cube(bufferedImage, pointsOfCubeWithAmbientLight);
        cubeWithAmbientLight.setBackgroundColor(
                cubeWithAmbientLight.applyAmbientLight(color, ambientLightIntensity));
        cubeWithAmbientLight.setLineColor(
                cubeWithAmbientLight.applyAmbientLight(color, ambientLightIntensity));
        cubeWithAmbientLight.draw(new int[]{-32, 28, 80});

//        cube.drawWithPerspective(new int[]{175, -200, 300});
    }
}
