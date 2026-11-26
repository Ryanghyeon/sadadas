package bluehour.heroes;

import bluehour.Map2D;
import bluehour.Viewport;
import bluehour.World;

public abstract class Hero implements Viewport {
    protected final Map2D map;
    protected double posX;
    protected double posY;
    protected double dirX, dirY;
    protected double planeX, planeY;
    protected final String name;
    protected final double speed;
    protected final double rotateSpeed;

    // 건강 포인트 추가
    private int health = 3;

    public Hero(Map2D map, double x, double y, double dir, String name,
            double speed, double rotateSpeed) {
        this.map = map;
        this.posX = x;
        this.posY = y;
        this.dirX = Math.cos(dir);
        this.dirY = Math.sin(dir);
        this.planeX = -this.dirY * 0.66;
        this.planeY = this.dirX * 0.66;
        this.name = name;
        this.speed = speed;
        this.rotateSpeed = rotateSpeed;
    }

    @Override public double getX() { return posX; }
    @Override public double getY() { return posY; }
    @Override public double getDirX() { return dirX; }
    @Override public double getDirY() { return dirY; }
    @Override public double getPlaneX() { return planeX; }
    @Override public double getPlaneY() { return planeY; }
    @Override public String getName() { return name; }

    public int getHealth() { return health; }
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
    public boolean isAlive() { return health > 0; }

    public void moveForward(double dt) {
        double step = speed * dt;
        double nx = posX + dirX * step;
        double ny = posY + dirY * step;
        if (map.get((int) nx, (int) posY) == 0) posX = nx;
        if (map.get((int) posX, (int) ny) == 0) posY = ny;
    }

    public void moveBackward(double dt) {
        double step = speed * dt;
        double nx = posX - dirX * step;
        double ny = posY - dirY * step;
        if (map.get((int) nx, (int) posY) == 0) posX = nx;
        if (map.get((int) posX, (int) ny) == 0) posY = ny;
    }

    public void strafeLeft(double dt) {
        double step = speed * dt;
        double dx = -dirY, dy = dirX;
        double nx = posX + dx * step;
        double ny = posY + dy * step;
        if (map.get((int) nx, (int) posY) == 0) posX = nx;
        if (map.get((int) posX, (int) ny) == 0) posY = ny;
    }

    public void strafeRight(double dt) {
        double step = speed * dt;
        double dx = dirY, dy = -dirX;
        double nx = posX + dx * step;
        double ny = posY + dy * step;
        if (map.get((int) nx, (int) posY) == 0) posX = nx;
        if (map.get((int) posX, (int) ny) == 0) posY = ny;
    }

    public void rotate(double dt) {
        double ang = rotateSpeed * dt;
        double c = Math.cos(ang);
        double s = Math.sin(ang);
        double oldDirX = dirX;
        dirX = dirX * c - dirY * s;
        dirY = oldDirX * s + dirY * c;
        double oldPlaneX = planeX;
        planeX = planeX * c - planeY * s;
        planeY = oldPlaneX * s + planeY * c;
    }

    public boolean canBreak(World world) { return false; }
    public String getHint(World world) { return null; }
    public void update(double dt, World world) { }
}
