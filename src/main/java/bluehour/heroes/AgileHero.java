package bluehour.heroes;

import bluehour.Map2D;

public class AgileHero extends Hero {
    public AgileHero(Map2D map, double x, double y, double dir) {
        super(map, x, y, dir, "Agile", 0.12, 0.05);
    }
}
