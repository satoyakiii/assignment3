# **Assignment 3 - Minimum Spanning Tree Algorithms (Report)**
**Author:** Assem Rakhmanova (satoyakiii)  
**Course:** Design and Analysis of Algorithms  
**Instructor:** Sayakulova Zarina

---

## **1. Learning Goals & Scope**

Implement and analyze **Minimum Spanning Tree (MST)** algorithms -  
**Kruskal’s** and **Prim’s** - within the Greedy paradigm.

**Goals:**
1. Implement safe, iterative/heap-based MST construction.
2. Measure empirical metrics (time, edge operations, MST weight).
3. Compare algorithmic efficiency on identical graphs.
4. Validate theoretical complexity and structural correctness.

**Implemented algorithms:**
- **Kruskal’s algorithm** - edge-sorted MST via Union–Find (DSU).
- **Prim’s algorithm** - priority-queue MST via adjacency expansion.

---

## **2. Architecture & Design Safety**

### **Core Components**
| Component | Role |
|------------|------|
| **Graph** | Stores vertices, undirected weighted edges, adjacency lists. |
| **Edge** | Immutable record (u, v, w), implements `Comparable` by weight. |
| **UnionFind** | Disjoint Set Union with path compression + union by rank. |
| **Kruskal / Prim** | Independent MST builders returning `Result` object. |
| **Result** | Holds edge list, total weight, and operation count. |
| **IOUtils** | JSON I/O via Jackson (ObjectMapper). |
| **Main** | CLI entry point measuring execution time (nanoTime). |

### **Safety Considerations**
- Defensive checks on null/empty graphs.
- Duplicate edges allowed; ignored when not minimal.
- Disconnected graphs → partial forests handled gracefully.

---

## **3. Correctness & Testing**

### **Unit Tests**
- **Small Graph:** A-B-C-D (5 edges) → expected MST weight - 7.
- **Edge Cases:** single node, no edges, duplicate weights.
- **Consistency:** Prim and Kruskal must yield identical MST weights.

### **JUnit 5 Integration**
All tests pass (`mvn test`), asserting total weight and edge count (V-1).

---

## **4. Algorithmic Analysis (Theory)**

### **4.1 Kruskal’s Algorithm**

**Steps:**
1. Sort all edges by weight.
2. Add edges if their endpoints belong to different components.
3. Repeat until (V - 1) edges selected.

**Recurrence & Complexity**

\[
T(E,V) = O(E \log E) + O(E \,\alpha(V))
\]

where \(\alpha(V)\) - inverse Ackermann function ≈ constant.

**Time Complexity:** \(\Theta(E \log E)\)  
**Space Complexity:** O(E + V)

---

### **4.2 Prim’s Algorithm**

**Steps:**
1. Start from any node.
2. Repeatedly select smallest edge connecting to an unvisited vertex.
3. Update PriorityQueue with new edges.

**Recurrence & Complexity**

\[
T(E,V) = O(E \log V)
\]

(using binary heap / priority queue)

**Time Complexity:** \(\Theta(E \log V)\)  
**Space Complexity:** O(E + V)

---

## **5. Empirical Methodology (Experimental Setup)**

| Metric | Description |
|---------|--------------|
| **operations** | Number of edge checks (Kruskal) or heap polls (Prim). |
| **totalWeight** | Sum of MST edge weights. |
| **time_ms** | Wall-time measured by System.nanoTime() / 1e6. |

**Input Graph (JSON)**

```json
{
  "nodes": ["A","B","C","D"],
  "edges": [
    {"from": "A", "to": "B", "weight": 4},
    {"from": "A", "to": "C", "weight": 1},
    {"from": "C", "to": "B", "weight": 2},
    {"from": "B", "to": "D", "weight": 3},
    {"from": "C", "to": "D", "weight": 5}
  ]
}
