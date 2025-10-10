package com.rpg.combate;
import com.rpg.itens.Arma;
/**
 * Representa um combatente em um sistema de combate, seja ele um herói ou um monstro.
 * Define as ações básicas que qualquer combatente deve ser capaz de realizar.
 * E expõe todos os atributos necessários para cálculos de combate.
 */

public interface Combatente {

       /**
     * Retorna o nome do combatente.
     * @return O nome do combatente.
     */
    String getNome();

       /**
     * Verifica se o combatente está vivo (pontos de vida > 0).
     * @return {@code true} se o combatente estiver vivo, {@code false} caso contrário.
     */
     boolean estaVivo();
    
     /**
     * Causa dano a este combatente, reduzindo seus pontos de vida.
     *
     * @param dano A quantidade de dano a ser recebida.
     */
    void receberDano(int dano);
    
    /**
     * Aumenta os pontos de vida deste combatente.
     *
     * @param cura A quantidade de cura a ser recebida.
     */
    void receberCura(int cura);
    
    /**
     * Determina e retorna a próxima ação que o combatente irá executar.
     * A lógica pode variar, desde uma escolha aleatória para monstros até uma
     * decisão estratégica para heróis.
     *
     * @param alvo O combatente que será o alvo da ação.
     * @return A ação de combate a ser executada.
     */
    AcaoDeCombate escolherAcao(Combatente alvo);

    // --- MÉTODOS PARA SUPORTAR AÇÕES DE COMBATE GENÉRICAS ---
    
    /**
     * Retorna o valor de força do combatente.
     * @return O valor de força.
     */
    int getForca();

    /**
     * Retorna o valor de agilidade do combatente, que influencia na chance de acerto/esquiva.
     * @return O valor de agilidade.
     */
    int getAgilidade();
    
    /**
     * Retorna a arma atualmente equipada pelo combatente.
     * @return A {@link Arma} equipada, ou {@code null} se nenhuma arma estiver equipada.
     */
    Arma getArma(); // Retorna a arma equipada, pode ser null

    // Atributos específicos de heróis

    /**
     * Retorna a quantidade de mana atual do combatente.
     * @return A quantidade de mana.
     */
    int getMana();

    /**
     * Diminui a mana do combatente pela quantidade especificada.
     * @param custo O custo de mana da ação/habilidade.
     */
    void usarMana(int custo);

    /**
     * Retorna o valor de sorte do combatente (entre 0.0 e 1.0).
     * @return O valor de sorte.
     */
    double getSorte();

    /**
     * Retorna o valor de carisma do combatente. Aplicável principalmente a heróis como Paladinos.
     * @return O valor de carisma.
     */
    int getCarisma();

    // Atributos específicos de monstros

    /**
     * Retorna o valor de tristeza do combatente. Aplicável a monstros do tipo Espírito.
     * @return O valor de tristeza.
     */
    int getTristeza(); // Específico de Espírito

    /**
     * Retorna o valor de brilho do combatente. Aplicável a monstros do tipo Vampiro.
     * @return O valor de brilho.
     */
    int getBrilho();   // Específico de Vampiro

    /**
     * Retorna o dano base da arma inerente do combatente. Aplicável a monstros como Goblins.
     * @return O dano base da arma.
     */
    int getDanoArmaBase(); // Dano base da arma do Goblin (se tiver arma fixa)

    /**
     * Retorna o tipo da arma inerente do combatente. Aplicável a monstros como Goblins.
     * @return O tipo da arma (ex: "Adaga", "Clava").
     */
    String getTipoDeArmaBase(); // Tipo da arma do Goblin

    /**
     * Retorna a chance de roubo de vida do combatente (entre 0.0 e 1.0). Aplicável a monstros como Goblins.
     * @return A chance de roubo.
     */
    double getChanceDeRoubo(); // Específico de Goblin

    // Para lógica de crítico (compartilhado entre Herói e Monstro)

    /**
     * Verifica se o próximo ataque do combatente está sinalizado para ser crítico.
     * @return {@code true} se o próximo ataque for crítico, {@code false} caso contrário.
     */
    boolean isProximoAtaqueCritico();

    /**
     * Define se o próximo ataque do combatente será crítico.
     * @param isCritico {@code true} para sinalizar um ataque crítico, {@code false} caso contrário.
     */
    void setProximoAtaqueCritico(boolean isCritico);
}
