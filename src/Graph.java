import java.util.Comparator;
import java.util.PriorityQueue;

public class Graph {
    protected static Tile[][] grid = new Tile[Map.ROW][Map.COL];
    protected static Tile characterLocation;
    protected static Tile destinationTile;

    public static void aStarAlgorithm() {
        Comparator<Tile> comparator = new TileComparator();
        PriorityQueue<Tile> frontier = new PriorityQueue<>(comparator);

        frontier.add(characterLocation);

        Tile current = null;
        Tile previousTile;
        double cost = 0.0;

        while (!frontier.isEmpty()) {
            previousTile = current;
            current = frontier.poll();

            if (current == Graph.destinationTile) {
                Graph.destinationTile.costSoFar = cost;
            }
        }
    }

    public static double heuristic(Tile currentTile, Tile destinationTile) {
        return Math.sqrt(Math.pow(currentTile.getCol() - destinationTile.getCol(), 2) +
                Math.pow(currentTile.getRow() - destinationTile.getRow(), 2));
    }
    public static void setAdjacentTiles() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length ; j++) {
                Tile currentTile = grid[i][j];
                for (int direction: new int[]{-1, 1}) {
                    if (i + direction >= 0 && i + direction < Map.ROW && !grid[i + direction][j].isObstacle) {
                        currentTile.addToAdjacents(grid[i + direction][j]);
                    }
                    if (j + direction >= 0 && j + direction < Map.COL && !grid[i][j + direction].isObstacle) {
                        currentTile.addToAdjacents(grid[i][j + direction]);
                    }
                }
            }
        }
    }
    public static Tile[][] generateDefaultGrid() {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                if (i == 7 && j == 0) {
                    grid[i][j] = new Tile(i, j, false, true);
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
    public static Tile[][] generateRandomGrid() {
        return null;
    }

    public static Tile getCharacterLocation() {
        return characterLocation;
    }

    public static void setCharacterLocation(Tile characterLocation) {
        Graph.characterLocation = characterLocation;
    }
}

class TileComparator implements Comparator<Tile> {

    @Override
    public int compare(Tile o1, Tile o2) {
        double totalCostOfO1 = o1.costSoFar + Graph.heuristic(o1, Graph.destinationTile);
        double totalCostOfO2 = o2.costSoFar + Graph.heuristic(o2, Graph.destinationTile);
        if (totalCostOfO1 < totalCostOfO2) {
            return 1;
        } else if (totalCostOfO1 == totalCostOfO2) {
            return 0;
        } else {
            return -1;
        }
    }
}


