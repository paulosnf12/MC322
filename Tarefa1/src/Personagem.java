public abstract class Personagem { // serve tanto para herói quanto monstro
    // atributos

    protected  String nome;
    protected  int pontosDeVida;
    protected  int forca;

    protected int agilidade; // influencia na chance de acerto

    // construtor

    public Personagem(String nome, int pontosDeVida, int forca, int agilidade) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.agilidade = agilidade;
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
    }

    public String getNome(){
        return nome;
    }

    public int getForca(){
        return forca;
    }

    public String exibirStatus(){
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "\nForça = " + forca;
    }

    public int getAgilidade() {
        return agilidade;
    }

    // método abstrato

    public abstract void atacar(Personagem alvo);

}

    
