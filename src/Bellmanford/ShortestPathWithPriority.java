import java.util.*;

class Graph {
    private int vertices;
    List<Edge> edges;

    Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    void addEdge(int source, int destination, int weight, int priority) {
        edges.add(new Edge(source, destination, weight, priority));
    }

    List<Integer> shortestPath(int source, int destination) {
        int[] distances = new int[vertices];
        int[] previous = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[source] = 0;

        // Run the Bellman-Ford algorithm
        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;
                int priority = edge.priority;

                if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    previous[v] = u;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;
            int priority = edge.priority;

            if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                throw new RuntimeException("Negative-weight cycle detected");
            }
        }

        // Build the shortest path by backtracking from the destination node
        List<Integer> path = new ArrayList<>();
        int current = destination;
        while (current != -1) {
            path.add(current);
            current = previous[current];
        }
        Collections.reverse(path);
        return path;
    }

    static class Edge {
        int source;
        int destination;
        int weight;
        int priority;

        Edge(int source, int destination, int weight, int priority) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
            this.priority = priority;
        }
    }
}

public class ShortestPathWithPriority {
    public static void main(String[] args) {
        // Create a graph with 8 vertices
        Graph graph = new Graph(8);

        // Add edges to the graph
        graph.addEdge(0, 1, 6, 0);
        graph.addEdge(0, 2, 5, 10);
        graph.addEdge(0, 3, 6, 5);
        graph.addEdge(1, 4, 1, 10);
        graph.addEdge(2, 4, 3, 4);
        graph.addEdge(2, 5, 3, 9);
        graph.addEdge(2, 6, 5, 6);
        graph.addEdge(3, 5, 5, 10);
        graph.addEdge(4, 5, 1, 8);
        graph.addEdge(4, 6, 1, 4);
        graph.addEdge(5, 6, 3, 5);
        graph.addEdge(5, 7, 6, 10);
        graph.addEdge(6, 7, 2, 3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Prompt the user to enter the source vertex
            System.out.print("Enter the source vertex (or -1 to exit): ");
            String source = scanner.next();

            if (source.equals("-1")) {
                break;
            }

            // Prompt the user to enter the destination vertex
            System.out.print("Enter the destination vertex: ");
            String destination = scanner.next();

            int sourceIndex = source.charAt(0) - 'A';
            int destinationIndex = destination.charAt(0) - 'A';

            // Find the shortest path from the source to the destination
            List<Integer> path;
            try {
                path = graph.shortestPath(sourceIndex, destinationIndex);

                // Calculate the total cost of the shortest path
                int totalCost = 0;
                for (int i = 0; i < path.size() - 1; i++) {
                    int current = path.get(i);
                    int next = path.get(i + 1);
                    for (Graph.Edge edge : graph.edges) {
                        if (edge.source == current && edge.destination == next) {
                            totalCost += edge.weight;
                            break;
                        }
                    }
                }

                // Print the shortest path and the total cost
                if (path.size() == 0) {
                    System.out.println("No path found from vertex " + source + " to vertex " + destination);
                } else {
                    System.out.println("Shortest path from vertex " + source + " to vertex " + destination + ":");
                    for (int vertex : path) {
                        char vertexChar = (char) (vertex + 'A');
                        System.out.print(vertexChar + " ");
                    }
                    System.out.println();
                    System.out.println("Total cost: " + totalCost);
                }
            } catch (RuntimeException e) {
                System.out.println("Negative-weight cycle detected. Cannot find the shortest path.");
            }

            System.out.println();
        }
        scanner.close();
    }
}
