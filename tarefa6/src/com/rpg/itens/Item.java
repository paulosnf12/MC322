//Item.java
package com.rpg.itens;

/**
 * Representa um item genérico no sistema de RPG. Todos os itens devem implementar este
 * interface para garantir que possuem um nome completo.
 */
public interface Item {
    String getNomeCompleto(); // modificado de getNome() para getNomeCompleto()
}
