package bluehour.puzzles;

public class TerminalPuzzle implements Puzzle {
    private boolean solved = false;
    private final String answer;

    public TerminalPuzzle(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean isSolved() {
        return solved;
    }

    @Override
    public boolean attemptSolve(String input) {
        if (input != null && input.equalsIgnoreCase(answer)) {
            solved = true;
            return true;
        }
        return false;
    }

    @Override
    public String getHint() {
        return "정답 코드: " + answer;
    }
}
