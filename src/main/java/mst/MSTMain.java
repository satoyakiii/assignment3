package mst;

import java.util.*;

public class MSTMain {
    public static void main(String[] args) {
        // Example graph (simple for testing)
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        Graph g = new Graph(nodes);

        g.addEdge("A", "B", 4);
        g.addEdge("A", "C", 3);
        g.addEdge("B", "C", 2);
        g.addEdge("B", "D", 5);
        g.addEdge("C", "D", 7);
        g.addEdge("C", "E", 8);
        g.addEdge("D", "E", 6);

        // Run Prim and Kruskal
        MSTResult prim = PrimMST.compute(g);
        MSTResult kruskal = KruskalMST.compute(g);

        // Output results
        System.out.println("=== Minimum Spanning Tree Comparison ===");
        System.out.println("Vertices: " + g.vertexCount());
        System.out.println("Prim total cost = " + prim.totalCost + ", ops = " + prim.operationsCount + ", time = " + prim.executionTimeMs + " ms");
        System.out.println("Kruskal total cost = " + kruskal.totalCost + ", ops = " + kruskal.operationsCount + ", time = " + kruskal.executionTimeMs + " ms");

        if (prim.totalCost == kruskal.totalCost)
            System.out.println("✅ Both algorithms produce the same MST cost.");
        else
            System.out.println("❌ MST costs differ! Check implementation.");
    }
}
