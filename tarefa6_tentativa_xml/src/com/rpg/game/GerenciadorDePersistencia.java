//src/com/rpg/game/GerenciadorDePersistencia.java

package com.rpg.game;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilitária para salvar e carregar o estado da Batalha usando JAXB.
 */
public class GerenciadorDePersistencia {

    private static final String SAVE_DIR = "saves"; // Diretório para armazenar os saves

    static {
        // Garante que o diretório de saves existe
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório de saves: " + e.getMessage());
        }
    }

    /**
     * Salva o objeto Batalha em um arquivo XML.
     * @param batalha O objeto Batalha a ser salvo.
     * @param nomeBatalha O nome do arquivo (sem extensão) para o save.
     */
    public static void salvarBatalha(Batalha batalha, String nomeBatalha) {
        try {
            JAXBContext context = JAXBContext.newInstance(Batalha.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formata o XML para leitura
            
            Path savePath = Paths.get(SAVE_DIR, nomeBatalha + ".xml");
            
            try (FileWriter writer = new FileWriter(savePath.toFile())) {
                marshaller.marshal(batalha, writer);
            }
            System.out.println("Jogo salvo em: " + savePath.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("Erro ao salvar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega um objeto Batalha a partir de um arquivo XML.
     * @param nomeBatalha O nome do arquivo (sem extensão) do save a ser carregado.
     * @return O objeto Batalha carregado, ou null em caso de erro ou arquivo não encontrado.
     */
    public static Batalha carregarBatalha(String nomeBatalha) {
        try {
            JAXBContext context = JAXBContext.newInstance(Batalha.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Path savePath = Paths.get(SAVE_DIR, nomeBatalha + ".xml");
            if (!Files.exists(savePath)) {
                System.err.println("Arquivo de save '" + nomeBatalha + ".xml' não encontrado.");
                return null;
            }

            try (FileReader reader = new FileReader(savePath.toFile())) {
                Batalha batalha = (Batalha) unmarshaller.unmarshal(reader);
                System.out.println("Jogo '" + nomeBatalha + "' carregado com sucesso!");
                return batalha;
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar o jogo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica se existem jogos salvos no diretório padrão.
     * @return true se houver pelo menos um arquivo .xml no diretório de saves, false caso contrário.
     */
    public static boolean existemJogosSalvos() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        String[] files = dir.list((d, name) -> name.toLowerCase().endsWith(".xml"));
        return files != null && files.length > 0;
    }

    /**
     * Lista os nomes dos jogos salvos disponíveis.
     * @return Uma lista de Strings com os nomes dos saves (sem extensão .xml).
     */
    public static List<String> listarJogosSalvos() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<>();
        }
        return Files.list(Paths.get(SAVE_DIR))
                    .filter(path -> path.toString().toLowerCase().endsWith(".xml"))
                    .map(path -> path.getFileName().toString().replace(".xml", ""))
                    .collect(Collectors.toList());
    }
}
