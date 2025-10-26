package mst;

import java.util.*;

public class KruskalMST {
    public static MSTResult compute(Graph g) {
        List<Edge> edges = new ArrayList<>(g.getAllEdges());
        edges.sort(Comparator.comparingInt(e -> e.weight));
        UnionFind uf = new UnionFind(g.vertexCount());
        List<Edge> mst = new ArrayList<>();
        long ops = 0;
        long start = System.nanoTime();

        for (Edge e : edges) {
            ops++;
            if (uf.union(e.from, e.to)) mst.add(e);
            if (mst.size() == g.vertexCount() - 1) break;
        }

        long end = System.nanoTime();
        long cost = mst.stream().mapToLong(e -> e.weight).sum();
        return new MSTResult(mst, cost, ops, (end - start) / 1e6);
    }
}
