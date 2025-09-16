// AtaqueGoblin.java (NOVO ARQUIVO)
public class AtaqueGoblin implements AcaoDeCombate {

    @Override
    public void executar(Combatente usuario, Combatente alvo) {
        if (usuario instanceof Goblin) {
            Goblin goblin = (Goblin) usuario;

            // Lógica do método atacar() original do Goblin
            int danoTotal = goblin.getDanoArma() + goblin.getForca();
            alvo.receberDano(danoTotal);
            
            System.out.println("Goblin atacou com " + goblin.getTipoDeArma() + " causando " + danoTotal + " de dano!");

            // Implementação do roubo
            if (Math.random() < goblin.getChanceDeRoubo()) {
                int valorRoubo = 3;
                alvo.receberDano(valorRoubo);
                // Precisamos de um método para alterar a vida diretamente ou usar receberCura
                goblin.receberCura(valorRoubo);
                System.out.println("Goblin roubou " + valorRoubo + " de vida do alvo!");
            }
        }
    }
}