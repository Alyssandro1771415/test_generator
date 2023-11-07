package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;



public class SelectQuestionWindow extends Window {


    private JFrame questionListWindow;
    private JTextField entryContent;
    //private JTextArea entryQuestionBody;
    private JTextField entryQuestionBody;
    private DefaultTableModel tableModel; // Declaramos a variável como campo de classe
    private ArrayList<Question> questionsList;
    private ArrayList<Question> testQuestionsList;
    private int selectedRow = -1;
    private JTable table;
    private Test newTest;
    private int questionsNumber = -1;
    JLabel labelQuestionsNumber;


    public SelectQuestionWindow(Test newTest) {
        this.newTest = newTest;
        this.questionsList = new ArrayList<>();
        this.testQuestionsList = new ArrayList<>();
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
        JButton buttonSearch = new JButton ("PESQUISAR");
        JButton buttonAdd = new JButton ("ADICIONAR");
        JButton buttonReturn = new JButton ("VOLTAR");
        JButton buttonViewQuestion = new JButton ("VER QUESTÃO");
        JButton buttonFinish = new JButton ("FINALIZAR");

        buttonClean.setToolTipText("CLIQUE PARA LIMPAR AS ENTRADAS");
        buttonSearch.setToolTipText ("CLIQUE PARA PESQUISAR");
        buttonAdd.setToolTipText ("CLIQUE PARA ADICIONAR");
        buttonReturn.setToolTipText ("CLIQUE PARA VOLTAR À TELA PRINCIPAL");
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


        buttonSearch.setBounds (605, 160, 110, 30);
        buttonReturn.setBounds (605, 435, 100, 30);
        buttonViewQuestion.setBounds (115, 435, 150, 30);
        buttonClean.setBounds(605, 85, 110, 30);
        buttonAdd.setBounds (470, 435, 100, 30);
        buttonReturn.setBounds (335, 435, 100, 30);
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
            createTestFile(newTest); //IMPLEMENTAÇÃO BACK-END
        });

        buttonAdd.addActionListener(e -> {

            if (selectedRow >= 0) {

                Question selectedQuestion = new Question();

                int id = (int)(table.getValueAt(selectedRow, 0));

                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // Se encontrar a questão, saia do loop
                    }
                }
                questionsNumber++;
                testQuestionsList.add(selectedQuestion);
                labelQuestionsNumber.setText("NÚMERO DE QUESTÕES ADICIONADAS ATÉ O MOMENTO: " + (1 + questionsNumber));


            }
            entryContent.setText("");
            entryQuestionBody.setText("");
        });

        buttonSearch.addActionListener(e -> {
            // Obtém o texto das entradas
            String topico = entryContent.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa não sensível a maiúsculas e minúsculas
            String corpoQuestao = entryQuestionBody.getText().toLowerCase(); // Use toLowerCase() para tornar a pesquisa não sensível a maiúsculas e minúsculas

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

                if (((topicoQuestao.contains(topico) && !topico.isEmpty()) && descricaoQuestao.contains(corpoQuestao))) {
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

                int id = (int)(table.getValueAt(selectedRow, 0));

                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // Se encontrar a questão, saia do loop
                    }
                }

                QuestionViewWindow questionViewWindowWindow = new QuestionViewWindow(selectedQuestion, questionListWindow);

            }
        });
    }

    private void createTestFile(Test newTest) {
        //IMPLEMENTAÇÃO BACK-END

        //SE TUDO SAIR OK
            System.out.println(newTest.toString());
            ConcludedPoPWindow concludedPoPWindow = new ConcludedPoPWindow();

    }

    protected void createLabels(){
        JLabel labelQuestionTable = new JLabel ("TABELA DE QUESTÕES");
        labelQuestionsNumber = new JLabel ("NÚMERO DE QUESTÕES ADICIONADAS ATÉ O MOMENTO: " + (1 + questionsNumber));
        JLabel labelContent = new JLabel ("TÓPICO");
        JLabel labelQuestionBody = new JLabel ("TEXTO DA QUESTÃO");
        JLabel labelTable_ID = new JLabel ("ID");
        JLabel labelTable_Disciplina = new JLabel ("DISCIPLINA");
        JLabel labelTable_CorpoQuestao = new JLabel ("TEXTO DA QUESTÃO");

        questionListWindow.add(labelQuestionTable);
        questionListWindow.add(labelQuestionsNumber);
        questionListWindow.add(labelContent);
        questionListWindow.add(labelQuestionBody);
        questionListWindow.add(labelTable_ID);
        questionListWindow.add(labelTable_Disciplina);
        questionListWindow.add(labelTable_CorpoQuestao);

        labelQuestionTable.setBounds (275, 50, 140, 25);
        labelQuestionsNumber.setBounds (150, 480, 450, 25);
        labelContent.setBounds (115, 85, 75, 30);
        labelQuestionBody.setBounds (115, 130, 135, 25);
        labelTable_ID.setBounds (115, 215, 75, 25);
        labelTable_Disciplina.setBounds (195, 215, 100, 25);
        labelTable_CorpoQuestao.setBounds (290, 215, 220, 25);


    }

    private void checkCharacterLimit() {
        int maxCharacters = 500;
        if (entryQuestionBody.getText().length() > maxCharacters) {
            String limitedText = entryQuestionBody.getText().substring(0, maxCharacters);
            entryQuestionBody.setText(limitedText);
        }
    }

    private void createTextFields(){
        entryContent = new JTextField ();
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
        entryContent.setBounds (190, 85, 380, 30);
        entryQuestionBody.setBounds(250, 130, 320, 30);

    }

    private void createTable() {
        // Adicionando linha ao arrayList para testar - IMPLEMENTAÇÃO BACK-END
        ArrayList<String> items1 = new ArrayList<>();
        items1.add("Decréscimo da demanda por educação nas áreas urbanas.");
        items1.add("Melhoria das condições de trabalho para a classe operária.");
        items1.add("Aumento da igualdade de renda entre a classe trabalhadora e a elite.");
        items1.add("Crescimento das cidades e das favelas urbanas.");
        items1.add("Redução das jornadas de trabalho e aumento dos salários.");
        items1.add("Crescimento das cidades e das favelas urbanas.");


        ArrayList<String> items4 = new ArrayList<>();
        items4.add("92");
        items4.add("118");


        ArrayList<String> items7 = new ArrayList<>();
        items7.add("Metano (CH4)");
        items7.add("Nitrogênio (N2)");
        items7.add("Oxigênio (O)");
        items7.add("Nenhuma das alternativas.");

        ArrayList<String> items9 = new ArrayList<>();
        items9.add("a^2 + b^2 = c");
        items9.add("a^2 - b^2 = c");
        items9.add("a + b = c^2");
        items9.add("a + b = c");
        items9.add("a^2 * b^2 = c^2");
        items9.add("Nenhuma das alternativas.");

        ArrayList<String> items2 = new ArrayList<>();



        questionsList.add(new Question(1, "História", "Revolução Industrial","A Revolução Industrial, que teve início no século XVIII na Inglaterra, transformou drasticamente a sociedade e a economia mundial. Uma das inovações mais impactantes foi o surgimento das fábricas e a mecanização da produção. No entanto, essas mudanças levaram a diversas questões sociais Pergunta: Qual foi uma das principais consequências sociais da Revolução Industrial?No entanto, essas mudanças levaram a diversas questões sociais Pergunta: Qual foi uma das principais consequências sociais da Revolução Industrial?No entanto, essas mudanças levaram a nças levaram a nças levaram a nças levaram?\n", 1, items1));

        questionsList.add(new Question(4, "Química", "Tabela Periódica" ,"A tabela periódica é uma ferramenta fundamental na química, organizando os elementos com base em suas propriedades. Ela é composta por várias linhas horizontais (períodos) e colunas verticais (grupos). Além disso, os elementos são classificados em metais, não metais e semimetais. Quantos elementos químicos estão presentes na tabela periódica moderna?", 2, items4));
        questionsList.add(new Question(7, "Geografia", "Mudanças Climáticas" ,"As mudanças climáticas globais têm sido uma preocupação crescente devido ao aumento das emissões de gases de efeito estufa. Essas mudanças afetam os padrões climáticos em todo o mundo, causando eventos climáticos extremos. Qual é um dos principais gases de efeito estufa que contribui para o aquecimento global", 3, items7));
        questionsList.add(new Question(9, "Matemática", "Teorema de Pitágoras","Qual é a fórmula matemática que representa o Teorema de Pitágoras?", 2, items9));

        questionsList.add(new Question(2, "Português","Tempo verbal", "O que é pretérito perfeito?", 2, items2));

        String[] colunas = {"ID", "Disciplina", "Tópico", "Contéudo", "Nível"};
        tableModel = new DefaultTableModel(colunas, 0);

        buildTable(questionsList);


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
            tableModel.addRow(new Object[]{question.getId(), question.getSchoolSubject(),question.getContent(),
                    question.getQuestion(), question.getDifficult()});
        }
    }

}