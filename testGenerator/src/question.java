public class question {

    private String schoolSubject;
    private String content;
    private String Question;
    private int difficult;

    public question(String schoolSubject, String content, String question, int difficult) {
        this.schoolSubject = schoolSubject;
        this.content = content;
        Question = question;
        this.difficult = difficult;
    }
    
    public String getSchoolSubject() {
        return schoolSubject;
    }
    public void setSchoolSubject(String schoolSubject) {
        this.schoolSubject = schoolSubject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String question) {
        Question = question;
    }
    public int getDifficult() {
        return difficult;
    }
    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
    
}
