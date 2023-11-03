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

    private void createLabels(){
        labelAddingItem = new JLabel ("ADICIONANDO ALTERNATIVA " + (1 + itemIndex));
        JLabel labelItemText = new JLabel ("TEXTO DA ALTERNATIVA:");
        itemsWriteWindow.add (labelAddingItem);
        itemsWriteWindow.add (labelItemText);
        labelAddingItem.setBounds (220, 0, 180, 35);
        labelItemText.setBounds (10, 35, 150, 25);}


    private void createTextArea(){
        itemTextArea = new JTextArea (5, 5);
        itemsWriteWindow.add(itemTextArea);
        itemTextArea.setBounds (10, 65, 605, 90);
    }

    @Override
    protected void createButtons() {
        JButton buttonNextItem = new JButton ("PRÓXIMA ALTERNATIVA");
        JButton buttonCancel = new JButton ("CANCELAR");
        itemsWriteWindow.add (buttonNextItem);
        itemsWriteWindow.add (buttonCancel);
        buttonNextItem.setBounds (440, 185, 175, 30);
        buttonCancel.setBounds (10, 185, 100, 30);

        buttonNextItem.addActionListener(e -> {
            if(!(itemTextArea.getText().isEmpty())){
                if(itemsQuantity == 0){

                    itemsWriteWindow.dispose();
                    itemsWriteWindow = null;
                    QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);

                }
                else{
                    if(type == AddQuestionTextWindow.CLOSED) {
                        question.setEspecificItem(itemTextArea.getText());
                    }
                    else if (type == AddQuestionTextWindow.AFIRMATIVE) {
                        question.setEspecificItem(itemTextArea.getText());
                    }
                    itemTextArea.setText(""); // Limpa o JTextArea
                    itemIndex++;
                    labelAddingItem.setText("ADICIONANDO ALTERNATIVA " + (1 + itemIndex));
                    itemsQuantity--;

                    if(itemsQuantity==0){
                        if(checkBoxValue == true){
                            question.setEspecificItem("N.D.A");
                        }

                        QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);

                        itemsWriteWindow.dispose();
                        itemsWriteWindow = null;


                    }

                }

            }

        });

        buttonCancel.addActionListener(e -> {
            
            itemsWriteWindow.dispose();
            itemsWriteWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();

        });

    }
}
