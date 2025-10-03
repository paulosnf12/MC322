// com/rpg/util/InputManager.java
package com.rpg.util;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputManager {
    // Mantivemos o scanner como estático, mas adicionamos uma flag para controlar seu estado
    private static Scanner scanner;

    // Adicionamos um método estático para obter o scanner, garantindo que esteja sempre aberto
    private static Scanner getScanner() {
        if (scanner == null || !scanner.hasNextLine()) { // hasNextLine() pode indicar se está fechado em alguns casos
            if (scanner != null) {
                scanner.close(); // Fecha o antigo se existir e estiver inútil
            }
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    // Métodos do InputManager para usar getScanner()
    public static int lerInteiro(String mensagem, int min, int max) {
        Scanner currentScanner = getScanner(); // Usar o scanner garantido como aberto
        while (true) {
            System.out.print(mensagem + " (" + min + " - " + max + "): ");
            String input = null;
            try {
                input = currentScanner.nextLine().trim(); // Usar currentScanner
                if (input.isEmpty()) {
                    System.out.println("Entrada vazia. Digite um numero entre " + min + " e " + max + ".");
                    continue;
                }
                int valor = Integer.parseInt(input);
                if (valor < min || valor > max) {
                    System.out.println("Fora do intervalo. Digite um numero entre " + min + " e " + max + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            } catch (NoSuchElementException e) {
                // Esta exceção pode ocorrer se o stream for fechado prematuramente
                throw new RuntimeException("Entrada nao disponivel.", e);
            }
        }
    }

    public static String lerString(String mensagem) {
        Scanner currentScanner = getScanner();
        while (true) { // Adicione um loop while(true)
            System.out.print(mensagem + ": ");
            try {
                String input = currentScanner.nextLine();
                if (!input.trim().isEmpty()) { // Se a entrada não for vazia (após remover espaços)
                    return input; // Retorna a entrada válida
                } else {
                    System.out.println("Entrada não pode ser vazia. Por favor, digite algo."); // Mensagem de erro
                }
            } catch (NoSuchElementException e) {
                throw new RuntimeException("Entrada nao disponivel.", e);
            }
        }
    }

    public static boolean lerSimNao(String mensagem) {
        Scanner currentScanner = getScanner(); // Usar o scanner garantido como aberto
        while (true) {
            System.out.print(mensagem + " (s/n): ");
            try {
                String input = currentScanner.nextLine().trim().toLowerCase(); // Usar currentScanner
                if (input.equals("s")) {
                    return true;
                } else if (input.equals("n")) {
                    return false;
                } else {
                    System.out.println("Resposta invalida. Por favor, digite 's' para sim ou 'n' para nao.");
                }
            } catch (NoSuchElementException e) {
                throw new RuntimeException("Entrada nao disponivel.", e);
            }
        }
    }

    public static void esperarEnter(String mensagem) {
        Scanner currentScanner = getScanner(); // Usar o scanner garantido como aberto
        System.out.print(mensagem);
        try {
            currentScanner.nextLine(); // Usar currentScanner
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Erro ao esperar ENTER: Entrada nao disponivel.", e);
        }
    }

    public static void fecharScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null; // Zera a referência para que getScanner() crie um novo
            System.out.println();
        }
    }
}