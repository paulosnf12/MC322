//Heroi.java

import java.util.Random; // Importar Random para a sorte

public abstract class Heroi extends Personagem {
    protected int nivel;
    protected int experiencia;
    protected int expProximoNivel;
    protected double sorte; // Valor entre 0.0 e 1.0

    // construtor
    public Heroi(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia) {
        super(nome, pontosDeVida, forca, agilidade);
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.expProximoNivel = 100; // Experiência inicial para o próximo nível
        this.sorte = new Random().nextDouble(); // Sorte inicial aleatória entre 0.0 e 1.0
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

    // método abstrato
    public abstract void usarHabilidadeEspecial(Personagem alvo); // exclusiva do herói --> será especificada/utilizada em cada herói especifico
}