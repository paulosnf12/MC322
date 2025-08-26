public abstract class Heroi extends Personagem {

    // atributos

    private int nivel;
    private int experiencia;

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

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Nível = " + nivel + ", Experiência = " + experiencia;
    }

    // método abstrato

    public abstract void usarHabilidadeEspecial(Personagem alvo);

}
    