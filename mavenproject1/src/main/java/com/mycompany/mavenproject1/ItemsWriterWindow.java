package com.mycompany.mavenproject1;
import javax.swing.*;

public class ItemsWriterWindow extends Window {
    private JTextArea itemTextArea;
    private JFrame itemsWriteWindow;
    private JLabel labelAddingItem;
    private int itemIndex = 0;
    private Question question;
    private int type;
    private boolean checkBoxValue;
    private int itemsQuantity;

    // Método construtor da classe
    public ItemsWriterWindow(int type, Question question, boolean checkBoxValue, int itemsQuantity) {

        this.question = question;
        this.type = type;
        this.checkBoxValue = checkBoxValue;
        this.itemsQuantity = itemsQuantity;

        itemsWriteWindow = new JFrame();
        itemsWriteWindow.setLayout(null);
        itemsWriteWindow.setSize(624, 223);
        itemsWriteWindow.setUndecorated(true);
        itemsWriteWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsWriteWindow.setResizable(false);
        itemsWriteWindow.setLocationRelativeTo(null);
        itemsWriteWindow.setVisible(true);

        createLabels();
        createButtons();
        createTextArea();
    }

    // Criação das labels
    private void createLabels(){
        labelAddingItem = new JLabel ("ADICIONANDO ALTERNATIVA " + (1 + itemIndex)); // Atualização do valor na label comforme as alternativas são criadas
        JLabel labelItemText = new JLabel ("TEXTO DA ALTERNATIVA:");
        itemsWriteWindow.add (labelAddingItem);
        itemsWriteWindow.add (labelItemText);
        labelAddingItem.setBounds (220, 0, 180, 35);
        labelItemText.setBounds (10, 35, 150, 25);}

    // Criação da área de entrada de texto para o usuário inserir o texto do item da questão
    private void createTextArea(){
        itemTextArea = new JTextArea (5, 5);
        itemsWriteWindow.add(itemTextArea);
        itemTextArea.setBounds (10, 65, 605, 90);
    }

    // Criação dos botões
    @Override
    protected void createButtons() {
        JButton buttonNextItem = new JButton ("PRÓXIMA ALTERNATIVA");
        JButton buttonCancel = new JButton ("CANCELAR");
        itemsWriteWindow.add (buttonNextItem);
        itemsWriteWindow.add (buttonCancel);
        buttonNextItem.setBounds (440, 185, 175, 30);
        buttonCancel.setBounds (10, 185, 100, 30);

        // Adição de evento para que ao botão PRÓXIMA ALTERNATIVA seja acionado o programa siga para o próximo item a ser escrito
        buttonNextItem.addActionListener(e -> {

            // Verificação de se o campo de texto não está vazio
            if(!(itemTextArea.getText().isEmpty())){
                
                // Verifica se a contagem de itens não é 0, se caso for o programa fechara a tela e abrirá a janela de visualização da quetão que acaba de ser cadastrada
                if(itemsQuantity == 0){

                    itemsWriteWindow.dispose();
                    itemsWriteWindow = null;
                    QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);

                }

                // Se a contagem ainda não for 0, ele fará as ações abaixo
                else{

                    // Verifica o tipo do item em questão
                    if(type == AddQuestionTextWindow.CLOSED) {
                        question.setEspecificItem(itemTextArea.getText());
                    }
                    else if (type == AddQuestionTextWindow.AFIRMATIVE) {
                        question.setEspecificItem(itemTextArea.getText());
                    }
                    itemTextArea.setText(""); // Limpa o JTextArea
                    itemIndex++;
                    labelAddingItem.setText("ADICIONANDO ALTERNATIVA " + (1 + itemIndex)); // Atualiza o index da alternativa
                    itemsQuantity--; // Diminui a quantidade de itens conforme se for criando

                    // Verifica se a quantidade de itens é 0, caso seja ele verifica se deve criar a alternativa de N.D.A
                    if(itemsQuantity==0){
                        if(checkBoxValue == true){
                            question.setEspecificItem("N.D.A");
                        }

                        // Abre a janela de visualização da questão
                        QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);

                        itemsWriteWindow.dispose();
                        itemsWriteWindow = null;


                    }

                }

            }

        });

        // Caso o usuário aciona po botão de cancelar a janela é fechada e uma janela de intância da classe AddQuestionWindow é aberta
        buttonCancel.addActionListener(e -> {
            
            itemsWriteWindow.dispose();
            itemsWriteWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();

        });

    }

}
