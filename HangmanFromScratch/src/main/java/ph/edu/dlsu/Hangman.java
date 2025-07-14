package ph.edu.dlsu;

import java.io.*;
import java.util.*;

public abstract class Hangman implements HangmanInterface {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Hangman() {
            @Override
            public int playOneGame(String secretWord) {
                return 0;
            }
        }.run(); // start game
    }

    @Override
    public void run() {
        intro();

        int gamesPlayed = 0;
        int gamesWon = 0;
        int best = Integer.MAX_VALUE;

        boolean playAgain = true;

        while (playAgain) {
            String word = getRandomWord("/words.txt");
            String guessedLetters = "";
            int incorrectGuesses = 0;
            final int MAX_GUESSES = 8;

            while (true) {
                displayHangman(incorrectGuesses);
                String hint = createHint(word, guessedLetters);
                System.out.println("Secret word: " + hint);
                System.out.println("Your guesses: " + guessedLetters);
                System.out.println("Guesses left: " + (MAX_GUESSES - incorrectGuesses));

                if (hint.equals(word)) {
                    System.out.println("You win! My word was \"" + word + "\".");
                    gamesWon++;
                    if (incorrectGuesses < best) {
                        best = incorrectGuesses;
                    }
                    break;
                }

                char guess = readGuess(guessedLetters);
                guessedLetters += guess;

                if (word.indexOf(guess) < 0) {
                    System.out.println("Incorrect.");
                    incorrectGuesses++;
                    if (incorrectGuesses >= MAX_GUESSES) {
                        displayHangman(incorrectGuesses);
                        System.out.println("You lost! My word was \"" + word + "\".");
                        break;
                    }
                } else {
                    System.out.println("Correct!");
                }
            }

            gamesPlayed++;
            stats(gamesPlayed, gamesWon, best);

            System.out.print("Play again? (Y/N): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            playAgain = answer.equals("Y");
        }

        System.out.println("Thanks for playing Hangman!");
    }

    @Override
    public void intro() {
        System.out.println("                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("                              Welcome to Hangman!");
        System.out.println("          I will think of a random word while you try to guess its letters.");
        System.out.println("                Every time you guess a letter that isn't in my word,");
        System.out.println("                   a new body part of the hanging man appears.");
        System.out.println("                                 Good luck!!!");
        System.out.println("                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Override
    public void displayHangman(int guessCount) {
        loadText("/display" + (8 - guessCount) + ".txt");
    }

    @Override
    public String createHint(String secretWord, String guessedLetters) {
        StringBuilder hint = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.indexOf(c) >= 0) {
                hint.append(c);
            } else {
                hint.append('-');
            }
        }
        return hint.toString();
    }

    @Override
    public char readGuess(String guessedLetters) {
        while (true) {
            System.out.print("Your guess? ");
            String input = scanner.nextLine().toUpperCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Type a single letter from A-Z.");
                continue;
            }

            char guess = input.charAt(0);

            if (guessedLetters.indexOf(guess) >= 0) {
                System.out.println("You already guessed that letter.");
                continue;
            }

            return guess;
        }
    }

    @Override
    public String getRandomWord(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try (InputStream input = App.class.getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word.trim().toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load words file.", e);
        }
        return words.get(new Random().nextInt(words.size()));
    }
    @Override
    public void stats(int gamesCount, int gamesWon, int best) {
        System.out.println("                           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("                                      Games played: " + gamesCount);
        System.out.println("                                       Games won: " + gamesWon);
        System.out.println("                                   Win rate: " + String.format("%.2f", (gamesWon * 100.0 / gamesCount)) + "%");
        System.out.println("                             Best game (fewest wrong guesses): " + best);
        System.out.println("                                Thanks for playing Hangman!");
        System.out.println("                           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    public void loadText(String fileName) {
        try (InputStream input = App.class.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException | NullPointerException e) {
            System.out.println("[ASCII ART MISSING for " + fileName + "]");
        }
    }
}
