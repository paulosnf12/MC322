// Personagem.java
package com.rpg.personagens; // Pacote correto para a classe base de personagens

// Importa as classes necessárias dos novos pacotes
import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma; // Personagem tem uma Arma

/**
 * Classe abstrata base para todas as entidades vivas do jogo, como heróis e monstros.
 * Implementa a interface {@link Combatente} e define os atributos fundamentais:
 * nome, pontos de vida, força e agilidade.
 */

public abstract class Personagem implements Combatente {
    protected String nome;
    protected int pontosDeVida;
    protected int forca;
    protected int agilidade; // influencia na chance de acerto
    protected Arma arma; // **NOVO: arma equipada**

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


    @Override
    public abstract AcaoDeCombate escolherAcao(Combatente alvo);

    // metodos especificos da classe Personagem
    public void setpontosdevida(int pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }

    /**
     * Retorna os pontos de vida atuais do personagem.
     * @return Os pontos de vida do personagem.
     */
    public int getpontosdevida() {
        return pontosDeVida;
    }

    /**
     * Retorna a força do personagem.
     * @return A força do personagem.   
     */
    public int getForca() {
        return forca;
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

    // O método setArma() foi removido/substituído por equiparArma() em Heroi para controle de nível
    // public void setArma(Arma arma) {
    //     this.arma = arma;
    // }
}