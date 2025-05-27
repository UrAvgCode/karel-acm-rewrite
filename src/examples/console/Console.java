package examples.console;

import acm.program.ConsoleProgram;

public class Console extends ConsoleProgram {
    public void run() {
        println("Welcome to the Console!");
        println("---------------------------------------------");

        String name = readLine("Please enter your name: ");
        println("Nice to meet you, " + name + "!\n");

        int age = readInt("What is your age in years? ");
        println("You are " + age + " years old.");

        if (age >= 18) {
            println("You are an adult.\n");
        } else {
            println("You are still a minor.\n");
        }

        double height = readDouble("What is your height in meters? ");
        println("Your height is " + height + " meters.\n");

        double growthPerYear = height / age;
        println("Based on your current height and age, you've grown an average of " + growthPerYear + " meters per year.");
        println("---------------------------------------------");
        println("Thank you for using this program!");
    }
}
