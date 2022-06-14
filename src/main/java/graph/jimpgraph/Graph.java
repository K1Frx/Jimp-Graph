package graph.jimpgraph;
public class Graph {

    final int noConnection = -1;
    final int directions = 4;
    private int rows;
    private int columns;
    private int mode;
    private double min;
    private double max;
    final int graphDisplayWidth = 400;
    final int graphDisplayHeight = 376;
    final int graphDisplayStartPositionX = 14;
    final int graphDisplayStartPositionY = 15;
    final int differentColors = 5;
    final int defaultGraphSize = 15;
    final double defaultMinimum = 0;
    final double defaultMaximum = 1;
    private int rowsN = defaultGraphSize, colsN = defaultGraphSize;
    private double minN = defaultMinimum, maxN = defaultMaximum;


    private int[][] vertex;
    private double[][] weight;

    int getRowsN(){
        return rowsN;
    }
    int getColumnsN(){
        return colsN;
    }
    double getMinWeightN(){
        return minN;
    }
    double getMaxWeightN(){ return maxN; }
    void setRowsN(int a){ rowsN = a;}
    void setColumnsN(int a){
        colsN = a;
    }
    void setMinWeightN(double a){ minN = a; }
    void setMaxWeightN(double a){ maxN = a; }

    int getVertex(int a, int b){
        return vertex[a][b];
    }
    double getWeight(int a, int b) {
        return weight[a][b];
    }

    void setVertex(int a, int b, int c){
        vertex[a][b] = c;
    }
    void setWeight(int a, int b, double c){
        weight[a][b] = c;
    }
    int getRows(){
        return rows;
    }
    int getColumns(){
       return columns;
    }
    double getMinWeight(){
       return min;
    }
    double getMaxWeight(){
       return max;
    }
    String getColorOfWeight(int a, int b){
        double diffrence = (getMaxWeight() - getMinWeight()) / differentColors;
        if(getWeight(a,b) < diffrence)
            return "WHITE";
        if(getWeight(a,b) < diffrence*2)
            return "#bee1ff";
        if(getWeight(a,b) < diffrence*3)
            return "#81c6ff";
        if(getWeight(a,b) < diffrence*4)
            return "#42aaff";
        else
            return "#0090ff";
    }
    int getMode(){
        return mode;
    }

    void initializeGraph(int r, int c, double minG, double maxG, int m){
        rowsN = r;
        colsN = c;
        minN = minG;
        maxN = maxG;
        rows = r;
        columns = c;
        min = minG;
        max = maxG;
        mode = m;
        vertex = new int[rows*columns][directions];
        weight = new double[rows*columns][directions];
        for(int i=0; i<rows*columns; i++){
            for(int j=0; j<directions; j++){
                vertex[i][j] = noConnection;
            }
        }
    }
}
