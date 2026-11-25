package bluehour.puzzles;

import bluehour.World;

/**
 * Terminal Puzzle 3층 - 간단한 터미널-스타일 퍼즐
 * 각 층은 (prompt, answer, meaning)로 구성되며,
 * 1층 -> 2층 -> 3층 순으로 진행됩니다.
 */
public class TerminalPuzzle extends Puzzle {
    public static class Floor {
        final String prompt;   // 프롬프트/힌트
        final String answer;   // 이 층의 정답
        final String meaning;  // 뜻 풀이 (힌트가 풀렸을 때 표시)
        boolean solved;

        Floor(String prompt, String answer, String meaning) {
            this.prompt = prompt;
            this.answer = answer;
            this.meaning = meaning;
            this.solved = false;
        }
    }

    private final int x;
    private final int y;
    private final Floor[] floors;
    private int currentFloor = 0;

    // 좌표(x,y)와 3층 데이터로 퍼즐 생성
    public TerminalPuzzle(int x, int y) {
        this.x = x;
        this.y = y;

        // 3층 데이터 예시
        // Floor 1: 프롬프트 -> 정답 "OPEN" -> 뜻
        Floor f1 = new Floor("힌트: 시험", "자바시험쉽게내주세요", "");
        // Floor 2: 프롬프트 -> 정답 "NEXT" -> 뜻
        Floor f2 = new Floor("힌트: 량현씨가 제일많이하는말", "집가고싶어요", "");
        // Floor 3: 프롬프트 -> 정답 "EXIT" -> 뜻
        Floor f3 = new Floor("", "여러분 팀플 수고하셨습니다", "");

        this.floors = new Floor[] { f1, f2, f3 };
    }

    // 플레이어의 입력 단어로 현재 층 풀이 시도
    public boolean guess(String word) {
        if (currentFloor >= floors.length) return true;
        Floor f = floors[currentFloor];
        if (f.answer.equalsIgnoreCase(word)) {
            f.solved = true;
            currentFloor++;
            return currentFloor >= floors.length;
        }
        return false;
    }

    @Override
    public boolean isSolved() {
        return currentFloor >= floors.length;
    }

    @Override
    public String getHint() {
        if (currentFloor < floors.length) {
            Floor f = floors[currentFloor];
            if (!f.solved) {
                return f.prompt;
            } else {
                // 직전 층의 뜻 풀이를 보여주기
                Floor prev = currentFloor > 0 ? floors[currentFloor - 1] : null;
                if (prev != null) return "뜻 풀이: " + prev.meaning;
            }
        }
        return "모든 층 퍼즐을 풀었습니다.";
    }

    @Override
    public void onSolved(World world) {
        // 필요 시 월드와 연동 로직 추가
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
