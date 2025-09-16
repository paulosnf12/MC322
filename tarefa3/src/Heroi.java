//Heroi.java

import java.util.ArrayList;
import java.util.List;
import java.util.Random; // Importar Random para a sorte


// A classe Personagem já implementa Combatente, então Heroi herda isso.
// Irá agora implementar a inferface Combatente.

public abstract class Heroi extends Personagem {
    protected int nivel;
    protected int experiencia;
    protected int expProximoNivel;
    protected double sorte; // Valor entre 0.0 e 1.0

    // 1. Atributo para armazenar as ações disponíveis para o herói
    protected List<AcaoDeCombate> acoes;

    // construtor
    public Heroi(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia) {
        super(nome, pontosDeVida, forca, agilidade);
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.expProximoNivel = 100; // Experiência inicial para o próximo nível
        this.sorte = new Random().nextDouble(); // Sorte inicial aleatória entre 0.0 e 1.0

        // Inicializa a lista de ações
        this.acoes = new ArrayList<>();
        // Chama o método que será implementado pelas subclasses para popular a lista
        inicializarAcoes();
    }

    // Lógica para implementar a interface:

    // 3. Método abstrato para forçar subclasses a definirem suas ações
    protected abstract void inicializarAcoes();

    // 4. Implementação do método escolherAcao  da interface Combatente

    @Override
    public void escolherAcao(Combatente alvo) {
        // 2. Simula a escolha de uma ação pelo jogador (sem entrada de teclado)
        if (acoes != null && !acoes.isEmpty()) {
            // Escolhe uma ação aleatória da lista de ações disponíveis
            Random rand = new Random();
            int indiceAcao = rand.nextInt(acoes.size());
            AcaoDeCombate acaoEscolhida = acoes.get(indiceAcao);
            
            // Executa a ação escolhida
            acaoEscolhida.executar(this, alvo);
        } else {
            System.out.println(this.getNome() + " não tem ações para executar!");
        }
    }



    // metodos
    public int getNivel() {
        return nivel;
    }

    public double getSorte() { // Getter para sorte
        return sorte;
    }

    // método para ganhar experiência e subir de nível
    public void ganharExperiencia(int exp) {
        experiencia += exp;
        System.out.println(nome + " ganhou " + exp + " de experiência. Total: " + experiencia + "/" + expProximoNivel);
        while (experiencia >= expProximoNivel) {
            subirDeNivel();
        }
    }

    public int getExperiencia(){ // retorna experiencia do herói (público)
        return experiencia;
    }

    @Override
    public String exibirStatus() { // exibe status atual do herói
        return super.exibirStatus() + ", Nível = " + nivel + ", Experiência = " + experiencia + "/" + expProximoNivel;
    }

    public void equiparArma(Arma novaArma) {
        if (novaArma == null) { // Permite desequipar arma
            this.arma = null;
            System.out.println(nome + " desequipou a arma.");
            return;
        }

        if (this.nivel >= novaArma.getMinNivel()) {
            this.arma = novaArma;
            System.out.println(nome + " equipou a arma: " + novaArma.getDano() + " de dano, Nível mínimo: " + novaArma.getMinNivel() + ".");
        } else {
            System.out.println("Nível insuficiente para equipar esta arma. Nível mínimo requerido: " + novaArma.getMinNivel());
        }
    }

    // Método privado para aumentar o nível do herói e atualizar atributos
    private void subirDeNivel() {
        
        /*
        nivel++;
        experiencia -= expProximoNivel; // Deduz a experiência necessária para o nível anterior
        expProximoNivel = 100 + (nivel - 1) * 50; // Aumenta a experiência necessária para o próximo nível
        pontosDeVida += 20 + (nivel * 5); // Aumenta mais a cada nível
        forca += 5 + (nivel * 2);
        agilidade += 3 + nivel;
        */

        nivel++;
        experiencia -= expProximoNivel; // Deduz a experiência necessária para o nível anterior
        expProximoNivel = 100 + (nivel * 75); 
        pontosDeVida += 15 + (nivel * 4); 
        forca += 4 + (nivel * 1);           
        agilidade += 2 + nivel;         

        sorte = new Random().nextDouble(); // Atualiza sorte ao subir de nível
        System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel + "!");
        System.out.println("Novos status: Vida = " + pontosDeVida + ", Força = " + forca + ", Agilidade = " + agilidade + ", Sorte = " + String.format("%.2f", sorte));
    }


    /* Agora implementado via interface

    // método abstrato
    public abstract void usarHabilidadeEspecial(Personagem alvo); // exclusiva do herói --> será especificada/utilizada em cada herói especifico

    */
}