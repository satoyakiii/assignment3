package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MSTTests {

    @Test
    public void testSmallGraph() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        Graph g = new Graph(nodes);
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("A", "C", 3);
        g.addEdge("C", "D", 4);

        MSTResult prim = PrimMST.compute(g);
        MSTResult kruskal = KruskalMST.compute(g);

        assertEquals(prim.totalCost, kruskal.totalCost,
                "Prim and Kruskal should produce same total cost");

        assertEquals(7, prim.totalCost, "Expected MST cost = 7");
        assertEquals(3, prim.mstEdges.size(), "MST should have V-1 edges");
    }

    @Test
    public void testSingleNode() {
        Graph g = new Graph(List.of("A"));
        MSTResult prim = PrimMST.compute(g);
        MSTResult kruskal = KruskalMST.compute(g);

        assertEquals(0, prim.totalCost);
        assertEquals(0, kruskal.totalCost);
        assertEquals(prim.totalCost, kruskal.totalCost);
    }

    @Test
    public void testDisconnectedGraph() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        Graph g = new Graph(nodes);
        g.addEdge("A", "B", 5);

        MSTResult prim = PrimMST.compute(g);
        MSTResult kruskal = KruskalMST.compute(g);

        assertTrue(prim.totalCost >= 5);
        assertTrue(kruskal.totalCost >= 5);
    }
}
