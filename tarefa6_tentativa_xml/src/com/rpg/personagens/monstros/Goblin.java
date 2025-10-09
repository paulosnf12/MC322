// src/com/rpg/personagens/monstros/Goblin.java

package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueGoblin;
import com.rpg.itens.ArmaDropSpec; // Importa a nova classe ArmaDropSpec
import com.rpg.personagens.Monstro;
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um XML ou um elemento
import jakarta.xml.bind.annotation.XmlTransient; // ADICIONADO PARA JAXB: Para ignorar campos static final
import java.util.ArrayList;

/**
 * Representa um monstro do tipo Goblin no jogo.
 * Goblins possuem atributos específicos relacionados ao seu estilo de combate,
 * como o tipo de arma que utilizam, o dano causado por essa arma e a chance de roubo de vida.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueGoblin}.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class Goblin extends Monstro {
    /**
     * O tipo de arma que o Goblin utiliza (ex: "Adaga", "Clava").
     */
    protected String tipoDeArma;
    /**
     * O dano base da arma do Goblin, somado à sua força no cálculo de ataque.
     */
    protected int danoArma;
    /**
     * A probabilidade de o Goblin roubar vida do alvo durante um ataque.
     */
    protected double chanceDeRoubo;

    /**
     * Instância compartilhada da ação de combate AtaqueGoblin.
     * Todos os objetos Goblin utilizarão esta mesma instância para seus ataques,
     * demonstrando o conceito de agregação compartilhada.
     */
    @XmlTransient // ADICIONADO PARA JAXB: Campo estático não deve ser serializado
    private static final AtaqueGoblin ATAQUE_GOBLIN_INSTANCE = new AtaqueGoblin();

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Goblin() {
        super(); // Chama o construtor vazio da superclasse Monstro
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.tipoDeArma = "";
        this.danoArma = 0;
        this.chanceDeRoubo = 0.0;
        // As ações serão inicializadas pelo método inicializarAcoes() que é chamado no construtor de Monstro.
    }

    /**
     * Construtor da classe Goblin.
     *
     * @param nome O nome do Goblin.
     * @param pontosDeVida Os pontos de vida iniciais do Goblin.
     * @param forca A força inicial do Goblin.
     * @param agilidade A agilidade inicial do Goblin.
     * @param xpConcedido A quantidade de experiência que o Goblin concede.
     * @param tipoDeArma O tipo de arma inerente do Goblin.
     * @param danoArma O dano base da arma do Goblin.
     * @param chanceDeRoubo A probabilidade de roubo de vida do Goblin.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec}
     *                               que o Goblin pode dropar.
     * @param nivelFase O nível da fase em que o Goblin está, usado para escalonamento de loot.
     */
    public Goblin(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido,
                  String tipoDeArma, int danoArma, double chanceDeRoubo,
                  ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int nivelFase) {
        // Passa a lista de especificações de drop e o nível da fase para o construtor da superclasse Monstro
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar, nivelFase);
        this.tipoDeArma = tipoDeArma;
        this.danoArma = danoArma;
        this.chanceDeRoubo = chanceDeRoubo;
    }

    /**
     * Inicializa as ações de combate específicas do Goblin.
     * Adiciona a instância compartilhada de {@link AtaqueGoblin} à lista de ações.
     */
    @Override
    protected void inicializarAcoes() {
        // Adiciona a ação específica do Goblin
        this.acoes.add(ATAQUE_GOBLIN_INSTANCE);
    }

    /**
     * Apresenta um diálogo especial se o Goblin for o "Goblin Guerreiro".
     */
    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Goblin Guerreiro")) {
            System.out.println("Goblin Guerreiro: \"Hehehe! Você não vai passar por mim tão fácil, herói! Minha clava está sedenta por batalha!\"");
            System.out.println(); // Adiciona uma linha em branco
        }
    }

    /**
     * Gera uma representação textual do status atual do Goblin,
     * incluindo informações da superclasse, tipo de arma, dano da arma e chance de roubo.
     * @return Uma String com as informações do status do Goblin.
     */
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Arma: " + tipoDeArma + ", Dano Arma: " + danoArma + ", Chance de Roubo: " + String.format("%.2f", chanceDeRoubo);
    }

    /**
     * Retorna o dano base da arma inerente do Goblin.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return O dano base da arma.
     */
    @Override
    @XmlElement // ADICIONADO PARA JAXB
    public int getDanoArmaBase() {
        return danoArma;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setDanoArmaBase(int danoArma) {
        this.danoArma = danoArma;
    }


    /**
     * Retorna o tipo da arma inerente do Goblin.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return O tipo da arma.
     */
    @Override
    @XmlElement // ADICIONADO PARA JAXB
    public String getTipoDeArmaBase() {
        return tipoDeArma;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setTipoDeArmaBase(String tipoDeArma) {
        this.tipoDeArma = tipoDeArma;
    }

    /**
     * Retorna a chance de roubo de vida do Goblin.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return A chance de roubo.
     */
    @Override
    @XmlElement // ADICIONADO PARA JAXB
    public double getChanceDeRoubo() {
        return chanceDeRoubo;
    }
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setChanceDeRoubo(double chanceDeRoubo) {
        this.chanceDeRoubo = chanceDeRoubo;
    }
}