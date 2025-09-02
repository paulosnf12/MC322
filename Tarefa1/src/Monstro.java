public abstract class Monstro extends Personagem {

    // atributos

    protected  int xpConcedido;

    // construtor

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
    }

    // metodos

    @Override
    
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}
