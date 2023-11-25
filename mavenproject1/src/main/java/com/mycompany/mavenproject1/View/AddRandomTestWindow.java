package com.mycompany.mavenproject1.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.mycompany.mavenproject1.Model.Assessment;
import com.mycompany.mavenproject1.Model.Question;
import com.mycompany.mavenproject1.Repository.QueryExecutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRandomTestWindow extends JPanel implements Window{

    private JFrame addRandomTestWindow;
    private JLabel labelQuestionsTotalNumber;
    private JLabel labelTopicsNumber;
    private JCheckBox checkBoxNoClosedQuestions;
    private JTextField entryMediumCount;
    private JTextField entryHardCount;
    private JTextField entryEasyCount;
    private Map<String, String> contentList;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private Assessment randomTest;
    private ArrayList<String> contentsSelecteds = new ArrayList<>();
    private int contentsCount = 0;
    private boolean checkboxCloseQuestionValue;
    private int testsNumber;

    //DATABASE CONSULTATION
    QueryExecutions query = new QueryExecutions();
    ArrayList<Question> dataBaseQuestions = query.realizeConsult();

    public AddRandomTestWindow(Assessment randomTest, int testsNumber) {
        this.randomTest = randomTest;
        this.contentList = new HashMap<>();
        this.testsNumber = testsNumber;
        //FRAME CONFIGS
        addRandomTestWindow = new JFrame();
        addRandomTestWindow.setLayout(null);//ABSOLUTE LAYOUT
        addRandomTestWindow.setSize(787, 557);
        addRandomTestWindow.setUndecorated(true);//REMOVE THE TOP BAR
        addRandomTestWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addRandomTestWindow.setResizable(false);
        addRandomTestWindow.setLocationRelativeTo(null);//PUT THE FRAME LOCATION IN THE CENTER
        addRandomTestWindow.setVisible(true);

        createLabels();
        createButtons();
        createTextFields();
        createCheckBox();
        createTable();

    }

    //SPECIFIC LABEL UPDATER
    private void updateTotalQuestionsLabel() {

        //UPDATE THE LABEL ONLY WHEN ALL THE ENTRY SPACES ARE NOT EMPTY
        if (!entryEasyCount.getText().isEmpty() && !entryMediumCount.getText().isEmpty()
                && !entryHardCount.getText().isEmpty()) {
            try {
                int easyCount = Integer.parseInt(entryEasyCount.getText());
                int mediumCount = Integer.parseInt(entryMediumCount.getText());
                int hardCount = Integer.parseInt(entryHardCount.getText());
                int totalQuestions = easyCount + mediumCount + hardCount;
                labelQuestionsTotalNumber.setText("NÚMERO TOTALIZADO  DE QUESTÕES: " + totalQuestions);
            } catch (NumberFormatException ex) {
                //EXCEPTION WHEN THE ENTRY ISN'T AN INTEGER NUMBER
                JOptionPane.showMessageDialog(null,
                        "As entradas das dificuldades das questões devem ser somente valores inteiros.");
                labelQuestionsTotalNumber.setText("NÚMERO TOTALIZADO  DE QUESTÕES: ");
            }
        }
    }

    /// METHOD TO CREATE AND CONFIGURE TEXT FIELDS, AND ADD THEM TO THE GRAPHICAL USER INTERFACE
    private void createTextFields() {
        // INITIALIZE TEXT FIELDS FOR QUESTION DIFFICULTY ENTRY
        entryMediumCount = new JTextField(5);
        entryHardCount = new JTextField(5);
        entryEasyCount = new JTextField(5);

        // ADD DOCUMENT LISTENERS TO UPDATE TOTAL QUESTIONS LABEL WHEN TEXT CHANGES
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

        // ADD TEXT FIELDS TO THE GRAPHICAL USER INTERFACE
        addRandomTestWindow.add(entryMediumCount);
        addRandomTestWindow.add(entryHardCount);
        addRandomTestWindow.add(entryEasyCount);

        // SET BOUNDS FOR TEXT FIELDS TO POSITION THEM ON THE INTERFACE
        entryMediumCount.setBounds(140, 95, 40, 25);
        entryHardCount.setBounds(140, 135, 40, 25);
        entryEasyCount.setBounds(140, 55, 40, 25);
    }


    // METHOD TO RETRIEVE AND VALIDATE INPUT DATA FROM THE TEXT FIELDS
    private int[] getEntriesData() {
        // Check if all text fields for difficulty levels are not empty
        if (!entryEasyCount.getText().isEmpty() && !entryMediumCount.getText().isEmpty()
                && !entryHardCount.getText().isEmpty()) {
            try {
                // Attempt to parse the entered values into integers
                int[] testDifficulty = {
                        Integer.parseInt(entryEasyCount.getText()),
                        Integer.parseInt(entryMediumCount.getText()),
                        Integer.parseInt(entryHardCount.getText())
                };
                // Return the array of parsed difficulty values
                return testDifficulty;
            } catch (NumberFormatException ex) {
                // Handle the case where the entered value is not an integer
                JOptionPane.showMessageDialog(null,
                        "Entries for question difficulties must be integers only.");
            }
        } else {
            // Display a message if any of the text fields are empty
            JOptionPane.showMessageDialog(null, "ALL ENTRIES MUST BE FILLED.");
        }
        // Return null if any validation check fails
        return null;
    }


    /*
     *Creates and configures a JTable in the graphical user interface.
     *Constructs the content for the table (contentList) based on database questions.
     *Initializes the table model (DefaultTableModel) with specified column names.
     *Builds the table interface by populating it with data from the contentList, filtered by school subject.
     *Adds a ListSelectionListener to track row selections in the table.
     */
    private void createTable() {
        //CREATING THE COLECTION(MAP) TO THE TABLE AND INSERTING THE DATA
        contentList = new HashMap<>();

        for (Question question : dataBaseQuestions) {

            if (!contentList.containsValue(question.getContent())) {

                contentList.put(question.getContent(), question.getSchoolSubject());

            }

        }

        String schoolSubject = randomTest.getSchoolSubject();
        //SETTING THE COLUMNS
        String[] columns = { "Disciplina", "Tópico" };
        tableModel = new DefaultTableModel(columns, 0);

        //BUILDING DE TABLE
        buildTable(contentList, schoolSubject);


        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        table.getColumnModel().getColumn(0).setPreferredWidth(50); // DISCIPLINA (coluna 1)
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // TÓPICO (coluna 2)
        addRandomTestWindow.add(scrollPane);

        // ADDING A LISTENER TO THE TABLE(SELECTOR)
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow(); // UPDATE WITH DE SELECTED LINE
            }
        });

    }

    private void buildTable(Map<String, String> contentList, String schoolSubject) {
        // CLEANING THE TABLE
        tableModel.setRowCount(0);

        // CONVERT THE SCHOOL SUBJECT TO LOWER
        String schoolSubjectLower = schoolSubject.toLowerCase();



        // ADDING THE ENTRIES FROM THE MAP TO THE TABLE IF THE SCHOOL-SUBJECT CORRESPONDS
        // IGNORING UPPERCASE AND LOWERCASE
        for (Map.Entry<String, String> entry : contentList.entrySet()) {
            String disciplina = entry.getValue().toLowerCase();
            String topico = entry.getKey();

            if (disciplina.contains(schoolSubjectLower)) {
                tableModel.addRow(new Object[] { entry.getValue(), topico });
            }
        }
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {

        JLabel labelEasy = new JLabel("Nº DE FÁCEIS:");
        JLabel labelMedium = new JLabel("Nº DE MÉDIAS:");
        JLabel labelHard = new JLabel("Nº DE DIFÍCEIS:");
        labelQuestionsTotalNumber = new JLabel("NÚMERO TOTALIZADO  DE QUESTÕES: ");
        JLabel labelContentSelector = new JLabel("SELECIONE OS CONTEÚDOS DAS QUESTÕES:");
        JLabel labelQuestionsNumberPerLevel = new JLabel("SELECIONE O NÚMERO DE QUESTÕES POR DIFICULDADE:");
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

    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {
        JButton buttonFinish = new JButton("FINALIZAR");
        JButton buttonCancel = new JButton("CANCELAR");
        JButton buttonAddContent = new JButton("ADICIONAR CONTEÚDO");
        addRandomTestWindow.add(buttonFinish);
        addRandomTestWindow.add(buttonCancel);
        addRandomTestWindow.add(buttonAddContent);
        buttonFinish.setBounds(680, 520, 95, 30);
        buttonCancel.setBounds(10, 520, 100, 30);
        buttonAddContent.setBounds(480, 520, 175, 30);

        //CANCEL BUTTON, RETURNS TO THE MAIN WINDOW
        buttonCancel.addActionListener(e -> {
            addRandomTestWindow.dispose();
            addRandomTestWindow = null;
            MainWindow mainWindow = new MainWindow();

        });


        //FINISH BUTTON, GENERATES DE TEST ACCORDING TO THE QUANTITY IF EVERYTHING IS ON THE RIGHTS PARAMETERS
        buttonFinish.addActionListener(e -> {


            if(!contentsSelecteds.isEmpty()){
                //DIRECTORY SELECTOR
                DirectorySelector selector = new DirectorySelector();
                String chossedDirectory = selector.directorySelector();

                if(chossedDirectory == null){
                    addRandomTestWindow.dispose();
                    MainWindow mainWindow = new MainWindow();
                    return;
                }

                ArrayList<Question> selectedQuestions = new ArrayList<Question>();

                for (Question question : dataBaseQuestions) {

                    if (contentsSelecteds.contains(question.getContent())) {

                        selectedQuestions.add(question);

                    }

                }
                Assessment test = new Assessment(randomTest.getInstitution(), randomTest.getSchoolSubject(),
                        randomTest.getEducatorName(), randomTest.getTestsNumber(), selectedQuestions);

                //GENERATING THE FILE
                test.generateFile(selectedQuestions, Integer.parseInt(entryEasyCount.getText()),
                        Integer.parseInt(entryMediumCount.getText()), Integer.parseInt(entryHardCount.getText()),
                        chossedDirectory, testsNumber, checkBoxNoClosedQuestions.isSelected());

                checkboxCloseQuestionValue = checkBoxNoClosedQuestions.isSelected();

                //POP UP WINDOW BUILT THROUGH A STRING-BUILDER
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
                if (checkboxCloseQuestionValue) {
                    sb.append("Sem questões fechadas.\n");
                } else {
                    sb.append("Com questões fechadas.\n");
                }

                //STRING-BUILDER POP UP ON SCREEN
                JOptionPane.showMessageDialog(null, sb);
                addRandomTestWindow.dispose();
                addRandomTestWindow = null;
                ConcludedPoPWindow concludedPoPWindow = new ConcludedPoPWindow();
            }else {
                JOptionPane.showMessageDialog(null, "Não há conteúdos adicionados até o momento.");
            }


        });

        buttonAddContent.addActionListener(e -> {
            //ADD CONTENT, UPDATE THE LABEL AND THEN REMOVE THE CONTENT CHOSEN FROM THE TABLE
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String topic = (String) table.getValueAt(selectedRow, 1); // Obtém o tópico da tabela
                contentsSelecteds.add(topic);
                contentsCount++;
                labelTopicsNumber.setText("NÚMERO DE TÓPICOS ADICIONADOS: " + contentsCount);
                contentList.remove(topic);
                buildTable(contentList, randomTest.getSchoolSubject());
            }
        });
    }

    // OVERRIDE METHOD TO CREATE THE CHECKBOX, CONFIG AND ADD THEM TO THE GRAPHICAL USER INTERFACE
    private void createCheckBox() {
        
        checkBoxNoClosedQuestions = new JCheckBox("SEM QUESTÕES FECHADAS");
        
        addRandomTestWindow.add(checkBoxNoClosedQuestions);
        
        checkBoxNoClosedQuestions.setBounds(115, 460, 195, 25);
    }

}
