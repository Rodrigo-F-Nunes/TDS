import java.awt.event.*;
import javax.swing.*;

public class launchMenu implements ActionListener{

    JFrame frame = new JFrame();
    JButton startButton = new JButton("Start Game");

    launchMenu(){

        startButton.setBounds(260,340,200,40);
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        frame.add(startButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720,720);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()== startButton) {
            frame.dispose();
            new gameWindow();
        }
    }
}
