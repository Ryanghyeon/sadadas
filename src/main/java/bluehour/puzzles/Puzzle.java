package bluehour.puzzles;

import bluehour.World;

public class DoorPuzzle extends Puzzle {
    private final int x;
    private final int y;
    private final char op; // '+', '-', '*', '/'
    private boolean solved = false;

    public DoorPuzzle(int x, int y) {
        this(x, y, '+');
    }

    public DoorPuzzle(int x, int y, char op) {
        this.x = x;
        this.y = y;
        this.op = op;
    }

    @Override
    public boolean isSolved() {
        return solved;
    }

    @Override
    public String getHint() {
        return "계산 문제: " + x + " " + op + " " + y + " = ?";
    }

    @Override
    public void onSolved(World world) {
        solved = true;
        // 필요시 월드의 다른 요소와 연동
    }

    private int compute() {
        switch (op) {
            case '+': return x + y;
            case '-': return x - y;
            case '*': return x * y;
            case '/': return (y != 0) ? (x / y) : 0;
            default: return 0;
        }
    }

    // 외부에서 정답 확인용
    public boolean checkAnswer(int answer) {
        return compute() == answer;
    }
}
