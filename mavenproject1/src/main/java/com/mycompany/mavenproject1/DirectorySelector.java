package com.mycompany.mavenproject1;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class DirectorySelector {

    public String directorySelector() {
        // Crie uma instância do JFrame (pode ser uma janela vazia)
        JFrame frame = new JFrame();

        // Crie uma instância do JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Defina para selecionar apenas diretórios

        // Abra a janela de diálogo para escolher um diretório
        int resultado = fileChooser.showOpenDialog(frame);

        // Verifique se o usuário escolheu um diretório
        if (resultado == JFileChooser.APPROVE_OPTION) {
            // Obtenha o caminho do diretório selecionado
            String caminhoDiretorio = fileChooser.getSelectedFile().getAbsolutePath();
            return caminhoDiretorio;
        } else {
            return null; // Retorna nulo se nenhum diretório foi selecionado
        }
    }
}