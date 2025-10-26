package mst;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class JsonModels {

    public static void main(String[] args) throws Exception {
        String inputFile = args.length > 0 ? args[0] : "generated_input.json";
        String outputFile = args.length > 1 ? args[1] : "output.json";
        String csvFile = "results_summary.csv";

        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> root = mapper.readValue(new File(inputFile), Map.class);
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) root.get("graphs");

        List<Map<String, Object>> results = new ArrayList<>();
        StringBuilder csv = new StringBuilder("id,algorithm,vertices,total_cost,operations,time_ms\n");

        int count = 0;
        for (Map<String, Object> g : graphs) {
            count++;
            int id = (int) g.get("id");
            List<String> nodes = (List<String>) g.get("nodes");
            List<Map<String, Object>> edges = (List<Map<String, Object>>) g.get("edges");

            Graph graph = new Graph(nodes);
            for (Map<String, Object> e : edges) {
                graph.addEdge((String)e.get("from"), (String)e.get("to"), (int)e.get("weight"));
            }

            MSTResult prim = PrimMST.compute(graph);
            MSTResult kruskal = KruskalMST.compute(graph);

            Map<String, Object> resultEntry = new LinkedHashMap<>();
            resultEntry.put("id", id);
            resultEntry.put("vertices", graph.vertexCount());
            resultEntry.put("edges", graph.edgeCount());
            resultEntry.put("prim_cost", prim.totalCost);
            resultEntry.put("kruskal_cost", kruskal.totalCost);
            resultEntry.put("same_cost", prim.totalCost == kruskal.totalCost);
            resultEntry.put("prim_time", prim.executionTimeMs);
            resultEntry.put("kruskal_time", kruskal.executionTimeMs);
            resultEntry.put("prim_ops", prim.operationsCount);
            resultEntry.put("kruskal_ops", kruskal.operationsCount);
            results.add(resultEntry);

            csv.append(id).append(",Prim,").append(graph.vertexCount()).append(",")
                    .append(prim.totalCost).append(",").append(prim.operationsCount).append(",")
                    .append(prim.executionTimeMs).append("\n");

            csv.append(id).append(",Kruskal,").append(graph.vertexCount()).append(",")
                    .append(kruskal.totalCost).append(",").append(kruskal.operationsCount).append(",")
                    .append(kruskal.executionTimeMs).append("\n");
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), results);
        Files.writeString(Paths.get(csvFile), csv.toString());

        System.out.println("✅ Results written to " + outputFile + " and " + csvFile);
    }
}
