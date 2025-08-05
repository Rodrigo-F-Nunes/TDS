import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class gameWindow extends JFrame implements KeyListener {

    JPanel playerCharacter;
    int frameWidth;
    int frameHeight;
    final int playerMovement = 3;

    gameWindow(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(720,720);
        this.setLayout(null);
        this.addKeyListener(this);

        playerCharacter = new JPanel();

        playerCharacter.setBounds(600, 600, 15, 15);
        playerCharacter.setBackground(Color.BLACK);
        playerCharacter.setOpaque(true);

        this.add(playerCharacter);
        this.setVisible(true);

        frameWidth = this.getContentPane().getWidth();
        frameHeight = this.getContentPane().getHeight();
    }

    void movePlayerCharacter(int x, int y){

        int newXPos = playerCharacter.getX() + x;
        int newYPos = playerCharacter.getY() + y;

        if (newXPos < 0) newXPos = 0;
        if (newXPos + playerCharacter.getWidth() > frameWidth) {
            newXPos = frameWidth - playerCharacter.getWidth();
        }

        playerCharacter.setLocation(newXPos, newYPos);
    }

    @Override
    public void keyTyped(KeyEvent e){
        switch (e.getKeyChar()){
            case 'a':movePlayerCharacter(-playerMovement, 0);
                break;
            case 'd':movePlayerCharacter(+playerMovement, 0);
                break;
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case 37:movePlayerCharacter(-playerMovement, 0);
                break;
            case 39:movePlayerCharacter(+playerMovement, 0);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
    }
}
