package graph.jimpgraph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

class TheComparator implements Comparator<Integer> {
    public int compare(Integer a, Integer b)
    {
        if (Dijkstra.getPathLength(a)>Dijkstra.getPathLength(b)){
            return 1;
        }
        else if (Dijkstra.getPathLength(a)<Dijkstra.getPathLength(b)){
            return -1;
        }
        else if (Dijkstra.getPathLength(a)==Dijkstra.getPathLength(b)){
            return 0;
        }

        return 0;
    }
}


public class Dijkstra {
    final static double INFINITY = 1000000;
    static PriorityQueue<Integer> pQueue;
    private static double[] pathLength;


    public static double getPathLength(int a){
        return pathLength[a];
    }
    public static LinkedList<Integer> dijkstra (Graph graph, int start, int destination) {
        LinkedList<Integer> path;
        int n = graph.getRows()*graph.getColumns();
        path = new LinkedList<>();
        pQueue = new PriorityQueue<>(new TheComparator());
        int[] ancestor = new int[n];
        int[] wasVisited = new int[n];
        pathLength  = new double[n];
        int currentVertex;
        for(int i=0;i<n;i++){
            ancestor[i]= -1;
            wasVisited[i] = 0;
            pathLength[i] =INFINITY;
        }
        pathLength[start] = 0;
        pQueue.add(start);

        for (int i=0; i < n ; i++){

            if (!pQueue.isEmpty()){
                currentVertex = pQueue.remove();

            }
            else
                break;

            for (int j=0; j<graph.directions; j++){

                if(graph.getVertex(currentVertex,j)!=-1 &&
                        (graph.getWeight(currentVertex, j) +pathLength[currentVertex]<pathLength[graph.getVertex(currentVertex,j)]))
                {
                    pathLength[graph.getVertex(currentVertex,j)] = graph.getWeight(currentVertex,j) +pathLength[currentVertex];
                    ancestor[graph.getVertex(currentVertex, j)] = currentVertex;
                    if(wasVisited[graph.getVertex(currentVertex, j)]==0)
                    {
                        pQueue.add(graph.getVertex(currentVertex, j));
                        wasVisited[graph.getVertex(currentVertex, j)]=1;
                    }
                }
            }
        }
        if(pathLength[destination]==INFINITY)
        {
            return null;
        }


        int tmp = destination;
        path.add(destination);
        while(ancestor[tmp]!=-1)
        {

            path.addFirst(ancestor[tmp]);
            tmp = ancestor[tmp];
        }
        return path;

    }


}
