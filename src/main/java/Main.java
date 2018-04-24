import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.retina.client.FullClient;
import io.cortical.retina.client.LiteClient;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.rest.ApiException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Main extends Application{

    private static String apiKey = "your key here";

    private static List<String> retrieveTermsDemo(String searchTerm) throws JsonProcessingException, ApiException {
        LiteClient lite = new io.cortical.retina.client.LiteClient(apiKey);
        List<String> terms = lite.getSimilarTerms(searchTerm);
        return terms;
    }

    private static ByteArrayInputStream retrieveExpressionImage(String searchTerm) throws JsonProcessingException, ApiException {
        FullClient fullClient = new FullClient(apiKey);
        ByteArrayInputStream image = fullClient.getImage(new Term(searchTerm));
        return image;
    }

    public static void main(String[] args) throws JsonProcessingException, ApiException {
        launch(args); // initiates JavaFX
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Demo of semantically similar terms
//        List<String> similarTerms = retrieveTermsDemo("Thought");
//        System.out.println(similarTerms);

        FullClient retinaClient = new io.cortical.retina.client.FullClient(apiKey);
        List<Retina> availableRetinas = retinaClient.getRetinas();
        System.out.println("Retina information from Cortical.io\'s API:\n--");
        for (Retina retina : availableRetinas) {
            System.out.println("Name: " + retina.getRetinaName());
            System.out.println("Description: " + retina.getRetinaDescription());
            System.out.println("Number of terms: " + retina.getNumberOfTermsInRetina());
            System.out.println("SDR matrix dimensions: " + retina.getNumberOfRows() + "rows x " + retina.getNumberOfColumns() + "columns." );
            System.out.println("..");
        }

//         Retrieving image visualisation of a search term
        String searchTerm = "Synapse";
        ByteArrayInputStream imageBytes = retrieveExpressionImage(searchTerm);
        Image imagePattern = new Image(imageBytes);
        ImageView imagePatternView = new ImageView(imagePattern);
        imagePatternView.setFitHeight(500);
        imagePatternView.setFitWidth(500);
        imagePatternView.setPreserveRatio(true);

        // Creating layout
        StackPane pane = new StackPane();
        pane.getChildren().add(imagePatternView);

        Scene scene = new Scene(pane, 500,500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Cortical.io encoded image pattern: " + searchTerm);
        primaryStage.show();


    }
}