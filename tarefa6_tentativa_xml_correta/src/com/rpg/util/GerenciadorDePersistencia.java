// src/com/rpg/util/GerenciadorDePersistencia.java
package com.rpg.util;

import com.rpg.game.Batalha;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Gerencia a persistência do estado do jogo (Batalha) usando JAXB para
 * serialização e deserialização para XML. Salva os jogos em um diretório "saves".
 */
public class GerenciadorDePersistencia {

    private static final String SAVE_DIRECTORY = "saves"; // Diretório onde os saves serão armazenados

    /**
     * Garante que o diretório de saves exista. Se não existir, ele é criado.
     */
    private static void ensureSaveDirectoryExists() {
        File dir = new File(SAVE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs(); // Cria o diretório e quaisquer diretórios pai necessários
        }
    }

    /**
     * Salva o estado atual de uma batalha em um arquivo XML.
     *
     * @param batalha A instância de Batalha a ser salva.
     * @param nomeBatalha O nome para o arquivo salvo (sem extensão).
     */
    public static void salvarBatalha(Batalha batalha, String nomeBatalha) {
        ensureSaveDirectoryExists(); // Garante que o diretório exista
        try {
            // Cria um contexto JAXB para a classe Batalha
            JAXBContext context = JAXBContext.newInstance(Batalha.class);
            // Cria um Marshaller para converter objetos Java em XML
            Marshaller marshaller = context.createMarshaller();
            // Configura para formatar o XML com indentação para melhor legibilidade
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Define o arquivo onde a batalha será salva
            File file = new File(SAVE_DIRECTORY + File.separator + nomeBatalha + ".xml");
            // Realiza a serialização do objeto Batalha para o arquivo
            marshaller.marshal(batalha, file);
            System.out.println("Batalha salva em: " + file.getAbsolutePath());
        } catch (JAXBException e) {
            System.err.println("Erro ao salvar batalha: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega uma batalha a partir de um arquivo XML.
     *
     * @param nomeBatalha O nome do arquivo salvo (sem extensão).
     * @return A instância de Batalha carregada, ou null se ocorrer um erro ou o arquivo não existir.
     */
    public static Batalha carregarBatalha(String nomeBatalha) {
        ensureSaveDirectoryExists(); // Garante que o diretório exista
        try {
            File file = new File(SAVE_DIRECTORY + File.separator + nomeBatalha + ".xml");
            if (!file.exists()) {
                System.out.println("Arquivo de save '" + nomeBatalha + ".xml' não encontrado.");
                return null;
            }

            // Cria um contexto JAXB para a classe Batalha
            JAXBContext context = JAXBContext.newInstance(Batalha.class);
            // Cria um Unmarshaller para converter XML em objetos Java
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Realiza a deserialização do arquivo para um objeto Batalha
            Batalha batalha = (Batalha) unmarshaller.unmarshal(file);
            // Chama o método afterUnmarshal para reinicializar campos transient
            batalha.afterUnmarshal(unmarshaller, null);
            System.out.println("Batalha '" + nomeBatalha + "' carregada com sucesso.");
            return batalha;
        } catch (JAXBException e) {
            System.err.println("Erro ao carregar batalha: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todos os nomes de jogos salvos disponíveis no diretório de saves.
     *
     * @return Uma lista de Strings com os nomes dos arquivos salvos (sem extensão).
     */
    public static List<String> listarJogosSalvos() {
        ensureSaveDirectoryExists(); // Garante que o diretório exista
        File dir = new File(SAVE_DIRECTORY);
        // Filtra apenas arquivos .xml
        String[] files = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        List<String> saveNames = new ArrayList<>();
        if (files != null) {
            // Remove a extensão ".xml" dos nomes dos arquivos
            for (String file : files) {
                saveNames.add(file.substring(0, file.length() - 4));
            }
        }
        return saveNames;
    }
}
