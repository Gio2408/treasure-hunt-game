package tech.fallqvist;

import tech.fallqvist.entity.Player;
import tech.fallqvist.object.ObjectManager;
import tech.fallqvist.object.SuperObject;
import tech.fallqvist.sound.SoundManager;
import tech.fallqvist.tile.TileManager;
import tech.fallqvist.ui.UI;
import tech.fallqvist.util.CollisionChecker;
import tech.fallqvist.util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int maxScreenColumns = 16;
    private final int maxScreenRows = 12;
    private final int screenWidth = tileSize * maxScreenColumns; // 768 px
    private final int screenHeight = tileSize * maxScreenRows; // 576 px

    // WORLD SETTINGS
    private final int maxWorldColumns = 50;
    private final int maxWorldRows = 50;

    // FPS
    private final int FPS = 60;

    // INITIALIZE UTILS
    private final KeyHandler keyHandler = new KeyHandler();
    private final CollisionChecker collisionChecker = new CollisionChecker(this);
    private final TileManager tileManager = new TileManager(this);
    private final ObjectManager objectManager = new ObjectManager(this);
    private final SoundManager music = new SoundManager();
    private final SoundManager soundEffect = new SoundManager();
    private final UI ui = new UI(this);

    // GAME THREAD
    private Thread gameThread;

    // IN-GAMES ENTITIES & OBJECTS
    private final Player player = new Player(this, keyHandler);
    private final SuperObject[] objects = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame() {
        objectManager.setObjects();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // TILES
        tileManager.draw(graphics2D);

        // OBJECTS
        for (SuperObject object : objects) {
            if (object != null) {
                object.draw(graphics2D, this);
            }
        }

        // ENTITIES
        player.draw(graphics2D);

        // UI
        ui.draw(graphics2D);

        // CLOSE
        graphics2D.dispose();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenColumns() {
        return maxScreenColumns;
    }

    public int getMaxScreenRows() {
        return maxScreenRows;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldColumns() {
        return maxWorldColumns;
    }

    public int getMaxWorldRows() {
        return maxWorldRows;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public GamePanel setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public SuperObject[] getObjects() {
        return objects;
    }

    public UI getUi() {
        return ui;
    }
}
