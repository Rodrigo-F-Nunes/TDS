import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class gameWindow extends JFrame implements KeyListener {

    JPanel playerCharacter;
    int frameWidth;
    int frameHeight;
    final int playerMovement = 3;
    final int projectileMovement = 5;
    final int enemyMovement = 1;
    List<JPanel> activeProjectiles;
    List<JPanel> activeEnemies;
    Timer gameLoopTimer, timeToSpawn;

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
        activeEnemies = new ArrayList<>();

        gameLoopTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProjectiles();
                updateEnemy();
                collisionHandler();
            }
        });
        gameLoopTimer.start();

        timeToSpawn = new Timer(4500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
        timeToSpawn.start();

        this.add(playerCharacter);
        this.setVisible(true);

        frameWidth = this.getContentPane().getWidth();
        frameHeight = this.getContentPane().getHeight();
    }

    void movePlayerCharacter(int x, int y){

        if (gameLoopTimer.isRunning()) {
            int newXPos = playerCharacter.getX() + x;
            int newYPos = playerCharacter.getY() + y;

            if (newXPos < 0) newXPos = 0;
            if (newXPos + playerCharacter.getWidth() > frameWidth) {
                newXPos = frameWidth - playerCharacter.getWidth();
            }
            playerCharacter.setLocation(newXPos, newYPos);
        }
    }

    void spawnProjectile() {

        JPanel newProjectile = new JPanel();
        newProjectile.setBounds(playerCharacter.getX() + 6, playerCharacter.getY() - 10, 3, 9);
        newProjectile.setBackground(Color.RED);
        this.add(newProjectile);

            //TODO add a cooldown for it later

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

    public void spawnEnemy(){
        JPanel newEnemy = new JPanel();
        Random setEnemyX = new Random();
        newEnemy.setBounds(setEnemyX.nextInt(frameWidth - 15), 0, 15, 15);
        newEnemy.setBackground(Color.GREEN);
        this.add(newEnemy);
        activeEnemies.add(newEnemy);
    }

    public void updateEnemy() {
        List<JPanel> enemiesToRemove = new ArrayList<>();

        for (JPanel enemy : activeEnemies) {
            int newY = enemy.getY() + enemyMovement;
            enemy.setLocation(enemy.getX(), newY);

            if (newY > frameHeight) {
                enemiesToRemove.add(enemy);
            }
        }

        for (JPanel enemy : enemiesToRemove) {
            this.remove(enemy);
            activeEnemies.remove(enemy);
        }


        for (JPanel enemy : activeEnemies){

            int newY = enemy.getY() + enemyMovement;

            if (newY > frameHeight) {
                gameLoopTimer.stop();
                timeToSpawn.stop();

            }
        }

        this.revalidate();
        this.repaint();
    }

    public void collisionHandler(){
        List<JPanel> enemiesToRemove = new ArrayList<>();
        List<JPanel> projectilesToRemove = new ArrayList<>();

        for (JPanel enemy : activeEnemies) {
            for (JPanel projectile : activeProjectiles) {
                if (projectile.getBounds().intersects(enemy.getBounds())) {
                    enemiesToRemove.add(enemy);
                    projectilesToRemove.add(projectile);
                }
            }
        }

        for (JPanel enemy : enemiesToRemove) {
            this.remove(enemy);
            activeEnemies.remove(enemy);
        }
        for (JPanel projectile : projectilesToRemove) {
            this.remove(projectile);
            activeProjectiles.remove(projectile);
        }

        for (JPanel enemy : activeEnemies){
            if(enemy.getBounds().intersects(playerCharacter.getBounds())){
                gameLoopTimer.stop();
                timeToSpawn.stop();
            }
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
