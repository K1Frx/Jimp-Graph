module graph.jimpgraph {
    requires javafx.controls;
    requires javafx.fxml;


    opens graph.jimpgraph to javafx.fxml;
    exports graph.jimpgraph;
}