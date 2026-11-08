package bluehour.heroes;

import bluehour.Map2D;
import bluehour.World;
import bluehour.puzzles.Puzzle;

public class SmartHero extends Hero {
    public SmartHero(Map2D map, double x, double y, double dir) {
        super(map, x, y, dir, "Smart", 0.08, 0.04);
    }

    @Override
    public String getHint(World world) {
        if (world.puzzles != null) {
            for (Puzzle p : world.puzzles) {
                if (!p.isSolved()) {
                    String hint = p.getHint();
                    return hint != null ? hint : "힌트 없음";
                }
            }
        }
        return "힌트 없음";
    }
}
