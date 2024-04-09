public abstract class Map {
    protected final static int CELL_SIZE = 40; // tile pixel size
    protected final static int ROW = 15; // # of rows
    protected final static int COL = 20; // # of columns

    protected static void drawMap() {
        StdDraw.clear(StdDraw.LIGHT_GRAY); // set the background color
        StdDraw.setPenColor(StdDraw.WHITE); // set the line color
        // draw the horizontal lines
        for (int i = 0; i <= ROW; i++) {
            StdDraw.line(0, i * CELL_SIZE, COL * CELL_SIZE, i * CELL_SIZE);
        }
        // draw the vertical lines
        for (int i = 0; i <= COL; i++) {
            StdDraw.line(i * CELL_SIZE, 0, i * CELL_SIZE, ROW * CELL_SIZE);
        }
    }
}
