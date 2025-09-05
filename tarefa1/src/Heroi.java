public abstract class Heroi extends Personagem {

    // atributos

    protected  int nivel; 
    protected  int experiencia;
    

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

    // método abstrato

    public abstract void usarHabilidadeEspecial(Personagem alvo); // exclusiva do herói --> será especificada/utilizada em cada herói especifico
}
    