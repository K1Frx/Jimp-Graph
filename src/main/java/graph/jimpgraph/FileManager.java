package graph.jimpgraph;

import javafx.scene.control.TextArea;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class FileManager {
    public static int readFile(String path, Graph graph) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String parameters = sc.nextLine();
        String[] parsedParameters = parameters.split(" ");
        try {
            graph.initializeGraph(Integer.parseInt(parsedParameters[0]), Integer.parseInt(parsedParameters[1]), 0, 1, 0);
            int numberOfLine = 0;
            while (sc.hasNextLine()) {
                int numberOfConnections = 0;
                String line = sc.nextLine();
                String[] parsedLine = line.split(" ");
                for (int i = 0; i < parsedLine.length; i += 3) {
                    if ((Integer.parseInt(parsedLine[i + 1]) - numberOfLine)==1) { numberOfConnections = 0;}
                    if ((Integer.parseInt(parsedLine[i + 1]) - numberOfLine)==graph.getColumns()) { numberOfConnections = 1;}
                    if ((Integer.parseInt(parsedLine[i + 1]) - numberOfLine)==-1) { numberOfConnections = 2;}
                    if ((Integer.parseInt(parsedLine[i + 1]) - numberOfLine)== -(graph.getColumns())) { numberOfConnections = 3;}

                    graph.setVertex(numberOfLine, numberOfConnections, Integer.parseInt(parsedLine[i + 1]));
                    parsedLine[i + 2] = parsedLine[i + 2].replace(':', '0');
                    graph.setWeight(numberOfLine, numberOfConnections, Double.parseDouble(parsedLine[i + 2]));
                    numberOfConnections += 1;
                }
                numberOfLine += 1;
            }
            return 0;
        }
        catch(NumberFormatException e){
            return 1;
        }
    }

    public static final String DATE_FORMAT_NOW = "yyyyMMddHHmmss";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static int saveGraph(Graph graph, TextArea stdOut) throws IOException {
        try {
            File createdGraph = new File("graph" + now() + ".txt");
            if (createdGraph.createNewFile()) {
                PrintWriter writer = new PrintWriter(createdGraph);
                writer.print("");
                writer.close();
                try(FileWriter fw = new FileWriter(createdGraph)) {
                    fw.write(graph.getRows() + " " + graph.getColumns() + "\n");
                    for (int i = 0; i < graph.getColumns() * graph.getRows(); i++) {
                        char tabulator = 9;
                        fw.write(tabulator);
                        for (int k = 0; k < graph.directions; k++) {
                            if (graph.getVertex(i, k) != -1) {
                                fw.write(graph.getVertex(i, k) + " :" + graph.getWeight(i, k) + "  ");
                            }
                        }
                        fw.write("\n");
                    }
                    stdOut.appendText("Graf został zapisany!\n");
                    return 0;
                }
            }

        }
        catch(FileNotFoundException e){
            stdOut.appendText("Nie udało się zapisać grafu!\n");
            return 1;
        }
        stdOut.appendText("Nie udało się zapisać grafu!\n");
        return 1;
    }
}
