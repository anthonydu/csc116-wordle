import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A game of Wordle
 *
 * @author Anthony Du
 */
public class Wordle {

    /**
     * Runs the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        String[] valids = getWords("txt/valid_words.txt");
        String[] possibles = getWords("txt/possible_words.txt");

        String answer = possibles[new Random().nextInt(possibles.length)];

        String[] guesses = new String[]{"first", "second", "third", "forth", "fifth", "sixth and final"};

        Scanner console = new Scanner(System.in);

        printHeader();

        for (int i = 0; i < 6; i++) {

            while (true) {
                System.out.print("Please enter your " + guesses[i] + " guess: ");
                if (console.hasNext()) break;
            }
            String guess = console.next().toLowerCase();

            if (guess.equals("q")) {
                System.out.print("Thanks for playing!");
                System.exit(0);
            } else if (guess.length() != 5) {
                System.out.println("Please enter a five-letter word.");
                i -= 1;
                continue;
            } else if (!isStringInArray(guess, valids)) {
                System.out.println(guess.toUpperCase() + " is not a valid word.");
                i -= 1;
                continue;
            }

            String indicators = generateIndicators(guess, answer);

            if (indicators.equals("VVVVV")) {
                System.out.println();
                System.out.println("You won! The word was " + answer.toUpperCase() + ".");
                System.out.println("You got the answer in " + (i + 1) + " guesses!");
                System.out.println();
                System.exit(1);
            } else if (i != 5) {
                System.out.println();
                System.out.println("\t" + guess.toUpperCase());
                System.out.println("\t" + indicators);
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("You did not guess the correct word.");
        System.out.println("The correct word was " + answer.toUpperCase() + ".");
        System.out.println();
    }

    private static String[] getWords(String filename) {
        Scanner wordsScanner = null;
        try {
            wordsScanner = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        String[] words = new String[0];
        while (wordsScanner.hasNext()) {
            words = Arrays.copyOf(words, words.length + 1);
            words[words.length - 1] = wordsScanner.next();
        }

        return words;
    }

    private static String generateIndicators(String guess, String answer) {
        if (guess.length() != answer.length()) {
            throw new IllegalArgumentException("Different lengths!");
        }
        String indicators = "";
        for (int j = 0; j < 5; j++) {
            if (guess.charAt(j) == answer.charAt(j)) {
                indicators += "V";
            } else {
                if (answer.contains(String.valueOf(guess.charAt(j)))) {
                    indicators += "O";
                } else {
                    indicators += "X";
                }
            }
        }
        return indicators;
    }

    private static boolean isStringInArray(String str, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (str.equals(arr[i])) {
                return true;
            }
        }
        return false;
    }

    private static void printHeader() {
        System.out.println();
        System.out.println("Welcome to Wordle!");
        System.out.println();
        System.out.println("Guess the WORDLE in six tries.");
        System.out.println("Each guess must be a valid five-letter word.");
        System.out.println();
        System.out.println("Hit the enter button to submit your guess.");
        System.out.println("Type in \"q\" and submit to leave the game.");
        System.out.println();
        System.out.println("After each guess, you will be shown how close your guess was to the word:");
        System.out.println();
        System.out.println("\tV - letter in the correct spot");
        System.out.println("\tO - letter in the word but wrong spot");
        System.out.println("\tX - letter not in the word");
        System.out.println();
    }

}
