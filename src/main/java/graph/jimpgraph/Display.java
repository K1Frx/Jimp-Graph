package graph.jimpgraph;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;

import static javafx.scene.paint.Color.*;

public class Display extends HelloController{

    static void initializeGraphSpecs(TextArea standardOutput, Graph graph,
                                     TextField rowsTextField, TextField columnsTextField,
                                     TextField minWeightTextField, TextField maxWeightTextField){
        standardOutput.setText("");
        try{
            graph.setRowsN(Integer.parseInt(rowsTextField.getText()));
            if (graph.getRowsN() < 2 || graph.getRowsN() > 100){
                standardOutput.appendText("Blad zwiazany z wierszami! |"+graph.getRowsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                graph.setRowsN(15);
                rowsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z wierszami! |"+graph.getRowsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            graph.setRowsN(15);
            rowsTextField.setText("15");
        }
        try{
            graph.setColumnsN(Integer.parseInt(columnsTextField.getText()));
            if (graph.getColumnsN() < 2 || graph.getColumnsN() > 100){
                standardOutput.appendText("Blad zwiazany z kolumnami! |"+graph.getColumnsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                graph.setColumnsN(15);
                columnsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z kolumnami! |"+graph.getColumnsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            graph.setColumnsN(15);
            columnsTextField.setText("15");
        }
        try{
            graph.setMinWeightN(Double.parseDouble(minWeightTextField.getText()));
            if(graph.getMinWeightN() < -10 || graph.getMinWeightN() > 10){
                graph.setMinWeightN(0);
                minWeightTextField.setText("0");
                standardOutput.appendText("Blad zwiazany z minimalna waga! |"+graph.getMinWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            }
        }
        catch(NumberFormatException e){
            graph.setMinWeightN(0);
            minWeightTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z minimalna waga! |"+graph.getMinWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
        try{
            graph.setMaxWeightN(Double.parseDouble(maxWeightTextField.getText()));
            if(graph.getMaxWeightN() - graph.getMinWeightN() <= 0){
                standardOutput.appendText("Blad! Gorny zakres wag mniejszy od dolnego! Zamieniam miejscami!\n");
                double temp = graph.getMaxWeightN();
                graph.setMaxWeightN(graph.getMinWeightN());
                graph.setMinWeightN(temp);
                maxWeightTextField.setText(String.valueOf(graph.getMaxWeightN()));
                minWeightTextField.setText(String.valueOf(graph.getMinWeightN()));
            }
            if(graph.getMaxWeightN() - graph.getMinWeightN() > 100){
                standardOutput.appendText("Blad! Roznica gornego i dolnego zakresu wag jest wieksza niz 100! Ustawiono wartosci domyslne!\n");
                graph.setMaxWeightN(1);
                graph.setMinWeightN(0);
                maxWeightTextField.setText(String.valueOf(graph.getMaxWeightN()));
                minWeightTextField.setText(String.valueOf(graph.getMinWeightN()));
            }
        }
        catch(NumberFormatException e){
            graph.setMaxWeightN(1);
            maxWeightTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z maksymalna waga! |"+graph.getMaxWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
    }

    static int setModeCheckList(CheckBox randWeightModeCheckBox, CheckBox allRandModeCheckBox, CheckBox conModeCheckBox, int chosen){
        switch(chosen){
            case 1 -> {
                if(!randWeightModeCheckBox.isSelected()){
                    randWeightModeCheckBox.setSelected(false);
                }
                else{
                    allRandModeCheckBox.setSelected(false);
                    conModeCheckBox.setSelected(false);
                    randWeightModeCheckBox.setSelected(true);
                    return 1;
                }
            }
            case 2 -> {
                if(!allRandModeCheckBox.isSelected()){
                    allRandModeCheckBox.setSelected(false);
                }
                else{
                    conModeCheckBox.setSelected(false);
                    randWeightModeCheckBox.setSelected(false);
                    allRandModeCheckBox.setSelected(true);
                    return 2;
                }
            }
            case 3 ->{
                if(!conModeCheckBox.isSelected()){
                    conModeCheckBox.setSelected(false);
                }
                else{
                    randWeightModeCheckBox.setSelected(false);
                    allRandModeCheckBox.setSelected(false);
                    conModeCheckBox.setSelected(true);
                    return 3;
                }
            }
        }
        return 0;
    }
    static ClickableCircle[] drawGraph(AnchorPane mainPane, Graph graph, int a, int b,
                          TextArea standardOutput, CheckBox colorCheck, CheckBox onlyPath){
        Rectangle background = new Rectangle();
        background.setFill(Color.valueOf("#616161"));
        background.setX(graph.graphDisplayStartPositionX);
        background.setY(graph.graphDisplayStartPositionY);
        background.setHeight(graph.graphDisplayHeight);
        background.setWidth(graph.graphDisplayWidth);
        mainPane.getChildren().add(background);
        int vertexToVertexWidth = graph.graphDisplayWidth / (graph.getColumns() + 1);
        int vertexToVertexHeight = graph.graphDisplayHeight / (graph.getRows() + 1);
        ClickableCircle[] vertex = new ClickableCircle[graph.getRows() * graph.getColumns()];
        Line[] connection = new Line[graph.getRows() * graph.getColumns() * graph.directions];
        Polygon[] arrow = new Polygon[graph.getRows() * graph.getColumns() * graph.directions * 2];
        for(int i = 0; i<graph.getRows() * graph.getColumns(); i+=graph.getColumns()){
            for(int j = 0; j<graph.getColumns(); j++) {
                vertex[i+j] = new ClickableCircle();
                vertex[i+j].setCenterX(14 + (j + 1) * vertexToVertexWidth);
                vertex[i+j].setCenterY(15 + (((i/graph.getColumns()) + 1) * vertexToVertexHeight));
                if(vertexToVertexHeight > vertexToVertexWidth)
                    vertex[i+j].setRadius((vertexToVertexWidth-1)/4);
                else
                    vertex[i+j].setRadius((vertexToVertexHeight-1)/4);
                vertex[i+j].setFill(BLACK);
                vertex[i+j].setStroke(BLACK);
                vertex[i+j].setStrokeWidth(vertex[i+j].getRadius()/6);

                mainPane.getChildren().add(vertex[i+j]);
            }
        }

        double arrowWidth = 0.5;
        double arrowLength = 1.9;
        double arrowDistance = 0.8;

        if(!onlyPath.isSelected()) {
            for (int i = 0; i < graph.getRows() * graph.getColumns(); i += graph.getColumns()) {
                for (int j = 0; j < graph.getColumns(); j++) {
                    for (int k = 0; k < graph.directions; k++) {
                        if (graph.getVertex(i + j, k) != graph.noConnection) {
                            connection[i + j] = new Line();
                            connection[i + j].setStrokeWidth(vertex[i + j].getRadius() / 8);
                            connection[i + j].setStroke(Color.BLACK);
                            switch (k) {
                                case 0 -> {
                                    connection[i + j].setStartX(vertex[i + j].getCenterX() + vertex[i + j].getRadius());
                                    connection[i + j].setStartY(vertex[i + j].getCenterY());
                                    connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius());
                                    connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                                }
                                case 1 -> {
                                    connection[i + j].setStartX(vertex[i + j].getCenterX());
                                    connection[i + j].setStartY(vertex[i + j].getCenterY() + vertex[i + j].getRadius());
                                    connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX());
                                    connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius());
                                }
                                case 2 -> {
                                    connection[i + j].setStartX(vertex[i + j].getCenterX() - vertex[i + j].getRadius());
                                    connection[i + j].setStartY(vertex[i + j].getCenterY());
                                    connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius());
                                    connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                                }
                                case 3 -> {
                                    connection[i + j].setStartX(vertex[i + j].getCenterX());
                                    connection[i + j].setStartY(vertex[i + j].getCenterY() - vertex[i + j].getRadius());
                                    connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX());
                                    connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius());
                                }
                            }
                            mainPane.getChildren().add(connection[i + j]);
                        }
                    }
                }
            }
        }
        if(!onlyPath.isSelected()) {
            for (int i = 0; i < graph.getRows() * graph.getColumns(); i += graph.getColumns()) {
                for (int j = 0; j < graph.getColumns(); j++) {
                    for (int k = 0; k < graph.directions; k++) {
                        if (graph.getVertex(i + j, k) != graph.noConnection) {
                            arrow[i + j] = new Polygon();
                            switch (k) {
                                case 0 -> arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() - arrowDistance * vertex[i + j].getRadius(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                                );
                                case 1 -> arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - arrowDistance * vertex[i + j].getRadius(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength

                                );
                                case 2 -> arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() + arrowDistance * vertex[i + j].getRadius(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                                );
                                case 3 -> arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + arrowDistance * vertex[i + j].getRadius(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength

                                );
                            }
                            if (colorCheck.isSelected())
                                arrow[i + j].setFill((Color.valueOf(graph.getColorOfWeight(i + j, k))));
                            else
                                arrow[i + j].setFill(WHITE);
                            mainPane.getChildren().add(arrow[i + j]);
                        }
                    }
                }
            }
        }

        if(a == -1 || b == -1){
            return vertex;
        }

        LinkedList<Integer> path;
        if(Dijkstra.dijkstra(graph, a, b)!=null) {
            path = Dijkstra.dijkstra(graph, a, b);
            assert path != null;
            standardOutput.appendText("Znaleziona sciezka " + path + "\n");
            Line[] pathLines = new Line[path.size()];
            Text startText = new Text("Start");
            startText.setFill(RED);
            startText.setX(vertex[path.get(0)].getCenterX());
            startText.setY(vertex[path.get(0)].getCenterY() - vertex[0].getRadius()/2);
            startText.setFont(Font.font(vertex[0].getRadius()*1.5));
            for(int i = 0; i<path.size()-1; i++) {
                pathLines[i] = new Line();
                pathLines[i].setStartX(vertex[path.get(i)].getCenterX());
                pathLines[i].setStartY(vertex[path.get(i)].getCenterY());
                pathLines[i].setEndX(vertex[path.get(i+1)].getCenterX());
                pathLines[i].setEndY(vertex[path.get(i+1)].getCenterY());
                pathLines[i].setStroke(RED);
                pathLines[i].setStrokeWidth(vertex[i].getRadius() / 4);
                mainPane.getChildren().add(pathLines[i]);
            }
            mainPane.getChildren().add(startText);
            int tmp2 = path.removeFirst();
            double tmpLength;
            StringBuilder withWeightsCommunicate;
            withWeightsCommunicate = new StringBuilder(String.valueOf(tmp2));
            while (!path.isEmpty()) {
                tmpLength = Dijkstra.getPathLength(path.peekFirst()) - Dijkstra.getPathLength(tmp2);
                tmp2 = path.removeFirst();
                withWeightsCommunicate.append("-(").append(String.format("%.03f", tmpLength)).append(")->").append(tmp2);
            }
            standardOutput.appendText("Z wagami: " + withWeightsCommunicate + "\n");

        }
        else
            standardOutput.appendText("Sciezka nie istnieje!" + "\n");
        return vertex;
    }
}
