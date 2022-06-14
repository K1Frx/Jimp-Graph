package graph.jimpgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Test {
    public static int allTests() throws FileNotFoundException {
        if(testRead() == 0)
        {
            testBFS();
            testDijkstra();
        }
        testRandWeightModeGenerator();
        testAllRandModeGenerator();
        testConModeGenerator();

        return 0;
    }
    static int testRandWeightModeGenerator()
    {
        Graph graphForTest = new Graph();
        graphForTest.initializeGraph(5,5,1.9,5.4,0);
        Generator.generateRandWeightMode(graphForTest);
        if(graphForTest  != null)
        {
            System.out.println("Test generateRandWeightMode udany!");
            return 0;
        }
        else
        {
            System.out.println("Test generateRandWeightMode nieudany!");
            return 1;
        }
    }

    static int testAllRandModeGenerator()
    {
        Graph graphForTest = new Graph();
        graphForTest.initializeGraph(5,5,1.9,5.4,0);
        Generator.generateAllRandMode(graphForTest);
        if(graphForTest  != null)
        {
            System.out.println("Test generateAllRandMode udany!");
            return 0;
        }
        else
        {
            System.out.println("Test generateAllRandMode nieudany!");
            return 1;
        }
    }

    static int testConModeGenerator()
    {
        Graph graphForTest = new Graph();
        graphForTest.initializeGraph(5,5,1.9,5.4,0);
        Generator.generateConMode(graphForTest);
        if(graphForTest  != null)
        {
            System.out.println("Test generateConMode udany!");
            return 0;
        }
        else
        {
            System.out.println("Test generateConMode nieudany!");
            return 1;
        }
    }

    static int testRead() throws FileNotFoundException {
        Graph graphForTest = new Graph();
        FileManager.readFile("src\\main\\java\\graph\\jimpgraph\\isodData",graphForTest);

        if(graphForTest.getRows() != -1 && graphForTest.getColumns() != -1)
        {
            System.out.println("Test wczytywania udany!");
            return 0;
        }
        else
        {
            System.out.println("Test wczytywania nieudany!");
            return 1;
        }
    }

    static int testBFS() throws FileNotFoundException {
        Graph graphForTest = new Graph();
        FileManager.readFile("src\\main\\java\\graph\\jimpgraph\\isodData",graphForTest);

        if(Bfs.BFS(graphForTest,0) == 1)
        {
            System.out.println("Test BFSa udany!\n");
            return 0;
        }
        else
        {
            System.out.println("Test BFSa nieudany!\n");
            return 1;
        }
    }

    static int testDijkstra() throws FileNotFoundException {
        Graph graphForTest = new Graph();
        FileManager.readFile("src\\main\\java\\graph\\jimpgraph\\isodData",graphForTest);






        int [] pathForTest = {1,5,9,13};
        LinkedList<Integer> pathFromDijkstra = Dijkstra.dijkstra(graphForTest,1,13);
        int i = 0;
        while (!pathFromDijkstra.isEmpty()){
            if(pathForTest[i++] != (int)pathFromDijkstra.removeFirst()){
                System.out.println("Test Dijkstry nieudany!");
                return 1;

            }
        }


        System.out.println("Test Dijkstry udany!");
        return 0;
    }







}
