/// Classe de testes para a classe CalculaNucleotideos
/// Alunos: Gabriel Antunes Cunha, Wender Alves da Silva

package org.atividadeJava;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CalculaNucleotideosTest {

    private File tempFile;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("sequencia-dna", ".txt");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            assertTrue(tempFile.delete(), "Falha ao deletar o arquivo temporário");
        }
    }

    @Test
    @DisplayName("Sequencia valida sem erros")
    void testSequenciaValidaSemErros() throws IOException {
        // Escreve uma sequência válida no arquivo
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("AAAGTCTGAC");
        }

        // Chama o metodo calculaNucleotideos
        CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
        int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

        // Verifica se o resultado é o esperado
        int[] esperado = {4, 2, 2, 2, 0};
        assertArrayEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Sequencia valida com erros < 10%")
    void testSequenciaValidaComErrosMinimos() throws IOException {
        // Escreve uma sequência com um caractere inválido no arquivo
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("AACTGTCGBA"); // 10 caracteres, 1 inválido (10%)
        }

        // Chama o método calculaNucleotideos
        CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
        int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

        // Verifica se o resultado é o esperado
        int[] esperado = {3, 2, 2, 2, 1};
        assertArrayEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Sequencia invalida com erros > 10%")
    void testSequenciaInvalidaComErrosElevados() throws IOException {
        // Escreve uma sequência com mais de 10% de caracteres inválidos no arquivo
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ABC TEM FALHAA"); // 13 caracteres, 4 inválidos
        }

        // Chama o metodo calculaNucleotideos
        CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
        int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

        // Verifica se o resultado é null
        assertNull(resultado);
    }

    @Test
    @DisplayName("Arquivo vazio")
    void testArquivoVazio() throws IOException {
        // Cria um arquivo vazio

        CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
        int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

        // Verifica se o resultado é o esperado
        int[] esperado = {0, 0, 0, 0, 0};
        assertArrayEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Arquivo não encontrado lança exceção")
    void testArquivoNaoEncontrado() {
        // Define um caminho de arquivo inexistente
        String caminhoInexistente = "caminho/inexistente.txt";

        // Chama o metodo calculaNucleotideos e espera uma exceção
        CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> calculaNucleotideos.calculaNucleotideos(caminhoInexistente));

        // Verifica se a mensagem da exceção está correta
        assertEquals("Arquivo não encontrado: " + caminhoInexistente, exception.getMessage());
    }

}