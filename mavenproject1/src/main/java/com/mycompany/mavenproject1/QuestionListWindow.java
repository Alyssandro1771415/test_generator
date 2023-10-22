package com.mycompany.mavenproject1;


import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class QuestionListWindow extends JPanel {

    private JFrame mainWindow;
    private JFrame questionListWindow;

    private JLabel labeltabelaQuestoes, labelTopico, labelCorpoQuestao, labelTV_ID, labelTV_Disciplina ,labelTV_CorpoQuestao;
    private JTextField entryTopico;
    private JTextArea entryCorpoQuestao;
    private DefaultTableModel tableModel; // Declaramos a variável como campo de classe

    queryExecutions consultor = new queryExecutions(); // Instaciando o consultor
    private ArrayList<Question> ListaQuestoes = consultor.realizeConsult(); //Colocando os dados do consultor da DB num arraylist para usar os dados
    
    private int linhaSelecionada = -1;
    private JTable table;
    

    public QuestionListWindow(JFrame mainWindow) {
        this.mainWindow = mainWindow;

        questionListWindow = new JFrame();
        questionListWindow.setLayout(null);
        questionListWindow.setSize(754, 527);
        questionListWindow.setUndecorated(true);
        questionListWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionListWindow.setResizable(false);
        questionListWindow.setLocationRelativeTo(null);

        instaciarBotoes();
        instaciarLabels();
        instaciarEntrys();
        instanciarTabela();
        mostrarJanela(true);
    }

    public void instaciarBotoes() {

        JButton buttonPesquisar = new JButton ("PESQUISAR");
        JButton buttonDeletar = new JButton ("DELETAR");
        JButton buttonVoltar = new JButton ("VOLTAR");
        JButton buttonVisualizar = new JButton ("VER QUESTÃO");

        buttonPesquisar.setToolTipText ("CLIQUE PARA PESQUISAR");
        buttonDeletar.setToolTipText ("CLIQUE PARA DELETAR");
        buttonVoltar.setToolTipText ("CLIQUE PARA VOLTAR À TELA PRINCIPAL");

        questionListWindow.add(buttonPesquisar);
        questionListWindow.add(buttonDeletar);
        questionListWindow.add(buttonVoltar);
        questionListWindow.add(buttonVisualizar);

        buttonPesquisar.setBounds (605, 160, 110, 30);
        buttonDeletar.setBounds (470, 435, 100, 30);
        buttonVoltar.setBounds (605, 435, 100, 30);
        buttonVisualizar.setBounds (115, 435, 150, 30);

        buttonVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                questionListWindow.dispose();
                questionListWindow = null;
                mainWindow.setVisible(true);
            }
        });

        buttonDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //IMPLEMENTAÇÃO BACK-END
            }
        });


        buttonPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o texto das entradas
                String topico = entryTopico.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa não sensível a maiúsculas e minúsculas
                String corpoQuestao = entryCorpoQuestao.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa não sensível a maiúsculas e minúsculas

                // Verifique se ambos os campos estão vazios
                if (topico.isEmpty() && corpoQuestao.isEmpty()) {
                    // Ambos os campos estão vazios, não faça nada
                    construirTabela();
                    return;
                }

                // Crie uma nova lista para armazenar as questões correspondentes à pesquisa
                ArrayList<Question> questoesFiltradas = new ArrayList<>();

                // Percorra a lista existente de ListaQuestoes
                for (Question questao : ListaQuestoes) {
                    String topicoQuestao = questao.getContent().toLowerCase();
                    String descricaoQuestao = questao.getQuestion().toLowerCase();

                    // Verifique se a disciplina corresponde
                    boolean topicoVazio = topico.isEmpty();


                    if (((topicoQuestao.contains(topico) && !topico.isEmpty()) && descricaoQuestao.contains(corpoQuestao))) {
                        questoesFiltradas.add(questao);
                    } else if (((topico.isEmpty()) && descricaoQuestao.contains(corpoQuestao))) {
                        questoesFiltradas.add(questao);
                    }

                }

                // Atualize a tabela para exibir as questões na nova lista
                atualizarTabela(questoesFiltradas);
            }
        });
        buttonVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (linhaSelecionada >= 0) {
                    // Obtém os valores das colunas da linha selecionada
                    Object id = table.getValueAt(linhaSelecionada, 0);
                    Object disciplina = table.getValueAt(linhaSelecionada, 1);
                    Object topico = table.getValueAt(linhaSelecionada, 2);
                    Object conteudo = table.getValueAt(linhaSelecionada, 3);
                    Object nivel = table.getValueAt(linhaSelecionada, 4);

                    // Imprime os valores
                    System.out.println("Linha selecionada a ser deletada:");
                    System.out.println("ID: " + id);
                    System.out.println("Disciplina: " + disciplina);
                    System.out.println("Tópico: " + topico);
                    System.out.println("Conteúdo: " + conteudo);
                    System.out.println("Nível: " + nivel);

                    // Você pode adicionar o código para deletar a linha aqui
                }
            }
        });

    }

    public void instaciarLabels(){
        labeltabelaQuestoes = new JLabel ("TABELA DE QUESTÕES");
        labelTopico = new JLabel ("TÓPICO");
        labelCorpoQuestao = new JLabel ("TEXTO DA QUESTÃO");
        labelTV_ID = new JLabel ("ID");
        labelTV_Disciplina = new JLabel ("DISCIPLINA");
        labelTV_CorpoQuestao = new JLabel ("TEXTO DA QUESTÃO");

        questionListWindow.add(labeltabelaQuestoes);
        questionListWindow.add(labelTopico);
        questionListWindow.add(labelCorpoQuestao);
        questionListWindow.add(labelTV_ID);
        questionListWindow.add(labelTV_Disciplina);
        questionListWindow.add(labelTV_CorpoQuestao);

        labeltabelaQuestoes.setBounds (275, 50, 140, 25);
        labelTopico.setBounds (115, 85, 75, 30);
        labelCorpoQuestao.setBounds (115, 130, 135, 25);
        labelTV_ID.setBounds (115, 215, 75, 25);
        labelTV_Disciplina.setBounds (195, 215, 100, 25);
        labelTV_CorpoQuestao.setBounds (290, 215, 220, 25);


    }

    public void instaciarEntrys(){
        entryTopico = new JTextField (5);
        entryCorpoQuestao = new JTextArea (5, 5);

        //Define a quebra de linha como automática
        entryCorpoQuestao.setLineWrap(true);

        //Faz com que quando houver quebra de linha, as palavras sejam transferidas inteiras para a próxima linha
        entryCorpoQuestao.setWrapStyleWord(true);

        questionListWindow.add(entryTopico);
        questionListWindow.add(entryCorpoQuestao);
        entryTopico.setBounds (190, 85, 380, 30);
        entryCorpoQuestao.setBounds (250, 130, 320, 60);

    }

    public void instanciarTabela() {

        String[] colunas = {"ID", "Disciplina", "Tópico", "Contéudo", "Nível"};
        tableModel = new DefaultTableModel(colunas, 0);

        construirTabela();


        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        questionListWindow.add(scrollPane);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);//DISCIPLINA
        table.getColumnModel().getColumn(2).setPreferredWidth(130);//TÓPICO
        table.getColumnModel().getColumn(3).setPreferredWidth(300);//CONTEÚDO
        table.getColumnModel().getColumn(4).setPreferredWidth(30);//DIFICULDADE


        // Adicione um ListSelectionListener à tabela
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    linhaSelecionada = table.getSelectedRow(); // Atualize a linha selecionada
                }
            }
        });

    }

    public void construirTabela() {
        tableModel.setRowCount(0); // Limpa o modelo da tabela

        for (Question questao : ListaQuestoes) {
            tableModel.addRow(new Object[]{questao.getId(), questao.getSchoolSubject(), questao.getContent(),
                    questao.getQuestion(), questao.getDifficult()});
        }
    }

    private void atualizarTabela(ArrayList<Question> questoes) {
        // Limpe o modelo da tabela
        tableModel.setRowCount(0);

        // Adicione as questões filtradas ao modelo da tabela
        for (Question questao : questoes) {
            tableModel.addRow(new Object[]{questao.getId(), questao.getSchoolSubject(),questao.getContent(),
                    questao.getQuestion(), questao.getDifficult()});
        }
    }

    public void mostrarJanela(boolean valor){
        questionListWindow.setVisible(valor);
    }

    private Question buscarQuestaoPorID(int id) {
        for (Question questao : ListaQuestoes) {
            if (questao.getId() == id) {
                return questao;
            }
        }
        return null; // Retorna null se a questão não for encontrada
    }

    private BufferedImage loadImage(String url) {
        BufferedImage img = null;
        InputStream is = getClass().getResourceAsStream(url);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}