package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class QuestionListWindow extends Window {

    private JFrame questionListWindow;
    private JTextField entryContent;
    // private JTextArea entryQuestionBody;
    private JTextField entryQuestionBody;
    private DefaultTableModel tableModel; // Declaramos a variável como campo de classe

    QueryExecutions consultant = new QueryExecutions(); // Instaciando o consultor
    protected ArrayList<Question> questionsList = consultant.realizeConsult();

    private int selectedRow = -1;
    private JTable table;

    public QuestionListWindow() {

        questionListWindow = new JFrame();
        questionListWindow.setLayout(null);
        questionListWindow.setSize(754, 527);
        questionListWindow.setUndecorated(true);
        questionListWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionListWindow.setResizable(false);
        questionListWindow.setLocationRelativeTo(null);

        createButtons();
        createLabels();
        createTextFields();
        createTable();
        questionListWindow.setVisible(true);
    }

    @Override
    protected void createButtons() {
        JButton buttonLimpar = new JButton("LIMPAR");
        JButton buttonPesquisar = new JButton("PESQUISAR");
        JButton buttonDeletar = new JButton("DELETAR");
        JButton buttonVoltar = new JButton("VOLTAR");
        JButton buttonVisualizar = new JButton("VER QUESTÃO");

        buttonLimpar.setToolTipText("CLIQUE PARA LIMPAR AS ENTRADAS");
        buttonPesquisar.setToolTipText("CLIQUE PARA PESQUISAR");
        buttonDeletar.setToolTipText("CLIQUE PARA DELETAR");
        buttonVoltar.setToolTipText("CLIQUE PARA VOLTAR À TELA PRINCIPAL");

        buttonVoltar.setFocusable(false);
        buttonDeletar.setFocusable(false);
        buttonVisualizar.setFocusable(false);
        buttonPesquisar.setFocusable(false);
        buttonLimpar.setFocusable(false);

        questionListWindow.add(buttonPesquisar);
        questionListWindow.add(buttonDeletar);
        questionListWindow.add(buttonVoltar);
        questionListWindow.add(buttonVisualizar);
        questionListWindow.add(buttonLimpar);

        buttonPesquisar.setBounds(605, 160, 110, 30);
        buttonDeletar.setBounds(470, 435, 100, 30);
        buttonVoltar.setBounds(605, 435, 100, 30);
        buttonVisualizar.setBounds(115, 435, 150, 30);
        buttonLimpar.setBounds(605, 85, 110, 30);

        buttonLimpar.addActionListener(e -> {

            entryContent.setText(""); // Limpa o campo de tópico
            entryQuestionBody.setText(""); // Limpa o campo de texto da questão
            buildTable(questionsList); // Reconstrói a tabela com todas as questões
        
        });

        buttonVoltar.addActionListener(e -> {

            questionListWindow.dispose();
            questionListWindow = null;
            MainWindow mainWindow = new MainWindow();
        
        });

        buttonDeletar.addActionListener(e -> {

            int tabelRow = table.getSelectedRow();
            int dataToDelet = (int) table.getValueAt(tabelRow, 0);
            consultant.dataDelete(dataToDelet);

            buildTable(consultant.realizeConsult());
            questionsList = consultant.realizeConsult();

        });

        buttonPesquisar.addActionListener(e -> {
            // Obtém o texto das entradas
            String topico = entryContent.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa não
                                                                  // sensível a maiúsculas e minúsculas
            String corpoQuestao = entryQuestionBody.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa
                                                                             // não sensível a maiúsculas e minúsculas

            // Verifique se ambos os campos estão vazios
            if (topico.isEmpty() && corpoQuestao.isEmpty()) {
                // Ambos os campos estão vazios, não faça nada
                buildTable(questionsList);
                return;
            }

            // Crie uma nova lista para armazenar as questões correspondentes à pesquisa
            ArrayList<Question> questoesFiltradas = new ArrayList<>();

            // Percorra a lista existente de ListaQuestoes
            for (Question questao : questionsList) {
                String topicoQuestao = questao.getContent().toLowerCase();
                String descricaoQuestao = questao.getQuestion().toLowerCase();

                // Verifique se a disciplina corresponde

                if (((topicoQuestao.contains(topico) && !topico.isEmpty())
                        && descricaoQuestao.contains(corpoQuestao))) {
                    questoesFiltradas.add(questao);
                } else if (((topico.isEmpty()) && descricaoQuestao.contains(corpoQuestao))) {
                    questoesFiltradas.add(questao);
                }

            }

            // Atualize a tabela para exibir as questões na nova lista
            buildTable(questoesFiltradas);
        });

        buttonVisualizar.addActionListener(e -> {
            if (selectedRow >= 0) {

                Question selectedQuestion = new Question();

                int id = (int) (table.getValueAt(selectedRow, 0));

                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // Se encontrar a questão, saia do loop
                    }
                }

                QuestionViewWindow questionViewWindowWindow = new QuestionViewWindow(selectedQuestion,
                        questionListWindow);

            }
        });
    }

    protected void createLabels() {
        JLabel labelQuestionTable = new JLabel("TABELA DE QUESTÕES");
        JLabel labelContent = new JLabel("TÓPICO");
        JLabel labelQuestionBody = new JLabel("TEXTO DA QUESTÃO");
        JLabel labelTable_ID = new JLabel("ID");
        JLabel labelTable_Disciplina = new JLabel("DISCIPLINA");
        JLabel labelTable_CorpoQuestao = new JLabel("TEXTO DA QUESTÃO");

        questionListWindow.add(labelQuestionTable);
        questionListWindow.add(labelContent);
        questionListWindow.add(labelQuestionBody);
        questionListWindow.add(labelTable_ID);
        questionListWindow.add(labelTable_Disciplina);
        questionListWindow.add(labelTable_CorpoQuestao);

        labelQuestionTable.setBounds(275, 50, 140, 25);
        labelContent.setBounds(115, 85, 75, 30);
        labelQuestionBody.setBounds(115, 130, 135, 25);
        labelTable_ID.setBounds(115, 215, 75, 25);
        labelTable_Disciplina.setBounds(195, 215, 100, 25);
        labelTable_CorpoQuestao.setBounds(290, 215, 220, 25);

    }

    private void checkCharacterLimit() {
        int maxCharacters = 500;
        if (entryQuestionBody.getText().length() > maxCharacters) {
            String limitedText = entryQuestionBody.getText().substring(0, maxCharacters);
            entryQuestionBody.setText(limitedText);
        }
    }

    private void createTextFields() {
        entryContent = new JTextField();
        entryQuestionBody = new JTextField();

        questionListWindow.add(entryQuestionBody);

        entryQuestionBody.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkCharacterLimit();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkCharacterLimit();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components do not fire these events.
            }
        });

        questionListWindow.add(entryContent);
        questionListWindow.add(entryQuestionBody);
        entryContent.setBounds(190, 85, 380, 30);
        entryQuestionBody.setBounds(250, 130, 320, 30);

    }

    private void createTable() {

        String[] colunas = { "ID", "Disciplina", "Tópico", "Contéudo", "Nível" };
        tableModel = new DefaultTableModel(colunas, 0);

        buildTable(questionsList);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        questionListWindow.add(scrollPane);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);// ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);// DISCIPLINA
        table.getColumnModel().getColumn(2).setPreferredWidth(130);// TÓPICO
        table.getColumnModel().getColumn(3).setPreferredWidth(300);// CONTEÚDO
        table.getColumnModel().getColumn(4).setPreferredWidth(30);// DIFICULDADE

        // Adicione um ListSelectionListener à tabela
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow(); // Atualize a linha selecionada
            }
        });

    }

    private void buildTable(ArrayList<Question> questions) {
        // Limpe o modelo da tabela

        tableModel.setRowCount(0);

        // Adicione as questões filtradas ao modelo da tabela
        for (Question question : questions) {
            tableModel.addRow(new Object[] { question.getId(), question.getSchoolSubject(), question.getContent(),
                    question.getQuestion(), question.getDifficult() });
        }
    }

}