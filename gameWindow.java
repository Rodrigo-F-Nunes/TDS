import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class gameWindow extends JFrame implements KeyListener {

    JPanel playerCharacter;
    int frameWidth;
    int frameHeight;
    final int playerMovement = 3;
    final int projectileMovement = 5;
    List<JPanel> activeProjectiles;
    Timer gameLoopTimer;

    gameWindow(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(720,720);
        this.setResizable(false);
        this.setLayout(null);
        this.addKeyListener(this);

        playerCharacter = new JPanel();

        playerCharacter.setBounds(600, 600, 15, 15);
        playerCharacter.setBackground(Color.BLACK);
        playerCharacter.setOpaque(true);

        activeProjectiles = new ArrayList<>();

        gameLoopTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProjectiles();
            }
        });
        gameLoopTimer.start();

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

    void spawnProjectile() {
        JPanel newProjectile = new JPanel();
        newProjectile.setBounds(playerCharacter.getX() + 6, playerCharacter.getY() - 10, 3, 9);
        newProjectile.setBackground(Color.RED);
        this.add(newProjectile); // turns the projectile visible

            // might as well add a cooldown for it later

        activeProjectiles.add(newProjectile);
        this.revalidate();
        this.repaint();
    }
    private void updateProjectiles() {
        List<JPanel> projectilesToRemove = new ArrayList<>();

        for (JPanel projectile : activeProjectiles) {
            int newY = projectile.getY() - projectileMovement;
            projectile.setLocation(projectile.getX(), newY);

            if (newY < 0) {
                projectilesToRemove.add(projectile);
            }
        }

        for (JPanel projectile : projectilesToRemove) {
            this.remove(projectile);
            activeProjectiles.remove(projectile);
        }

        this.revalidate();
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){
        switch (e.getKeyChar()){
            case 'a':movePlayerCharacter(-playerMovement, 0);
                break;
            case 'd':movePlayerCharacter(+playerMovement, 0);
                break;
            case ' ':spawnProjectile();
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
