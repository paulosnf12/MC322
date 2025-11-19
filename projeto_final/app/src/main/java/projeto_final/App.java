// Em: src/main/java/projeto_final/App.java
package projeto_final;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        this.game = new Game();
        primaryStage.setTitle("Lights Out");
        mostrarMenuPrincipal();
        primaryStage.show();
    }

    private void mostrarMenuPrincipal() {
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        Button btnNovoJogo = new Button("Iniciar Novo Jogo");
        btnNovoJogo.setOnAction(event -> {
            // 1. Exibe instruções antes de começar
            exibirInstrucoes(() -> {
                // 2. Só inicia o jogo após fechar o pop-up
                game.iniciarNovoJogo();
                mostrarPainelJogo();
            });
        });

        menuLayout.getChildren().add(btnNovoJogo);
        primaryStage.setScene(new Scene(menuLayout, 300, 250));
    }

    // Implementação do Pop-up de Instruções [cite: 11, 12, 13]
    private void exibirInstrucoes(Runnable aoFechar) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Como Jogar");
        alert.setHeaderText("Instruções do Lights Out");
        alert.setContentText(
            "1. O objetivo é apagar todas as luzes do tabuleiro.\n" +
            "2. Clicar em uma célula inverte seu estado e o das adjacentes.\n" +
            "3. Tente vencer com o menor número de movimentos!"
        );
        
        // Configura o que acontece quando o usuário fecha o alerta
        alert.setOnHidden(evt -> aoFechar.run());
        alert.show();
    }

    private void mostrarPainelJogo() {
        PainelJogo painel = new PainelJogo(game);
        // Ajustei o tamanho da janela para caber o novo painel
        primaryStage.setScene(new Scene(painel, 400, 450)); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}