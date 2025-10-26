package mst;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

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

            // ✅ только 3 маленьких графа
            if (nodes.size() > 30 || count >= 3) continue;

            List<Map<String, Object>> edges = (List<Map<String, Object>>) g.get("edges");
            String dot = toDot(id, nodes, edges);

            File dotFile = new File("graph_" + id + ".dot");
            try (FileWriter fw = new FileWriter(dotFile)) {
                fw.write(dot);
            }

            // ✅ используем sfdp — он делает более круглый layout
            ProcessBuilder pb = new ProcessBuilder(
                    "sfdp", "-Goverlap=false", "-Gsplines=true",
                    "-Tpng", dotFile.getName(), "-o", "graph_" + id + ".png"
            );
            pb.inheritIO().start().waitFor();

            System.out.println("✅ Rendered: graph_" + id + ".png (" + nodes.size() + " vertices)");
            count++;
        }

        System.out.println("🎨 Rendered " + count + " beautiful graphs using SFDP layout.");
    }

    private static String toDot(int id, List<String> nodes, List<Map<String, Object>> edges) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G").append(id).append(" {\n");
        sb.append("  layout=sfdp;\n");
        sb.append("  bgcolor=\"#f9f9f9\";\n"); // светлый фон
        sb.append("  node [shape=circle, style=filled, fillcolor=lightblue, color=gray30, fontsize=12, fixedsize=true, width=0.5];\n");
        sb.append("  edge [color=gray50, penwidth=1.2, fontsize=9];\n");

        for (String node : nodes)
            sb.append("  ").append(node).append(";\n");

        for (Map<String, Object> e : edges)
            sb.append("  ").append(e.get("from")).append(" -- ").append(e.get("to"))
                    .append(" [label=").append(e.get("weight")).append("];\n");

        sb.append("}\n");
        return sb.toString();
    }
}
