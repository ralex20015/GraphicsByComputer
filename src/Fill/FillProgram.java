package Fill;

import Lines.MyCanva;

import javax.swing.*;

public class FillProgram extends JFrame {

    public static void main(String[] args) {
        new FillProgram();
    }

    public FillProgram(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MyCanva(500,500));
        setVisible(true);
    }
}
