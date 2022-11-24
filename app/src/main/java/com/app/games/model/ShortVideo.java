package com.app.games.model;

import com.app.games.utils.AnswerType;

import java.util.ArrayList;

public class ShortVideo {
    private String key;
    private String name;
    private String videoURL;
    private String description;
    private ArrayList<Question> questions;

    public ShortVideo() {
    }


    public ShortVideo(String key, String name, String videoURL, String description, ArrayList<Question> questions) {
        this.key = key;
        this.name = name;
        this.videoURL = videoURL;
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
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
        private com.app.games.model.QuestionType questionType;
        private ArrayList<AnswerType> answerTypes;
        private int correctAnswer;
        private String question;

        public Question() {
        }

        public Question(com.app.games.model.QuestionType questionType, ArrayList<AnswerType> answerTypes, int correctAnswer, String question) {
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

        public com.app.games.model.QuestionType getQuestionType() {
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
