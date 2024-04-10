import java.util.*;

/**
 * Represents a graph used for pathfinding algorithms.
 * This class provides methods for running A* algorithm, setting paths, drawing paths on the map,
 * calculating heuristics, generating grids, and setting destination tiles based on user input.
 */
public class Graph {
    // Define class variables
    public static Tile[][] grid = new Tile[Map.ROW][Map.COL];
    public static Tile characterLocation;
    public static Tile destinationTile;
    public static LinkedList<Tile> path = new LinkedList<>();
    public static Random random = new Random();

    /**
     * Runs the A* algorithm to find the shortest path from the character's location to the destination.
     * This method explores neighboring tiles iteratively, prioritizing those with lower total costs (estimated cost from start to current tile + heuristic value to destination).
     * The algorithm terminates when either the destination tile is reached or there are no more accessible tiles to explore.
     * Once the algorithm completes, the path to the destination tile is stored in the {path} variable.
     * <p>
     * A* algorithm works as follows:
     * 1. Add the character's starting tile to the priority queue with a cost of zero.
     * 2. While the priority queue is not empty:
     * - Remove the tile with the lowest cost from the priority queue (current tile).
     * - If the current tile is the destination tile, terminate the algorithm.
     * - Otherwise, for each neighboring tile of the current tile:
     * - Calculate the new cost to reach the neighboring tile from the start.
     * - If the neighboring tile has not been visited or the new cost is lower than its current cost:
     * - Update the cost to reach the neighboring tile.
     * - Add the neighboring tile to the priority queue.
     * - Mark the neighboring tile as visited.
     * - Set the previous tile of the neighboring tile to the current tile.
     */
    public static void aStarAlgorithm() {
        PriorityQueue<Tile> frontier = new PriorityQueue<>();
        frontier.add(characterLocation);
        characterLocation.isVisited = true;
        Tile current;
        double newCost;

        while (!frontier.isEmpty()) {
            current = frontier.poll();
            if (current == destinationTile) {
                break;
            }
            for (Tile next : current.adjacency) {
                newCost = current.costSoFar + current.costOfTile;
                if (!next.isVisited || newCost < next.costSoFar) {
                    next.costSoFar = newCost;
                    frontier.add(next);
                    next.isVisited = true;
                    next.previousTile = current;
                }
            }
        }
    }

    /**
     * Sets the path after A* algorithm has been executed.
     * This method sets the path variable with the computed shortest path
     * by tracing back from the destination tile to the source tile.
     */
    public static void setPathAfterAStar() {
        Tile lastTile = destinationTile;

        while (lastTile.previousTile != null) {
            path.addFirst(lastTile);
            lastTile = lastTile.previousTile;
        }
    }

    /**
     * Draws the shortest path line on the map.
     *
     * @param animation Flag indicating whether to animate the drawing of the path line.
     *                  If true, the path line will be drawn with animation.
     *                  If false, the path line will be drawn without animation.
     */
    public static void drawPathLine(boolean animation) {
        for (Tile nextTile : path) {
            nextTile.drawLine();
        }

        //String destinationLogo = "assets/java_logo.png";
        String destinationLogo = "assets/computer.png";
        Map.drawTile(destinationTile, destinationLogo);

        String characterPicture = "assets/berkgokberk.png";
        Map.drawTile(characterLocation, characterPicture);

        for (Tile nextTile : path) {
            nextTile.drawCircle(animation);
        }


    }

    /**
     * Calculates the heuristic value between two tiles.
     * This heuristic function is used in the A* algorithm to estimate
     * the cost of reaching the destination tile from the current tile.
     *
     * @param currentTile     The current tile.
     * @param destinationTile The destination tile.
     * @return The heuristic value between the current and destination tiles.
     */
    public static double heuristic(Tile currentTile, Tile destinationTile) {
        return Math.abs(destinationTile.getCol() - currentTile.getCol()) +
                Math.abs(destinationTile.getRow() - currentTile.getRow());
    }

    /**
     * Sets the adjacent tiles for each tile in the grid.
     * This method populates the adjacency list for each tile
     * by considering neighboring tiles in the grid.
     */
    public static void setAdjacentTiles() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Tile currentTile = grid[i][j];
                for (int direction : new int[]{-1, 1}) {
                    if (i + direction >= 0 && i + direction < Map.ROW && !grid[i + direction][j].isObstacle) {
                        currentTile.addToAdjacent(grid[i + direction][j]);
                    }
                    if (j + direction >= 0 && j + direction < Map.COL && !grid[i][j + direction].isObstacle) {
                        currentTile.addToAdjacent(grid[i][j + direction]);
                    }
                }
            }
        }
    }

    /**
     * Generates a default grid for the graph.
     * This method creates a default grid layout with predefined properties,
     * such as obstacles, character location, and destination tile.
     *
     * @return The generated default grid represented as a 2D array of tiles.
     */
    public static Tile[][] generateDefaultGrid() {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                if (i == 7 && j == 0) {
                    grid[i][j] = new Tile(i, j, false, true, 1);
                    characterLocation = grid[i][j];
                    continue;
                }
                if (j == 12) {
                    if (i != 7) {
                        grid[i][j] = new Tile(i, j, true);
                    } else {
                        grid[i][j] = new Tile(i, j, false);
                    }
                } else {
                    grid[i][j] = new Tile(i, j, false);
                }
            }
        }
        return grid;
    }

    /**
     * Generates a random grid for the graph.
     * This method creates a random grid layout with random obstacles,
     * character location, and destination tile.
     *
     * @param isSourceRandom Flag indicating whether the source tile should be randomly placed.
     * @param isCostRandom   Flag indicating whether the cost of tiles should be randomly assigned.
     * @return The generated random grid represented as a 2D array of tiles.
     */
    public static Tile[][] generateRandomGrid(boolean isSourceRandom, boolean isCostRandom) {
        // source is not random in this case
        if (!isSourceRandom) {
            gridHelper(7, 0);
        } else { // source is random
            int sourceRow = random.nextInt(Map.ROW);
            int sourceCol = random.nextInt(Map.COL);
            gridHelper(sourceRow, sourceCol);
        }
        if (isCostRandom) {
            randomCost();
        }
        return grid;
    }

    /**
     * Randomly assigns costs to tiles on the grid.
     * Generates a random number of tiles and sets their cost randomly within a specified range.
     * Tiles that are obstacles or the source tile are not assigned costs.
     */
    private static void randomCost() {
        int numberCost = random.nextInt(10) + 3;
        for (int i = 0; i < numberCost; i++) {
            int row = random.nextInt(Map.ROW);
            int col = random.nextInt(Map.COL);
            Tile tile = grid[row][col];
            if (!tile.isSource && !tile.isObstacle) {
                tile.setCostOfTile(random.nextDouble(2, 3));
            }
        }
    }

    /**
     * Helper method to initialize the grid with random obstacle tiles.
     * Generates the grid layout with obstacles and sets the character's location.
     *
     * @param sourceRow The row index of the character's starting location.
     * @param sourceCol The column index of the character's starting location.
     */
    private static void gridHelper(int sourceRow, int sourceCol) {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                int rand = random.nextInt(5);
                if (i == sourceRow && j == sourceCol) {
                    grid[i][j] = new Tile(i, j, false, true, 1);
                    characterLocation = grid[i][j];
                    continue;
                }
                if (rand == 0) {
                    grid[i][j] = new Tile(i, j, true);
                } else {
                    grid[i][j] = new Tile(i, j, false);
                }
            }
        }
    }

    /**
     * Sets the destination tile based on user input from the mouse.
     * This method waits for user input from the mouse click and sets the destination tile
     * based on the coordinates of the mouse click on the map.
     */
    public static void inputDestinationTile() {
        double mouseX;
        double mouseY;
        while (true) {
            if (StdDraw.isMousePressed()) {
                mouseX = StdDraw.mouseX();
                mouseY = StdDraw.mouseY();
                break;
            }
        }
        int row = (int) ((Map.ROW * Map.CELL_SIZE - mouseY) / Map.CELL_SIZE);
        int col = (int) (mouseX / Map.CELL_SIZE);
        grid[row][col].setDestination(true);
        destinationTile = grid[row][col];
    }
}



