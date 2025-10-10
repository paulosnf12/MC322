// src/com/rpg/personagens/Heroi.java
package com.rpg.personagens;

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.personagens.herois.Elfo;    // Import para a subclasse Elfo
import com.rpg.personagens.herois.Paladino; // Import para a subclasse Paladino
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe abstrata que representa um herói controlável pelo jogador.
 * Estende {@link Personagem} adicionando mecânicas de nível, experiência, sorte,
 * e a capacidade de equipar armas e usar ações de combate específicas.
 * Esta classe sobrescreve implementações padrão da interface {@link Combatente}
 * fornecidas por {@link Personagem} para gerenciar atributos como mana e sorte
 * de forma mais específica ao herói.
 */
// JAXB: Indica que Heroi pode ter subclasses Paladino e Elfo
@XmlSeeAlso({Paladino.class, Elfo.class})
public abstract class Heroi extends Personagem {
    @XmlElement
    protected int nivel;
    @XmlElement
    protected int experiencia;
    @XmlElement
    protected int expProximoNivel;
    @XmlElement // ADICIONADO: Anotação para serializar o atributo mana do Heroi
    protected int mana;
    @XmlElement
    protected int manaMaxima;
    @XmlElement // ADICIONADO: Anotação para serializar o atributo sorte do Heroi
    protected double sorte;
    // JAXB: A lista 'acoes' contém instâncias stateless e é reinicializada após a desserialização.
    @XmlTransient
    protected List<AcaoDeCombate> acoes;
    // JAXB: proximoAtaqueEhCritico também é um campo específico de Heroi para o JAXB
    @XmlElement // ADICIONADO: Anotação para serializar o atributo proximoAtaqueEhCritico do Heroi
    protected boolean proximoAtaqueEhCritico = false;


    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para desserialização.
     * Como Heroi é uma classe abstrata, o construtor é protected.
     */
    protected Heroi() { // ALTERADO: Visibilidade para protected
        super();
        this.acoes = new ArrayList<>(); // Garante que a lista não seja null
        // Outros campos devem ser inicializados para evitar NPEs ou assumir valores padrão
        // (por exemplo, mana e sorte serão 0, 0.0 por padrão, que podem ser redefinidos em initializeTransientFields)
    }

    /**
     * Construtor da classe Heroi.
     * Inicializa os atributos básicos do personagem e específicos do herói,
     * como nível, experiência, mana e sorte.
     *
     * @param nome O nome do herói.
     * @param pontosDeVida Os pontos de vida iniciais do herói.
     * @param forca A força inicial do herói.
     * @param agilidade A agilidade inicial do herói.
     * @param nivel O nível inicial do herói.
     * @param experiencia A experiência inicial do herói.
     * @param mana A mana inicial do herói.
     */
    public Heroi(String nome, int pontosDeVida, int forca, int agilidade, int nivel,
                 int experiencia, int mana) {
        super(nome, pontosDeVida, forca, agilidade); // Chama o construtor da superclasse Personagem
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.expProximoNivel = 100; // Experiência inicial para o próximo nível
        this.mana = mana; // Atribui a mana inicial
        this.manaMaxima = mana; // Define a mana máxima como a inicial
        this.sorte = new Random().nextDouble(); // Sorte inicial aleatória entre 0.0 e 1.0
        this.acoes = new ArrayList<>();
        inicializarAcoes(); // Popula a lista de ações específicas do herói
    }

    /**
     * JAXB: Este método é chamado após a desserialização do objeto.
     * Serve para reinicializar campos {@code transient} ou campos que não foram diretamente serializados,
     * como a lista de ações.
     */
    @Override // Sobrescreve o método da superclasse para garantir que as ações do Heroi sejam inicializadas.
    public void initializeTransientFields() {
        super.initializeTransientFields(); // Chama a inicialização da superclasse
        if (this.acoes == null) {
            this.acoes = new ArrayList<>();
        } else {
            this.acoes.clear(); // Limpa para evitar duplicatas ou ações antigas
        }
        inicializarAcoes(); // Re-popula as ações específicas do herói
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void usarMana(int custo) {
        if (custo <= this.mana) {
            this.mana -= custo;
        }
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public double getSorte() { // Getter para sorte
        return sorte;
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Nível = " + nivel + ", Mana = " + mana + "/"
                + manaMaxima + ", Experiência = " + experiencia + "/" + expProximoNivel;
    }

    /**
     * Método abstrato para forçar subclasses a definirem suas ações de combate.
     * Deve popular a lista {@link #acoes}.
     */
    protected abstract void inicializarAcoes();

    @Override
    public void setProximoAtaqueCritico(boolean isCritico) {
        this.proximoAtaqueEhCritico = isCritico;
    }

    @Override
    public boolean isProximoAtaqueCritico() {
        return this.proximoAtaqueEhCritico;
    }

    @Override
    public AcaoDeCombate escolherAcao(Combatente alvo) {
        if (acoes == null || acoes.isEmpty()) {
            System.out.println(this.getNome() + " não tem ações para executar!");
            return null;
        }

        AcaoDeCombate acaoEscolhida;
        if (this.proximoAtaqueEhCritico && acoes.size() > 1) {
            acaoEscolhida = acoes.get(1); // Habilidade especial (índice 1)
        } else {
            acaoEscolhida = acoes.get(0); // Ataque básico (índice 0)
        }
        this.proximoAtaqueEhCritico = false; // Reseta o sinalizador
        return acaoEscolhida;
    }

    public int getNivel() {
        return nivel;
    }

    public void ganharExperiencia(int exp) {
        experiencia += exp;
        System.out.println(nome + " ganhou " + exp + " de experiência. Total: " +
                experiencia + "/" + expProximoNivel);
        while (experiencia >= expProximoNivel) {
            subirDeNivel();
        }
    }

    public int getExperiencia(){
        return experiencia;
    }

    public void equiparArma(Arma novaArma) throws NivelInsuficienteException {
        if (novaArma == null) {
            this.arma = null;
            System.out.println(nome + " desequipou a arma.");
            return;
        }
        if (this.nivel >= novaArma.getMinNivel()) {
            this.arma = novaArma;
            System.out.println(nome + " equipou a arma: " + novaArma.getNomeCompleto() +
                    " (Dano: " + novaArma.getDano() + ", Nível mínimo: " +
                    novaArma.getMinNivel() + ").");
        } else {
            throw new NivelInsuficienteException(
                    "o nível " + this.nivel + " de " + nome + " é insuficiente para " +
                            "equipar a arma " + novaArma.getNomeCompleto() + " (nível mínimo: " +
                            novaArma.getMinNivel() + ")."
            );
        }
    }

    private void subirDeNivel() {
        nivel++;
        experiencia -= expProximoNivel;
        expProximoNivel = 100 + (nivel * 75);
        pontosDeVida += 16 + (nivel * 4);
        forca += 4 + (nivel * 1);
        sorte = new Random().nextDouble(); // Atualiza sorte ao subir de nível
        System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel + "!");
        System.out.println("Novos status: Vida = " + pontosDeVida + ", Força = " +
                forca + ", Agilidade = " + agilidade + ", Sorte = " +
                String.format("%.2f", sorte));
    }
}