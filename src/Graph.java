import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Graph {
    protected static Tile[][] grid = new Tile[Map.ROW][Map.COL];
    protected static Tile characterLocation;
    protected static Tile destinationTile;

    protected static Random random = new Random();

    public static void aStarAlgorithm() {
        Comparator<Tile> comparator = new TileComparator();
        PriorityQueue<Tile> frontier = new PriorityQueue<>(comparator);

        destinationTile = grid[8][7];

        frontier.add(characterLocation);

        Tile current = null;
        Tile previousTile;
        double cost = 0.0;

        while (!frontier.isEmpty()) {
            previousTile = current;
            current = frontier.poll();

            if (current == Graph.destinationTile) {
                Graph.destinationTile.previousTile = previousTile;
                Graph.destinationTile.costSoFar = cost;
                break;
            }
            for (Tile next: current.adjacencies) {
                double newCost = current.costSoFar + current.costOfTile;
                if (!next.isVisited || newCost < next.costSoFar) {
                    next.costSoFar = newCost;
                    frontier.add(next);
                    next.isVisited = true;
                    next.previousTile = current;
                }
            }
        }
    }

    public static void drawAStar() {
        StdDraw.setPenColor(Color.BLUE);
        Tile backtrack = Graph.destinationTile;
        do {
            StdDraw.filledCircle(backtrack.col, backtrack.row, 0.05);
        } while (backtrack.previousTile != null);
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
    public static Tile[][] generateRandomGrid(boolean isSourceRandom) {
        // source is not random in this case
        if (!isSourceRandom) {
            gridHelper(7, 0);
        } else {
            int sourceRow = random.nextInt(Map.ROW);
            int sourceCol = random.nextInt(Map.COL);
            grid[sourceRow][sourceCol] = new Tile(sourceRow, sourceCol, false, true);
            gridHelper(sourceRow, sourceCol);
        }
        return grid;
    }
    private static void gridHelper(int sourceRow, int sourceCol) {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                int rand = random.nextInt(5);
                if (i == sourceRow && j == sourceCol) {
                    grid[i][j] = new Tile(i, j, false, true);
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
        double totalCostOfO1 = o1.costSoFar + o1.costOfTile + Graph.heuristic(o1, Graph.destinationTile);
        double totalCostOfO2 = o2.costSoFar + o2.costOfTile + Graph.heuristic(o2, Graph.destinationTile);
        if (totalCostOfO1 < totalCostOfO2) {
            return 1;
        } else if (totalCostOfO1 == totalCostOfO2) {
            return 0;
        } else {
            return -1;
        }
    }
}


