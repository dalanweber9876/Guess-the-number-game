import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class GuessingGame {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner menuInput = new Scanner(System.in);

        // Main Loop
        while (true) {

            // Main Menu
            System.out.println("\nWelcome to the Guess the Number Game");
            System.out.println("Try to guess a random number (1-100) in as few attempts as possible!");
            System.out.println("\nPlease choose and option:");
            System.out.println("1. Play");
            System.out.println("2. Highscores");
            System.out.println("3. Quit");
            System.out.print("> ");
            int userChoice = Integer.parseInt(menuInput.nextLine());
            if (userChoice == 1) {
                Run();

            } else if (userChoice == 2) {
                System.out.println("\nWould you like to:");
                System.out.println("1. View Highscores");
                System.out.println("2. Reset Highscores");
                userChoice = Integer.parseInt(menuInput.nextLine());
                if (userChoice == 1) {
                    System.out.println("\nHighscores");
                    DisplayHighscore();
                } else if (userChoice == 2) {
                    ClearHighscore();
                }
            } else if (userChoice == 3) {
                break;

            } else {
                System.out.println("\nPlease enter a number between 1-3");

            }
        }

    }

    public static void Run() throws FileNotFoundException, IOException {
        Scanner guessInput = new Scanner(System.in);
        int guess = -1;
        int guesses = 0;

        // Get random number from RandomNumber class
        RandomNumber rn = new RandomNumber();
        int number = rn.GetRandomNumber();
        System.out.println("The random number has been selected");
        System.out.print("Press 'enter' when you are ready to begin: ");
        guessInput.nextLine();

        // Start stopwatch.
        long startTime = System.currentTimeMillis();

        // Game Loop
        while (guess != number) {

            System.out.print("> ");
            guess = Integer.parseInt(guessInput.nextLine());
            guesses++;
            if (guess < number) {
                System.out.println("Higher");
            } else if (guess > number) {
                System.out.println("Lower");
            } else {
                System.out.printf("\nCongratulations! You got it in %s guesses!", guesses);

                // End stopwatch and calculate time in seconds.
                long stopTime = System.currentTimeMillis();
                long elapsedTime = (stopTime - startTime) / 1000;

                System.out.printf("\nIt took you %s seconds to find the random number!", elapsedTime);

                String timeScore = BeatTimeHighscore(elapsedTime);
                String guessScore = BeatGuessesHighscore(guesses);
                FileWriter writer = new FileWriter(
                        "highscores.txt");
                writer.write(timeScore + "\n" + guessScore);
                writer.close();

                System.out.print("\nPress 'enter' when you are ready to return to the main screen: ");
                guessInput.nextLine();
            }
        }

    }

    public static String BeatTimeHighscore(long elapsedTime) throws FileNotFoundException, IOException {

        // Read file and store info into lists
        File file = new File("highscores.txt");
        Scanner scan = new Scanner(file);
        System.out.println("");

        ArrayList<String> scores = new ArrayList<String>();
        while (scan.hasNextLine()) {
            scores.add(scan.nextLine());
        }

        ArrayList<Long> parts = new ArrayList<Long>();
        String[] pieces = scores.get(0).split(" ");
        parts.add(Long.parseLong(pieces[1]));

        // Check for a new highscore
        if (elapsedTime < parts.get(0)) {
            System.out.printf("\n%s seconds is the new fastest time! Nice job!", elapsedTime);
            return "Time: " + elapsedTime;
        } else {
            return "Time: " + parts.get(0);
        }

    }

    public static String BeatGuessesHighscore(int guesses) throws FileNotFoundException {
        // Read file and store info into lists
        File file = new File("highscores.txt");
        Scanner scan = new Scanner(file);
        System.out.println("");

        ArrayList<String> scores = new ArrayList<String>();
        while (scan.hasNextLine()) {
            scores.add(scan.nextLine());
        }

        ArrayList<Long> parts = new ArrayList<Long>();
        String[] pieces = scores.get(1).split(" ");
        parts.add(Long.parseLong(pieces[1]));

        // Check for a new highscore
        if (guesses < parts.get(0)) {
            System.out.printf("\n%s guesses is a new record! Great work!", guesses);
            return "Score: " + guesses;
        } else {
            return "Score: " + parts.get(0);
        }

    }

    public static void DisplayHighscore() throws FileNotFoundException {
        // Read file and store info into lists
        Scanner input = new Scanner(System.in);
        File file = new File("highscores.txt");
        Scanner scan = new Scanner(file);

        ArrayList<String> scores = new ArrayList<String>();
        while (scan.hasNextLine()) {
            scores.add(scan.nextLine());
        }
        ArrayList<Long> parts = new ArrayList<Long>();
        String[] timepieces = scores.get(0).split(" ");
        String[] guesspieces = scores.get(1).split(" ");

        // Retrieve highscore data and display it
        Long time = Long.parseLong(timepieces[1]);
        Long guesses = Long.parseLong(guesspieces[1]);
        System.out.printf("Best Time: %s seconds", time);
        System.out.printf("\nLeast Number of Guesses: %s guesses\n", guesses);
        System.out.print("\nPress 'enter' when you are ready to return to the main screen: ");
        input.nextLine();
    }

    public static void ClearHighscore() throws FileNotFoundException, IOException {
        // Open file
        Scanner input = new Scanner(System.in);
        File file = new File("highscores.txt");
        Scanner scan = new Scanner(file);

        // Reset highscore
        FileWriter writer = new FileWriter("highscores.txt");
        writer.write("Time: 99" + "\n" + "Score: 99");
        writer.close();
    }

}
