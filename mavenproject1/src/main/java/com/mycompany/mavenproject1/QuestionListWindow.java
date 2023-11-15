package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class QuestionListWindow extends Window {

    private JFrame questionListWindow;
    private JTextField entryContent;
    private JTextField entryQuestionBody;
    private DefaultTableModel tableModel;

    QueryExecutions consultant = new QueryExecutions(); // Instaciando o consultor
    protected ArrayList<Question> questionsList = consultant.realizeConsult(); // Criando um arrayList de questões e
                                                                               // fazendo ele receber diretamente da
                                                                               // consulta da DB

    private int selectedRow = -1; // Apenas para não aparecer nenhuma linha selecionada já ao abrir a janela
    private JTable table;

    // Método contrutor
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

    // Criação dos botões
    @Override
    protected void createButtons() {
        JButton buttonLimpar = new JButton("LIMPAR"); // Botão para limpar todas as entradas
        JButton buttonPesquisar = new JButton("PESQUISAR"); // Botão para realizar pesquisa de questões com base no
                                                            // valor inserido pelo usuário no campo de TÓPICO ou TEXTO A
                                                            // QUESTÃO
        JButton buttonDeletar = new JButton("DELETAR"); // Botão para deletar permanentemente uma questão da DB
        JButton buttonVoltar = new JButton("VOLTAR"); // Botão para voltar à tela principal do programa
        JButton buttonVisualizar = new JButton("VER QUESTÃO"); // Botão para visualizar por completo uma questão
                                                               // exitente na DB

        // Textos que aparecem ao deixar o mouse sobre os botões
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

        // Adição da ação de limparos campos de entrada
        buttonLimpar.addActionListener(e -> {

            entryContent.setText(""); // Limpa o campo de tópico
            entryQuestionBody.setText(""); // Limpa o campo de texto da questão
            buildTable(questionsList); // Reconstrói a tabela com todas as questões

        });

        // Adição da ação de voltar à tela principal do programa
        buttonVoltar.addActionListener(e -> {

            questionListWindow.dispose();
            questionListWindow = null;
            MainWindow mainWindow = new MainWindow();

        });

        // Adição da ação de deletar uma questão na DB
        buttonDeletar.addActionListener(e -> {

            int tabelRow = table.getSelectedRow(); // Pega a linha selecionada na tabela
            int dataToDelet = (int) table.getValueAt(tabelRow, 0); // Pega o valor da coluna de ID da questão
                                                                   // selecionada na tabela
            consultant.dataDelete(dataToDelet); // realiza a exclusão da questão que possui o ID capturado na DB

            buildTable(consultant.realizeConsult()); // Reconstroi a tabela com os dados atualizados
            questionsList = consultant.realizeConsult(); // Pega os dados atualizados da DB

        });

        // Adição da ação de pesquisar por uma questão
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

        // Adição da ação de visualizar uma questão por completa
        buttonVisualizar.addActionListener(e -> {

            // Verifica se a linha selecionada é a 0 ou superior
            if (selectedRow >= 0) {

                Question selectedQuestion = new Question();

                int id = (int) (table.getValueAt(selectedRow, 0)); // Coloca o ID da questão numa variável inteira
                                                                   // possível de se usar

                // Varre a lista de questões até encontrar a de ID selecionado
                for (Question question : questionsList) {
                    if (question.getId() == id) {
                        selectedQuestion = question;
                        break; // Se encontrar a questão, saia do loop
                    }
                }

                // Abre a janela de visualização da questão por completa
                QuestionViewWindow questionViewWindowWindow = new QuestionViewWindow(selectedQuestion,
                        questionListWindow);

            }
        });
    }

    // Criação das labels
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

    // Verificação do tamanho máximo de caracteres do corpo da questão para não
    // haver conflitos com a configuração desse campo com o da DB
    private void checkCharacterLimit() {
        int maxCharacters = 500;
        if (entryQuestionBody.getText().length() > maxCharacters) {
            String limitedText = entryQuestionBody.getText().substring(0, maxCharacters);
            entryQuestionBody.setText(limitedText);
        }
    }

    // Método privado para criar campos de texto na interface gráfica
    private void createTextFields() {
        // Criar campos de texto para conteúdo e descrição da pergunta
        entryContent = new JTextField();
        entryQuestionBody = new JTextField();

        // Adicionar o campo de descrição da pergunta à janela da lista de perguntas
        questionListWindow.add(entryQuestionBody);

        // Adicionar um ouvinte de documento ao campo de descrição da pergunta para
        // verificar o limite de caracteres
        entryQuestionBody.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Chamado quando caracteres são inseridos no campo
                checkCharacterLimit();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Chamado quando caracteres são removidos do campo
                checkCharacterLimit();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Eventos não são gerados para componentes de texto simples (plain text
                // components)
            }
        });

        // Adicionar o campo de conteúdo à janela da lista de perguntas
        questionListWindow.add(entryContent);

        // Definir as posições e tamanhos dos campos de texto na interface
        entryContent.setBounds(190, 85, 380, 30);
        entryQuestionBody.setBounds(250, 130, 320, 30);
    }

    // Método para criar e configurar uma tabela com um modelo personalizado
    private void createTable() {
        // Definir as colunas da tabela
        String[] colunas = { "ID", "Disciplina", "Tópico", "Conteúdo", "Nível" };

        // Criar um modelo de tabela com as colunas definidas
        tableModel = new DefaultTableModel(colunas, 0);

        // Construir a tabela com base na lista de questões
        buildTable(questionsList);

        // Instanciar a tabela e adicionar um painel de rolagem
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(115, 240, 590, 165);

        // Adicionar o painel de rolagem à janela da lista de perguntas
        questionListWindow.add(scrollPane);

        // Definir larguras preferenciais das colunas
        table.getColumnModel().getColumn(0).setPreferredWidth(30); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Disciplina
        table.getColumnModel().getColumn(2).setPreferredWidth(130); // Tópico
        table.getColumnModel().getColumn(3).setPreferredWidth(300); // Conteúdo
        table.getColumnModel().getColumn(4).setPreferredWidth(30); // Nível de dificuldade

        // Adicionar um ouvinte de seleção de lista para obter a linha selecionada
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow();
            }
        });
    }

    // Método para a construção da tabela
    private void buildTable(ArrayList<Question> questions) {

        tableModel.setRowCount(0);

        // Adicione as questões filtradas ao modelo da tabela
        for (Question question : questions) {
            tableModel.addRow(new Object[] { question.getId(), question.getSchoolSubject(), question.getContent(),
                    question.getQuestion(), question.getDifficult() });
        }
    }

}