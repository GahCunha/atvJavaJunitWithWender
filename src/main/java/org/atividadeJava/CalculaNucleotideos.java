/// Classe CalculaNucleotideos para calcular a quantidade de nucleotídeos em uma sequência de DNA
/// Alunos: Gabriel Antunes Cunha, Wender Alves da Silva

package org.atividadeJava;

import java.io.*;

public class CalculaNucleotideos {

    public int[] calculaNucleotideos(String filePath) throws FileNotFoundException {
        int[] resultado = new int[5]; // [A, C, G, T, erros]

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha = reader.readLine();
            if (linha == null || linha.isEmpty()) {
                return resultado; // Arquivo vazio retorna array zerado
            }

            int totalCaracteres = linha.length();
            int erros = 0;

            for (char c : linha.toCharArray()) {
                switch (c) {
                    case 'A': resultado[0]++; break;
                    case 'C': resultado[1]++; break;
                    case 'G': resultado[2]++; break;
                    case 'T': resultado[3]++; break;
                    default: erros++; break;
                }
            }

            resultado[4] = erros;
            if ((double) erros / totalCaracteres > 0.1) {
                return null; // Mais de 10% de caracteres inválidos retorna array zerado
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
