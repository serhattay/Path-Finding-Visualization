public class Graph {
    protected static Tile[][] grid = new Tile[Map.ROW][Map.COL];


    protected static Tile[][] generateDefaultGrid() {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                if (i == 7 && j == 0) {
                    grid[i][j] = new Tile(j, i, false, true);
                    continue;
                }
                if (j == 12) {
                    if (i != 7) {
                        grid[i][j] = new Tile(j, i, true);
                    } else {
                        grid[i][j] = new Tile(j, i, false);
                    }
                } else {
                    grid[i][j] = new Tile(j, i, false);
                }
            }
        }
        return grid;
    }
    protected static Tile[][] generateRandomGrid() {
        return null;
    }

}
