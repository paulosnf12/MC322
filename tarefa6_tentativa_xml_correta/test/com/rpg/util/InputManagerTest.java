// test/com/rpg/util/InputManagerTest.java
package com.rpg.util; // O mesmo pacote da classe que você está testando

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach; // Importado para inicialização, útil para o setup
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue; // Para testar lerSimNao

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException; // Para simular falta de entrada

public class InputManagerTest {
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut; // Buffer para capturar a saída do console

    @BeforeEach // Executado antes de cada teste
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut)); // Redireciona System.out para nosso buffer
    }

    @AfterEach // Executado após cada teste
    void restoreStreams() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        //InputManager.fecharScanner(); // Garante que o scanner seja fechado para não afetar outros testes
        // Agora a gestão do scanner é interna do InputManager
    }

    // Método auxiliar para simular a entrada do usuário
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // --- Testes para lerString ---
    @Test
    void testLerString_ValidInput() {
        String inputSimulado = "Minha entrada de teste\n";
        provideInput(inputSimulado);

        String mensagemPrompt = "Digite algo";
        String resultado = InputManager.lerString(mensagemPrompt);

        assertEquals("Minha entrada de teste", resultado); // Verifica se o retorno está correto
        assertTrue(testOut.toString().contains(mensagemPrompt + ": ")); // Verifica se o prompt foi exibido
    }

    @Test
    void testLerString_EmptyInputThenValid() {
        String inputSimulado = "\n" + "Entrada valida\n"; // Primeiro vazio, depois válido
        provideInput(inputSimulado);

        String mensagemPrompt = "Digite algo";
        String resultado = InputManager.lerString(mensagemPrompt);

        assertEquals("Entrada valida", resultado);
        // O prompt deve ser exibido pelo menos duas vezes: para a entrada vazia e para a válida
        assertTrue(testOut.toString().contains(mensagemPrompt + ": "));
    }

    @Test
    void testLerString_NoInputAvailable() {
        // Não fornecemos input, então InputManager.lerString tentará ler e falhará
        provideInput(""); // Simula nenhuma entrada disponível

        String mensagemPrompt = "Digite algo";
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                InputManager.lerString(mensagemPrompt));

        assertEquals("Entrada nao disponivel.", thrown.getMessage());
    }

    // --- Testes para lerSimNao ---
    @Test
    void testLerSimNao_ValidYes() {
        provideInput("s\n"); // Simula a entrada 's'

        boolean resultado = InputManager.lerSimNao("Deseja continuar?");

        assertTrue(resultado); // Espera que retorne true
        assertTrue(testOut.toString().contains("Deseja continuar? (s/n): ")); // Verifica o prompt
    }

    @Test
    void testLerSimNao_ValidNo() {
        provideInput("n\n"); // Simula a entrada 'n'

        boolean resultado = InputManager.lerSimNao("Deseja continuar?");

        assertEquals(false, resultado); // Espera que retorne false
        assertTrue(testOut.toString().contains("Deseja continuar? (s/n): "));
    }

    @Test
    void testLerSimNao_InvalidThenValid() {
        provideInput("talvez\n" + "s\n"); // Primeiro inválido, depois 's'

        boolean resultado = InputManager.lerSimNao("Deseja continuar?");

        assertTrue(resultado);
        // Verifica se a mensagem de erro foi exibida antes de aceitar o 's'
        assertTrue(testOut.toString().contains("Resposta invalida. Por favor, digite 's' para sim ou 'n' para nao."));
        assertTrue(testOut.toString().contains("Deseja continuar? (s/n): ")); // Prompt exibido mais de uma vez
    }

    @Test
    void testLerSimNao_NoInputAvailable() {
        // Simula a ausência de entrada
        provideInput("");

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                InputManager.lerSimNao("Deseja continuar?"));

        assertEquals("Entrada nao disponivel.", thrown.getMessage());
    }
}