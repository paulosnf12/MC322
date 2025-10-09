// src/com/rpg/personagens/herois/Paladino.java
package com.rpg.personagens.herois; // Pacote correto para heróis

import com.rpg.combate.AtaqueEspada;
import com.rpg.combate.GolpeSagrado;
import com.rpg.exceptions.NivelInsuficienteException; // Importa a exceção
import com.rpg.itens.Arma;
import com.rpg.itens.EspadaDiamante;
import com.rpg.itens.EspadaFerro;
import com.rpg.itens.EspadaMadeira;
import com.rpg.personagens.Heroi; // Paladino estende Heroi
import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB

/**
 * Representa o personagem Paladino, um herói especializado em combate corpo a corpo com espadas.
 * O Paladino pode utilizar diferentes tipos de espadas conforme seu nível, e possui habilidades
 * únicas como o Golpe Sagrado. Ele pode equipar armas que implementam a interface Arma (atualmente espadas).
 *
 * Esta classe utiliza o conceito de agregação para suas ações de combate,
 * onde as instâncias de {@link AtaqueEspada} e {@link GolpeSagrado} são referenciadas,
 * mas podem existir independentemente do Paladino.
 */
public class Paladino extends Heroi {
    // Os atributos 'tipoDeEspada' e 'danoEspadaBase' foram removidos,
    // pois suas informações são agora derivadas da 'Arma' equipada.
    /**
     * O valor de carisma do Paladino, um atributo único que pode afetar habilidades mágicas.
     * Este atributo sobrescreve a implementação padrão de {@link com.rpg.personagens.Personagem}.
     */
    private int carisma;
    /**
     * Dano configurável base para a {@link EspadaMadeira}, usado no método {@link #atualizarEspada()}.
     */
    private int configDanoMadeira;
    /**
     * Dano configurável base para a {@link EspadaFerro}, usado no método {@link #atualizarEspada()}.
     */
    private int configDanoFerro;
    /**
     * Dano configurável base para a {@link EspadaDiamante}, usado no método {@link #atualizarEspada()}.
     */
    private int configDanoDiamante;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Paladino() {
        super(); // Chama o construtor vazio da superclasse Heroi
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.carisma = 0; // Pode ser um valor padrão diferente se preferir
        this.configDanoMadeira = 0;
        this.configDanoFerro = 0;
        this.configDanoDiamante = 0;
        // As ações serão inicializadas pelo método inicializarAcoes() que é chamado no construtor de Heroi.
        // O `atualizarEspada()` será chamado no final da desserialização ou quando o Paladino for usado.
    }

    /**
     * Construtor do Paladino.
     * Inicializa os atributos do herói e define os danos de configuração para os tipos de espada.
     *
     * @param nome Nome do Paladino.
     * @param pontosDeVida Pontos de vida iniciais do Paladino.
     * @param forca Força do Paladino, que afeta o dano físico.
     * @param agilidade Agilidade do Paladino, que pode afetar esquiva e velocidade.
     * @param nivel Nível inicial do Paladino.
     * @param experiencia Experiência inicial do Paladino.
     * @param danoMadeira Dano base da espada de madeira para configuração.
     * @param danoFerro Dano base da espada de ferro para configuração.
     * @param danoDiamante Dano base da espada de diamante para configuração.
     * @param mana Mana inicial do Paladino.
     */
    public Paladino(String nome, int pontosDeVida, int forca, int agilidade, int
                    nivel, int experiencia,
                    int danoMadeira, int danoFerro, int danoDiamante, int mana) {
        // O parâmetro Espada espadaConfig foi removido
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia, mana);
        this.carisma = 26; // Valor inicial de carisma
        // Armazena os danos de configuração para uso no método atualizarEspada()
        this.configDanoMadeira = danoMadeira;
        this.configDanoFerro = danoFerro;
        this.configDanoDiamante = danoDiamante;
        atualizarEspada(); // Garante que a arma inicial seja equipada
    }

    /**
     * Inicializa as ações de combate específicas do Paladino.
     * Essas ações são instanciadas e adicionadas à lista {@code acoes}.
     * CONVENÇÃO: Índice 0 é o ataque básico, Índice 1 é o especial.
     */
    @Override
    protected void inicializarAcoes() {
        // Adicionando as ações específicas do Paladino.
        // As instâncias de AtaqueEspada e GolpeSagrado são agregadas ao Paladino,
        // existindo de forma independente mas sendo referenciadas por ele.
        this.acoes.add(new AtaqueEspada()); // Adicionado na posição 0
        this.acoes.add(new GolpeSagrado()); // Adicionado na posição 1
    }

    /**
     * Retorna o valor de carisma do Paladino.
     * Esta implementação sobrescreve a padrão de {@link com.rpg.personagens.Personagem}.
     * @return O valor de carisma.
     */
    @Override
    @XmlElement // ADICIONADO PARA JAXB
    public int getCarisma() {
        return this.carisma;
    }

    // ADICIONADO PARA JAXB: Setter para carisma
    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoMadeira
    @XmlElement
    public int getConfigDanoMadeira() {
        return configDanoMadeira;
    }
    public void setConfigDanoMadeira(int configDanoMadeira) {
        this.configDanoMadeira = configDanoMadeira;
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoFerro
    @XmlElement
    public int getConfigDanoFerro() {
        return configDanoFerro;
    }
    public void setConfigDanoFerro(int configDanoFerro) {
        this.configDanoFerro = configDanoFerro;
    }

    // ADICIONADO PARA JAXB: Getters e Setters para configDanoDiamante
    @XmlElement
    public int getConfigDanoDiamante() {
        return configDanoDiamante;
    }
    public void setConfigDanoDiamante(int configDanoDiamante) {
        this.configDanoDiamante = configDanoDiamante;
    }

    /**
     * Atualiza a espada equipada pelo Paladino com base no seu nível atual.
     * Determina a espada padrão para o nível (Madeira, Ferro ou Diamante) e a equipa
     * se não houver uma arma equipada ou se a nova arma padrão for mais forte.
     * Gerencia a agregação de um novo objeto {@link Arma}.
     */
    public void atualizarEspada() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao; // A arma padrão que o Paladino pode criar neste nível

        // 1. Determina qual é a arma padrão para o nível atual
        if (nivelAtual < 2) { // Nível 1 usa Madeira
            novaArmaPadrao = new EspadaMadeira(configDanoMadeira);
        } else if (nivelAtual < 3) { // Nível 2 usa Ferro
            novaArmaPadrao = new EspadaFerro(configDanoFerro);
        } else { // Nível 3 ou superior usa Diamante
            novaArmaPadrao = new EspadaDiamante(configDanoDiamante);
        }

        // 2. Compara a arma padrão com a arma atualmente equipada
        Arma armaAtual = this.getArma();
        if (armaAtual == null) {
            // CASO 1: O Paladino não tem arma. Equipa a nova arma padrão.
            System.out.println(this.getNome() + " sente sua fe renovada e consagra uma " +
                               "nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao equipar arma padrao para " + this.getNome() + ": " + e.getMessage());
                // Em um cenário de arma padrão, é improvável que isso ocorra,
                // mas a exceção precisa ser tratada ou propagada.
            }
        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            // CASO 2: A nova arma padrão é mais forte que a atual.
            System.out.println("Guiado por sua conviccao " + this.getNome() + " forja uma " +
                               "arma mais poderosa: uma " + novaArmaPadrao.getNomeCompleto() + "!");
            try { // try-catch para NivelInsuficienteException
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao aprimorar arma para Paladino" + this.getNome() + ": " + e.getMessage());
            }
        } else {
            // CASO 3: A arma atual (possivelmente de um drop) é melhor ou igual. Não faz nada.
            System.out.println(this.getNome() + " poderia forjar uma " +
                               novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" +
                               armaAtual.getNomeCompleto() + ") ja serve bem ao seu proposito sagrado.");
        }
        // A linha de atualização de 'danoEspadaBase' foi removida,
        // pois esse atributo não é mais diretamente armazenado no Paladino.
        // O dano é obtido via getArma().getDano().
    }

    /**
     * Processa o ganho de experiência do Paladino.
     * Após o processamento da superclasse, verifica se a espada precisa ser atualizada,
     * caso o nível do Paladino tenha mudado.
     *
     * @param exp A quantidade de experiência ganha.
     */
    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada(); // Atualiza a espada caso o nível mude
    }

    /*
    Os métodos atacar() e usarHabilidadeEspecial() foram removidos diretamente do Paladino
    e suas lógicas foram refatoradas para as classes AcaoDeCombate (AtaqueEspada e GolpeSagrado),
    seguindo o princípio de separação de responsabilidades e agregação.
    As implementações anteriores estavam fortemente acopladas ao tipo Paladino,
    enquanto as novas aceitam qualquer Combatente.
    */

    /**
     * Retorna o tipo de arma (espada) atualmente equipada pelo Paladino.
     *
     * @return Uma String indicando o tipo de espada (e.g., "Espada") ou {@code null} se nenhuma estiver equipada.
     */
    // Nao precisa de @XmlElement, pois o Paladino nao "possui" o tipo de espada, ele apenas o deriva da arma equipada.
    public String getTipoDeEspada() {
        return (this.arma != null) ? this.arma.getTipoArma() : null; // Retorna o tipo da arma equipada
    }

    /**
     * Retorna o dano da espada atualmente equipada pelo Paladino.
     *
     * @return O valor de dano da espada equipada, ou 0 se nenhuma espada estiver equipada.
     */
    // Nao precisa de @XmlElement, pois o Paladino nao "possui" o dano da espada, ele apenas o deriva da arma equipada.
    public int getDanoEspada() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}