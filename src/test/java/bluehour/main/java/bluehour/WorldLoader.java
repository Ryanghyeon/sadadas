package bluehour;

import java.util.ArrayList;
import java.util.List;

import bluehour.AI;
import bluehour.heroes.AgileHero;
import bluehour.heroes.SmartHero;
import bluehour.heroes.StrongHero;
import bluehour.puzzles.DoorPuzzle;
import bluehour.puzzles.Puzzle;
import bluehour.puzzles.TerminalPuzzle;

public class WorldLoader {
    public static World loadSample() {
        Map2D map = new Map2D();
        List<World.Entity> entities = new ArrayList<>();
        List<Puzzle> puzzles = new ArrayList<>();

        puzzles.add(new DoorPuzzle(5, 5));
        puzzles.add(new TerminalPuzzle("42"));

        // CCTV AI를 샘플 월드에 추가
        entities.add(new AI(map, 8.0, 8.0));

        List<bluehour.heroes.Hero> heroes = new ArrayList<>();
        heroes.add(new StrongHero(map, 3.0, 3.0, 0.0));
        heroes.add(new SmartHero(map, 3.0, 3.0, 0.0));
        heroes.add(new AgileHero(map, 3.0, 3.0, 0.0));

        return new World(map, entities, puzzles, heroes);
    }
}
