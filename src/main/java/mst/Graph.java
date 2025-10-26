package mst;

import java.util.*;

public class Graph {
    private final List<String> nodes;
    private final Map<String, Integer> map;
    private final List<Edge> allEdges;
    private final List<List<Edge>> adj;

    public Graph(List<String> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.map = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) map.put(nodes.get(i), i);
        this.adj = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) adj.add(new ArrayList<>());
        this.allEdges = new ArrayList<>();
    }

    public void addEdge(String a, String b, int w) {
        int u = map.get(a), v = map.get(b);
        Edge e1 = new Edge(u, v, w);
        Edge e2 = new Edge(v, u, w);
        adj.get(u).add(e1);
        adj.get(v).add(e2);
        allEdges.add(e1);
    }

    public List<Edge> getAllEdges() { return allEdges; }
    public List<Edge> adj(int u) { return adj.get(u); }
    public int vertexCount() { return nodes.size(); }
    public String labelOf(int i) { return nodes.get(i); }
}
