import java.util.Random;

public abstract class Map {
    protected final static int CELL_SIZE = 60; // tile pixel size
    protected final static int ROW = 15; // # of rows
    protected final static int COL = 20; // # of columns

    protected static void drawMap() {
        StdDraw.clear(StdDraw.LIGHT_GRAY); // set the background color
        StdDraw.setPenColor(StdDraw.WHITE); // set the line color
        StdDraw.setPenRadius();
        // draw the horizontal lines
        for (int i = 0; i <= ROW; i++) {
            StdDraw.line(0, i * CELL_SIZE, COL * CELL_SIZE, i * CELL_SIZE);
        }
        // draw the vertical lines
        for (int i = 0; i <= COL; i++) {
            StdDraw.line(i * CELL_SIZE, 0, i * CELL_SIZE, ROW * CELL_SIZE);
        }
    }

    protected static void drawGrid(Tile[][] grid) {
        Random random = new Random();
        String[] grassList = new String[]{"assets/grass0.jpeg", "assets/grass1.jpeg", "assets/grass3.jpeg"};
        String treeGround = "assets/tree_ground.jpeg";
        String[] treeList = new String[]{"assets/green_tree.png", "assets/pink_tree.png"};
        String sand = "assets/sand.png";

        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.isSource) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE,
                            (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                } else if (tile.isObstacle) {
                    StdDraw.picture((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE,
                            treeGround, Map.CELL_SIZE, Map.CELL_SIZE);
                } else if (tile.costOfTile > 1) {
                    StdDraw.picture((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE,
                            sand, Map.CELL_SIZE, Map.CELL_SIZE);
                } else if (tile.isDestination) {
                    StdDraw.setPenColor(StdDraw.GREEN);
                    StdDraw.filledSquare((tile.col + 0.5) * Map.CELL_SIZE,
                            (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE, Map.CELL_SIZE / 2.0);
                } else {
                    StdDraw.picture((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE,
                            grassList[random.nextInt(grassList.length)], Map.CELL_SIZE, Map.CELL_SIZE);
                }
            }
        }
        StdDraw.show();
    }

    protected static void animation(Tile[][] grid) {
        while (!Graph.path.isEmpty()) {
            Graph.characterLocation.setSource(false);
            Graph.characterLocation = Graph.path.removeFirst();
            Graph.characterLocation.setSource(true);
            Map.drawMap();
            Map.drawGrid(grid);
            Graph.drawPathLine(false);
            StdDraw.show();
            StdDraw.pause(150);
        }
        Map.drawMap();
        Map.drawGrid(grid);
        StdDraw.show();
        Tile.setDefault(grid);
    }
}
