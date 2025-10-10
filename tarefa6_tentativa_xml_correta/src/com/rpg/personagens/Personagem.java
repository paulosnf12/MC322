// src/com/rpg/personagens/Personagem.java
package com.rpg.personagens;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses

/**
 * Classe abstrata base para todas as entidades vivas do jogo, como heróis e monstros.
 * Implementa a interface {@link Combatente} e define os atributos fundamentais:
 * nome, pontos de vida, força e agilidade.
 * Fornece implementações padrão para os métodos da interface {@link Combatente}
 * que podem ser sobrescritos por subclasses mais específicas.
 */
// JAXB: Indica que Personagem pode ter subclasses Heroi e Monstro
@XmlSeeAlso({Heroi.class, Monstro.class})
public abstract class Personagem implements Combatente {
    @XmlElement // JAXB: Campo nome será serializado como um elemento XML
    protected String nome;
    @XmlElement
    protected int pontosDeVida;
    @XmlElement
    protected int forca;
    @XmlElement
    protected int agilidade; // influencia na chance de acerto
    @XmlElement
    protected Arma arma; // arma equipada
    @XmlElement
    protected boolean proximoAtaqueEhCritico = false;
    @XmlElement
    protected int mana = 0;
    @XmlElement
    protected double sorte = 0.0;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Personagem() {
        // Inicializações padrão ou vazias, já que os campos serão preenchidos pelo JAXB
        this.arma = null;
    }

    // construtor
    public Personagem(String nome, int pontosDeVida, int forca, int agilidade) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.agilidade = agilidade;
        this.arma = null; // Inicialmente sem arma equipada, pode ser ajustado em subclasses
    }

    /**
     * JAXB: Método para reinicializar quaisquer campos transient ou dependentes
     * após a deserialização. Implementação padrão vazia.
     */
    public void initializeTransientFields() {
        // Nada a fazer por padrão, subclasses podem sobrescrever
    }

    // metodos interface Combatente
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public boolean estaVivo() {
        return this.pontosDeVida > 0;
    }

    @Override
    public void receberDano(int dano) {
        this.pontosDeVida -= dano;
        if (this.pontosDeVida < 0) {
            this.pontosDeVida = 0;
        }
    }

    @Override
    public void receberCura(int cura) {
        this.pontosDeVida += cura;
    }

    public int getpontosdevida() {
        return pontosDeVida;
    }

    public String exibirStatus() {
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "\nForca = " + forca + ", Agilidade = " + agilidade;
    }

    @Override
    public int getAgilidade() {
        return agilidade;
    }

    @Override
    public Arma getArma() {
        return arma;
    }

    @Override
    public abstract AcaoDeCombate escolherAcao(Combatente alvo);

    // --- Implementações padrão para os novos métodos da interface Combatente ---
    @Override
    public int getForca() { return forca; }

    @Override
    public int getMana() { return mana; }

    @Override
    public void usarMana(int custo) { /* Apenas heróis usam mana efetivamente, monstros não */ }

    @Override
    public double getSorte() { return sorte; }

    @Override
    public int getCarisma() { return 0; } // Valor padrão para personagens sem carisma

    @Override
    public int getTristeza() { return 0; } // Valor padrão para personagens sem tristeza

    @Override
    public int getBrilho() { return 0; } // Valor padrão para personagens sem brilho

    @Override
    public int getDanoArmaBase() { return 0; } // Valor padrão para personagens sem uma "arma base"

    @Override
    public String getTipoDeArmaBase() { return null; } // Valor padrão para personagens sem uma "arma base"

    @Override
    public double getChanceDeRoubo() { return 0.0; } // Valor padrão para personagens sem chance de roubo

    @Override
    public boolean isProximoAtaqueCritico() { return proximoAtaqueEhCritico; }

    @Override
    public void setProximoAtaqueCritico(boolean isCritico) {
        this.proximoAtaqueEhCritico = isCritico;
    }
}