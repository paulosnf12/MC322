// src/com/rpg/personagens/Personagem.java
package com.rpg.personagens; // Pacote correto para a classe base de personagens

// Importa as classes necessárias dos novos pacotes
import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma; // Personagem tem uma Arma

/**
 * Classe abstrata base para todas as entidades vivas do jogo, como heróis e monstros.
 * Implementa a interface {@link Combatente} e define os atributos fundamentais:
 * nome, pontos de vida, força e agilidade.
 * Fornece implementações padrão para os métodos da interface {@link Combatente}
 * que podem ser sobrescritos por subclasses mais específicas.
 */
public abstract class Personagem implements Combatente {
    protected String nome;
    protected int pontosDeVida;
    protected int forca;
    protected int agilidade; // influencia na chance de acerto
    protected Arma arma; // arma equipada

    /**
     * Sinaliza se o próximo ataque do personagem será crítico.
     * Este atributo é usado para comunicar o resultado de uma rolagem de crítico
     * para a {@link AcaoDeCombate} correspondente.
     */
    protected boolean proximoAtaqueEhCritico = false;

    /**
     * A quantidade de mana do personagem. Inicialmente 0 para personagens genéricos,
     * mas sobrescrito em subclasses de heróis.
     */
    protected int mana = 0;

    /**
     * O valor de sorte do personagem (entre 0.0 e 1.0). Inicialmente 0.0 para personagens genéricos,
     * mas sobrescrito em subclasses de heróis.
     */
    protected double sorte = 0.0;
    
    // construtor
    public Personagem(String nome, int pontosDeVida, int forca, int agilidade) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.agilidade = agilidade;
        this.arma = null; // Inicialmente sem arma equipada, pode ser ajustado em subclasses
    }

    // metodos interface Combatente

    /**
     * Retorna o nome do personagem.
     * @return O nome do personagem.
     */
    @Override
    public String getNome() {
        return this.nome;
    }

    /**
     * Verifica se o personagem está vivo (pontos de vida > 0).
     * @return true se o personagem estiver vivo, false caso contrário.
     */
    @Override
    public boolean estaVivo() {
        return this.pontosDeVida > 0;
    }

    /**
     * Reduz os pontos de vida do personagem ao receber dano.
     * Garante que os pontos de vida não fiquem negativos.
     * @param dano A quantidade de dano a ser subtraída dos pontos de vida.
     */
    @Override
    public void receberDano(int dano) {
        this.pontosDeVida -= dano;
        if (this.pontosDeVida < 0) {
            this.pontosDeVida = 0;
        }
    }

    /**
    * Aumenta os pontos de vida do personagem ao receber cura.
    * @param cura A quantidade de pontos de vida a ser adicionada.
    */
    @Override
    public void receberCura(int cura) {
        this.pontosDeVida += cura;
    }

    /**
     * Retorna os pontos de vida atuais do personagem.
     * @return Os pontos de vida do personagem.
     */
    public int getpontosdevida() {
        return pontosDeVida;
    }

    /**
     * Gera uma representação textual do status atual do personagem.
     * @return Uma String com informações de nome, vida, força e agilidade.
     */
    public String exibirStatus() {
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "\nForca = " + forca +
               ", Agilidade = " + agilidade;
    }

    /**
     * Retorna a agilidade do personagem. Atributo relacionado à chance de acerto do ataque.
     * @return A agilidade do personagem.
     */
    public int getAgilidade() {
        return agilidade;
    }

    /**
     * Retorna a arma atualmente equipada pelo personagem.
     * @return A arma equipada, ou null se nenhuma arma estiver equipada.
     */
    public Arma getArma() {
        return arma;
    }

    /**
     * Método abstrato para o personagem escolher a próxima ação de combate.
     * Deve ser implementado por subclasses (Heroi, Monstro).
     * @param alvo O combatente alvo da ação.
     * @return A {@link AcaoDeCombate} a ser executada.
     */
    @Override
    public abstract AcaoDeCombate escolherAcao(Combatente alvo);

    // --- Implementações padrão para os novos métodos da interface Combatente ---
    // Estes métodos fornecem valores padrão para personagens genéricos e são
    // esperados para serem sobrescritos por subclasses mais específicas (Heroi, Elfo, Goblin, etc.).

    /**
     * Retorna o valor de força do personagem.
     * @return O valor de força.
     */
    @Override
    public int getForca() { return forca; }

    /**
     * Retorna a quantidade de mana atual do personagem.
     * Por padrão, personagens genéricos não possuem mana (retorna 0).
     * @return A quantidade de mana.
     */
    @Override
    public int getMana() { return mana; }

    /**
     * Diminui a mana do personagem pela quantidade especificada.
     * Por padrão, esta implementação não faz nada, pois personagens genéricos
     * não gerenciam mana. Heróis que usam mana devem sobrescrever este método.
     * @param custo O custo de mana da ação/habilidade.
     */
    @Override
    public void usarMana(int custo) { /* Apenas heróis usam mana efetivamente, monstros não */ }

    /**
     * Retorna o valor de sorte do personagem (entre 0.0 e 1.0).
     * Por padrão, personagens genéricos têm sorte 0.0. Heróis devem sobrescrever.
     * @return O valor de sorte.
     */
    @Override
    public double getSorte() { return sorte; }

    /**
     * Retorna o valor de carisma do personagem.
     * Por padrão, personagens genéricos têm carisma 0. Heróis como Paladinos devem sobrescrever.
     * @return O valor de carisma.
     */
    @Override
    public int getCarisma() { return 0; } // Valor padrão para personagens sem carisma

    /**
     * Retorna o valor de tristeza do personagem.
     * Por padrão, personagens genéricos não têm tristeza (retorna 0). Monstros do tipo Espírito devem sobrescrever.
     * @return O valor de tristeza.
     */
    @Override
    public int getTristeza() { return 0; } // Valor padrão para personagens sem tristeza

    /**
     * Retorna o valor de brilho do personagem.
     * Por padrão, personagens genéricos não têm brilho (retorna 0). Monstros do tipo Vampiro devem sobrescrever.
     * @return O valor de brilho.
     */
    @Override
    public int getBrilho() { return 0; } // Valor padrão para personagens sem brilho

    /**
     * Retorna o dano base da arma inerente do personagem.
     * Por padrão, personagens genéricos não têm uma arma base (retorna 0). Monstros como Goblins devem sobrescrever.
     * @return O dano base da arma.
     */
    @Override
    public int getDanoArmaBase() { return 0; } // Valor padrão para personagens sem uma "arma base"
    
    /**
     * Retorna o tipo da arma inerente do personagem.
     * Por padrão, personagens genéricos não têm um tipo de arma base (retorna null). Monstros como Goblins devem sobrescrever.
     * @return O tipo da arma (ex: "Adaga", "Clava").
     */
    @Override
    public String getTipoDeArmaBase() { return null; } // Valor padrão para personagens sem uma "arma base"

    /**
     * Retorna a chance de roubo de vida do personagem (entre 0.0 e 1.0).
     * Por padrão, personagens genéricos não têm chance de roubo (retorna 0.0). Monstros como Goblins devem sobrescrever.
     * @return A chance de roubo.
     */
    @Override
    public double getChanceDeRoubo() { return 0.0; } // Valor padrão para personagens sem chance de roubo

    /**
     * Verifica se o próximo ataque do personagem está sinalizado para ser crítico.
     * @return {@code true} se o próximo ataque for crítico, {@code false} caso contrário.
     */
    @Override
    public boolean isProximoAtaqueCritico() { return proximoAtaqueEhCritico; }

    /**
     * Define se o próximo ataque do personagem será crítico.
     * @param isCritico {@code true} para sinalizar um ataque crítico, {@code false} caso contrário.
     */
    @Override
    public void setProximoAtaqueCritico(boolean isCritico) { this.proximoAtaqueEhCritico = isCritico; }
}