package bluehour.puzzles;

public interface Puzzle {
    boolean isSolved();

    boolean attemptSolve(String input);

    default void onSolved(bluehour.World world) {}

    default String getHint() {
        return null;
    }
}
