package com.rpg.util;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputManager {
    private static Scanner scanner = new Scanner(System.in);

    // Implemente os métodos aqui
    // ...
    // ... dentro da classe InputManager ...
public static int lerInteiro(String mensagem, int min, int max) {
    while (true) {
        System.out.print(mensagem + " (" + min + " - " + max + "): ");
        String input = null;
        try {
            input = scanner.nextLine().trim();
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
            throw new RuntimeException("Entrada nao disponivel.", e);
        }
    }
}

// ... dentro da classe InputManager ...
public static String lerString(String mensagem) {
    System.out.print(mensagem + ": ");
    try {
        return scanner.nextLine();
    } catch (NoSuchElementException e) {
        throw new RuntimeException("Entrada nao disponivel.", e);
    }
}

// ... dentro da classe InputManager ...
public static boolean lerSimNao(String mensagem) {
    while (true) {
        System.out.print(mensagem + " (s/n): ");
        try {
            String input = scanner.nextLine().trim().toLowerCase();
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

// ... dentro da classe InputManager ...
public static void esperarEnter(String mensagem) {
    System.out.print(mensagem);
    try {
        scanner.nextLine(); // Espera o usuário pressionar Enter
    } catch (NoSuchElementException e) {
        // Não é comum para esperar Enter, mas é bom tratar
        throw new RuntimeException("Erro ao esperar ENTER: Entrada nao disponivel.", e);
    }
}

// ... dentro da classe InputManager ...
public static void fecharScanner() {
    if (scanner != null) {
        scanner.close();
        System.out.println("Recursos do Scanner liberados.");
    }
}

}