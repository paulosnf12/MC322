// src/com/rpg/itens/ArmaDropSpec.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Representa uma especificação para a criação de uma instância de {@link Arma} que pode ser dropada por um monstro.
 * Esta classe armazena o nome completo da classe da arma, um offset de dano base e o nível mínimo
 * da fase em que esta especificação de drop é válida. O objetivo é permitir a instanciação da arma
 * somente no momento em que ela é efetivamente "dropada" por um monstro, implementando assim
 * o conceito de Agregação e evitando a criação desnecessária de instâncias de objetos {@link Arma}.
 *
 * Ao invés de um monstro "possuir" uma {@link Arma}, ele "sabe como criar" uma quando necessário.
 */
@XmlRootElement(name = "armaDropSpec") // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class ArmaDropSpec {
    /**
     * O nome completo da classe da Arma a ser instanciada (ex: "com.rpg.itens.EspadaMadeira").
     * Utilizado para carregamento dinâmico da classe via reflexão.
     */
    // REMOVIDO "final" para permitir que o JAXB defina o valor após a desserialização.
    private String className;

    /**
     * Um valor de offset que será adicionado ao nível da fase para calcular o dano base final da arma.
     * Este valor é uma constante para a especificação, enquanto o escalonamento final ocorre com o {@code nivelFase}.
     */
    // REMOVIDO "final" para permitir que o JAXB defina o valor após a desserialização.
    private int baseDamageOffset;

    /**
     * O nível mínimo de fase a partir do qual esta especificação de drop de arma é considerada válida.
     * Pode ser usado para filtrar dinamicamente as armas que um monstro pode dropar em diferentes fases.
     */
    // REMOVIDO "final" para permitir que o JAXB defina o valor após a desserialização.
    private int minLevel;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public ArmaDropSpec() {
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.className = "";
        this.baseDamageOffset = 0;
        this.minLevel = 0;
    }

    /**
     * Construtor para criar uma especificação de drop de arma.
     *
     * @param className O nome completo da classe da Arma (e.g., "com.rpg.itens.EspadaMadeira").
     * @param baseDamageOffset O offset de dano base que será somado ao nível da fase para determinar o dano da arma.
     * @param minLevel O nível mínimo da fase para que esta especificação de drop seja válida.
     */
    public ArmaDropSpec(String className, int baseDamageOffset, int minLevel) {
        this.className = className;
        this.baseDamageOffset = baseDamageOffset;
        this.minLevel = minLevel;
    }

    /**
     * Instancia e retorna uma nova arma baseada nesta especificação.
     * O dano base final da arma é calculado somando o {@code baseDamageOffset} ao {@code nivelFase} atual.
     * Utiliza reflexão para encontrar e invocar o construtor da classe da arma que aceita um único parâmetro {@code int} (danoBase).
     *
     * @param nivelFase O nível da fase atual, usado para escalonar o dano da arma.
     * @return Uma nova instância de {@link Arma} com o dano calculado, ou {@code null} em caso de erro na instanciação.
     */
    // O método instantiate() não precisa de anotação JAXB, pois é um método de comportamento, não um campo para serialização.
    public Arma instantiate(int nivelFase) {
        try {
            // Tenta carregar a classe da arma usando o nome completo.
            Class<?> cls = Class.forName(className);

            // Assumindo que todas as subclasses de Arma têm um construtor que aceita um único 'int' como dano base.
            // Ex: public EspadaMadeira(int danoBase)
            Constructor<?> constructor = cls.getConstructor(int.class);

            // Calcula o dano final da arma somando o offset base ao nível da fase.
            int finalDanoBase = baseDamageOffset + nivelFase;

            // Cria uma nova instância da arma, passando o dano calculado.
            return (Arma) constructor.newInstance(finalDanoBase);

        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe da arma não encontrada: " + className);
            // Para depuração, você pode printar o stack trace:
            // e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("Erro: Construtor (int danoBase) não encontrado para a classe: " + className + ". Verifique se o construtor correto existe.");
            // e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Erro ao instanciar arma '" + className + "': " + e.getMessage());
            // e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Erro: A classe " + className + " não pode ser convertida para Arma.");
            // e.printStackTrace();
        }
        return null; // Retorna null em caso de qualquer erro na instanciação.
    }

    /**
     * Retorna o nível mínimo da fase para que esta especificação de drop seja válida.
     * @return O nível mínimo de fase da especificação.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public int getMinLevel() {
        return minLevel;
    }
    // ADICIONADO PARA JAXB: Setter para minLevel, necessário para a desserialização do JAXB.
    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    /**
     * Retorna o nome da classe da arma desta especificação.
     * @return O nome completo da classe da arma.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public String getClassName() {
        return className;
    }
    // ADICIONADO PARA JAXB: Setter para className, necessário para a desserialização do JAXB.
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Retorna o offset de dano base da especificação.
     * @return O offset de dano base.
     */
    @XmlElement // ADICIONADO PARA JAXB
    public int getBaseDamageOffset() {
        return baseDamageOffset;
    }
    // ADICIONADO PARA JAXB: Setter para baseDamageOffset, necessário para a desserialização do JAXB.
    public void setBaseDamageOffset(int baseDamageOffset) {
        this.baseDamageOffset = baseDamageOffset;
    }
}