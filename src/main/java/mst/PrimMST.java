package mst;

import java.util.*;

public class PrimMST {
    public static MSTResult compute(Graph g) {
        int n = g.vertexCount();
        if (n == 0) return new MSTResult(Collections.emptyList(), 0, 0, 0L);

        boolean[] used = new boolean[n];
        int[] parent = new int[n];
        int[] key = new int[n];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        class Node implements Comparable<Node> {
            int v, w;
            Node(int v, int w) { this.v = v; this.w = w; }
            public int compareTo(Node o) { return Integer.compare(this.w, o.w); }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0));

        List<Edge> mst = new ArrayList<>();
        long ops = 0;
        long start = System.nanoTime();

        while (!pq.isEmpty()) {
            Node cur = pq.poll(); ops++;
            int u = cur.v;
            if (used[u]) continue;
            used[u] = true;
            if (parent[u] != -1) mst.add(new Edge(parent[u], u, key[u]));
            for (Edge e : g.adj(u)) {
                int v = e.to;
                if (!used[v] && e.weight < key[v]) {
                    key[v] = e.weight;
                    parent[v] = u;
                    pq.add(new Node(v, key[v]));
                    ops++;
                }
            }
        }

        long end = System.nanoTime();
        long cost = mst.stream().mapToLong(e -> e.weight).sum();

        long elapsedMs = (end - start) / 1_000_000L; // floor milliseconds
        if (elapsedMs == 0 && end > start) elapsedMs = 1L; // if non-zero duration but <1ms, show 1ms

        return new MSTResult(mst, cost, ops, elapsedMs);
    }
}
