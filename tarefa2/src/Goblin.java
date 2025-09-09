public class Goblin extends Monstro {
    // atributos
    protected  String tipoDeArma;
    protected  int danoArma;
    protected  double chanceDeRoubo;

    // construtor:
    public Goblin(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, String tipoDeArma, int danoArma, double chanceDeRoubo) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido);
        this.tipoDeArma = tipoDeArma;
        this.danoArma = danoArma;
        this.chanceDeRoubo = chanceDeRoubo;
    }

    @Override
    public void atacar(Personagem alvo) {
        int danoTotal = danoArma + getForca();
        alvo.receberDano(danoTotal);
        System.out.println("Goblin atacou com " + tipoDeArma + " causando " + danoTotal + " de dano!");

        // Implementação do roubo
        if (Math.random() < chanceDeRoubo) {
            int valorRoubo = 10; // valor fixo roubado
            alvo.receberDano(valorRoubo); // personagem perde vida extra
            this.pontosDeVida += valorRoubo; // goblin recupera vida
            System.out.println("Goblin roubou " + valorRoubo + " de vida do alvo!");
        }
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus();
    }


}
