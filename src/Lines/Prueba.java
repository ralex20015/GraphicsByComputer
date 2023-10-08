package Lines;

import javax.swing.*;

public class Prueba extends JFrame {

    public static void main(String[] args) {
        new Prueba();
    }

    public Prueba(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MyCanva(500,500));
        setVisible(true);
    }
}
