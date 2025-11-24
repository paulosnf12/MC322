package projeto_final.fixtures;

import projeto_final.model.DificuldadeFacil;
import projeto_final.model.DificuldadeMedio;
import projeto_final.model.DificuldadeDificil;
import projeto_final.abstracts.Dificuldade;

/**
 * Classe auxiliar para criar fixtures de Dificuldade para uso em testes unitários.
 * <p>
 * Esta classe fornece métodos estáticos para criar instâncias de diferentes
 * níveis de dificuldade de forma conveniente.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class DificuldadeFixture {
    
    /**
     * Cria uma instância de DificuldadeFacil.
     * 
     * @return DificuldadeFacil
     */
    public static Dificuldade criarFacil() {
        return new DificuldadeFacil();
    }
    
    /**
     * Cria uma instância de DificuldadeMedio.
     * 
     * @return DificuldadeMedio
     */
    public static Dificuldade criarMedio() {
        return new DificuldadeMedio();
    }
    
    /**
     * Cria uma instância de DificuldadeDificil.
     * 
     * @return DificuldadeDificil
     */
    public static Dificuldade criarDificil() {
        return new DificuldadeDificil();
    }
}

