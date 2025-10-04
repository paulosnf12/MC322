package com.rpg.personagens; // Declaração do pacote para monstros

import com.rpg.combate.AcaoDeCombate;
import com.rpg.combate.Combatente;
import com.rpg.itens.Arma;
import com.rpg.itens.Item;
import com.rpg.itens.Lootavel; // Embora herdado de Personagem, mantido para clareza em casos de uso direto
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Agora implementa Lootavel (e herda Combatente de Personagem)

/**
 * Representa um inimigo no jogo.
 * Monstros são combatentes que, ao serem derrotados, concedem experiência
 * e podem deixar um item (loot).
 */

public abstract class Monstro extends Personagem implements Lootavel {
    protected int xpConcedido;
    protected ArrayList<Arma> listaDeArmasParaLargar;
    // 1. Atributo para armazenar as ações do monstro
    protected List<AcaoDeCombate> acoes;

    protected boolean proximoAtaqueEhCritico = false;

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int
                   xpConcedido, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        // NOTA: A lista de armas passada aqui já deve ter sido populada no GeradorDeFases
        // levando em conta a dificuldade do jogo.
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
        // Inicializa a lista e chama o método que as subclasses irão implementar
        this.acoes = new ArrayList<>();
        inicializarAcoes();
    }

    /**
     * Sinaliza que o próximo ataque deste monstro deve ser um acerto crítico.
     * @param isCritico true se o próximo ataque for crítico, false caso contrário.
     */
    public void setProximoAtaqueCritico(boolean isCritico) {
        this.proximoAtaqueEhCritico = isCritico;
    }

     /**
     * Verifica se o próximo ataque do monstro está sinalizado como crítico.
     * @return true se o próximo ataque for crítico.
     */
    public boolean isProximoAtaqueCritico() {
        return this.proximoAtaqueEhCritico;
    }


    // 2. Método abstrato que força as subclasses (Goblin, Vampiro e Espírito)
    // definirem suas ações
    protected abstract void inicializarAcoes();

    @Override
    public AcaoDeCombate escolherAcao(Combatente alvo) {
        // 3. IA do monstro: escolhe e executa uma ação aleatória
        if (acoes != null && !acoes.isEmpty()) {
            Random rand = new Random();
            int indiceAcao = rand.nextInt(acoes.size());
            return acoes.get(indiceAcao); // Retorna a ação escolhida
        } else {
            System.out.println(this.getNome() + " observa, sem saber o que fazer!");
            return null; // Retorna null se não houver ações disponíveis
        }
    }


    /**
     * Gera e retorna um item que o monstro deixa ao ser derrotado.
     * Pode retornar null se nenhum item for deixado.
     *
     * @return Um objeto do tipo {@link Item} ou null.
     */
    
    @Override
    public Item droparLoot() { // Antigamente era largarArma()
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null;
        }
        Random rand = new Random();
        int idx = rand.nextInt(listaDeArmasParaLargar.size());
        Arma armaLargada = listaDeArmasParaLargar.get(idx); // Pega uma Arma...
        System.out.println(nome + " largou uma arma! (" + // Removido "encantada" para ser mais genérico
                           armaLargada.getNomeCompleto() + ")"); // Alterado para exibir apenas o nome completo
        return armaLargada; // ...e a retorna como um Item.
    }


    /**
     * Concede a experiência que este monstro vale ao ser derrotado.
     *
     * @return A quantidade de pontos de experiência.
     */

    public int getXpConcedido() {
        return xpConcedido;
    }

    public ArrayList<Arma> getListaDeArmasParaLargar() {
        return listaDeArmasParaLargar;
    }

    public void apresentarDialogoEspecial() {
        // Implementação padrão vazia ou com uma mensagem genérica
        // Pode ser sobrescrito por subclasses para diálogos específicos
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}