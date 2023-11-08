package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRandomTestWindow extends JPanel {
    private JFrame addRandomTestWindow;
    private JLabel labelQuestionsTotalNumber;
    private JLabel labelContentSelector;
    private JLabel labelQuestionsNumberPerLevel;
    private JLabel labelTopicsNumber;
    private JButton buttonFinish;
    private JButton buttonCancel;
    private JCheckBox checkbox_NDA_Option;
    private JCheckBox checkBoxNoClosedQuestions;
    private JButton buttonAddContent;
    private JTextField entryMediumCount;
    private JTextField entryHardCount;
    private JTextField entryEasyCount;

    private Map<String, String> contentList;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private Test randomTest;
    private ArrayList<String> contentsSelecteds = new ArrayList<>();
    private int contentsCount = 0;
    private boolean checkboxCloseQuestionValue;
    private boolean checkboxNdaValue;
    private int testsNumber;

    QueryExecutions query = new QueryExecutions();
    ArrayList<Question> questions = query.realizeConsult();

    public AddRandomTestWindow(Test randomTest, int testsNumber) {
        this.randomTest = randomTest;
        this.contentList = new HashMap<>();
        addRandomTestWindow = new JFrame();
        addRandomTestWindow.setLayout(null);
        addRandomTestWindow.setSize(787, 557);
        addRandomTestWindow.setUndecorated(true);
        addRandomTestWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addRandomTestWindow.setResizable(false);
        addRandomTestWindow.setLocationRelativeTo(null);
        addRandomTestWindow.setVisible(true);
        this.testsNumber = testsNumber;

        createLabels();
        createButtons();
        createTextFields();
        createCheckBox();
        createTable();

    }

    private void updateTotalQuestionsLabel() {
        if (!entryEasyCount.getText().isEmpty() && !entryMediumCount.getText().isEmpty()
                && !entryHardCount.getText().isEmpty()) {
            try {
                int easyCount = Integer.parseInt(entryEasyCount.getText());
                int mediumCount = Integer.parseInt(entryMediumCount.getText());
                int hardCount = Integer.parseInt(entryHardCount.getText());
                int totalQuestions = easyCount + mediumCount + hardCount;
                labelQuestionsTotalNumber.setText("NÚMERO TOTALIZADO  DE QUESTÕES: " + totalQuestions);
            } catch (NumberFormatException ex) {
                // Tratar o caso em que não é um número inteiro
                JOptionPane.showMessageDialog(null,
                        "As entradas das dificuldades das questões devem ser somente valores inteiros.");
                labelQuestionsTotalNumber.setText("NÚMERO TOTALIZADO  DE QUESTÕES: ");
            }
        }
    }

    public static void addSchoolSubject(Map<String, String> map, String topico, String disciplina) {
        map.put(topico, disciplina);
    }

    private void createTextFields() {
        entryMediumCount = new JTextField(5);
        entryHardCount = new JTextField(5);
        entryEasyCount = new JTextField(5);

        entryEasyCount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }
        });

        entryMediumCount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }
        });

        entryHardCount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalQuestionsLabel();
            }
        });

        addRandomTestWindow.add(entryMediumCount);
        addRandomTestWindow.add(entryHardCount);
        addRandomTestWindow.add(entryEasyCount);
        entryMediumCount.setBounds(140, 95, 40, 25);
        entryHardCount.setBounds(140, 135, 40, 25);
        entryEasyCount.setBounds(140, 55, 40, 25);
    }

    private int[] getEntriesData() {
        if (!entryEasyCount.getText().isEmpty() && !entryMediumCount.getText().isEmpty()
                && !entryHardCount.getText().isEmpty()) {
            try {
                int[] testDifficulty = {
                        Integer.parseInt(entryEasyCount.getText()),
                        Integer.parseInt(entryMediumCount.getText()),
                        Integer.parseInt(entryHardCount.getText())
                };
                return testDifficulty;
            } catch (NumberFormatException ex) {
                // Tratar o caso em que não é um número inteiro
                JOptionPane.showMessageDialog(null,
                        "As entradas das dificuldades das questões devem ser somente valores inteiros.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "TODAS AS ENTRADAS DEVEM SER PREENCHIDAS.");
        }
        return null;
    }

    private void createTable() {

        contentList = new HashMap<>();

        for (Question q : questions) {

            if (!contentList.containsValue(q.getContent())) {

                addSchoolSubject(contentList, q.getContent(), q.getSchoolSubject());

            }

        }

        String schoolSubject = randomTest.getSchoolSubject();

        String[] colunas = { "Disciplina", "Tópico" };
        tableModel = new DefaultTableModel(colunas, 0);

        buildTable(contentList, schoolSubject);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        table.getColumnModel().getColumn(0).setPreferredWidth(50); // DISCIPLINA (coluna 1)
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // TÓPICO (coluna 2)
        addRandomTestWindow.add(scrollPane);

        // Adicione um ListSelectionListener à tabela
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow(); // Atualize a linha selecionada
            }
        });

    }

    private void buildTable(Map<String, String> contentList, String schoolSubject) {
        // Limpe o modelo da tabela
        tableModel.setRowCount(0);

        // Converta a disciplina para minúsculas
        String schoolSubjectLower = schoolSubject.toLowerCase();

        // Adicione as entradas do Map à tabela se a disciplina corresponder (ignorando
        // maiúsculas e minúsculas)
        for (Map.Entry<String, String> entry : contentList.entrySet()) {
            String disciplina = entry.getValue().toLowerCase(); // Obtenha a disciplina em minúsculas
            String topico = entry.getKey(); // Obtenha o tópico

            if (disciplina.contains(schoolSubjectLower)) {
                tableModel.addRow(new Object[] { entry.getValue(), topico });
            }
        }
    }

    private void createLabels() {
        JLabel labelEasy = new JLabel("Nº DE FÁCEIS:");
        JLabel labelMedium = new JLabel("Nº DE MÉDIAS:");
        JLabel labelHard = new JLabel("Nº DE DIFÍCEIS:");
        labelQuestionsTotalNumber = new JLabel("NÚMERO TOTALIZADO  DE QUESTÕES: ");
        labelContentSelector = new JLabel("SELECIONE OS CONTEÚDOS DAS QUESTÕES:");
        labelQuestionsNumberPerLevel = new JLabel("SELECIONE O NÚMERO DE QUESTÕES POR DIFICULDADE:");
        labelTopicsNumber = new JLabel("NÚMERO DE TÓPICOS ADICIONADOS:");
        addRandomTestWindow.add(labelEasy);
        addRandomTestWindow.add(labelMedium);
        addRandomTestWindow.add(labelHard);
        addRandomTestWindow.add(labelQuestionsTotalNumber);
        addRandomTestWindow.add(labelContentSelector);
        addRandomTestWindow.add(labelQuestionsNumberPerLevel);
        addRandomTestWindow.add(labelTopicsNumber);

        labelQuestionsNumberPerLevel.setBounds(25, 15, 355, 25);

        labelEasy.setBounds(25, 55, 85, 25);
        labelMedium.setBounds(25, 95, 85, 25);
        labelHard.setBounds(25, 135, 95, 25);

        labelQuestionsTotalNumber.setBounds(450, 55, 265, 25);
        labelTopicsNumber.setBounds(450, 135, 255, 25);

        labelContentSelector.setBounds(115, 200, 275, 30);

    }

    private void createButtons() {
        buttonFinish = new JButton("FINALIZAR");
        buttonCancel = new JButton("CANCELAR");
        buttonAddContent = new JButton("ADICIONAR CONTEÚDO");
        addRandomTestWindow.add(buttonFinish);
        addRandomTestWindow.add(buttonCancel);
        addRandomTestWindow.add(buttonAddContent);
        buttonFinish.setBounds(680, 520, 95, 30);
        buttonCancel.setBounds(10, 520, 100, 30);
        buttonAddContent.setBounds(480, 520, 175, 30);

        buttonCancel.addActionListener(e -> {
            addRandomTestWindow.dispose();
            addRandomTestWindow = null;
            MainWindow mainWindow = new MainWindow();

        });

        buttonFinish.addActionListener(e -> {

            DirectorySelector selector = new DirectorySelector();
            String chossedDirectory = selector.directorySelector();

            ArrayList<Question> selectedQuestions = new ArrayList<Question>();

            for (Question question : questions) {

                if (contentsSelecteds.contains(question.getContent())) {

                    selectedQuestions.add(question);

                }

            }

            Test test = new Test(randomTest.getInstitution(), randomTest.getSchoolSubject(),
                    randomTest.getEducatorName(), randomTest.getTestsNumber(), selectedQuestions);
                    
            test.generateFile(selectedQuestions, Integer.parseInt(entryEasyCount.getText()),
                    Integer.parseInt(entryMediumCount.getText()), Integer.parseInt(entryHardCount.getText()),
                    chossedDirectory, testsNumber);

            checkboxCloseQuestionValue = checkBoxNoClosedQuestions.isSelected();
            checkboxNdaValue = checkbox_NDA_Option.isSelected();
            int[] testDifficultyValues = getEntriesData();
            StringBuilder sb = new StringBuilder();
            sb.append("FÁCEIS: ").append(testDifficultyValues[0]).append("\n");
            sb.append("MÉDIAS: ").append(testDifficultyValues[1]).append("\n");
            sb.append("DIFÍCEIS: ").append(testDifficultyValues[2]).append("\n");
            sb.append("---------------------------------------------------------------\n");
            sb.append("Instituição: ").append(randomTest.getInstitution()).append("\n");
            sb.append("Disciplina: ").append(randomTest.getSchoolSubject()).append("\n");
            sb.append("Professor: ").append(randomTest.getEducatorName()).append("\n");
            sb.append("Número de avaliações: ").append(randomTest.getTestsNumber()).append("\n");
            sb.append("---------------------------------------------------------------\n");
            for (String content : contentsSelecteds) {
                sb.append(content).append("\n");
            }
            if (checkboxNdaValue) {
                sb.append("Opção N.D.A selecionada.\n");
            } else {
                sb.append("Opção N.D.A não selecionada.\n");
            }
            if (checkboxCloseQuestionValue) {
                sb.append("Sem questões fechadas.\n");
            } else {
                sb.append("Com questões fechadas.\n");
            }

            JOptionPane.showMessageDialog(null, sb);
            addRandomTestWindow.dispose();
            addRandomTestWindow = null;
            ConcludedPoPWindow concludedPoPWindow = new ConcludedPoPWindow();

        });

        buttonAddContent.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String topic = (String) table.getValueAt(selectedRow, 1); // Obtém o tópico da tabela
                contentsSelecteds.add(topic);
                contentsCount++;
                labelTopicsNumber.setText("NÚMERO DE TÓPICOS ADICIONADOS: " + contentsCount);
            }
        });
    }

    private void createCheckBox() {
        checkbox_NDA_Option = new JCheckBox("ADICIONAR A ALTERNATIVA N.D.A ( OPÇÃO NENHUMA DAS ALTERNATIVAS)");
        checkBoxNoClosedQuestions = new JCheckBox("SEM QUESTÕES FECHADAS");
        addRandomTestWindow.add(checkbox_NDA_Option);
        addRandomTestWindow.add(checkBoxNoClosedQuestions);
        checkbox_NDA_Option.setBounds(115, 420, 485, 25);
        checkBoxNoClosedQuestions.setBounds(115, 460, 195, 25);
    }

}
