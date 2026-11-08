package bluehour;

public class Map2D {
    private final int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    public int get(int x, int y) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) return 1;
        return map[y][x];
    }

    public void set(int x, int y, int v) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) return;
        map[y][x] = v;
    }

    public int width() {
        return map[0].length;
    }

    public int height() {
        return map.length;
    }
}
