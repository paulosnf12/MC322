// AtaqueAssombrado.java (NOVO ARQUIVO)
public class AtaqueAssombrado implements AcaoDeCombate {
    
    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Espirito) {
            Espirito espirito = (Espirito) usuario;

            // Lógica do método atacar() original do Espirito
            int dano = espirito.getForca() + (espirito.getTristeza() / 10);
            alvo.receberDano(dano);

            System.out.println(espirito.getNome() + " está triste, ele não gosta de ficar triste... Tristeza: " + espirito.getTristeza() + ". Dano causado: " + dano);
        }
    }
}