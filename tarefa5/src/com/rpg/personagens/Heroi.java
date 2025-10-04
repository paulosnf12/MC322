//Heroi.java

package com.rpg.personagens; // Declaração do pacote, conforme a nova estrutura

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.exceptions.NivelInsuficienteException; // Importar Random para a sorte
import com.rpg.itens.Arma;
import java.util.ArrayList;
import java.util.List; // Importa a exceção customizada
import java.util.Random;

// A classe Personagem já implementa Combatente, então Heroi herda isso.
// Irá agora implementar a inferface Combatente (já implementado por Personagem).

/**
 * Classe abstrata que representa um herói controlável pelo jogador.
 * Estende {@link Personagem} adicionando mecânicas de nível, experiência, sorte,
 * e a capacidade de equipar armas e usar ações de combate específicas.
 */

public abstract class Heroi extends Personagem {
    protected int nivel;
    protected int experiencia;
    protected int expProximoNivel;
    protected double sorte; // Valor entre 0.0 e 1.0

    protected int mana;
    protected int manaMaxima; 

    // 1. Atributo para armazenar as ações disponíveis para o herói
    protected List<AcaoDeCombate> acoes;

    // ---- NOVO ATRIBUTO ---
    protected boolean proximoAtaqueEhCritico = false; // Verifica se o próximo ataque será crítico

    // construtor
    public Heroi(String nome, int pontosDeVida, int forca, int agilidade, int nivel,
                 int experiencia, int mana) {
        super(nome, pontosDeVida, forca, agilidade);
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.expProximoNivel = 100; // Experiência inicial para o próximo nível
        this.mana = mana; // Atribui a mana inicial
        this.manaMaxima = mana; // Define a mana máxima como a inicial
        this.sorte = new Random().nextDouble(); // Sorte inicial aleatória entre 0.0 e 1.0

        // Inicializa a lista de ações
        this.acoes = new ArrayList<>();
        // Chama o método que será implementado pelas subclasses para popular a lista
        inicializarAcoes();
    }

    // Adicione métodos para gerenciar a mana
    public int getMana() {
        return mana;
    }

    // Método para simular o gasto de mana
    public void usarMana(int custo) {
        if (custo <= this.mana) {
            this.mana -= custo;
        }
    }

    // Método para testes, para forçar um valor de mana
    public void setMana(int mana) {
        this.mana = mana;
    }
    
    // (Opcional, mas recomendado) Atualize o exibirStatus() para mostrar a mana
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Nivel = " + nivel + ", Mana = " + mana + "/" + manaMaxima +
               ", Experiencia = " + experiencia + "/" + expProximoNivel;
    }

    // Lógica para implementar a interface:
    // 3. Método abstrato para forçar subclasses a definirem suas ações
    protected abstract void inicializarAcoes();

    // ---- NOVO MÉTODO PARA "SINALIZAR" O CRÍTICO ---
    /**
         * Define se a próxima ação escolhida pelo herói deve ser um ataque
         * crítico (especial). Este método será chamado pela classe Main antes de
         * invocar escolherAcao.
         * @param isCritico true se o próximo ataque deve ser crítico, false caso contrário.
         */
    public void setProximoAtaqueCritico(boolean isCritico) {
        this.proximoAtaqueEhCritico = isCritico;
    }

    // 4. Implementação do método escolherAcao da interface Combatente
    // ---- MÉTODO escolherAcao MODIFICADO ---

    /**
     * Seleciona a ação de combate a ser usada no turno.
     * Se o sinalizador de ataque crítico estiver ativo, retorna a habilidade especial (índice 1).
     * Caso contrário, retorna o ataque básico (índice 0).
     * @param alvo O combatente alvo da ação.
     * @return A {@link AcaoDeCombate} a ser executada.
     */
    
    @Override
    public AcaoDeCombate escolherAcao(Combatente alvo) {
        if (acoes == null || acoes.isEmpty()) {
            System.out.println(this.getNome() + " nao tem acoes para executar!");
            return null;
        }

        AcaoDeCombate acaoEscolhida;
        // Verifica o sinalizador de crítico
        if (this.proximoAtaqueEhCritico && acoes.size() > 1) {
            // Se for crítico e houver uma habilidade especial, escolha-a
            acaoEscolhida = acoes.get(1); // Retorna a habilidade especial,  usa a convenção: índice 1 = Ataque Especial
        } else {
            // Caso contrário, use o ataque básico
            acaoEscolhida = acoes.get(0); // Retorna o ataque básico acaoEscolhida = acoes.get(0); // Usa a convenção: índice 0 = Ataque Básico
        }

        // Executa a ação
        //acaoEscolhida.executar(this, alvo);
        // IMPORTANTE: Reseta o sinalizador para o estado padrão após a ação.
        this.proximoAtaqueEhCritico = false;

        return acaoEscolhida;
    }

    // metodos
    public int getNivel() {
        return nivel;
    }

    public double getSorte() { // Getter para sorte
        return sorte;
    }

    // método para ganhar experiência e subir de nível

    /**
     * Processa o ganho de experiência do herói.
     * Se a experiência acumulada for suficiente, o método {@link #subirDeNivel()} é invocado.
     *
     * @param exp A quantidade de experiência ganha.
     */

    public void ganharExperiencia(int exp) {
        experiencia += exp;
        System.out.println(nome + " ganhou " + exp + " de experiencia. Total: " +
                           experiencia + "/" + expProximoNivel);
        while (experiencia >= expProximoNivel) {
            subirDeNivel();
        }
    }

    public int getExperiencia(){ // retorna experiencia do herói (público)
        return experiencia;
    }

    /**
     * Equipa uma nova arma ao herói.
     * @param novaArma A arma a ser equipada. Pode ser null para desequipar.
     * @throws NivelInsuficienteException Se o herói não tiver o nível mínimo para equipar a arma.
     */
     
    public void equiparArma(Arma novaArma) throws NivelInsuficienteException { // <-- MUDANÇA AQUI: Adicionado 'throws NivelInsuficienteException'
        if (novaArma == null) { // Permite desequipar arma
            this.arma = null;
            System.out.println(nome + " desequipou a arma.");
            return;
        }

        if (this.nivel >= novaArma.getMinNivel()) {
            this.arma = novaArma;
            System.out.println(nome + " equipou a arma: " + novaArma.getNomeCompleto() + // <-- MUDANÇA: Usando getNomeCompleto()
                               " (Dano: " + novaArma.getDano() + ", Nivel minimo: " +
                               novaArma.getMinNivel() + ").");
        } else {
            // <-- MUDANÇA AQUI: Lançando a exceção customizada em vez de apenas imprimir
            throw new NivelInsuficienteException(
                "o nivel " + this.nivel + " de " + nome + " e insuficiente para equipar a arma " +
                novaArma.getNomeCompleto() + " (nivel minimo: " + novaArma.getMinNivel() + ")."
            );
        }
    }

    // Método privado para aumentar o nível do herói e atualizar atributos
    private void subirDeNivel() {
        nivel++;
        experiencia -= expProximoNivel; // Deduz a experiência necessária para o nível anterior
        expProximoNivel = 100 + (nivel * 75);
        pontosDeVida += 16 + (nivel * 4);
        forca += 4 + (nivel * 1);
        //agilidade += 2 + nivel; Se ele ganha agilidade o jogo fica muito desequilibrado, então decidimos retirar.
        sorte = new Random().nextDouble(); // Atualiza sorte ao subir de nível
        System.out.println("Parabéns! " + nome + " subiu para o nivel " + nivel +
                           "!");
        System.out.println("Novos status: Vida = " + pontosDeVida + ", Forca = " +
                           forca + ", Agilidade = " + agilidade + ", Sorte = " + String.format("%.2f", sorte));
    }

    /* Agora implementado via interface (AcaoDeCombate)
    // método abstrato
    public abstract void usarHabilidadeEspecial(Personagem alvo); // exclusiva do
    herói --> será especificada/utilizada em cada herói especifico
    */
}