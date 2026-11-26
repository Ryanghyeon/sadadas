package bluehour;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Engine {
  private final World world;
  private final RayCaster rc;

  public Engine(World world, RayCaster rc) {
    this.world = world;
    this.rc = rc;
  }

  public World getWorld() { return world; }
  public RayCaster getRayCaster() { return rc; }

  public void start() {
    // 기본 실행 준비
  }

  public void update(double dt) {
    if (world != null) world.update(dt);
  }

  public BufferedImage renderFrame() {
    int w = 800;
    int h = 600;

    // ARGB로 설정하여 FOV 오버레이의 투명도를 표현할 수 있게 함
    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = img.createGraphics();

    // 배경: 하늘/바닥
    g.setColor(new Color(135, 206, 235)); // 하늘색
    g.fillRect(0, 0, w, h / 2);
    g.setColor(new Color(64, 64, 64));
    g.fillRect(0, h / 2, w, h / 2);

    // 간단한 중앙 교차점 포인터
    g.setColor(Color.YELLOW);
    int cx = w / 2;
    int cy = h / 2;
    g.fillRect(cx - 1, cy - 1, 3, 3);

    // FOV overlay (45도) 표시
    if (world != null) {
      Viewport vp = world.getActiveHeroViewport();
      if (vp != null) {
        double angle = Math.atan2(vp.getDirY(), vp.getDirX());
        double halfFov = Math.toRadians(22.5);
        int L = Math.min(w, h) / 2;
        int lx = (int) (cx + Math.cos(angle - halfFov) * L);
        int ly = (int) (cy + Math.sin(angle - halfFov) * L);
        int rx = (int) (cx + Math.cos(angle + halfFov) * L);
        int ry = (int) (cy + Math.sin(angle + halfFov) * L);
        int[] xs = {cx, lx, rx};
        int[] ys = {cy, ly, ry};
        // 반투명 초록색으로 시야를 표현
        g.setColor(new Color(0, 255, 0, 80));
        g.fillPolygon(xs, ys, 3);
        g.setColor(new Color(0, 255, 0, 120));
        g.drawLine(cx, cy, lx, ly);
        g.drawLine(cx, cy, rx, ry);
      }
    }

    g.dispose();
    return img;
  }
}
