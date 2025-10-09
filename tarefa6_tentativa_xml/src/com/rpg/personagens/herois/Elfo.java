// src/com/rpg/personagens/herois/Elfo.java
package com.rpg.personagens.herois; // Pacote correto para heróis

import com.rpg.combate.AtaqueArco;
import com.rpg.combate.FlechaMagica;
import com.rpg.exceptions.NivelInsuficienteException; // Importa a exceção
import com.rpg.itens.ArcoAlpha;
import com.rpg.itens.ArcoBeta;
import com.rpg.itens.ArcoSigma;
import com.rpg.itens.Arma;
import com.rpg.personagens.Heroi; // Elfo estende Heroi
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB

/**
 * Representa o personagem Elfo, um herói especializado em ataques à distância com arcos.
 * O Elfo pode utilizar diferentes tipos de arcos conforme seu nível, e possui habilidades
 * únicas como cura ao atacar. Ele pode equipar armas que implementam a interface Arma (atualmente arcos).
 *
 * Esta classe utiliza o conceito de agregação para suas ações de combate,
 * onde as instâncias de {@link AtaqueArco} e {@link FlechaMagica} são referenciadas,
 * mas podem existir independentemente do Elfo.
 */
public class Elfo extends Heroi {
    // Os atributos 'tipoDeArco', 'danoArcoBase' e 'cura' foram removidos,
    // pois suas informações são agora derivadas da 'Arma' equipada ou
    // calculadas diretamente pelas ações de combate.

    /**
     * Dano configurável base para o {@link ArcoBeta}, usado no método {@link #atualizarArco()}.
     */
    private int configDanoBeta;
    /**
     * Dano configurável base para o {@link ArcoAlpha}, usado no método {@link #atualizarArco()}.
     */
    private int configDanoAlpha;
    /**
     * Dano configurável base para o {@link ArcoSigma}, usado no método {@link #atualizarArco()}.
     */
    private int configDanoSigma;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Elfo() {
        super(); // Chama o construtor vazio da superclasse Heroi
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.configDanoBeta = 0; // Pode ser um valor padrão diferente se preferir
        this.configDanoAlpha = 0;
        this.configDanoSigma = 0;
        // As ações serão inicializadas pelo método inicializarAcoes() que é chamado no construtor de Heroi.
        // O `atualizarArco()` será chamado no final da desserialização ou quando o Elfo for usado.
    }

    /**
     * Construtor do Elfo.
     *
     * @param nome       Nome do Elfo.
     * @param pontosDeVida Pontos de vida iniciais do Elfo.
     * @param forca      Força do Elfo, que afeta o dano físico.
     * @param agilidade  Agilidade do Elfo, que pode afetar esquiva e velocidade.
     * @param nivel      Nível inicial do Elfo.
     * @param experiencia Experiência inicial do Elfo.
     * @param danoBeta   Dano base do arco Beta.
     * @param danoAlpha  Dano base do arco Alpha.
     * @param danoSigma  Dano base do arco Sigma.
     * @param mana       Mana inicial do Elfo.
     */
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel,
                int experiencia,
                int danoBeta, int danoAlpha, int danoSigma, int mana) {
        // O parâmetro Arcos arcos foi removido
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia, mana);
        // Armazena os danos de configuração para uso no método atualizarArco()
        this.configDanoBeta = danoBeta;
        this.configDanoAlpha = danoAlpha;
        this.configDanoSigma = danoSigma;
        atualizarArco(); // Garante que a arma inicial seja equipada
    }

    /**
     * Inicializa as ações de combate específicas do Elfo.
     * Essas ações são instanciadas e adicionadas à lista {@code acoes}.
     * CONVENÇÃO: Índice 0 é o ataque básico, Índice 1 é o especial.
     */
    @Override
    protected void inicializarAcoes() {
        // Adicionando as ações específicas do Elfo à lista de ações.
        // As instâncias de AtaqueArco e FlechaMagica são agregadas ao Elfo,
        // existindo de forma independente mas sendo referenciadas por ele.
        this.acoes.add(new AtaqueArco()); // Adicionado na posição 0
        this.acoes.add(new FlechaMagica()); // Adicionado na posição 1
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoBeta
    @XmlElement
    public int getConfigDanoBeta() {
        return configDanoBeta;
    }
    public void setConfigDanoBeta(int configDanoBeta) {
        this.configDanoBeta = configDanoBeta;
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoAlpha
    @XmlElement
    public int getConfigDanoAlpha() {
        return configDanoAlpha;
    }
    public void setConfigDanoAlpha(int configDanoAlpha) {
        this.configDanoAlpha = configDanoAlpha;
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoSigma
    @XmlElement
    public int getConfigDanoSigma() {
        return configDanoSigma;
    }
    public void setConfigDanoSigma(int configDanoSigma) {
        this.configDanoSigma = configDanoSigma;
    }

    /**
     * Atualiza o arco equipado pelo Elfo com base no seu nível atual.
     * Determina o arco padrão para o nível (Beta, Alpha ou Sigma) e o equipa
     * se não houver uma arma equipada ou se a nova arma padrão for mais forte.
     * Gerencia a agregação de um novo objeto {@link Arma}.
     */
    public void atualizarArco() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao; // A arma padrão que o Elfo pode criar neste nível

        // 1. Determina qual é a arma padrão para o nível atual
        if (nivelAtual < 2) { // Nível 1 usa Beta
            novaArmaPadrao = new ArcoBeta(configDanoBeta);
        } else if (nivelAtual < 3) { // Nível 2 usa Alpha
            novaArmaPadrao = new ArcoAlpha(configDanoAlpha);
        } else { // Nível 3 ou superior usa Sigma
            novaArmaPadrao = new ArcoSigma(configDanoSigma);
        }

        // 2. Compara a arma padrão com a arma atualmente equipada
        Arma armaAtual = this.getArma();
        if (armaAtual == null) {
            // CASO 1: O Elfo não tem arma. Equipa a nova arma padrão.
            System.out.println(this.getNome() + " atingiu um novo patamar e forja uma " +
                               "nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao equipar arma padrao para " + this.getNome() + ": " + e.getMessage());
            }
        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            // CASO 2: A nova arma padrão é mais forte que a atual.
            System.out.println("Com sua nova experiencia, " + this.getNome() + " " +
                               "aprimora seu equipamento para uma " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao aprimorar arma para " + this.getNome() + ": " + e.getMessage());
            }
        } else {
            // CASO 3: A arma atual (possivelmente de um drop) é melhor ou igual. Não faz nada.
            System.out.println(this.getNome() + " sente que poderia forjar uma " +
                               novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" +
                               armaAtual.getNomeCompleto() + ") ainda e superior.");
        }
        // As linhas de atualização de 'danoArcoBase' e 'cura' foram removidas,
        // pois esses atributos não são mais diretamente armazenados no Elfo.
        // O dano é obtido via getArma().getDano() e a cura é calculada na AcaoDeCombate.
    }

    /**
     * Processa o ganho de experiência do Elfo.
     * Após o processamento da superclasse, verifica se o arco precisa ser atualizado,
     * caso o nível do Elfo tenha mudado.
     *
     * @param exp A quantidade de experiência ganha.
     */
    @Override
    public void ganharExperiencia(int exp) {
        // primeiro, chama o método da superclasse para processar o ganho de XP e o
        // possível nível-up
        super.ganharExperiencia(exp);
        // depois atualiza o arco porque o nível pode ter mudado
        atualizarArco();
    }

    /*
    Os métodos atacar() e usarHabilidadeEspecial() foram removidos diretamente do Elfo
    e suas lógicas foram refatoradas para as classes AcaoDeCombate (AtaqueArco e FlechaMagica),
    seguindo o princípio de separação de responsabilidades e agregação.
    As implementações anteriores estavam fortemente acopladas ao tipo Elfo,
    enquanto as novas aceitam qualquer Combatente.
    */

    /**
     * Retorna o tipo de arma (arco) atualmente equipada pelo Elfo.
     *
     * @return Uma String indicando o tipo de arco (e.g., "Arco" ou null se não houver).
     */
    // Nao precisa de @XmlElement, pois o Elfo nao "possui" o tipo de arco, ele apenas o deriva da arma equipada.
    public String getTipoDeArco() {
        return (this.arma != null) ? this.arma.getTipoArma() : null; // Retorna o tipo da arma equipada
    }

    /**
     * Retorna o dano do arco atualmente equipado pelo Elfo.
     *
     * @return O valor de dano do arco equipado, ou 0 se não houver arco.
     */
    // Nao precisa de @XmlElement, pois o Elfo nao "possui" o dano do arco, ele apenas o deriva da arma equipada.
    public int getDanoArco() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}