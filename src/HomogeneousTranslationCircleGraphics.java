import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomogeneousTranslationCircleGraphics extends JPanel implements ActionListener {
    private double centerX = 100.0; // X-coordinate of the circle's center
    private double centerY = 100.0; // Y-coordinate of the circle's center
    private double radius = 50.0;  // Radius of the circle

    private double translationX = 2.0; // Horizontal translation amount
    private double translationY = 2.0;
    private Timer timer;

    public HomogeneousTranslationCircleGraphics() {
        timer = new Timer(150, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        // Calculate the translation matrix
        double[][] translationMatrix = {
                {1, 0, translationX},
                {0, 1, translationY},
                {0, 0, 1}
        };

        // Translate the center of the circle
        double[] center = {centerX, centerY, 1};
        double[] translatedCenter = new double[3];

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                translatedCenter[j] += translationMatrix[j][k] * center[k];
            }
        }

        centerX = translatedCenter[0];
        centerY = translatedCenter[1];

        // Draw the translated circle
        int x = (int) (centerX - radius);
        int y = (int) (centerY - radius);
        int diameter = (int) (2 * radius);
        g2d.fillOval(x, y, diameter, diameter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        translationX += 2.0;
        translationY += 2.0;// Increment the horizontal translation
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Homogeneous Translation Circle Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new HomogeneousTranslationCircleGraphics());
            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}
