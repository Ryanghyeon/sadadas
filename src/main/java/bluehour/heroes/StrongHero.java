package bluehour.heroes;

import bluehour.Map2D;
import bluehour.World;

public class StrongHero extends Hero {
    public StrongHero(Map2D map, double x, double y, double dir) {
        super(map, x, y, dir, "Strong", 0.08, 0.04);
    }

    @Override
    public boolean tryBreak(World world) {
        int tx = (int) (x + dirX);
        int ty = (int) (y + dirY);
        if (map.get(tx, ty) == 1) {
            map.set(tx, ty, 0);
            return true;
        }
        return false;
    }
}
