import java.util.ArrayList;

public class Tile {
    protected int row;
    protected int col;
    protected boolean isObstacle;
    protected boolean isSource;
    protected Tile previousTile = null;
    protected double costSoFar;
    protected int costOfTile;
    protected boolean isVisited = false;
    protected ArrayList<Tile> adjacencies = new ArrayList<>();

    Tile(int row, int col, boolean isObstacle) {
        this(row, col, isObstacle, false);
    }

    Tile(int row, int col, boolean isObstacle, boolean isSource) {
        this.row = row;
        this.col = col;
        this.isObstacle = isObstacle;
        this.isSource = isSource;
        this.costOfTile = 1;
        Graph.setCharacterLocation(this);
    }

    protected static void drawGrid(Tile[][] grid) {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.isSource) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE,
                            (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                } else if (tile.isObstacle) {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE,
                            (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                }
            }
        }
    }

    public void addToAdjacents(Tile adjacent) {
        adjacencies.add(adjacent);
    }

    public ArrayList<Tile> getAdjacencies() {
        return adjacencies;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public boolean isSource() {
        return isSource;
    }

    public Tile getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(Tile previousTile) {
        this.previousTile = previousTile;
    }

    public void resetPreviousTile() {
        this.previousTile = null;
        this.costSoFar = Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row, col);
    }

}
