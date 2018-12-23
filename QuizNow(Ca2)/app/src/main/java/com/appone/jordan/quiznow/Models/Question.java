package com.appone.jordan.quiznow.Models;

/*
This class defines the model for our database
* */
public class Question
{
    private int qId;
    private String question;
    private String optionA;
    private String optionB;
    private String answer;
    private String optionC;



    public Question()
    {
        this.qId = 0;
        this.question = "";
        this.optionA = "";
        this.optionB = "";
        this.optionC = "";
        this.answer = "";
    }

    public Question(String question, String optionA, String optionB, String optionC, String answer)
    {
        this.qId = qId;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.answer = answer;
    }

    public String getOptionC()
    {
        return optionC;
    }

    public void setOptionC(String optionC)
    {
        this.optionC = optionC;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
