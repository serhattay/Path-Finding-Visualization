import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Map.COL * Map.CELL_SIZE, Map.ROW * Map.CELL_SIZE);
        StdDraw.setXscale(0, Map.COL * Map.CELL_SIZE);
        StdDraw.setYscale(0, Map.ROW * Map.CELL_SIZE);
        Tile[][] grid = Graph.generateDefaultGrid();
        Map.drawMap();
        Tile.drawGrid(grid);

        //Tested and approved
        Graph.setAdjacentTiles();
        StdDraw.show();
    }
}
