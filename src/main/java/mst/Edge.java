package mst;

public class Edge implements Comparable<Edge> {
    public final int from;
    public final int to;
    public final int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d(%d)", from, to, weight);
    }
}
