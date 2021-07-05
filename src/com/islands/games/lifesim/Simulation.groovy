package com.islands.games.lifesim

import com.islands.games.lifesim.Time.Age
import com.islands.games.lifesim.exceptions.UserQuittingException

/**
 * General class for starting and manipulating the Simulation.
 */
class Simulation {
    // Time object tracking when "now" is.
    static Time now
    // List of valid commands that can be given in CLI mode.
    static def commands = [
            'make',
            'advance',
            'quit',
            'now'
    ]

    /**
     * Main method.
     */
    static void main(args) {
        // Create the first Age.
        Age.newAge('The First Age')

        // Set "now" to year 1, month 1 (and Age 1 by default).
        now = new Time(1,1)

        // Start the REPL.
        println '===================='
        println 'Simulation starting.'
        println '===================='
        repl()
        println '===================='
        println 'Simulation ended.'
        println '===================='
    }

    /**
     * Method to perform the core REPL action.
     */
    static void repl() {
        String input
        do {
            input = read()
        } while(exec(input))
    }

    /**
     * Shorthand for Java's line reader.
     */
    static String read() {
        System.in.newReader().readLine()
    }

    /**
     * Execute code based on given text.
     * @param text User input to execute.
     * @return False if the user called the {@link #quit} method, true otherwise.
     */
    static boolean exec(String text) {
        // Split the text into words by spaces.
        def words = text.split(/ +/)
        // The first word is the command.
        def command = words.first()

        // Check that the first word is in the list of permissible commands.
        if(command in commands) {
            try {
                // If we have more than 1 word, pass the remaining words to the method with name "command".
                if (words.length > 1)
                    "$command"(words[1..-1])
                // Otherwise, just call the method named "command".
                else
                    "$command"()
            } catch (MissingMethodException any) {
                // Catch it when the user tries to call a method that requires N arguments, but gave fewer than N.
                println "Not enough arguments, or too many, for command $command."
                any.printStackTrace()
            } catch (UserQuittingException ignored) {
                // Thrown when the user calls the quit() method.
                return false
            }
        } else {
        // If it's not, let them know.
            println "Command $command is not valid."
        }
        return true
    }

    //////////////////
    /// REPL Commands
    //////////////////
    /**
     * Command to make
     * TODO: Make what? An Age? Person? Need to define
     * @param words
     */
    static void make(ArrayList<String> words) {
        println "Tried to make with args $words"
    }

    /**
     * Advance {@link #now} by 1 month.
     */
    static void advance() {
        now++
    }

    /**
     * Advance {@link #now} by some number of months.
     * @param months Number of months to advance by.
     */
    static void advance(ArrayList<String> months) {
        def m = months.first() as int
        m.times {
            advance()
        }
    }

    /**
     * Quits the REPL and ends the simulation.
     */
    static void quit() {
        throw new UserQuittingException('User initiated quit')
    }

    /**
     * Prints the string representation of {@link #now}.
     */
    static void now() {
        println "$now"
    }
    //////
}
