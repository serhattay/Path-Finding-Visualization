import java.util.ArrayList;

public class Tile {
    protected int row;
    protected int col;
    protected boolean isObstacle;
    protected boolean isSource;
    protected boolean isDestination = false;
    protected Tile previousTile = null;
    protected double costSoFar;
    protected double costOfTile;
    protected ArrayList<Tile> adjacencies = new ArrayList<>();

    Tile(int row, int col, boolean isObstacle) {
        this(row, col, isObstacle, false, 1);
    }
    Tile(int row, int col, int costOfTile) {
        this(row, col, false, false, costOfTile);
    }
    Tile(int row, int col, boolean isObstacle, boolean isSource, int costOfTile) {
        this.row = row;
        this.col = col;
        this.isObstacle = isObstacle;
        this.isSource = isSource;
        this.costOfTile = costOfTile;
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
                } else if (tile.costOfTile > 1) {
                    StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE,
                            (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                } else if (tile.isDestination) {
                    StdDraw.setPenColor(StdDraw.GREEN);
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

    public void setCostOfTile(double newCost) {
        costOfTile = newCost;
    }

    public void setDestination() {
        isDestination = true;
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
