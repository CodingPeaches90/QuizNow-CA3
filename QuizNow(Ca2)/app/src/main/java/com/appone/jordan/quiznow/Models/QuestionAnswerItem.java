package com.appone.jordan.quiznow.Models;

public class QuestionAnswerItem
{
    /* This class is used for our recycler view in "all question view activity*/

    private String question, answer;

    private boolean isExpandable;

    public QuestionAnswerItem(String question, String answer, boolean isExpandable) {
        this.question = question;
        this.answer = answer;
        this.isExpandable = isExpandable;
    }

    public QuestionAnswerItem() {}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
