/******************************************************************
 *
 *   Jenine Irshaid / 002
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     *
     * (see assignment description)
     */
    public boolean canFinish(int numExams, int[][] prerequisites) {

        int numNodes = numExams;

        // Build directed graph's adjacency list
        ArrayList<Integer>[] adj = getAdjList(numNodes, prerequisites);

        // 0 = unvisited, 1 = visiting, 2 = visited
        int[] state = new int[numNodes];

        // Run DFS from every node to detect a cycle
        for (int i = 0; i < numNodes; i++) {
            if (state[i] == 0) {
                if (hasCycle(i, adj, state)) {
                    return false;   // cycle found → cannot finish
                }
            }
        }

        // no cycles detected
        return true;
    }

    // DFS helper: returns true if a cycle is found starting from node
    private boolean hasCycle(int node, ArrayList<Integer>[] adj, int[] state) {
        state[node] = 1; // visiting

        for (int neighbor : adj[node]) {
            if (state[neighbor] == 1) {
                // back edge to a node in the recursion stack → cycle
                return true;
            }
            if (state[neighbor] == 0) {
                if (hasCycle(neighbor, adj, state)) {
                    return true;
                }
            }
        }

        state[node] = 2; // visited
        return false;
    }

    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     */
    private ArrayList<Integer>[] getAdjList(int numNodes, int[][] edges) {

        ArrayList<Integer>[] adj = new ArrayList[numNodes];

        for (int node = 0; node < numNodes; node++) {
            adj[node] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            // edge[0] depends on edge[1]; direction edge[0] -> edge[1]
            adj[edge[0]].add(edge[1]);
        }
        return adj;
    }

    /*
     * Assignment Graphing - Number of groups.
     *
     * (see assignment description)
     */
    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int i, j;

        // Convert adjacency matrix to adjacency list
        for (i = 0; i < numNodes; i++) {
            for (j = 0; j < numNodes; j++) {
                if (adjMatrix[i][j] == 1 && i != j) {
                    graph.putIfAbsent(i, new ArrayList<>());
                    graph.putIfAbsent(j, new ArrayList<>());
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        boolean[] visited = new boolean[numNodes];
        int groups = 0;

        // Count connected components
        for (int node = 0; node < numNodes; node++) {
            if (!visited[node]) {
                groups++;
                dfsGroups(node, graph, visited);
            }
        }

        return groups;
    }

    // DFS helper for numGroups
    private void dfsGroups(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
        visited[node] = true;

        List<Integer> neighbors = graph.get(node);
        if (neighbors == null) {
            // isolated node
            return;
        }

        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                dfsGroups(neighbor, graph, visited);
            }
        }
    }
}
