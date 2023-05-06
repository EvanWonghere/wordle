import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Wordless {

    public static class data {
        String face = "";
        int cnt = 0;
    }

    static int getRandomIndex() {
        return (int) (Math.random() * 22);
    }

    static String getRandomWord() {
        String[] words = {"hello", "world", "java", "python",
                "apple", "banana", "pear", "watermelon",
                "access", "project", "intention", "negotiate",
                "disappointing", "alternative", "generous", "strategy",
                "primary", "grab", "logic", "theory", "download"};

        return words[getRandomIndex()];
    }

    static boolean isGuessValid(@NotNull String guess) {
        if (guess.length() != 1) return false;
        return guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z';
    }

    static void changeFace(@NotNull String answer, data info, char guess) {
        char[] tmpFace = info.face.toCharArray();
        for (int i = 0; i < answer.length(); ++ i) {
            if (answer.charAt(i) == guess) {
                tmpFace[i] = guess;
            }
        }
        info.face = String.copyValueOf(tmpFace);
    }

    static boolean playGame(String answer, @NotNull data info) { // Return value means whether the game is end or not.
        Scanner input = new Scanner(System.in);

        String tip = "(Guess)Enter a letter in word " + info.face + " > ";
        System.out.print(tip);
        String guess = input.next();

        while (!isGuessValid(guess)) {
            String errorTip = "Invalid input!\n";
            System.out.print(errorTip + tip);
            guess = input.next();
        }

        if (info.face.contains(guess)){
            String exitTip = '\t' + guess + " is already in the word";
            System.out.println(exitTip);
            return false;
        }

        if (answer.contains(guess)) {
            changeFace(answer, info, guess.charAt(0));
            if (answer.equals(info.face)){
                String guessed = "The word is " + answer + ". "
                        + "You missed " + info.cnt + " time"
                        + (info.cnt <= 1 ? "" : "s");
                System.out.println(guessed);
                return true;
            } else return false;
        } else {
            String foundNot = '\t' + guess + " is not in the word";
            System.out.println(foundNot);
            ++ info.cnt;
            return false;
        }
    }

    static boolean newGame(@NotNull data info) {
        String answer = getRandomWord();
        String mod = "********************";
        info.face = mod.substring(0, answer.length());

        boolean flag = false;
        while(!flag) {
            flag = playGame(answer, info);
        }

        return true;
    }

    static boolean isRespondValid(@NotNull String res) {
        if (res.length() != 1) return false;
        return res.equals("y") || res.equals("Y") || res.equals("n") || res.equals("N");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        data info = new data();

        while (newGame(info)) {
            String ask = "Do you want to guess another word? Enter y or n> ";
            System.out.print(ask);
            String res = input.next();
            while (!isRespondValid(res)) {
                System.out.print("Invalid input! Enter y or n> ");
                res = input.next();
            }
            if (res.equals("n") || res.equals("N")) break;
            info.face = "";
            info.cnt = 0;
        }

        System.out.println("Bye!");
    }
}
