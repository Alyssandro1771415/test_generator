package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class SelectQuestionWindow extends Window {

    private JFrame questionListWindow;
    private JTextField entryContent;
    // private JTextArea entryQuestionBody;
    private JTextField entryQuestionBody;
    private DefaultTableModel tableModel; // Declaramos a variável como campo de classe
    private QueryExecutions query = new QueryExecutions();
    private ArrayList<Question> questionsList;
    private ArrayList<Question> testQuestionsList;
    private int selectedRow = -1;
    private JTable table;
    private Test newTest;
    private int questionsNumber = -1;
    JLabel labelQuestionsNumber;
    private int testsNumber;
    private String schoolSubject;

    public SelectQuestionWindow(Test newTest, int testsNumber, String schoolSubject) {
        this.newTest = newTest;
        this.questionsList = new ArrayList<>();
        this.testQuestionsList = new ArrayList<>();
        this.testsNumber = testsNumber;
        this.schoolSubject = schoolSubject;

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
        JButton buttonClean = new JButton("LIMPAR");
        JButton buttonSearch = new JButton("PESQUISAR");
        JButton buttonAdd = new JButton("ADICIONAR");
        JButton buttonReturn = new JButton("VOLTAR");
        JButton buttonViewQuestion = new JButton("VER QUESTÃO");
        JButton buttonFinish = new JButton("FINALIZAR");

        buttonClean.setToolTipText("CLIQUE PARA LIMPAR AS ENTRADAS");
        buttonSearch.setToolTipText("CLIQUE PARA PESQUISAR");
        buttonAdd.setToolTipText("CLIQUE PARA ADICIONAR");
        buttonReturn.setToolTipText("CLIQUE PARA VOLTAR À TELA PRINCIPAL");
        buttonFinish.setToolTipText("CLIQUE PARA FINALIZAR");

        buttonReturn.setFocusable(false);
        buttonAdd.setFocusable(false);
        buttonViewQuestion.setFocusable(false);
        buttonSearch.setFocusable(false);
        buttonClean.setFocusable(false);
        buttonFinish.setFocusable(false);

        questionListWindow.add(buttonSearch);
        questionListWindow.add(buttonAdd);
        questionListWindow.add(buttonReturn);
        questionListWindow.add(buttonViewQuestion);
        questionListWindow.add(buttonClean);
        questionListWindow.add(buttonFinish);

        buttonSearch.setBounds(605, 160, 110, 30);
        buttonReturn.setBounds(605, 435, 100, 30);
        buttonViewQuestion.setBounds(115, 435, 150, 30);
        buttonClean.setBounds(605, 85, 110, 30);
        buttonAdd.setBounds(470, 435, 100, 30);
        buttonReturn.setBounds(335, 435, 100, 30);
        buttonFinish.setBounds(605, 435, 100, 30);

        buttonClean.addActionListener(e -> {
            entryContent.setText("");
            entryQuestionBody.setText("");
            buildTable(questionsList);
        });

        buttonReturn.addActionListener(e -> {
            questionListWindow.dispose();
            questionListWindow = null;
            MainWindow mainWindow = new MainWindow();
        });

        buttonFinish.addActionListener(e -> {
            questionListWindow.dispose();
            questionListWindow = null;
            newTest.setQuestionsList(testQuestionsList);
            createTestFile(newTest); // IMPLEMENTAÇÃO BACK-END
        });

        buttonAdd.addActionListener(e -> {

            if (selectedRow >= 0) {

                Question selectedQuestion = new Question();

                int id = (int) (table.getValueAt(selectedRow, 0));

                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // Se encontrar a questão, saia do loop
                    }
                }
                questionsNumber++;
                testQuestionsList.add(selectedQuestion);
                questionsList.remove(selectedQuestion);
                buildTable(questionsList);
                labelQuestionsNumber.setText("NÚMERO DE QUESTÕES ADICIONADAS ATÉ O MOMENTO: " + (1 + questionsNumber));

            }
            entryContent.setText("");
            entryQuestionBody.setText("");
        });

        buttonSearch.addActionListener(e -> {
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

        buttonViewQuestion.addActionListener(e -> {
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

    private void createTestFile(Test newTest) {
        // IMPLEMENTAÇÃO BACK-END

        DirectorySelector selector = new DirectorySelector();
        String chossedDirectory = selector.directorySelector();

        Test test = new Test(newTest.getInstitution(), newTest.getSchoolSubject(), newTest.getEducatorName(),
                newTest.getTestsNumber(), questionsList);
        test.generateFile(testQuestionsList, chossedDirectory, testsNumber);

        ConcludedPoPWindow concludeWindow = new ConcludedPoPWindow();

    }

    protected void createLabels() {
        JLabel labelQuestionTable = new JLabel("TABELA DE QUESTÕES");
        labelQuestionsNumber = new JLabel("NÚMERO DE QUESTÕES ADICIONADAS ATÉ O MOMENTO: " + (1 + questionsNumber));
        JLabel labelContent = new JLabel("TÓPICO");
        JLabel labelQuestionBody = new JLabel("TEXTO DA QUESTÃO");
        JLabel labelTable_ID = new JLabel("ID");
        JLabel labelTable_Disciplina = new JLabel("DISCIPLINA");
        JLabel labelTable_CorpoQuestao = new JLabel("TEXTO DA QUESTÃO");

        questionListWindow.add(labelQuestionTable);
        questionListWindow.add(labelQuestionsNumber);
        questionListWindow.add(labelContent);
        questionListWindow.add(labelQuestionBody);
        questionListWindow.add(labelTable_ID);
        questionListWindow.add(labelTable_Disciplina);
        questionListWindow.add(labelTable_CorpoQuestao);

        labelQuestionTable.setBounds(275, 50, 140, 25);
        labelQuestionsNumber.setBounds(150, 480, 450, 25);
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
        // Adicionando linha ao arrayList para testar - IMPLEMENTAÇÃO BACK-END

        for (int i = 0; i < query.realizeConsult().size(); i++) {

            if (schoolSubject != null && schoolSubject.equals(query.realizeConsult().get(i).getSchoolSubject())) {
                questionsList.add(query.realizeConsult().get(i));
            }
        }

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