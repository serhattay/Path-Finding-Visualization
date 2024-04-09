public class Tile {
    protected int row;
    protected int col;
    protected boolean isObstacle;
    protected boolean isSource;

    Tile(int col, int row, boolean isObstacle) {
        this(col, row, isObstacle, false);
    }

    Tile(int col, int row, boolean isObstacle, boolean isSource) {
        this.col = col;
        this.row = row;
        this.isObstacle = isObstacle;
        this.isSource = isSource;
    }

    protected static void drawGrid(Tile[][] grid) {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.isSource) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                } else if (tile.isObstacle) {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                }
            }
        }
    }
}
