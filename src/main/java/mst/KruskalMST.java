package mst;

import java.util.*;

public class KruskalMST {
    public static MSTResult compute(Graph g) {
        int n = g.vertexCount();
        List<Edge> edges = new ArrayList<>(g.getAllEdges());
        edges.sort(Comparator.comparingInt(e -> e.weight));
        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();
        long ops = 0;
        long start = System.nanoTime();

        for (Edge e : edges) {
            ops++;
            if (uf.union(e.from, e.to)) {
                mst.add(e);
                if (mst.size() == n - 1) break;
            }
        }

        long end = System.nanoTime();
        long cost = mst.stream().mapToLong(e -> e.weight).sum();

        long elapsedMs = (end - start) / 1_000_000L;
        if (elapsedMs == 0 && end > start) elapsedMs = 1L;

        return new MSTResult(mst, cost, ops, elapsedMs);
    }
}
