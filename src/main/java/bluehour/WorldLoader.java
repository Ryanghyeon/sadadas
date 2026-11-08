package bluehour;

import bluehour.heroes.AgileHero;
import bluehour.heroes.Hero;
import bluehour.heroes.SmartHero;
import bluehour.heroes.StrongHero;
import bluehour.puzzles.DoorPuzzle;
import bluehour.puzzles.TerminalPuzzle;
import java.util.ArrayList;
import java.util.List;

public class WorldLoader {
    public static World loadSample() {
        Map2D map = new Map2D();
        List<World.Entity> entities = new ArrayList<>();
        List<bluehour.puzzles.Puzzle> puzzles = new ArrayList<>();

        // 퍼즐 예시
        puzzles.add(new DoorPuzzle(5, 5));
        puzzles.add(new TerminalPuzzle("42"));

        // 3인 히어로 예시
        List<Hero> heroes = new ArrayList<>();
        heroes.add(new StrongHero(map, 3.0, 3.0, 0.0));
        heroes.add(new SmartHero(map, 3.0, 3.0, 0.0));
        heroes.add(new AgileHero(map, 3.0, 3.0, 0.0));

        return new World(map, entities, puzzles, heroes);
    }
}
