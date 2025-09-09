public abstract class Heroi extends Personagem {

    // atributos

    protected  int nivel; 
    protected  int experiencia;

    protected int expProximoNivel;

    protected int sorte; //Valor entre 0 e 1 que define a atual sorte do Herói para algumas ações do jogo.


    // construtor

    public Heroi(String nome, int pontosDeVida, int forca, int agilidade, int nivel, int experiencia) {
        super(nome, pontosDeVida, forca, agilidade);
        this.nivel = nivel;
        this.experiencia = experiencia;
    }
    

    // metodos

    public int getNivel() {
        return nivel;
    }
    
    // método para ganhar experiência e subir de nível
    public void ganharExperiencia(int exp) {
        experiencia += exp;
        if (experiencia >= 100) {
            nivel++;
            experiencia -= 100;
            System.out.println("Parabéns! " + "Você subiu para o nível " + nivel + "!");
        }
    }



    public int getExperiencia(){ // retorna experiencia do herói (público)
        return experiencia;
    }

    @Override
    public String exibirStatus() { // exibe status atual do herói
        return super.exibirStatus() + ", Nível = " + nivel + ", Experiência = " + experiencia;
    }

    public void equiparArma(Arma novaArma) {
        if (this.nivel >= novaArma.getMinNivel()) {
            this.arma = novaArma;
            System.out.println(nome + " equipou a arma com dano: " + novaArma.getDano());
        } else {
            System.out.println("Nível insuficiente para equipar esta arma. Nível mínimo requerido: " + novaArma.getMinNivel());
        }
}

    // subirDeNıvel() M ́etodo privado - M ́etodo para aumentar o n ́ıvel atual do her ́oi. Deve ser chamado apenas de forma interna na classe (privado). Subir de n ́ıvel deve atualizar a experiˆencia necess ́aria para o pr ́oximo n ́ıvel, assim como fortalecer os atributos atuais do her ́oi de alguma forma;


    private void subirDeNivel() {
        nivel++;
        experiencia = 0;
        expProximoNivel = 100 + (nivel - 1) * 50; // Exemplo: aumenta 50 por nível
        pontosDeVida += 20 + (nivel * 5); // Exemplo: aumenta mais a cada nível
        forca += 5 + (nivel * 2);
        agilidade += 3 + nivel;
        System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel + "!");
    }

    /*
    
    private void subirDeNivel(){
        nivel++;
        experiencia = 0;
        expProximoNivel += 50; // aumenta a experiência necessária para o próximo nível
        pontosDeVida += 20; // aumenta pontos de vida
        forca += 5; // aumenta força
        agilidade += 3; // aumenta agilidade
        System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel + "!");
    }

    */

    // método abstrato

    public abstract void usarHabilidadeEspecial(Personagem alvo); // exclusiva do herói --> será especificada/utilizada em cada herói especifico
}
    