package com.app.games.utils;

import com.app.games.R;

import java.util.ArrayList;
import java.util.Random;

public class FruitGameHelper {

    private ArrayList<FruitGame> fruitGames;

    public FruitGameHelper(){
        fillList();
    }


    //0   1    2   3   4   5   6   7   8   9    10

    public Question getQuestion(){
        Random rn = new Random();
        int fruitRandomPicker = rn.nextInt(10)+1;
        int firstFruitChoice = fruitRandomPicker > 9 ? 9: fruitRandomPicker < 2 ? 3 : fruitRandomPicker - 2;
        int secondFruitChoice = fruitRandomPicker > 9 ? 8: fruitRandomPicker < 2 ? 4 : fruitRandomPicker - 1;
        int thirdFruitChoice = fruitRandomPicker > 9 ? 7 : fruitRandomPicker < 2 ? 5: fruitRandomPicker + 1;


        int correctAnswerPosition = rn.nextInt(3)+1;


        FruitGame selectedFruit = this.fruitGames.get(fruitRandomPicker);
        FruitGame firstFruit    = this.fruitGames.get(firstFruitChoice);
        FruitGame secondFruit   = this.fruitGames.get(secondFruitChoice);
        FruitGame thirdFruit    = this.fruitGames.get(thirdFruitChoice);

        int firstAnswer = correctAnswerPosition == 0 ? selectedFruit.getImage() : firstFruit.getImage();
        int secondAnswer = correctAnswerPosition == 1 ? selectedFruit.getImage() : secondFruit.getImage();
        int thirdAnswer = correctAnswerPosition == 2 ? selectedFruit.getImage() : thirdFruit.getImage();
        int forthAnswer = correctAnswerPosition == 3 ? selectedFruit.getImage() :
                correctAnswerPosition == 0 ? firstFruit.getImage() :
                correctAnswerPosition == 1 ? secondFruit.getImage() : thirdFruit.getImage();

        return new Question(
                "أين يوجد  "+selectedFruit.getName(),
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                forthAnswer,
                correctAnswerPosition
        );
    }

    private void fillList(){
        this.fruitGames = new ArrayList<>();
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_1,"البطيخ"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_2,"الليمون"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_3,"العنب"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_4,"الكريز"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_5,"الرمان"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_6,"المانجا"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_7,"البرتقال"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_8,"الفراولة"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_10,"الموز"));
        this.fruitGames.add(new FruitGame(R.mipmap.game_fruit_11,"التفاح"));
    }

    public ArrayList<FruitGame> getFruitGames() {
        return fruitGames;
    }

    public void setFruitGames(ArrayList<FruitGame> fruitGames) {
        this.fruitGames = fruitGames;
    }


    public static class Question{
        private String question;
        private int answer1;
        private int answer2;
        private int answer3;
        private int answer4;
        private int correctAnswer;

        public Question(String question, int answer1, int answer2, int answer3, int answer4, int correctAnswer) {
            this.question = question;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getAnswer1() {
            return answer1;
        }

        public void setAnswer1(int answer1) {
            this.answer1 = answer1;
        }

        public int getAnswer2() {
            return answer2;
        }

        public void setAnswer2(int answer2) {
            this.answer2 = answer2;
        }

        public int getAnswer3() {
            return answer3;
        }

        public void setAnswer3(int answer3) {
            this.answer3 = answer3;
        }

        public int getAnswer4() {
            return answer4;
        }

        public void setAnswer4(int answer4) {
            this.answer4 = answer4;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(int correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
    }

    public static class FruitGame{
        private int image;
        private String name;

        public FruitGame(int image, String name) {
            this.image = image;
            this.name = name;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
