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

        // Pretty output
        System.out.println("=========================================");
        System.out.println("     🌿 Minimum Spanning Tree Report     ");
        System.out.println("=========================================");
        System.out.printf("Vertices: %d, Edges: %d%n", g.vertexCount(), g.edgeCount());
        System.out.println("-----------------------------------------");

        System.out.println("Prim's Algorithm:");
        System.out.printf("  ➤ Total Cost: %d%n", prim.totalCost);
        System.out.printf("  ➤ Operations: %d%n", prim.operationsCount);
        System.out.printf("  ➤ Execution Time: %d ms%n", prim.executionTimeMs);
        System.out.println("  ➤ MST Edges:");
        prim.mstEdges.forEach(e -> System.out.printf("     %d — %d (w=%d)%n", e.from, e.to, e.weight));
        System.out.println();

        System.out.println("Kruskal's Algorithm:");
        System.out.printf("  ➤ Total Cost: %d%n", kruskal.totalCost);
        System.out.printf("  ➤ Operations: %d%n", kruskal.operationsCount);
        System.out.printf("  ➤ Execution Time: %d ms%n", kruskal.executionTimeMs);
        System.out.println("  ➤ MST Edges:");
        kruskal.mstEdges.forEach(e -> System.out.printf("     %d — %d (w=%d)%n", e.from, e.to, e.weight));
        System.out.println("-----------------------------------------");

        // Comparison
        if (prim.totalCost == kruskal.totalCost) {
            System.out.println("✅ Both algorithms produced the same MST total cost!");
        } else {
            System.out.println("❌ MST costs differ — check implementation!");
        }

        // Analysis summary
        System.out.println("-----------------------------------------");
        System.out.println("Complexity Comparison (Expected):");
        System.out.println("  • Prim:   O(E log V)");
        System.out.println("  • Kruskal: O(E log E)");
        System.out.println();
        if (prim.executionTimeMs < kruskal.executionTimeMs)
            System.out.println("➡️ Prim performed slightly faster on this dataset.");
        else if (prim.executionTimeMs > kruskal.executionTimeMs)
            System.out.println("➡️ Kruskal performed slightly faster on this dataset.");
        else
            System.out.println("➡️ Both algorithms performed equally fast.");
        System.out.println("=========================================");
    }
}
