package bluehour;

import bluehour.heroes.Hero;
import bluehour.puzzles.Puzzle;
import java.util.List;

public class World {
    public final Map2D map;
    public final List<Entity> entities;
    public final List<Puzzle> puzzles;
    public final List<bluehour.heroes.Hero> heroes;
    public int activeHeroIndex = 0;

    public World(
            Map2D map,
            List<Entity> entities,
            List<Puzzle> puzzles,
            List<bluehour.heroes.Hero> heroes) {
        this.map = map;
        this.entities = entities;
        this.puzzles = puzzles;
        this.heroes = heroes;
    }

    public bluehour.Viewport getActiveViewport() {
        if (heroes == null || heroes.isEmpty()) return null;
        return heroes.get(Math.max(0, Math.min(activeHeroIndex, heroes.size() - 1)));
    }

    public void update(double dt) {
        if (entities != null) for (Entity e : entities) e.update(dt, this);
        if (puzzles != null)
            for (Puzzle p : puzzles) {
                if (p.isSolved()) p.onSolved(this);
            }
        if (heroes != null) for (Hero h : heroes) h.update(dt, this);
    }

    public interface Entity {
        void update(double dt, World world);
    }
}
