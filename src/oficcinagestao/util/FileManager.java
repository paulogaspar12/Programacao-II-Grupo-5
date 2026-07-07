/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
 




 
public class FileManager {

    private static final String DB_PATH = "DB/";

    /**
     * Garante que a pasta DB exista.
     */
    public static void ensureDBDirectory() {
        File dbDir = new File(DB_PATH);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }
    }

    /**
     * Lê todas as linhas de um arquivo.
     * @param filename Nome do arquivo (ex: users.txt)
     * @return Lista de strings com as linhas do arquivo
     */
    public static List<String> readLines(String filename) {
        ensureDBDirectory();
        List<String> lines = new ArrayList<>();
        File file = new File(DB_PATH + filename);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo " + filename + ": " + e.getMessage());
        }
        return lines;
    }

    /**
     * Escreve uma lista de linhas em um arquivo, substituindo o conteúdo existente.
     * @param filename Nome do arquivo
     * @param lines Lista de linhas a serem escritas
     */
    public static void writeLines(String filename, List<String> lines) {
        ensureDBDirectory();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_PATH + filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Adiciona uma linha ao final do arquivo.
     * @param filename Nome do arquivo
     * @param line Linha a ser adicionada
     */
    public static void appendLine(String filename, String line) {
        ensureDBDirectory();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_PATH + filename, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao adicionar linha no arquivo " + filename + ": " + e.getMessage());
        }
    }
     
}


