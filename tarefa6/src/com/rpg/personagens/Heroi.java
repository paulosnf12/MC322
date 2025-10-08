//Heroi.java

package com.rpg.personagens; // Declaração do pacote, conforme a nova estrutura

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.Arma;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A classe Personagem já implementa Combatente, então Heroi herda isso.
// Irá agora implementar a interface Combatente (já implementado por Personagem).

/**
 * Classe abstrata que representa um herói controlável pelo jogador.
 * Estende {@link Personagem} adicionando mecânicas de nível, experiência, sorte,
 * e a capacidade de equipar armas e usar ações de combate específicas.
 * Esta classe sobrescreve implementações padrão da interface {@link Combatente}
 * fornecidas por {@link Personagem} para gerenciar atributos como mana e sorte
 * de forma mais específica ao herói.
 */
public abstract class Heroi extends Personagem {
    /**
     * O nível atual do herói.
     */
    protected int nivel;
    /**
     * A quantidade de experiência atual do herói.
     */
    protected int experiencia;
    /**
     * A quantidade de experiência necessária para o próximo nível.
     */
    protected int expProximoNivel;
    /**
     * O valor de sorte do herói (entre 0.0 e 1.0).
     * Este atributo é específico do herói e sobrescreve a implementação padrão de {@link Personagem}.
     */
    protected double sorte;

    /**
     * A quantidade de mana atual do herói.
     * Este atributo é específico do herói e sobrescreve a implementação padrão de {@link Personagem}.
     */
    protected int mana;
    /**
     * A quantidade máxima de mana que o herói pode ter.
     */
    protected int manaMaxima; 

    /**
     * Uma lista de {@link AcaoDeCombate} disponíveis para o herói.
     * O índice 0 é convencionalmente o ataque básico e o índice 1 a habilidade especial.
     */
    protected List<AcaoDeCombate> acoes;

    /**
     * Sinaliza se o próximo ataque escolhido pelo herói deve ser um ataque crítico (habilidade especial).
     * Este atributo é específico do herói e sobrescreve a implementação padrão de {@link Personagem}.
     */
    protected boolean proximoAtaqueEhCritico = false;

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

        // Inicializa a lista de ações
        this.acoes = new ArrayList<>();
        // Chama o método que será implementado pelas subclasses para popular a lista
        inicializarAcoes();
    }

    /**
     * Retorna a quantidade de mana atual do herói.
     * Esta implementação sobrescreve a padrão de {@link Personagem} para retornar
     * a mana específica do herói.
     * @return A quantidade de mana.
     */
    @Override
    public int getMana() {
        return mana;
    }

    /**
     * Diminui a mana do herói pela quantidade especificada.
     * Esta implementação sobrescreve a padrão de {@link Personagem} para gerenciar
     * a mana específica do herói.
     * @param custo O custo de mana da ação/habilidade.
     */
    @Override
    public void usarMana(int custo) {
        if (custo <= this.mana) {
            this.mana -= custo;
        }
    }

    /**
     * Define o valor de mana do herói. Utilizado principalmente para testes.
     * @param mana O novo valor de mana.
     */
    public void setMana(int mana) {
        this.mana = mana;
    }
    
    /**
     * Retorna o valor de sorte do herói.
     * Esta implementação sobrescreve a padrão de {@link Personagem} para retornar
     * a sorte específica do herói.
     * @return O valor de sorte (entre 0.0 e 1.0).
     */
    @Override
    public double getSorte() { // Getter para sorte
        return sorte;
    }

    /**
     * Gera uma representação textual do status atual do herói, incluindo
     * nome, vida, força, agilidade, nível, mana e experiência.
     * @return Uma String com as informações do status do herói.
     */
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Nivel = " + nivel + ", Mana = " + mana + "/" + manaMaxima +
               ", Experiencia = " + experiencia + "/" + expProximoNivel;
    }

    /**
     * Método abstrato para forçar subclasses a definirem suas ações de combate.
     * Deve popular a lista {@link #acoes}.
     */
    protected abstract void inicializarAcoes();

    /**
     * Define se o próximo ataque do herói deve ser um ataque crítico (habilidade especial).
     * Este método é chamado pela classe principal (Main) antes de invocar {@link #escolherAcao(Combatente)}.
     * Esta implementação sobrescreve a padrão de {@link Personagem} para gerenciar
     * o estado crítico específico do herói.
     * @param isCritico true se o próximo ataque deve ser crítico, false caso contrário.
     */
    @Override
    public void setProximoAtaqueCritico(boolean isCritico) {
        this.proximoAtaqueEhCritico = isCritico;
    }

    /**
     * Verifica se o próximo ataque do herói está sinalizado como crítico.
     * Esta implementação sobrescreve a padrão de {@link Personagem} para retornar
     * o estado crítico específico do herói.
     * @return {@code true} se o próximo ataque for crítico, {@code false} caso contrário.
     */
    @Override
    public boolean isProximoAtaqueCritico() {
        return this.proximoAtaqueEhCritico;
    }

    /**
     * Seleciona a ação de combate a ser usada no turno do herói.
     * Se o sinalizador de ataque crítico estiver ativo ({@link #isProximoAtaqueCritico()}),
     * retorna a habilidade especial (índice 1 da lista {@link #acoes}).
     * Caso contrário, retorna o ataque básico (índice 0).
     * Após a seleção, o sinalizador de ataque crítico é resetado.
     *
     * @param alvo O combatente alvo da ação.
     * @return A {@link AcaoDeCombate} a ser executada. Retorna {@code null} se não houver ações.
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
            acaoEscolhida = acoes.get(1); // Usa a convenção: índice 1 = Ataque Especial
        } else {
            // Caso contrário, use o ataque básico
            acaoEscolhida = acoes.get(0); // Usa a convenção: índice 0 = Ataque Básico
        }

        // IMPORTANTE: Reseta o sinalizador para o estado padrão após a ação.
        this.proximoAtaqueEhCritico = false;

        return acaoEscolhida;
    }

    /**
     * Retorna o nível atual do herói.
     * @return O nível do herói.
     */
    public int getNivel() {
        return nivel;
    }

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

    /**
     * Retorna a experiência atual do herói.
     * @return A experiência atual.
     */
    public int getExperiencia(){
        return experiencia;
    }

    /**
     * Equipa uma nova arma ao herói.
     * Se a {@code novaArma} for {@code null}, a arma atual é desequipada.
     * Lança uma {@link NivelInsuficienteException} se o herói não tiver o nível mínimo para equipar a arma.
     * @param novaArma A arma a ser equipada. Pode ser {@code null} para desequipar.
     * @throws NivelInsuficienteException Se o herói não tiver o nível mínimo para equipar a arma.
     */
    public void equiparArma(Arma novaArma) throws NivelInsuficienteException {
        if (novaArma == null) { // Permite desequipar arma
            this.arma = null;
            System.out.println(nome + " desequipou a arma.");
            return;
        }

        if (this.nivel >= novaArma.getMinNivel()) {
            this.arma = novaArma;
            System.out.println(nome + " equipou a arma: " + novaArma.getNomeCompleto() +
                               " (Dano: " + novaArma.getDano() + ", Nivel minimo: " +
                               novaArma.getMinNivel() + ").");
        } else {
            throw new NivelInsuficienteException(
                "o nivel " + this.nivel + " de " + nome + " e insuficiente para equipar a arma " +
                novaArma.getNomeCompleto() + " (nivel minimo: " + novaArma.getMinNivel() + ")."
            );
        }
    }

    /**
     * Aumenta o nível do herói e atualiza seus atributos (pontos de vida, força, sorte)
     * e a experiência necessária para o próximo nível.
     */
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

    /*
    Os métodos atacar() e usarHabilidadeEspecial() foram removidos diretamente do Heroi
    e suas lógicas foram refatoradas para as classes AcaoDeCombate, seguindo o princípio
    de separação de responsabilidades e agregação. O método escolherAcao() agora
    determina qual AcaoDeCombate será executada.
    */
}