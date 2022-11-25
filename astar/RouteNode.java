package astar;

class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {

    /* Fields */
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    /* Constructors */
    public RouteNode(T current) {
        /* if there isn't any previous node, just pass a perfect score */
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this. routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public T getCurrent() { return current; }
    public T getPrevious() { return previous; }
    public double getRouteScore() { return routeScore; }
    public double getEstimatedScore() { return estimatedScore; }

    public void setPrevious(T previous) { this.previous = previous; }
    public void setRouteScore(double routeScore) { this.routeScore = routeScore; }
    public void setEstimatedScore(double estimatedScore) { this.estimatedScore = estimatedScore; }

    @Override // オーバーライド
    public int compareTo(RouteNode other) {
        if(this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if(this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        // I'll define this shit later
        return "無い!";
    }
}

