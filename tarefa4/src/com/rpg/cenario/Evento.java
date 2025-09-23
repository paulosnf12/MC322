//Evento.java
package com.rpg.cenario;
import java.util.ArrayList;
import java.util.List;
import com.rpg.personagens.Heroi;

public interface Evento {
    boolean vericarGatilho(Heroi heroi, String ambiente);
    void executar(Heroi heroi);
}