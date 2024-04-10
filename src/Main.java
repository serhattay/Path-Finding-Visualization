/**
 * The main class responsible for initializing the program, handling user input, and executing pathfinding algorithms.
 * This class sets up the graphical interface, generates the grid with obstacles and a random source tile,
 * allows the user to input a destination tile using the mouse, and runs the A* algorithm to find the shortest path
 * from the source to the destination.
 *
 * @author Omer Ozan Mart & Serhat Tay, Student ID(respectively): 2022400267, 2022400201
 * @since Date: 8.04.2024
 */
public class Main {

    /**
     * The main method of the program.
     * Initializes the graphical interface, generates the grid, allows user input for the destination tile,
     * runs the A* algorithm, and continuously updates the path as the user changes the destination tile.
     *
     * @param args Command-line arguments (not used).
     */
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
