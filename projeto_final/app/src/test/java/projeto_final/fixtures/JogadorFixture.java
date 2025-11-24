package projeto_final.fixtures;

import projeto_final.model.Jogador;
import projeto_final.abstracts.Dificuldade;
import projeto_final.model.DificuldadeFacil;
import projeto_final.model.DificuldadeMedio;
import projeto_final.model.DificuldadeDificil;

/**
 * Classe auxiliar para criar fixtures de Jogador para uso em testes unitários.
 * <p>
 * Esta classe fornece métodos estáticos para criar instâncias de Jogador
 * em diferentes estados (com pontuação, recordes, etc.).
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class JogadorFixture {
    
    /**
     * Cria um jogador com nome padrão.
     * 
     * @return Jogador com nome "JogadorTeste"
     */
    public static Jogador criarJogador() {
        return new Jogador("JogadorTeste");
    }
    
    /**
     * Cria um jogador com nome personalizado.
     * 
     * @param nome Nome do jogador
     * @return Jogador com o nome especificado
     */
    public static Jogador criarJogador(String nome) {
        return new Jogador(nome);
    }
    
    /**
     * Cria um jogador com pontuação total específica.
     * 
     * @param nome Nome do jogador
     * @param pontuacaoTotal Pontuação total a ser definida
     * @return Jogador com a pontuação especificada
     */
    public static Jogador criarJogadorComPontuacao(String nome, int pontuacaoTotal) {
        Jogador jogador = new Jogador(nome);
        // Adiciona pontuação através de partidas
        int partidas = pontuacaoTotal > 0 ? 1 : 0;
        for (int i = 0; i < partidas; i++) {
            jogador.adicionarPontuacao(pontuacaoTotal);
        }
        return jogador;
    }
    
    /**
     * Cria um jogador com recordes em todas as dificuldades.
     * 
     * @param nome Nome do jogador
     * @param recordeFacil Recorde na dificuldade fácil
     * @param recordeMedio Recorde na dificuldade média
     * @param recordeDificil Recorde na dificuldade difícil
     * @return Jogador com os recordes especificados
     */
    public static Jogador criarJogadorComRecordes(String nome, int recordeFacil, int recordeMedio, int recordeDificil) {
        Jogador jogador = new Jogador(nome);
        Dificuldade facil = new DificuldadeFacil();
        Dificuldade medio = new DificuldadeMedio();
        Dificuldade dificil = new DificuldadeDificil();
        
        jogador.atualizarRecorde(facil, recordeFacil);
        jogador.atualizarRecorde(medio, recordeMedio);
        jogador.atualizarRecorde(dificil, recordeDificil);
        
        return jogador;
    }
}

