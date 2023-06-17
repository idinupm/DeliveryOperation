import java.util.*;



public class test {
    public static void main(String[] args) {
        // Create a graph with 6 vertices
        Graph graph = new Graph(6);

        // Add edges to the graph
        graph.addEdge(0, 5, 6, 3);
        graph.addEdge(0, 2, 2, 0);
        graph.addEdge(0, 3, 2, 1);
        graph.addEdge(1, 2, 1, 0);
        graph.addEdge(1, 3, 5, 0);
        graph.addEdge(2, 3, 8, 2);
        graph.addEdge(2, 4, 9, 0);
        graph.addEdge(3, 4, 1, 1);
        graph.addEdge(3, 5, 5, 3);
        graph.addEdge(4, 5, 3, 2);

        // Define the source and destination vertices
        int source = 0;
        int destination = 5;

        // Find the shortest path from the source to the destination
        List<Integer> path = graph.shortestPath(source, destination);

        // Print the shortest path
        if (path.size() == 0) {
            System.out.println("No path found from vertex " + source + " to vertex " + destination);
        } else {
            System.out.println("Shortest path from vertex " + source + " to vertex " + destination + ":");
            for (int vertex : path) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }
}