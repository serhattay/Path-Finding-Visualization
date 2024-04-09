public class Graph {
    protected static Tile[][] grid = new Tile[Map.ROW][Map.COL];


    public static void setAdjacentTiles() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length ; j++) {
                Tile currentTile = grid[i][j];

                for (int direction: new int[]{-1, 1}) {
                    if (i + direction >= 0 && i + direction < Map.ROW) {
                        currentTile.addToAdjacents(new Adjacent(grid[i + direction][j], 1.0));

                        if (j + direction >= 0 && j + direction < Map.COL) {
                            currentTile.addToAdjacents(new Adjacent(grid[i + direction][j + direction], Math.sqrt(2)));
                        }
                        if (j - direction >= 0 && j - direction < Map.COL) {
                            currentTile.addToAdjacents(new Adjacent(grid[i + direction][j - direction], Math.sqrt(2)));
                        }
                    }
                    if (j + direction >= 0 && j + direction < Map.COL) {
                        currentTile.addToAdjacents(new Adjacent(grid[i][j + direction], 1.0));
                    }
                }
            }
        }
    }
    public static Tile[][] generateDefaultGrid() {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                if (i == 7 && j == 0) {
                    grid[i][j] = new Tile(i, j, false, true);
                    continue;
                }
                if (j == 12) {
                    if (i != 7) {
                        grid[i][j] = new Tile(i, j, true);
                    } else {
                        grid[i][j] = new Tile(i, j, false);
                    }
                } else {
                    grid[i][j] = new Tile(i, j, false);
                }
            }
        }
        return grid;
    }
    public static Tile[][] generateRandomGrid() {
        return null;
    }

}


