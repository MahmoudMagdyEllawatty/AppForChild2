package com.app.games.utils;

import com.app.games.R;

import java.util.ArrayList;
import java.util.Random;

public class AnimalGameHelper {

    private ArrayList<AnimalGame> animalGames;

    public AnimalGameHelper(){
        fillList();
    }


    //0   1    2   3   4   5   6   7   8   9

    public Question getQuestion(){
        Random rn = new Random();
        int animalRandomPicker = rn.nextInt(9)+1;
        int firstAnimalChoice = animalRandomPicker > 8 ? 8 : animalRandomPicker < 2 ? 3  : animalRandomPicker - 2;
        int secondAnimalChoice = animalRandomPicker > 8 ? 7 : animalRandomPicker < 2 ? 4 :  animalRandomPicker - 1;
        int thirdAnimalChoice = animalRandomPicker > 8 ? 6 : animalRandomPicker < 2 ? 5 : animalRandomPicker + 1;


        int correctAnswerPosition = rn.nextInt(3)+1;


        AnimalGame selectedAnimal = this.animalGames.get(animalRandomPicker);
        AnimalGame firstAnimal    = this.animalGames.get(firstAnimalChoice);
        AnimalGame secondAnimal   = this.animalGames.get(secondAnimalChoice);
        AnimalGame thirdAnimal    = this.animalGames.get(thirdAnimalChoice);

        int firstAnswer = correctAnswerPosition == 0 ? selectedAnimal.getImage() : firstAnimal.getImage();
        int secondAnswer = correctAnswerPosition == 1 ? selectedAnimal.getImage() : secondAnimal.getImage();
        int thirdAnswer = correctAnswerPosition == 2 ? selectedAnimal.getImage() : thirdAnimal.getImage();
        int forthAnswer = correctAnswerPosition == 3 ? selectedAnimal.getImage() :
                correctAnswerPosition == 0 ? firstAnimal.getImage() :
                correctAnswerPosition == 1 ? secondAnimal.getImage() : thirdAnimal.getImage();

        return new Question(
                "أين يوجد  "+selectedAnimal.getName(),
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                forthAnswer,
                correctAnswerPosition
        );
    }

    private void fillList(){
        this.animalGames = new ArrayList<>();
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_1,"السلحفاة"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_2,"الأرنب"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_3,"البومة"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_4,"القرد"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_5,"الكلب"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_6,"الثعلب"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_7,"النمر"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_8,"القطة"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_9,"الجمل"));
        this.animalGames.add(new AnimalGame(R.mipmap.game_animal_10,"الدب"));
    }

    public ArrayList<AnimalGame> getAnimalGames() {
        return animalGames;
    }

    public void setAnimalGames(ArrayList<AnimalGame> animalGames) {
        this.animalGames = animalGames;
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

    public static class AnimalGame{
        private int image;
        private String name;

        public AnimalGame(int image, String name) {
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
