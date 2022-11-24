package com.app.games.utils;

import com.app.games.R;

import java.util.ArrayList;
import java.util.Random;

public class AnimalHouseGameHelper {

    private ArrayList<HouseGame> houseGames;

    public AnimalHouseGameHelper(){
        fillList();
    }


    //0   1    2   3   4   5   6   7   8   9

    public Question getQuestion(){
        Random rn = new Random();
        int houseRandomPicker = rn.nextInt(9)+1;
        int firstHouseChoice = houseRandomPicker > 8 ? 8 : houseRandomPicker < 2 ? 3 : houseRandomPicker - 2;
        int secondHouseChoice = houseRandomPicker > 8 ? 7  : houseRandomPicker < 2 ? 4: houseRandomPicker - 1;
        int thirdHouseChoice = houseRandomPicker > 8 ? 6 : houseRandomPicker < 2 ? 5 : houseRandomPicker + 1;


        int correctAnswerPosition = rn.nextInt(3)+1;


        HouseGame selectedHouse = this.houseGames.get(houseRandomPicker);
        HouseGame firstHouse    = this.houseGames.get(firstHouseChoice);
        HouseGame secondHouse   = this.houseGames.get(secondHouseChoice);
        HouseGame thirdHouse    = this.houseGames.get(thirdHouseChoice);

        int firstAnswer = correctAnswerPosition == 0 ? selectedHouse.getImage() : firstHouse.getImage();
        int secondAnswer = correctAnswerPosition == 1 ? selectedHouse.getImage() : secondHouse.getImage();
        int thirdAnswer = correctAnswerPosition == 2 ? selectedHouse.getImage() : thirdHouse.getImage();
        int forthAnswer = correctAnswerPosition == 3 ? selectedHouse.getImage() :
                correctAnswerPosition == 0 ? firstHouse.getImage() :
                correctAnswerPosition == 1 ? secondHouse.getImage() : thirdHouse.getImage();

        return new Question(
                "أين يوجد  "+selectedHouse.getName(),
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                forthAnswer,
                correctAnswerPosition
        );
    }

    private void fillList(){
        this.houseGames = new ArrayList<>();
        this.houseGames.add(new HouseGame(R.mipmap.game_house_1,"بيت الدب"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_2,"بيت الطائر"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_3,"بيت الكلب"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_4,"بيت النحلة"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_5,"بيت القوقعة"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_6,"بيت السنجاب"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_7,"بيت الخفاش"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_8,"بيت العنكبوت"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_9,"بيت الضفدع"));
        this.houseGames.add(new HouseGame(R.mipmap.game_house_10,"بيت الأسد"));
    }

    public ArrayList<HouseGame> getHouseGames() {
        return houseGames;
    }

    public void setHouseGames(ArrayList<HouseGame> houseGames) {
        this.houseGames = houseGames;
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

    public static class HouseGame{
        private int image;
        private String name;

        public HouseGame(int image, String name) {
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
