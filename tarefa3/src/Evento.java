public interface Evento {
    boolean vericarGatilho(Heroi heroi, String ambiente);
    void executar(Heroi heroi);
}