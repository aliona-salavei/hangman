package com.aliona.hangman;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Set<String> guessedLetters = new HashSet<>();
    private static final String START = "н";
    private static final String QUIT = "в";

    public static void main(String[] args) {
        launchMenu();
    }

    public static void launchMenu() {
        while (true) {
            System.out.println("Хотите [н]ачать новую игру или [в]ыйти из игры?");
            String letter = scanner.nextLine();

            if (letter.equals(START)) {
                startGame();
                break;
            } else if (letter.equals(QUIT)) {
                System.out.println("Вы вышли из игры.");
                break;
            } else {
                System.out.println("Некорректный ввод.");
            }
        }
    }

    public static void startGame() {
        System.out.println("Вы начали игру.");
        String word = "телевизор";
        String maskedWord = "_".repeat(word.length());

        startGameLoop(word, maskedWord);
    }

    public static void startGameLoop(String word, String maskedWord) {
        int attemptRate = 6;
        Set<String> inputtedLetters = new HashSet<>();
        while (!isGameFinished(maskedWord, attemptRate)) {
            showMaskedWord(maskedWord);
            String letter = inputLetter();
            validateInputLetter(letter);

            if (isLetterAlreadyInput(letter, inputtedLetters)) {
                System.out.println("Эта буква уже вводилась! Попробуйте другую.");
                continue;
            }

            inputtedLetters.add(letter);

            if (!isLetterInWord(word, letter)) {
                System.out.println("Такой буквы здесь нет! Попробуйте другую.");
                attemptRate--;
            }
            System.out.println("Осталось попыток: " + attemptRate);

            addGuessedLetter(letter);
            maskedWord = updateMaskedWord(word, maskedWord, letter);
        }

        if (maskedWord.contains("_")) {
            System.out.println("Вы проиграли!. Загаданное слово: " + word);
        } else {
            System.out.println("Поздравляем! Вы угадали слово: " + word);
        }
    }

    public static void showMaskedWord(String maskedWord) {
        System.out.println("Текущее слово: " + maskedWord);
    }

    public static String inputLetter() {
        System.out.print("Введите букву: ");
        return scanner.nextLine().trim();
    }

    public static boolean validateInputLetter(String letter) {
        if (letter.matches("[а-яА-ЯёЁ]")) {
            return true;
        }
        else {
            System.out.println("Введите букву русского алфавита.");
            return false;
        }
    }

    public static boolean isLetterAlreadyInput(String letter, Set<String> inputtedLetters) {
        return inputtedLetters.contains(letter);
    }

    private static boolean isLetterInWord(String word, String letter) {
        return word.contains(letter);
    }

    public static void addGuessedLetter(String letter) {
        guessedLetters.add(letter);
    }

    public static String updateMaskedWord(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i + 1, letter);
            }
        }
        return updatedMaskedWord.toString();
    }

    private static boolean isGameFinished(String maskedWord, int attemptRate) {
        return attemptRate == 0 || !maskedWord.contains("_");
    }
}