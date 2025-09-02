public abstract class Heroi extends Personagem {

    // atributos

    // todos os heróis terão nível/experiencia (pode ter mais alguma coisa)
    // ADICIONAR: Herois e monstros devem ter atributos e descricoes de ações 
    // (ataques, habilidades) que os tornem unicos entre si.

    protected  int nivel; 
    protected  int experiencia;
    

    // construtor

    public Heroi(String nome, int pontosDeVida, int forca, int nivel, int experiencia) {
        super(nome, pontosDeVida, forca);
        this.nivel = nivel;
        this.experiencia = experiencia;
    }
    

    // metodos

    public void ganharExperiencia(int exp) {
        experiencia += exp;
        if (experiencia >= 100) {
            nivel++;
            experiencia -= 100;
            System.out.println("Parabéns! " + "Você subiu para o nível " + nivel + "!");
        }
    }


    // **novo** 
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
    