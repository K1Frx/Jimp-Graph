package graph.jimpgraph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setResizable(false);
        stage.setTitle("JIMP projekt drugi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {

        /*if(args[0] != null ){
            if(args[0].equals("test")){
                System.out.println("Testy uruchomione...");
                Test.allTests();
            }
        }*/


        launch(args);
    }
}