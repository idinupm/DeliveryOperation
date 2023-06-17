import java.util.*;

class Graph {
    private int vertices;
    List<Edge>[] adjacencyList;

    Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    void addEdge(int source, int destination, int weight, int priority) {
        if (weight < 0){
            throw new IllegalArgumentException("Negative weights are not allowed");
        }
        adjacencyList[source].add(new Edge(destination, weight, priority));
    }

    List<Integer> shortestPath(int source, int destination) {
        // Priority queue to store nodes with the minimum distance
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        // Arrays to store distances, priorities, and previous nodes
        int[] distances = new int[vertices];
        int[] priorities = new int[vertices];
        int[] previous = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(priorities, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        // Set the distance and priority of the source node to 0
        distances[source] = 0;
        priorities[source] = 0;
        priorityQueue.offer(new Node(source, 0, 0));

        // Dijkstra's algorithm for finding the shortest path
        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            int current = node.vertex;

            // If we have reached the destination node, exit the loop
            if (current == destination) {
                break;
            }

            // Skip the current node if it has been visited with a better distance or priority
            if (node.distance > distances[current] || (node.distance == distances[current] && node.priority < priorities[current])) {
                continue;
            }

            // Explore the neighbors of the current node
            for (Edge edge : adjacencyList[current]) {
                int next = edge.destination;
                int weight = edge.weight;
                int distance = distances[current] + weight;
                int priority = priorities[current] + edge.priority;

                // Update the distance, priority, and previous node if a better path is found
                if (distance < distances[next] || (distance == distances[next] && priority > priorities[next])) {
                    distances[next] = distance;
                    priorities[next] = priority;
                    previous[next] = current;
                    priorityQueue.offer(new Node(next, distance, priority));
                }
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

    // Inner class to represent an edge in the graph
    static class Edge {
        int destination;
        int weight;
        int priority;

        Edge(int destination, int weight, int priority) {
            this.destination = destination;
            this.weight = weight;
            this.priority = priority;
        }
    }

    // Inner class to represent a node with its distance and priority
    private static class Node {
        int vertex;
        int distance;
        int priority;

        Node(int vertex, int distance, int priority) {
            this.vertex = vertex;
            this.distance = distance;
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
            int source = scanner.nextInt();

            if (source == -1) {
                break;
            }

            // Prompt the user to enter the destination vertex
            System.out.print("Enter the destination vertex: ");
            int destination = scanner.nextInt();

            // Find the shortest path from the source to the destination
            List<Integer> path = graph.shortestPath(source, destination);

            // Calculate the total cost of the shortest path
            int totalCost = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                int current = path.get(i);
                int next = path.get(i + 1);
                for (Graph.Edge edge : graph.adjacencyList[current]) {
                    if (edge.destination == next) {
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
                    System.out.print(vertex + " ");
                }
                System.out.println();
                System.out.println("Total cost: " + totalCost);
            }

            System.out.println();
        }
        scanner.close();
    }
}
