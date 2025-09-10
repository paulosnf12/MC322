//Personagem.java

public abstract class Personagem {
    protected String nome;
    protected int pontosDeVida;
    protected int forca;
    protected int agilidade; // influencia na chance de acerto
    protected Arma arma; // **NOVO: arma equipada**

    // construtor
    public Personagem(String nome, int pontosDeVida, int forca, int agilidade) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.agilidade = agilidade;
        this.arma = null; // Inicialmente sem arma equipada, pode ser ajustado em subclasses
    }

    // metodos
    public void setpontosdevida(int pontosDeVida){
        this.pontosDeVida = pontosDeVida;
    }
    public int getpontosdevida(){
        return pontosDeVida;
    }
    public void receberDano(int dano){
        int vida = getpontosdevida() - dano;
        setpontosdevida(vida);
        if (this.pontosDeVida < 0) { // Garante que a vida não fique negativa
            this.pontosDeVida = 0;
        }
    }
    public String getNome(){
        return nome;
    }
    public int getForca(){
        return forca;
    }
    public String exibirStatus(){
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "\nForça = " + forca + ", Agilidade = " + agilidade;
    }
    public int getAgilidade() {
        return agilidade;
    }

    public Arma getArma() { // NOVO getter para arma
        return arma;
    }

    public void setArma(Arma arma) { // NOVO setter para arma
        this.arma = arma;
    }

    // método abstrato
    public abstract void atacar(Personagem alvo);
}