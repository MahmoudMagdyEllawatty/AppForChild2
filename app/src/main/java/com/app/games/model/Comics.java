package com.app.games.model;

import com.app.games.utils.AnswerType;

import java.util.ArrayList;

public class Comics {
    private String key;
    private String name;
    private String pdfURL;
    private String description;
    private ArrayList<Question> questions;



    public Comics() {
    }

    public Comics(String key, String name, String pdfURL, String description, ArrayList<Question> questions) {
        this.key = key;
        this.name = name;
        this.pdfURL = pdfURL;
        this.description = description;
        this.questions = questions;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public static class Question {
        private QuestionType questionType;
        private ArrayList<AnswerType> answerTypes;
        private int correctAnswer;
        private String question;

        public Question() {
        }

        public Question(QuestionType questionType, ArrayList<AnswerType> answerTypes, int correctAnswer, String question) {
            this.questionType = questionType;
            this.answerTypes = answerTypes;
            this.correctAnswer = correctAnswer;
            this.question = question;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public QuestionType getQuestionType() {
            return questionType;
        }

        public void setQuestionType(QuestionType questionType) {
            this.questionType = questionType;
        }

        public ArrayList<AnswerType> getAnswerTypes() {
            return answerTypes;
        }

        public void setAnswerTypes(ArrayList<AnswerType> answerTypes) {
            this.answerTypes = answerTypes;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(int correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
    }


}
