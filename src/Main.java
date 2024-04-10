import java.awt.*;

public class Main {
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Map.COL * Map.CELL_SIZE, Map.ROW * Map.CELL_SIZE);
        StdDraw.setXscale(0, Map.COL * Map.CELL_SIZE);
        StdDraw.setYscale(0, Map.ROW * Map.CELL_SIZE);
        Tile[][] grid = Graph.generateRandomGrid(false, true);
        Map.drawMap();
        Tile.drawGrid(grid);
        StdDraw.show();

        Graph.setAdjacentTiles();

        Graph.inputDestinationTile();
        Tile.drawGrid(grid);
        StdDraw.show();

        Graph.aStarAlgorithm();
        Graph.setPathAfterAStar();
        StdDraw.show();
    }
}
