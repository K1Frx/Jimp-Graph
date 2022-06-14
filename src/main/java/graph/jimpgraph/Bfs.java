package graph.jimpgraph;

import java.util.LinkedList;
public class Bfs {

    static LinkedList<Integer> queue;
    static int[] vertexState;

    public static int BFS(Graph graph, int startingVertex){
        int isGraphCon = 1;
        for (int i=0;i < graph.getRows() * graph.getColumns();i++){
            if (singleBFS(graph,i)==0){
                isGraphCon = 0;
                break;
            }
        }
        return isGraphCon;
    }

    private static int singleBFS(Graph graph, int startingVertex){
        int n = graph.getColumns()*graph.getRows();
        queue = new LinkedList<>();
        vertexState = new int[n];
        for(int i=0;i<n;i++){
            vertexState[i] = 0;
        }
        queue.add(startingVertex);
        int currentVertex;
        while(!queue.isEmpty()){
            currentVertex = queue.removeFirst();
            for (int j = 0; j < graph.directions; j++)
            {
                if (graph.getVertex(currentVertex,j) != -1) {
                    if (vertexState[graph.getVertex(currentVertex, j)] == 0) {
                        queue.add(graph.getVertex(currentVertex, j));
                        vertexState[graph.getVertex(currentVertex, j)] = 1; // 1 - added to queue
                    }
                }
            }
            vertexState[currentVertex] = 2; // visited


        }
        int haveAllVertexesBeenVisited = 1;
        for (int i = 0; i < n; i++)
        {
            if (vertexState[i] != 2)
            {
                haveAllVertexesBeenVisited = 0;
                break;
            }
        }
        return haveAllVertexesBeenVisited;

    }
}
