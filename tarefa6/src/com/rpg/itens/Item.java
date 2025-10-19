// src/com/rpg/itens/Item.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses

/**
 * Representa um item genérico no sistema de RPG. Todos os itens devem implementar este
 * interface para garantir que possuem um nome completo.
 */
// JAXB: Indica que Item pode ter a subclasse Arma
@XmlSeeAlso({Arma.class})
public interface Item {
    String getNomeCompleto();
}