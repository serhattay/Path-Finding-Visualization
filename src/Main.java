
public class Main {
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Map.COL * Map.CELL_SIZE, Map.ROW * Map.CELL_SIZE);
        StdDraw.setXscale(0, Map.COL * Map.CELL_SIZE);
        StdDraw.setYscale(0, Map.ROW * Map.CELL_SIZE);

        Tile[][] grid = Graph.generateRandomGrid(true, true);
        Graph.setAdjacentTiles();

        Map.drawGrid(grid);
        Graph.inputDestinationTile();
        Map.drawGrid(grid);

        Graph.aStarAlgorithm();
        Graph.setPathAfterAStar();
        Graph.drawPathLine(true);
        Map.animation(grid);

        while (true) {
            Graph.inputDestinationTile();
            Map.resetMap();
            Map.drawGrid(grid);
            StdDraw.show();

            Graph.aStarAlgorithm();
            Graph.setPathAfterAStar();
            Graph.drawPathLine(true);
            Map.animation(grid);
        }
    }
}
