import java.awt.*;
import java.util.Random;

/**
 * Abstract class representing the map used for pathfinding visualization.
 * This class provides methods for drawing the grid, resetting the map,
 * animating the movement of the character, and drawing tiles on the map.
 */
public abstract class Map {
    protected final static int CELL_SIZE = 60; // tile pixel size
    protected final static int ROW = 15; // # of rows
    protected final static int COL = 20; // # of columns
    protected static Random random = new Random();

    /**
     * Draws the grid on the map based on the provided grid of tiles.
     * Each tile is drawn with its respective image or background color.
     *
     * @param grid The 2D array representing the grid of tiles.
     */
    protected static void drawGrid(Tile[][] grid) {
        String[] grassList = new String[]{"assets/grass0.jpeg", "assets/grass1.jpeg", "assets/grass3.jpeg"};
        String obstacleGround = "assets/tree_ground.jpeg";
        String obstacle = "assets/python_logo.png";
        String sand = "assets/sand.png";
        String berkGokberk = "assets/berkgokberk.png";
        //String destinationLogo = "assets/java_logo.png";
        String destinationLogo = "assets/computer.png";

        random.setSeed(4);

        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.costOfTile > 1) {
                    drawTile(tile, sand);
                } else if (tile.isObstacle) {
                    drawTile(tile, obstacleGround);
                    //drawBorder(tile, StdDraw.GREEN);
                    //drawTile(tile, obstacle);
                } else {
                    drawTile(tile, grassList);
                }

                if (tile.isSource) {
                    drawTile(tile, berkGokberk);
                } else if (tile.isDestination) {
                    drawTile(tile, destinationLogo);
                }
            }
        }
        StdDraw.show();
    }

    /**
     * Resets the map to its default state.
     * This method clears any drawn lines or animations on the map.
     */
    protected static void resetMap() {
        StdDraw.setPenColor(StdDraw.WHITE); // set the line color
        StdDraw.setPenRadius();
    }

    /**
     * Animates the movement of the character along the path.
     * This method iterates over the tiles in the path and updates the character's position accordingly,
     * redrawing the map with each step to show the animation.
     *
     * @param grid The 2D array representing the grid of tiles.
     */
    protected static void animation(Tile[][] grid) {
        while (!Graph.path.isEmpty()) {
            Graph.characterLocation.setSource(false);
            Graph.characterLocation = Graph.path.removeFirst();
            Graph.characterLocation.setSource(true);
            Map.resetMap();
            Map.drawGrid(grid);
            Graph.drawPathLine(false);
            StdDraw.show();
            StdDraw.pause(100);
        }
        Map.resetMap();
        Map.drawGrid(grid);
        StdDraw.show();
        Tile.setDefault(grid);
    }

    /**
     * Draws a border around the specified tile with the given color.
     * This method is used to highlight specific tiles on the map.
     *
     * @param tile  The tile for which to draw the border.
     * @param color The color of the border.
     */
    private static void drawBorder(Tile tile, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(0.005);
        StdDraw.rectangle((tile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - tile.row - 0.5) * Map.CELL_SIZE,
                Map.CELL_SIZE / 2.0, Map.CELL_SIZE / 2.0);
    }

    /**
     * Draws a single tile on the map with the specified image.
     *
     * @param characterTile The tile to draw on the map.
     * @param picture       The filename of the image to use for the tile.
     */
    public static void drawTile(Tile characterTile, String picture) {
        StdDraw.picture((characterTile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - characterTile.row - 0.5) * Map.CELL_SIZE,
                picture, Map.CELL_SIZE, Map.CELL_SIZE);
    }

    /**
     * Draws a single tile on the map with a randomly selected image from the provided list.
     *
     * @param characterTile The tile to draw on the map.
     * @param pictureList   The array of filenames of images to choose from for the tile.
     */
    public static void drawTile(Tile characterTile, String[] pictureList) {
        StdDraw.picture((characterTile.col + 0.5) * Map.CELL_SIZE, (Map.ROW - characterTile.row - 0.5) * Map.CELL_SIZE,
                pictureList[random.nextInt(pictureList.length)], Map.CELL_SIZE, Map.CELL_SIZE);
    }
}
