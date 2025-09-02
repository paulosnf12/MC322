public abstract class Personagem { // serve tanto para herói quanto monstro
    // atributos

    protected  String nome;
    protected  int pontosDeVida;
    protected  int forca;

    // construtor

    public Personagem(String nome, int pontosDeVida, int forca){
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
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
        return "Nome = " + nome + ", Vida = " + pontosDeVida + "Força = " + forca;
    }

    // método abstrato

    public abstract void atacar(Personagem alvo);

}

    
