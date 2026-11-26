package bluehour;

import javax.swing.JFrame;

public class GameWindow {
  private final JFrame frame;
  private final Engine engine;
  private final GamePanel panel;

  public GameWindow(Engine engine) {
     this.engine = engine;
     this.frame = new JFrame("BlueHour");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     int w = 800, h = 600;
     this.panel = new GamePanel(engine.getWorld(), engine, engine.getRayCaster(), w, h);
     frame.add(panel);
     frame.pack();
     frame.setLocationRelativeTo(null);
  }

  public void show() {
     frame.setVisible(true);
     panel.requestFocusInWindow();

     // 간단한 게임 루프: 매 프레임 월드 업데이트 + 렌더
     javax.swing.Timer timer = new javax.swing.Timer(16, ev -> {
        engine.update(0.016);
        panel.repaint();
     });
     timer.start();
  }

  public GamePanel getPanel() { return panel; }
}
