import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tile implements Comparable<Tile> {
    protected int row;
    protected int col;
    protected boolean isObstacle;
    protected boolean isSource;
    protected boolean isDestination = false;
    protected Tile previousTile = null;
    protected double costSoFar = 0.0;
    protected double costOfTile;
    protected boolean isVisited = false;
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

    protected void drawLine() {
        double lineRadius = 0.01 * Map.CELL_SIZE / 40.0;

        double previousTileX = (previousTile.col + 0.5) * Map.CELL_SIZE;
        double previousTileY = (Map.ROW - previousTile.row - 0.5) * Map.CELL_SIZE;
        double nextTileX =  (col + 0.5) * Map.CELL_SIZE;
        double nextTileY =  (Map.ROW - row - 0.5)  * Map.CELL_SIZE;

        StdDraw.setPenRadius(lineRadius);
        StdDraw.setPenColor(255, 215, 0);
        StdDraw.line(previousTileX, previousTileY, nextTileX, nextTileY);
    }

    protected void drawCircle(boolean animation) {
        double tileX =  (col + 0.5) * Map.CELL_SIZE;
        double tileY =  (Map.ROW - row - 0.5)  * Map.CELL_SIZE;

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
            StdDraw.pause(50);
            StdDraw.show();
        }


    }

    public void addToAdjacents(Tile adjacent) {
        adjacencies.add(adjacent);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCostOfTile(double newCost) {
        costOfTile = newCost;
    }
    public void setSource(boolean isSource) {
        this.isSource = isSource;
    }
    public void setDestination(boolean isDestination) {
        this.isDestination = isDestination;
    }

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

    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row, col);
    }


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
