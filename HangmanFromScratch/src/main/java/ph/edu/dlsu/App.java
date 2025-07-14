package ph.edu.dlsu;

public class App {


    public static void main(String[] args) {
        new Hangman() {
            @Override
            public int playOneGame(String secretWord) {
                return 0;
            }
        }.run();
    }

}
