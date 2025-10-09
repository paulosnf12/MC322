// src/com/rpg/personagens/monstros/Espirito.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueAssombrado;
import com.rpg.itens.ArmaDropSpec; // Importar a nova classe ArmaDropSpec
import com.rpg.personagens.Monstro;
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um XML ou um elemento
import jakarta.xml.bind.annotation.XmlTransient; // ADICIONADO PARA JAXB: Para ignorar campos static final
import java.util.ArrayList;

/**
 * Representa um monstro do tipo Espírito no jogo.
 * Espíritos possuem um atributo único chamado "tristeza",
 * que pode influenciar suas ações e interações no jogo.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueAssombrado}.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class Espirito extends Monstro {
    /**
     * O nível de tristeza do Espírito, um atributo único que afeta seu ataque.
     */
    private int tristeza; // atributo único

    /**
     * Instância compartilhada da ação de combate AtaqueAssombrado.
     * Todos os objetos Espírito utilizarão esta mesma instância para seus ataques,
     * demonstrando o conceito de agregação compartilhada.
     */
    @XmlTransient // ADICIONADO PARA JAXB: Campo estático não deve ser serializado
    private static final AtaqueAssombrado ATAQUE_ASSOMBRADO_INSTANCE = new AtaqueAssombrado();

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Espirito() {
        super(); // Chama o construtor vazio da superclasse Monstro
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.tristeza = 0; // Valor padrão
        // As ações serão inicializadas pelo método inicializarAcoes() que é chamado no construtor de Monstro.
    }

    /**
     * Construtor da classe Espirito.
     *
     * @param nome O nome do Espírito.
     * @param pontosDeVida Os pontos de vida iniciais do Espírito.
     * @param forca A força inicial do Espírito.
     * @param agilidade A agilidade inicial do Espírito.
     * @param xpConcedido A quantidade de experiência que o Espírito concede.
     * @param tristeza O nível de tristeza inicial do Espírito.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec}
     *                               que o Espírito pode dropar.
     * @param nivelFase O nível da fase em que o Espírito está, usado para escalonamento de loot.
     */
    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido,
                    int tristeza, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int nivelFase) {
        // Passa a lista de especificações de drop e o nível da fase para o construtor da superclasse Monstro
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar, nivelFase);
        this.tristeza = tristeza;
    }

    /**
     * Inicializa as ações de combate específicas do Espírito.
     * Adiciona a instância compartilhada de {@link AtaqueAssombrado} à lista de ações.
     */
    @Override
    protected void inicializarAcoes() {
        this.acoes.add(ATAQUE_ASSOMBRADO_INSTANCE);
    }

    /**
     * Apresenta um diálogo especial se o Espírito for o "Kaonashi".
     */
    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Kaonashi")) {
            System.out.println("Kaonashi: \"Você... quer?\" (A voz que sai dele é um eco distorcido de suas vítimas. Ao mesmo tempo, ele estende uma mão trêmula, oferecendo pepitas de ouro que brilham com uma luz doentia. O gesto é uma armadilha, e sua fome insaciável parece entortar os traços de sua máscara.)");
            System.out.println();
        }
    }

    /**
     * Retorna o nível de tristeza do Espírito.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return O nível de tristeza.
     */
    @Override
    @XmlElement // ADICIONADO PARA JAXB
    public int getTristeza() {
        return tristeza;
    }   
    // ADICIONADO PARA JAXB: Setter necessário para desserialização
    public void setTristeza(int tristeza) {
        this.tristeza = tristeza;
    }

    /**
     * Gera uma representação textual do status atual do Espírito,
     * incluindo informações da superclasse e seu nível de tristeza.
     * @return Uma String com as informações do status do Espírito.
     */
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Tristeza = " + tristeza;
    }
}