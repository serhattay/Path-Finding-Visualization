import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a tile in the grid used for pathfinding.
 * Each tile has properties such as its position, whether it's an obstacle, source, or destination,
 * as well as information for pathfinding such as cost, previous tile, and adjacency list.
 */
public class Tile implements Comparable<Tile> {
    public int row;
    public int col;
    public boolean isObstacle;
    public boolean isSource;
    public boolean isDestination = false;
    public Tile previousTile = null;
    public double costSoFar = 0.0;
    public double costOfTile;
    public boolean isVisited = false;
    public ArrayList<Tile> adjacency = new ArrayList<>();

    /**
     * Constructs a tile with the specified properties.
     *
     * @param row        The row index of the tile.
     * @param col        The column index of the tile.
     * @param isObstacle Indicates whether the tile is an obstacle.
     */
    Tile(int row, int col, boolean isObstacle) {
        this(row, col, isObstacle, false, 1);
    }

    /**
     * Constructs a tile with the specified properties.
     *
     * @param row        The row index of the tile.
     * @param col        The column index of the tile.
     * @param isObstacle Indicates whether the tile is an obstacle.
     * @param isSource   Indicates whether the tile is the source tile.
     * @param costOfTile The cost of traversing this tile.
     */
    Tile(int row, int col, boolean isObstacle, boolean isSource, int costOfTile) {
        this.row = row;
        this.col = col;
        this.isObstacle = isObstacle;
        this.isSource = isSource;
        this.costOfTile = costOfTile;
    }

    /**
     * Draws a line between this tile and its previous tile.
     * This method is used to visualize the path on the map.
     */
    protected void drawLine() {
        double lineRadius = 0.01 * Map.CELL_SIZE / 40.0;

        double previousTileX = (previousTile.col + 0.5) * Map.CELL_SIZE;
        double previousTileY = (Map.ROW - previousTile.row - 0.5) * Map.CELL_SIZE;
        double nextTileX = (col + 0.5) * Map.CELL_SIZE;
        double nextTileY = (Map.ROW - row - 0.5) * Map.CELL_SIZE;

        StdDraw.setPenRadius(lineRadius);
        StdDraw.setPenColor(255, 215, 0);
        StdDraw.line(previousTileX, previousTileY, nextTileX, nextTileY);
    }

    /**
     * Draws a circle on this tile.
     * This method is used to visualize the path on the map.
     *
     * @param animation Flag indicating whether to animate the drawing.
     */
    protected void drawCircle(boolean animation) {
        double tileX = (col + 0.5) * Map.CELL_SIZE;
        double tileY = (Map.ROW - row - 0.5) * Map.CELL_SIZE;

        double circleOuterLineRadius = 0.001 * Map.CELL_SIZE / 8.0;
        double circleRadius = Map.CELL_SIZE / 4.0;

        StdDraw.setPenColor(255, 215, 0);
        StdDraw.filledCircle(tileX, tileY, circleRadius);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.setPenRadius(circleOuterLineRadius);
        StdDraw.circle(tileX, tileY, circleRadius);
        Font font = new Font("Arial", Font.BOLD, Map.CELL_SIZE / 3);
        StdDraw.setFont(font);
        StdDraw.text(tileX, tileY, String.valueOf((int) costSoFar));
        if (animation) {
            StdDraw.pause(80);
            StdDraw.show();
        }
    }

    /**
     * Adds an adjacent tile to the adjacency list of this tile.
     *
     * @param adjacent The adjacent tile to be added.
     */
    public void addToAdjacent(Tile adjacent) {
        adjacency.add(adjacent);
    }

    /**
     * Gets the row index of the tile.
     *
     * @return The row index of the tile.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of the tile.
     *
     * @return The column index of the tile.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the cost of traversing this tile.
     *
     * @param newCost The new cost of traversing this tile.
     */
    public void setCostOfTile(double newCost) {
        costOfTile = newCost;
    }

    /**
     * Sets whether this tile is the source tile.
     *
     * @param isSource Indicates whether this tile is the source tile.
     */
    public void setSource(boolean isSource) {
        this.isSource = isSource;
    }

    /**
     * Sets whether this tile is the destination tile.
     *
     * @param isDestination Indicates whether this tile is the destination tile.
     */
    public void setDestination(boolean isDestination) {
        this.isDestination = isDestination;
    }

    /**
     * Resets the tile to its default state.
     * Clears the previous tile, resets the cost and visitation status, and unsets destination flag.
     *
     * @param grid The grid of tiles to reset.
     */
    public static void setDefault(Tile[][] grid) {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                tile.previousTile = null;
                tile.costSoFar = 0.0;
                tile.isVisited = false;
                tile.isDestination = false;
            }
        }
    }

    /**
     * Returns a string representation of the tile.
     *
     * @return A string representation of the tile.
     */
    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row, col);
    }

    /**
     * Compares this tile with another tile based on their total estimated costs.
     * Used for prioritizing tiles in the A* algorithm's priority queue.
     *
     * @param o2 The other tile to compare.
     * @return A negative integer, zero, or a positive integer if this tile is less than, equal to, or greater than the other tile.
     */
    @Override
    public int compareTo(Tile o2) {
        double totalCostOfO1 = costSoFar + costOfTile + Graph.heuristic(this, Graph.destinationTile);
        double totalCostOfO2 = o2.costSoFar + o2.costOfTile + Graph.heuristic(o2, Graph.destinationTile);
        if (totalCostOfO1 < totalCostOfO2) {
            return -1;
        } else if (totalCostOfO1 == totalCostOfO2) {
            return 0;
        } else {
            return 1;
        }
    }
}
