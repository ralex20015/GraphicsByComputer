package animation;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        int WIDTH = 600;
        int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("My animation");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Animation animation = new Animation(WIDTH,HEIGHT);
        frame.add(animation);
        animation.startAnimation();
        frame.setVisible(true);
    }
}
