public class Adjacent {
    protected Tile destinationTile;
    protected double cost;

    Adjacent() {}
    Adjacent(Tile destinationTile, double cost) {
        this.destinationTile = destinationTile;
        this.cost = cost;
    }

    public Tile getDestinationTile() {
        return destinationTile;
    }

    public double getCost() {
        return cost;
    }


}