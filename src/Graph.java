import java.awt.*;
import java.util.*;

public class Graph {
    protected static Tile[][] grid = new Tile[Map.ROW][Map.COL];
    protected static Tile characterLocation;
    protected static Tile destinationTile;
    protected static LinkedList<Tile> path = new LinkedList<>();
    protected static Random random = new Random();


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
            for (Tile next: current.adjacencies) {
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

    // returns path destination point inclusive but not starting point
    public static void setPathAfterAStar() {
        Tile lastTile = destinationTile;

        System.out.println(characterLocation.previousTile);
        while (lastTile.previousTile != null) {
            path.addFirst(lastTile);
            lastTile = lastTile.previousTile;
        }
    }

    public static void drawPathLine() {
        for (Tile nextTile: path) {
            nextTile.drawLine();
        }

        characterLocation.drawCircle();

        for (Tile nextTile: path) {
            nextTile.drawCircle();
        }


    }
    public static double heuristic(Tile currentTile, Tile destinationTile) {
        return Math.abs(destinationTile.getCol() - currentTile.getCol()) +
                Math.abs(destinationTile.getRow() - currentTile.getRow());
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
                    grid[i][j] = new Tile(i, j, false, true ,1);
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
    private static void randomCost() {
        int numberCost = random.nextInt(10) + 3;
        for (int i = 0; i < numberCost; i++) {
            int row = random.nextInt(Map.ROW);
            int col = random.nextInt(Map.COL);
            Tile tile = grid[row][col];
            if (!tile.isSource && !tile.isObstacle) {
                tile.setCostOfTile(random.nextDouble(2,3));
            }
        }
    }
    private static void gridHelper(int sourceRow, int sourceCol) {
        for (int i = 0; i < Map.ROW; i++) {
            for (int j = 0; j < Map.COL; j++) {
                int rand = random.nextInt(5);
                if (i == sourceRow && j == sourceCol) {
                    grid[i][j] = new Tile(i, j, false, true,1);
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
        int row = (int)((Map.ROW * Map.CELL_SIZE - mouseY) / Map.CELL_SIZE);
        int col = (int)(mouseX / Map.CELL_SIZE);
        grid[row][col].setDestination();
        destinationTile = grid[row][col];
    }
    public static Tile getCharacterLocation() {
        return characterLocation;
    }

    public static void setCharacterLocation(Tile characterLocation) {
        Graph.characterLocation = characterLocation;
    }
}



