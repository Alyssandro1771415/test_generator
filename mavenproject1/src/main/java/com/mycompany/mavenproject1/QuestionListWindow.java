package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class QuestionListWindow extends JPanel implements Window {

    // Declare instance variables
    private JFrame questionListWindow;
    private JTextField entryContent;
    private JTextField entryQuestionBody;
    private DefaultTableModel tableModel;
    private QueryExecutions consultant = new QueryExecutions();
    protected ArrayList<Question> questionsList = consultant.realizeConsult();
    private int selectedRow = -1;
    private JTable table;


    public QuestionListWindow() {

        // Initialize the JFrame for the question list window
        questionListWindow = new JFrame();
        questionListWindow.setLayout(null);
        questionListWindow.setSize(754, 527);
        questionListWindow.setUndecorated(true);
        questionListWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionListWindow.setResizable(false);
        questionListWindow.setLocationRelativeTo(null);

        // Create buttons, labels, text fields, and the table
        createButtons();
        createLabels();
        createTextFields();
        createTable();
        questionListWindow.setVisible(true);
    }

    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {
        // Create buttons with labels
        JButton buttonLimpar = new JButton("LIMPAR");
        JButton buttonPesquisar = new JButton("PESQUISAR");
        JButton buttonDeletar = new JButton("DELETAR");
        JButton buttonVoltar = new JButton("VOLTAR");
        JButton buttonVisualizar = new JButton("VER QUESTÃO");

        // Set tooltips for the buttons
        buttonLimpar.setToolTipText("CLIQUE PARA LIMPAR AS ENTRADAS");
        buttonPesquisar.setToolTipText("CLIQUE PARA PESQUISAR");
        buttonDeletar.setToolTipText("CLIQUE PARA DELETAR");
        buttonVoltar.setToolTipText("CLIQUE PARA VOLTAR À TELA PRINCIPAL");

        // Set focusable to false for better GUI experience
        buttonVoltar.setFocusable(false);
        buttonDeletar.setFocusable(false);
        buttonVisualizar.setFocusable(false);
        buttonPesquisar.setFocusable(false);
        buttonLimpar.setFocusable(false);

        // Add buttons to the window
        questionListWindow.add(buttonPesquisar);
        questionListWindow.add(buttonDeletar);
        questionListWindow.add(buttonVoltar);
        questionListWindow.add(buttonVisualizar);
        questionListWindow.add(buttonLimpar);

        // Set the bounds of each button
        buttonPesquisar.setBounds(605, 160, 110, 30);
        buttonDeletar.setBounds(470, 435, 100, 30);
        buttonVoltar.setBounds(605, 435, 100, 30);
        buttonVisualizar.setBounds(115, 435, 150, 30);
        buttonLimpar.setBounds(605, 85, 110, 30);

        // Add action listeners to the buttons
        buttonLimpar.addActionListener(e -> {
            entryContent.setText(""); // Clear the topic field
            entryQuestionBody.setText(""); // Clear the question text field
            buildTable(questionsList); // Rebuild the table with all questions
        });

        buttonVoltar.addActionListener(e -> {
            questionListWindow.dispose();
            questionListWindow = null;
            MainWindow mainWindow = new MainWindow();
        });

        buttonDeletar.addActionListener(e -> {
            int tableRow = table.getSelectedRow();
            int dataToDelete = (int) table.getValueAt(tableRow, 0);
            consultant.dataDelete(dataToDelete);

            buildTable(consultant.realizeConsult());
            questionsList = consultant.realizeConsult();
        });

        buttonPesquisar.addActionListener(e -> {
            // Get the text from the entries
            String topic = entryContent.getText().toLowerCase();
            String questionBody = entryQuestionBody.getText().toLowerCase();

            // Check if both fields are empty
            if (topic.isEmpty() && questionBody.isEmpty()) {
                // Both fields are empty, do nothing
                buildTable(questionsList);
                return;
            }

            // Create a new list to store questions matching the search
            ArrayList<Question> filteredQuestions = new ArrayList<>();

            // Iterate through the existing list of questions
            for (Question question : questionsList) {
                String topicQuestion = question.getContent().toLowerCase();
                String descriptionQuestion = question.getQuestion().toLowerCase();

                // Check if the topic and question body match
                if (((topicQuestion.contains(topic) && !topic.isEmpty()) && descriptionQuestion.contains(questionBody))) {
                    filteredQuestions.add(question);
                } else if ((topic.isEmpty()) && descriptionQuestion.contains(questionBody)) {
                    filteredQuestions.add(question);
                }
            }

            // Update the table to display the questions in the new list
            buildTable(filteredQuestions);
        });

        buttonVisualizar.addActionListener(e -> {
            if (selectedRow >= 0) {
                Question selectedQuestion = new Question();
                int id = (int) (table.getValueAt(selectedRow, 0));

                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // If found the question, exit the loop
                    }
                }

                QuestionViewWindow questionViewWindow = new QuestionViewWindow(selectedQuestion, questionListWindow);
            }
        });
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {
        // Create labels with text
        JLabel labelQuestionTable = new JLabel("TABELA DE QUESTÕES");
        JLabel labelContent = new JLabel("TÓPICO");
        JLabel labelQuestionBody = new JLabel("TEXTO DA QUESTÃO");
        JLabel labelTable_ID = new JLabel("ID");
        JLabel labelTable_Disciplina = new JLabel("DISCIPLINA");
        JLabel labelTable_CorpoQuestao = new JLabel("TEXTO DA QUESTÃO");

        // Add labels to the window
        questionListWindow.add(labelQuestionTable);
        questionListWindow.add(labelContent);
        questionListWindow.add(labelQuestionBody);
        questionListWindow.add(labelTable_ID);
        questionListWindow.add(labelTable_Disciplina);
        questionListWindow.add(labelTable_CorpoQuestao);

        // Set the bounds of each label
        labelQuestionTable.setBounds(275, 50, 140, 25);
        labelContent.setBounds(115, 85, 75, 30);
        labelQuestionBody.setBounds(115, 130, 135, 25);
        labelTable_ID.setBounds(115, 215, 75, 25);
        labelTable_Disciplina.setBounds(195, 215, 100, 25);
        labelTable_CorpoQuestao.setBounds(290, 215, 220, 25);
    }

    // Check and limit the character count in the question text field
    private void checkCharacterLimit() {
        int maxCharacters = 500;
        if (entryQuestionBody.getText().length() > maxCharacters) {
            String limitedText = entryQuestionBody.getText().substring(0, maxCharacters);
            entryQuestionBody.setText(limitedText);
        }
    }

    // Implementation of the createTextFields method
    private void createTextFields() {
        // Create text fields
        entryContent = new JTextField();
        entryQuestionBody = new JTextField();

        // Add the question text field and attach a document listener
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

        // Add text fields to the window
        questionListWindow.add(entryContent);
        questionListWindow.add(entryQuestionBody);
        entryContent.setBounds(190, 85, 380, 30);
        entryQuestionBody.setBounds(250, 130, 320, 30);
    }

    // Implementation of the createTable method
    private void createTable() {
        // Define column names for the table
        String[] columns = { "ID", "Disciplina", "Tópico", "Contéudo", "Nível" };

        // Initialize the table model with columns
        tableModel = new DefaultTableModel(columns, 0);

        // Build the table with the initial list of questions
        buildTable(questionsList);

        // Create the JTable and a scroll pane for it
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        // Add the scroll pane to the window
        questionListWindow.add(scrollPane);

        // Set preferred column widths for better display
        table.getColumnModel().getColumn(0).setPreferredWidth(30); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // DISCIPLINA
        table.getColumnModel().getColumn(2).setPreferredWidth(130); // TÓPICO
        table.getColumnModel().getColumn(3).setPreferredWidth(300); // CONTEÚDO
        table.getColumnModel().getColumn(4).setPreferredWidth(30); // DIFICULDADE

        // Add a ListSelectionListener to the table
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow(); // Update the selected row
            }
        });
    }

    // Implementation of the buildTable method
    private void buildTable(ArrayList<Question> questions) {
        // Clear the table model
        tableModel.setRowCount(0);

        // Add the filtered questions to the table model
        for (Question question : questions) {
            tableModel.addRow(new Object[] { question.getId(), question.getSchoolSubject(), question.getContent(),
                    question.getQuestion(), question.getDifficult() });
        }
    }
}