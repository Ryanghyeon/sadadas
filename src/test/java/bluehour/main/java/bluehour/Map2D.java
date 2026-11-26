package bluehour;

public class Map2D {
    private final int[][] map;

    public Map2D() {
    int size = 24;
    map = new int[size][size];
    for (int i = 0; i < size; i++) {
        map[i][0] = 1;
        map[i][size - 1] = 1;
        map[0][i] = 1;
        map[size - 1][i] = 1;
    }
    // 예시로 간단한 내부 벽 더하기
    for (int x = 3; x < size - 3; x++) {
        map[x][12] = 1;
    }
}

public int get(int x, int y) {
    if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) return 1;
    return map[x][y];
}

public void set(int x, int y, int v) {
    if (x >= 0 && y >= 0 && x < map.length && y < map[0].length) {
        map[x][y] = v;
    }
}
}
