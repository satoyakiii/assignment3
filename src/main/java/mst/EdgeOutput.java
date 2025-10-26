package mst;

import java.util.List;

public class EdgeOutput {
    public String algorithm;
    public int vertices;
    public int edges;
    public long totalCost;
    public long operations;
    public long executionTimeMs;
    public List<Edge> mstEdges;

    public EdgeOutput(String algorithm, int vertices, int edges, MSTResult result) {
        this.algorithm = algorithm;
        this.vertices = vertices;
        this.edges = edges;
        this.totalCost = result.totalCost;
        this.operations = result.operationsCount;
        this.executionTimeMs = result.executionTimeMs;
        this.mstEdges = result.mstEdges;
    }
}
