package mst;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class GraphVisualizer {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java mst.GraphVisualizer <input.json>");
            return;
        }

        File input = new File(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> root = mapper.readValue(input, Map.class);
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) root.get("graphs");

        int count = 0;
        for (Map<String, Object> g : graphs) {
            int id = (int) g.get("id");
            List<String> nodes = (List<String>) g.get("nodes");
            if (nodes.size() > 100) continue; // не рисуем графы больше 100 вершин
            nodes = (List<String>) g.get("nodes");
            List<Map<String, Object>> edges = (List<Map<String, Object>>) g.get("edges");

            String dot = toDot(id, nodes, edges);
            File dotFile = new File("graph_" + id + ".dot");
            try (FileWriter fw = new FileWriter(dotFile)) {
                fw.write(dot);
            }

            // генерируем PNG с помощью Graphviz
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotFile.getName(), "-o", "graph_" + id + ".png");
            pb.inheritIO().start().waitFor();

            count++;
        }
        System.out.println("✅ Rendered " + count + " graphs as PNGs.");
    }

    private static String toDot(int id, List<String> nodes, List<Map<String, Object>> edges) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G").append(id).append(" {\n");
        sb.append("  node [shape=circle, style=filled, color=lightblue];\n");
        for (String node : nodes)
            sb.append("  ").append(node).append(";\n");
        for (Map<String, Object> e : edges)
            sb.append("  ").append(e.get("from")).append(" -- ").append(e.get("to"))
                    .append(" [label=").append(e.get("weight")).append("];\n");
        sb.append("}\n");
        return sb.toString();
    }
}
