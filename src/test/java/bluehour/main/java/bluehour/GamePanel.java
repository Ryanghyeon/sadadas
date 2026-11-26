package bluehour;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import bluehour.heroes.Hero;

public class GamePanel extends JPanel implements KeyListener {
  private final World world;
  private final Engine engine;
  private final RayCaster rc;
  private final int w;
  private final int h;
  private enum Screen { MENU, GAME, PAUSE, INVENTORY }
  private Screen current = Screen.MENU;

  // UI for hero selection
  private final Rectangle[] heroBoxes = new Rectangle[] {
     new Rectangle(60, 100, 200, 160),
     new Rectangle(320, 100, 200, 160),
     new Rectangle(580, 100, 200, 160)
  };
  private Rectangle startButton;
  private int selectedHeroIndex = -1;

  public GamePanel(World world, Engine engine, RayCaster rc, int w, int h) {
     this.world = world;
     this.engine = engine;
     this.rc = rc;
     this.w = w; this.h = h;
     setPreferredSize(new Dimension(w, h));
     setFocusable(true);
     addKeyListener(this);
     startButton = new Rectangle(w/2 - 60, h - 90, 120, 32);

     addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
           Point p = e.getPoint();
           if (current == Screen.MENU) {
              for (int i=0;i<heroBoxes.length;i++) {
                 if (heroBoxes[i].contains(p)) { selectedHeroIndex = i; repaint(); return; }
              }
              if (startButton.contains(p) && world != null && world.heroes != null && world.heroes.size() > selectedHeroIndex) {
                 world.activeHeroIndex = selectedHeroIndex;
                 current = Screen.GAME;
                 if (engine != null) engine.start();
                 repaint();
              }
           }
        }
     });
  }

  public void start() { current = Screen.MENU; selectedHeroIndex = -1; }

  @Override protected void paintComponent(Graphics g) {
     super.paintComponent(g);
     switch (current) {
        case MENU: renderMenu(g); break;
        case GAME: renderGame(g); break;
        case PAUSE: renderGame(g); renderPause(g); break;
        case INVENTORY: renderGame(g); renderInventory(g); break;
     }
  }

  private void renderMenu(Graphics g) {
     g.setColor(new Color(0,0,0,180));
     g.fillRect(0,0,w,h);
     g.setColor(Color.WHITE);
     g.setFont(new Font("SansSerif", Font.BOLD, 20));
     g.drawString("히어로를 클릭하고 Start로 시작", 20, 40);
     for (int i = 0; i < 3; i++) {
        Rectangle r = new Rectangle(heroBoxes[i]);
        Color fill = (selectedHeroIndex == i) ? new Color(60,120,180) : new Color(60,60,60);
        g.setColor(fill);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.WHITE);
        g.drawRect(r.x, r.y, r.width, r.height);
        String name = (world != null && world.heroes != null && world.heroes.size() > i) ? world.heroes.get(i).getName() : ("Hero" + (i+1));
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(name, r.x + 10, r.y + r.height - 20);
        if (selectedHeroIndex == i) { g.setColor(Color.YELLOW); g.drawRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4); }
     }
     g.setColor(Color.LIGHT_GRAY);
     g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
     g.setColor(Color.BLACK);
     g.setFont(new Font("SansSerif", Font.BOLD, 14));
     FontMetrics fm = g.getFontMetrics();
     String btn = "Start";
     int tx = startButton.x + (startButton.width - fm.stringWidth(btn)) / 2;
     int ty = startButton.y + (startButton.height - fm.getHeight()) / 2 + fm.getAscent();
     g.drawString(btn, tx, ty);
     if (selectedHeroIndex >= 0) {
        String hint = "선택 히어로: ";
        if (world != null && world.heroes != null && world.heroes.size() > selectedHeroIndex) {
           hint += world.heroes.get(selectedHeroIndex).getName();
        } else {
           hint += "Hero" + (selectedHeroIndex + 1);
        }
        g.setColor(Color.YELLOW);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(hint, 10, h - 10);
     }
  }

  private void renderGame(Graphics g) {
     BufferedImage frame = engine.renderFrame();
     g.drawImage(frame, 0, 0, null);
     if (world != null && world.heroes != null && world.activeHeroIndex >= 0
         && world.activeHeroIndex < world.heroes.size()) {
        Hero active = world.heroes.get(world.activeHeroIndex);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("활성 히어로: " + active.getName(), 10, 20);
     }
  }

  private void renderPause(Graphics g) {
     g.setColor(new Color(0,0,0,128));
     g.fillRect(0,0,w,h);
     g.setColor(Color.WHITE);
     g.setFont(new Font("SansSerif", Font.BOLD, 20));
     g.drawString("PAUSED", w/2 - 40, h/2);
  }

  private void renderInventory(Graphics g) {
     g.setColor(new Color(0,0,0,128));
     g.fillRect(0,0,w,h);
     g.setColor(Color.WHITE);
     g.setFont(new Font("SansSerif", Font.BOLD, 20));
     g.drawString("Inventory (샘플)", 20, 40);
  }

  @Override public void keyPressed(KeyEvent e) {
     int code = e.getKeyCode();
     if (world != null && world.activeHeroIndex >= 0 && world.heroes != null && world.heroes.size() > world.activeHeroIndex) {
        Hero active = world.heroes.get(world.activeHeroIndex);
        double dt = 0.016;
        switch (code) {
           case KeyEvent.VK_W: active.moveForward(dt); break;
           case KeyEvent.VK_S: active.moveBackward(dt); break;
           case KeyEvent.VK_A: active.strafeLeft(dt); break;
           case KeyEvent.VK_D: active.strafeRight(dt); break;
           case KeyEvent.VK_LEFT: active.rotate(-dt); break;
           case KeyEvent.VK_RIGHT: active.rotate(dt); break;
        }
        repaint();
     }
     if (code == KeyEvent.VK_ENTER && current == Screen.MENU && selectedHeroIndex >= 0) {
        if (world != null && world.heroes != null && world.heroes.size() > selectedHeroIndex) {
           world.activeHeroIndex = selectedHeroIndex;
        }
        current = Screen.GAME;
        if (engine != null) engine.start();
        repaint();
     }
     if (code == KeyEvent.VK_ESCAPE && current == Screen.GAME) current = Screen.PAUSE;
     if (code == KeyEvent.VK_I && current == Screen.GAME) current = Screen.INVENTORY;
     if (code == KeyEvent.VK_ESCAPE && current == Screen.PAUSE) current = Screen.GAME;
     if (code == KeyEvent.VK_ESCAPE && current == Screen.INVENTORY) current = Screen.PAUSE;
  }

  @Override public void keyReleased(KeyEvent e) {}
  @Override public void keyTyped(KeyEvent e) {}
}
