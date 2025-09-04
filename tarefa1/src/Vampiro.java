public class Vampiro extends Monstro {
    private int brilho; // atributo único

    public Vampiro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int brilho) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido);
        this.brilho = brilho;
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = this.forca + this.brilho; // quanto maior o brilho, maior o dano
        alvo.receberDano(dano);
        System.out.println(nome + " ataca com suas presas! Brilho: " + brilho + ". Dano causado: " + dano);
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Brilho = " + brilho;
    }
}