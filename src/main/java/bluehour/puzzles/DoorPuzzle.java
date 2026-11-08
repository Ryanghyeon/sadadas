package bluehour.puzzles;

import bluehour.World;

public class DoorPuzzle implements Puzzle {
    private boolean solved = false;
    private final int x, y;

    public DoorPuzzle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isSolved() {
        return solved;
    }

    @Override
    public boolean attemptSolve(String input) {
        if ("OPEN".equalsIgnoreCase(input)) {
            solved = true;
            return true;
        }
        return false;
    }

    @Override
    public void onSolved(World world) {
        // 맵 확장 예시: 문이 열리면 벽 제거
        world.map.set(x, y, 0);
    }

    @Override
    public String getHint() {
        return "OPEN 입력으로 문을 엽니다.";
    }
}
