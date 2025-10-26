package mst;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;


public class GraphGenerator {

    public static class OutGraph {
        public int id;
        public List<String> nodes;
        public List<Map<String, Object>> edges;
    }

    public static void main(String[] args) throws Exception {
        String outputFile = "generated_input.json";
        if (args.length > 0) outputFile = args[0];

        Random rand = new Random(12345);
        List<OutGraph> graphs = new ArrayList<>();
        int gid = 1;

        // 5 small (5–30 вершин)
        int[] small = {5, 8, 12, 20, 28};
        for (int s : small) graphs.add(makeGraph(gid++, s, 0.15, rand));

        // 10 medium (30–300 вершин)
        int[] medium = {30, 50, 80, 120, 160, 200, 240, 280, 300, 320};
        for (int s : medium) graphs.add(makeGraph(gid++, s, 0.08, rand));

        // 10 large (300–1000 вершин)
        int[] large = {300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200};
        for (int s : large) graphs.add(makeGraph(gid++, s, 0.03, rand));

        // 3 extra large (1000–2000 вершин)
        int[] xlarge = {1300, 1600, 1800};
        for (int s : xlarge) graphs.add(makeGraph(gid++, s, 0.015, rand));

        // Сохраняем в JSON
        Map<String, Object> root = new HashMap<>();
        root.put("graphs", graphs);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), root);

        System.out.println("✅ Generated 28 graphs → " + outputFile);
    }

    private static OutGraph makeGraph(int id, int n, double extraProb, Random rand) {
        OutGraph g = new OutGraph();
        g.id = id;
        g.nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) g.nodes.add("N" + i);

        // гарантируем связность через остовное дерево
        List<int[]> edges = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            int parent = rand.nextInt(i);
            edges.add(new int[]{parent, i});
        }

        // добавляем случайные дополнительные рёбра
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rand.nextDouble() < extraProb) {
                    edges.add(new int[]{i, j});
                }
            }
        }

        // конвертируем в JSON-friendly формат
        g.edges = new ArrayList<>();
        for (int[] e : edges) {
            Map<String, Object> edge = new HashMap<>();
            edge.put("from", "N" + e[0]);
            edge.put("to", "N" + e[1]);
            edge.put("weight", rand.nextInt(100) + 1);
            g.edges.add(edge);
        }

        return g;
    }
}
