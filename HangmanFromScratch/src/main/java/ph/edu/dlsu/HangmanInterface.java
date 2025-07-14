package ph.edu.dlsu;

public interface HangmanInterface {
    public void run();
    public void intro();
    public int playOneGame(String secretWord);
    public void displayHangman(int guessCount);
    public String createHint(String secretWord, String guessedLetters);
    public char readGuess(String guessedLetters);
    public String getRandomWord(String filename);
    public void stats(int gamesCount, int gamesWon, int best);
}
