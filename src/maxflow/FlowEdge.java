package maxflow;

/*
 *  To build a residual network
 *  - Augmented path in original network = directed path in resid network
 */
public class FlowEdge {
    private final int v, w;
    private final double capacity;
    private double flow;
    
    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }
    
    public int from() {
        return v;
    }
    
    public int to() {
        return w;
    }
    
    public int other(int v) {
        return v == this.v ? this.w : this.v;
    }
    
    public double flow() {
        return flow;
    }
    
    public double capacity() {
        return capacity;
    }
    
    public double residualCapacityTo(int v) {
        return v == this.v ? flow : capacity - flow;
    }
    
    public void addResidualFlowTo(int v, double delta) {
        if (v == this.v) flow -= delta;
        else             flow += delta;
    }
    
    public String toString() {
        return v + " -> " + w +  " (" + flow() +"/" + capacity + ")";
    }
}
