package bluehour.heroes;

import bluehour.Map2D;
import bluehour.World;

public class StrongHero extends Hero {
    public StrongHero(Map2D map, double x, double y, double dir) {
        super(map, x, y, dir, "Strong", 0.08, 0.04);
    }

   @Override public boolean canBreak(World world) {
    int tx = (int)(posX + dirX);
    int ty = (int)(posY + dirY);
    if (world != null && world.map != null && world.map.get(tx, ty) == 1) {
        world.map.set(tx, ty, 0);
        return true;
    }
    return false;
   }
}
