public class Adjacent {
    protected Tile adjacent;
    protected double cost;

    Adjacent() {}
    Adjacent(Tile destinationTile, double cost) {
        this.adjacent = destinationTile;
        this.cost = cost;
    }

    public Tile getAdjacent() {
        return adjacent;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("adjacent: %s, cost: %f", adjacent, cost);
    }
}