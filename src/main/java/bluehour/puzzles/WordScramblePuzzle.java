package bluehour.puzzles;

import java.util.Random;

import bluehour.World;

public class WordScramblePuzzle extends Puzzle {
    public static class Floor {
        final String scrambled;
        final String answer;
        final String meaning;
        boolean solved;

        Floor(String scrambled, String answer, String meaning) {
            this.scrambled = scrambled;
            this.answer = answer;
            this.meaning = meaning;
            this.solved = false;
        }
    }

    private final int x;
    private final int y;
    private final Floor[] floors;
    private int currentFloor = 0;

    // floorsData: [{scrambled, answer, meaning}, ...] 길이가 3인 배열
    public WordScramblePuzzle(int x, int y, String[][] floorsData) {
        this.x = x;
        this.y = y;
        this.floors = new Floor[floorsData.length];
        for (int i = 0; i < floorsData.length; i++) {
            String scrambled = floorsData[i][0];
            String answer = floorsData[i][1];
            String meaning = floorsData[i][2];
            // scrambled가 비어있으면 deterministic하게 scramble(answer)로 채움
            if (scrambled == null || scrambled.isEmpty()) {
                scrambled = scramble(answer);
            }
            this.floors[i] = new Floor(scrambled, answer, meaning);
        }
    }

    // 간단한 형태의 스크램블: 같은 입력에 대해 같은 결과를 주도록 고정 시드 사용
    private static String scramble(String s) {
        if (s == null || s.length() <= 1) return s;
        String[] chars = s.split("");
        Random rand = new Random(0xABCDEF); // 고정 시드
        // Fisher-Yates 셔플
        for (int i = chars.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
        }
        return String.join("", chars);
    }

    // 플레이어의 입력으로 현재 층 풀이 시도
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
                return "스크램블 단어: " + f.scrambled;
            } else {
                // 직전 층의 뜻 풀이를 보여주고, 필요 시 다음 층으로 넘어가도록
                int idx = currentFloor - 1;
                if (idx >= 0 && idx < floors.length) {
                    return "뜻 풀이: " + floors[idx].meaning;
                }
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
