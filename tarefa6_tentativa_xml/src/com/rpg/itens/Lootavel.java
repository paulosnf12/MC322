// src/com/rpg/itens/Lootavel.java
package com.rpg.itens;

/**
 * Interface que define a capacidade de dropar um item (loot) quando implementada por uma classe.
 * Qualquer entidade que possa dropar itens deve implementar este interface.
 */
public interface Lootavel {
    Item droparLoot();
}
