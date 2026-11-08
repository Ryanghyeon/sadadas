package bluehour;

import bluehour.heroes.Hero;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
    private final World world;
    private final Engine engine;
    private RayCaster rc;
    private final int w;
    private final int h;

    private enum Screen {
        MENU,
        GAME,
        PAUSE,
        INVENTORY
    }

    private Screen current = Screen.MENU;

    public GamePanel(World world, Engine engine, RayCaster rc, int w, int h) {
        this.world = world;
        this.engine = engine;
        this.rc = rc;
        this.w = w;
        this.h = h;
        setPreferredSize(new Dimension(w, h));
        setFocusable(true);
        addKeyListener(this);
    }

    public void start() {
        current = Screen.MENU;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (current) {
            case MENU:
                renderMenu(g);
                break;
            case GAME:
                renderGame(g);
                break;
            case PAUSE:
                renderGame(g);
                renderPause(g);
                break;
            case INVENTORY:
                renderGame(g);
                renderInventory(g);
                break;
            default:
                // 알 수 없는 상태일 때도 예측 가능한 화면으로 안전하게 대체
                renderMenu(g);
                break;
        }
    }

    private void renderMenu(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("히어로를 선택하고 Enter로 시작", 20, 40);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString("1) Strong  2) Smart  3) Agile", 20, 60);
        g.drawString("Enter를 누르면 선택된 히어로로 시작합니다.", 20, 82);
    }

    private void renderGame(Graphics g) {
        BufferedImage frame = engine.renderFrame();
        g.drawImage(frame, 0, 0, null);
        // 활성 히어로 정보 표시
        if (world.heroes != null
                && world.activeHeroIndex >= 0
                && world.activeHeroIndex < world.heroes.size()) {
            Hero active = world.heroes.get(world.activeHeroIndex);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.drawString("활성 히어로: " + active.getName(), 10, 20);
        }
    }

    private void renderPause(Graphics g) {
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("PAUSED", w / 2 - 40, h / 2);
    }

    private void renderInventory(Graphics g) {
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("Inventory (샘플)", 20, 40);
    }

    // 키 입력 처리
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_1) {
            worldSwitch(0);
        }
        if (code == KeyEvent.VK_2) {
            worldSwitch(1);
        }
        if (code == KeyEvent.VK_3) {
            worldSwitch(2);
        }
        if (code == KeyEvent.VK_ENTER && current == Screen.MENU) {
            current = Screen.GAME;
        }
        if (code == KeyEvent.VK_ESCAPE && current == Screen.GAME) {
            current = Screen.PAUSE;
        }
        if (code == KeyEvent.VK_I && current == Screen.GAME) {
            current = Screen.INVENTORY;
        }
        if (code == KeyEvent.VK_ESCAPE && current == Screen.PAUSE) {
            current = Screen.GAME;
        }
        if (code == KeyEvent.VK_ESCAPE && current == Screen.INVENTORY) {
            current = Screen.PAUSE;
        }
    }

    // helper
    private void worldSwitch(int idx) {
        if (world != null && world.heroes != null && idx >= 0 && idx < world.heroes.size()) {
            world.activeHeroIndex = idx;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
