// Vampiro.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueVampirico;
import com.rpg.itens.ArmaDropSpec; // Importa a nova classe ArmaDropSpec
import com.rpg.personagens.Monstro;
import java.util.ArrayList;

/**
 * Representa um monstro do tipo Vampiro no jogo.
 * Vampiros possuem um atributo único chamado "brilho",
 * que pode influenciar suas ações e interações no jogo.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueVampirico}.
 */
public class Vampiro extends Monstro {
    /**
     * O nível de brilho do Vampiro, um atributo único que afeta seu ataque.
     */
    private int brilho; // atributo único

    /**
     * Instância compartilhada da ação de combate AtaqueVampirico.
     * Todos os objetos Vampiro utilizarão esta mesma instância para seus ataques,
     * demonstrando o conceito de agregação compartilhada.
     */
    private static final AtaqueVampirico ATAQUE_VAMPIRICO_INSTANCE = new AtaqueVampirico();
    
    /**
     * Construtor da classe Vampiro.
     *
     * @param nome O nome do Vampiro.
     * @param pontosDeVida Os pontos de vida iniciais do Vampiro.
     * @param forca A força inicial do Vampiro.
     * @param agilidade A agilidade inicial do Vampiro.
     * @param xpConcedido A quantidade de experiência que o Vampiro concede.
     * @param brilho O nível de brilho inicial do Vampiro.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec}
     *                               que o Vampiro pode dropar.
     * @param nivelFase O nível da fase em que o Vampiro está, usado para escalonamento de loot.
     */
    public Vampiro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido,
                   int brilho, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int nivelFase) {
        // Passa a lista de especificações de drop e o nível da fase para o construtor da superclasse Monstro
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar, nivelFase);
        this.brilho = brilho;
    }

    /**
     * Inicializa as ações de combate específicas do Vampiro.
     * Adiciona a instância compartilhada de {@link AtaqueVampirico} à lista de ações.
     */
    @Override
    protected void inicializarAcoes() {
        this.acoes.add(ATAQUE_VAMPIRICO_INSTANCE);
    }
    
    /**
     * Apresenta um diálogo especial se o Vampiro for o "Edward Cullen".
     */
    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Edward Cullen")) {
            System.out.println("Edward Cullen: \"Eu sou um monstro, Bella! Minha pele brilha. Essa é a pele de um assassino!\"");
            System.out.println();
        }
    }

    /**
     * Retorna o nível de brilho do Vampiro.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return O nível de brilho.
     */
    @Override
    public int getBrilho() {
        return brilho;
    }

    /**
     * Gera uma representação textual do status atual do Vampiro,
     * incluindo informações da superclasse e seu nível de brilho.
     * @return Uma String com as informações do status do Vampiro.
     */
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Brilho = " + brilho;
    }
}