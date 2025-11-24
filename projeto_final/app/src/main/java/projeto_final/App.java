// Em: src/main/java/projeto_final/App.java
package projeto_final;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projeto_final.controller.Game;
import projeto_final.exceptions.DadosCorruptosException;
import projeto_final.view.MenuPrincipal;
import projeto_final.view.PainelJogo;

/**
 * Classe principal da aplicação Lights Out.
 * <p>
 * Esta classe estende {@code Application} do JavaFX e é responsável por
 * inicializar a aplicação, criar a janela principal, exibir o menu
 * inicial e gerenciar a transição entre diferentes telas do jogo.
 * </p>
 * <p>
 * A aplicação segue o padrão MVC, onde esta classe atua como ponto de
 * entrada e coordenador entre o controller ({@code Game}) e as views
 * ({@code PainelJogo}, etc.).
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.controller.Game
 * @see projeto_final.view.PainelJogo
 */
public class App extends Application {
    /** Instância do controlador do jogo */
    private Game game;
    
    /** Stage principal da aplicação JavaFX */
    private Stage primaryStage;

    /**
     * Método principal de inicialização da aplicação JavaFX.
     * <p>
     * Este método é chamado automaticamente pelo JavaFX quando a aplicação
     * é iniciada. Ele configura a janela principal e exibe o menu inicial.
     * </p>
     * 
     * @param primaryStage Stage principal fornecido pelo JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.game = new Game();
        primaryStage.setTitle("Lights Out");
        mostrarMenuPrincipal();
        primaryStage.show();
    }

    /**
     * Exibe o menu principal da aplicação.
     * <p>
     * Cria e configura a interface do menu com opções para iniciar
     * um novo jogo, carregar um jogo salvo ou sair.
     * </p>
     */
    private void mostrarMenuPrincipal() {
        MenuPrincipal menu = new MenuPrincipal();
        
        // Configura ação do botão Novo Jogo
        menu.getBtnNovoJogo().setOnAction(event -> {
            exibirInstrucoes(() -> {
                game.iniciarNovoJogo();
                mostrarPainelJogo();
            });
        });
        
        // Configura ação do botão Carregar Jogo
        menu.getBtnCarregarJogo().setOnAction(event -> {
            carregarJogo();
        });
        
        // Configura ação do botão Sair
        menu.getBtnSair().setOnAction(event -> {
            primaryStage.close();
        });
        
        // Botão de carregar jogo sempre está ativo
        // O usuário pode tentar carregar mesmo se não houver arquivo salvo
        
        primaryStage.setScene(new Scene(menu.getLayout(), 300, 300));
    }
    
    /**
     * Carrega um jogo salvo anteriormente.
     * <p>
     * Permite ao usuário selecionar um arquivo de jogo salvo ou usar
     * o arquivo padrão. Exibe mensagens de erro apropriadas se houver
     * problemas ao carregar.
     * </p>
     */
    private void carregarJogo() {
        try {
            // Primeiro tenta carregar o arquivo padrão
            String arquivoPadrao = "save/jogo.sav";
            
            // Verifica se o arquivo padrão existe
            if (!new File(arquivoPadrao).exists()) {
                // Se não existir, permite escolher outro arquivo
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Carregar Jogo Salvo");
                fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Arquivos de Jogo", "*.sav")
                );
                fileChooser.setInitialDirectory(new File("save"));
                
                File arquivoSelecionado = fileChooser.showOpenDialog(primaryStage);
                if (arquivoSelecionado != null) {
                    arquivoPadrao = arquivoSelecionado.getAbsolutePath();
                } else {
                    return; // Usuário cancelou
                }
            }
            
            // Carrega o jogo
            game.carregarJogo(arquivoPadrao);
            
            // Verifica se o jogo foi carregado com sucesso
            if (game.getTabuleiro() != null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Jogo Carregado");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("O jogo foi carregado com sucesso.");
                alert.showAndWait();
                
                mostrarPainelJogo();
            } else {
                throw new DadosCorruptosException("Jogo carregado está vazio ou inválido");
            }
            
        } catch (DadosCorruptosException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao Carregar");
            alert.setHeaderText("Dados Corrompidos");
            alert.setContentText("Não foi possível carregar o jogo salvo.\n" +
                               "O arquivo pode estar corrompido ou em formato inválido.\n\n" +
                               "Detalhes: " + e.getMessage());
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao Carregar");
            alert.setHeaderText("Erro ao Carregar Jogo");
            alert.setContentText("Ocorreu um erro ao tentar carregar o jogo.\n\n" +
                               "Detalhes: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao Carregar");
            alert.setHeaderText("Erro Inesperado");
            alert.setContentText("Ocorreu um erro inesperado ao carregar o jogo.\n\n" +
                               "Detalhes: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Exibe um diálogo com as instruções do jogo.
     * <p>
     * Mostra um alerta informativo explicando as regras do Lights Out.
     * Após o usuário fechar o diálogo, executa a ação especificada.
     * </p>
     * 
     * @param aoFechar Ação a ser executada quando o diálogo for fechado
     */
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

    /**
     * Exibe o painel principal do jogo.
     * <p>
     * Cria uma nova instância de {@code PainelJogo} e a exibe na
     * janela principal, substituindo o menu.
     * </p>
     */
    private void mostrarPainelJogo() {
        PainelJogo painel = new PainelJogo(game);
        
        // Configura callback para voltar ao menu
        painel.setCallbackVoltarMenu(() -> {
            mostrarMenuPrincipal();
        });
        
        // Ajustei o tamanho da janela para caber o novo painel
        primaryStage.setScene(new Scene(painel, 400, 500)); 
    }

    /**
     * Método principal de entrada da aplicação.
     * <p>
     * Inicia a aplicação JavaFX chamando o método {@code launch()}.
     * </p>
     * 
     * @param args Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        launch(args);
    }
}