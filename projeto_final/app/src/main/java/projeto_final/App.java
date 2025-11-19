// Em: src/main/java/projeto_final/App.java
package projeto_final;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projeto_final.controller.Game;
import projeto_final.view.PainelJogo;

public class App extends Application {

    private Game game;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.game = new Game(); // Cria o controlador principal

        primaryStage.setTitle("Lights Out");
        mostrarMenuPrincipal(); // Começamos pelo menu
        primaryStage.show();
    }

    private void mostrarMenuPrincipal() {
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        Button btnNovoJogo = new Button("Iniciar Novo Jogo (Fácil)");
        btnNovoJogo.setOnAction(event -> {
            // Quando o botão for clicado, o jogo começa!
            game.iniciarNovoJogo();
            mostrarPainelJogo();
        });

        menuLayout.getChildren().add(btnNovoJogo);
        primaryStage.setScene(new Scene(menuLayout, 300, 250));
    }

    private void mostrarPainelJogo() {
        PainelJogo painel = new PainelJogo(game);
        // Colocamos o painel do jogo na cena principal
        primaryStage.setScene(new Scene(painel));
    }

    public static void main(String[] args) {
        launch(args);
    }
}