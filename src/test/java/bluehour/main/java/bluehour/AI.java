package bluehour;

import bluehour.Map2D;
import bluehour.Viewport;
import bluehour.World;

public class AI implements World.Entity {
    private final Map2D map;
    private final double x;
    private final double y;
    private boolean alive = true;
    private final double maxDistance = 12.0; // 시야 거리 한도

    public AI(Map2D map, double x, double y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update(double dt, World world) {
        if (!alive || world == null) return;

        Viewport vp = world.getActiveHeroViewport();
        if (vp == null) return;

        double px = vp.getX();
        double py = vp.getY();

        // 플레이어 방향 기준 각도
        double heroAngle = Math.atan2(vp.getDirY(), vp.getDirX());

        // CCTV -> 플레이어 방향 벡터
        double dx = px - x;
        double dy = py - y;
        double dist = Math.hypot(dx, dy);

        // 거리 한도 내 여부
        if (dist > maxDistance) return;

        // 각도 차이: CCTV가 플레이어를 보는 각도 차이
        double toPlayerAngle = Math.atan2(dy, dx);
        double diff = Math.abs(normalizeAngle(toPlayerAngle - heroAngle));

        // 시야 각도 45도(FOV의 절반 22.5도) 이내 검사
        boolean inFov = diff <= Math.toRadians(22.5);

        // 라인 오브 사이트 검사
        boolean los = lineOfSight(map, (int) x, (int) y, (int) px, (int) py);

        if (inFov && los) {
            // 현재 활약 중인 플레이어 히어로에게 데미지 1
            int heroIndex = world.activeHeroIndex;
            if (heroIndex >= 0 && heroIndex < world.heroes.size()) {
                bluehour.heroes.Hero h = world.heroes.get(heroIndex);
                if (h != null) {
                    h.takeDamage(1);
                }
            }
            // CCTV disappears
            alive = false;
        }
    }

    private static double normalizeAngle(double a) {
        while (a <= -Math.PI) a += Math.PI * 2;
        while (a > Math.PI) a -= Math.PI * 2;
        return a;
    }

    // Bresenham 스타일의 간단한 LOS 검사
    private static boolean lineOfSight(Map2D map, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        int x = x0;
        int y = y0;

        while (true) {
            if (map.get(x, y) > 0) return false;
            if (x == x1 && y == y1) return true;

            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 < dx)   { err += dx; y += sy; }
        }
    }
}
