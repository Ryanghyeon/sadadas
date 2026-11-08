package bluehour.heroes;

import bluehour.Map2D;
import bluehour.Viewport;
import bluehour.World;

public abstract class Hero implements Viewport {
    protected final Map2D map;
    protected double x, y;
    protected double dirX, dirY;
    protected double planeX, planeY;
    protected final String name;
    protected final double speed;
    protected final double rotateSpeed;

    public Hero(
            Map2D map,
            double x,
            double y,
            double dir,
            String name,
            double speed,
            double rotateSpeed) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.dirX = Math.cos(dir);
        this.dirY = Math.sin(dir);
        this.planeX = -this.dirY * 0.66;
        this.planeY = this.dirX * 0.66;
        this.name = name;
        this.speed = speed;
        this.rotateSpeed = rotateSpeed;
    }

    // Viewport 구현
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getDirX() {
        return dirX;
    }

    @Override
    public double getDirY() {
        return dirY;
    }

    @Override
    public double getPlaneX() {
        return planeX;
    }

    @Override
    public double getPlaneY() {
        return planeY;
    }

    @Override
    public String getName() {
        return name;
    }

    // 기본 이동/회전

    public void moveForward(double dt) {
        double step = speed * dt;
        double nx = x + dirX * step;
        double ny = y + dirY * step;
        if (map.get((int) nx, (int) y) == 0) {
            x = nx;
        }
        if (map.get((int) x, (int) ny) == 0) {
            y = ny;
        }
    }

    public void moveBackward(double dt) {
        double step = speed * dt;
        double nx = x - dirX * step;
        double ny = y - dirY * step;
        if (map.get((int) nx, (int) y) == 0) {
            x = nx;
        }
        if (map.get((int) x, (int) ny) == 0) {
            y = ny;
        }
    }

    public void strafeLeft(double dt) {
        double step = speed * dt;
        double dx = -dirY, dy = dirX;
        double nx = x + dx * step;
        double ny = y + dy * step;
        if (map.get((int) nx, (int) y) == 0) {
            x = nx;
        }
        if (map.get((int) x, (int) ny) == 0) {
            y = ny;
        }
    }

    public void strafeRight(double dt) {
        double step = speed * dt;
        double dx = dirY, dy = -dirX;
        double nx = x + dx * step;
        double ny = y + dy * step;
        if (map.get((int) nx, (int) y) == 0) {
            x = nx;
        }
        if (map.get((int) x, (int) ny) == 0) {
            y = ny;
        }
    }

    public void rotate(double dt) {
        double ang = rotateSpeed * dt;
        double c = Math.cos(ang), s = Math.sin(ang);
        double oldDirX = dirX;
        dirX = dirX * c - dirY * s;
        dirY = oldDirX * s + dirY * c;
        double oldPlaneX = planeX;
        planeX = planeX * c - planeY * s;
        planeY = oldPlaneX * s + planeY * c;
    }

    // 퍼블릭 훅들 (필요 시 오버라이드)
    public boolean tryBreak(World world) {
        return false;
    }

    public String getHint(World world) {
        return null;
    }

    public void update(double dt, World world) {}
}
