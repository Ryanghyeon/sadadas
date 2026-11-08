package bluehour;

import javax.swing.JFrame;

public class GameWindow {
    private final Engine engine;
    private final GamePanel panel;

    public GameWindow(Engine engine) {
        this.engine = engine;
        // World를 Engine과 연결해 Panel로 전달하는 방식으로 구성
        // 여기서는 엔진이 World를 보유한다고 가정
        this.panel = new GamePanel(engine.getWorld(), engine, engine.getRenderer(), 800, 600);
    }

    public void show() {
        JFrame frame = new JFrame("개인 프로젝트 - 3인 히어로 예제");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.start();
    }
}
