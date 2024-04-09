public class Tile {
    protected int row;
    protected int col;
    protected boolean isObstacle;
    protected boolean isSource;

    Tile(int row, int col, boolean isObstacle) {
        this(row, col, isObstacle, false);
    }

    Tile(int row, int col, boolean isObstacle, boolean isSource) {
        this.row = row;
        this.col = col;
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
