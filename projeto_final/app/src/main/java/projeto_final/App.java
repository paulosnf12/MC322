package projeto_final;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class App extends Application {

    public String getGreeting() {
        return "Hello World!";
    }

    @Override  
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My JavaFX Application");

        Button btn = new Button("Click Me");
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // obrigatorio para JavaFX
    }
}
