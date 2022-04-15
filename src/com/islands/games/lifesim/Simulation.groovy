package com.islands.games.lifesim

import com.islands.games.lifesim.Time.Age
import com.islands.games.lifesim.exceptions.UserQuittingException
import com.islands.games.lifesim.life.Person
import com.islands.games.lifesim.society.TribeManager

/**
 * General class for starting and manipulating the Simulation.
 */
class Simulation implements Printable {
    static boolean DEBUG = false
    static boolean USE_GUI = true

    static GUIManager gMan


    // Number of years in a human generation.
    static YEARS_PER_GENERATION = 20
    // Time object tracking when "now" is.
    static Time now
    // List of valid commands that can be given in CLI mode.
    static def commands = [
            'make',
            'get',
            'advance',
            'quit',
            'debug',
            'help',
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
        if(!USE_GUI) {
            repl()
            println '===================='
            println 'Simulation ended.'
            println '===================='
        }
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

    static List<String> getInput() {
        def text
        if(!USE_GUI) {
            text = read()
            // Split the text into words by spaces.
        } else {
            GUIManager.waiting_on_input = true
            while(!GUIManager.input_ready) {
                sleep(100)
            }
            GUIManager.input_ready = false
            text = GUIManager.input
        }
        return text.split(/ +/)
    }

    /**
     * Execute code based on given text.
     * @param text User input to execute.
     * @return False if the user called the {@link #quit} method, true otherwise.
     */
    static boolean exec(String text) {
        DBG "entering exec"
        // Split the text into words by spaces.
        def words = text.split(/ +/)
        // The first word is the command.
        def command = words.first()
        DBG "got words: $words"

        // Check that the first word is in the list of permissible commands.
        if(command in commands) {
            DBG "command $command is in approved list"
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

    static void help(ArrayList<String> words) {
        def first = words?.first()
        if(!first) {
            println "Run any of the following commands."
            commands.each {
                println "> $it"
            }
        }
        // TODO: implement sub-command help.
    }

    /**
     * Command to make various things.
     * @param words The list of words that define what we're doing.
     */
    static void make(ArrayList<String> words) {
        def makeOptions = [
                "tribe"
                ]

        DBG "entering make"
        def first = words?.first()
        DBG "first is $first"
        switch(first?.toLowerCase()) {
            case "tribe":
                DBG "tribe gotten, entering makeTribe"
                if(words.size() > 1)
                    makeTribe(words[1..-1],USE_GUI)
                else
                    makeTribe([],USE_GUI)
                break
            case null:
                println "The `make` command must be used with a subcommand. Possible options are: $makeOptions"
                break
        }
    }

    static void get(ArrayList<String> words) {
        def getOptions = [
                "tribes"
                ]

        DBG "entering get"
        def first = words?.first()
        DBG "first is $first"
        switch(first?.toLowerCase()) {
            case "tribes":
                DBG "tribes gotten, gettin dem tribes"
                if(words.size() > 1) {
                    getTribes(words[1..-1])
                } else {
                    getTribes([])
                }
                break
            case null:
                println "The `get` command must be used with a subcommand. Possible options are: $getOptions"
                break
        }
    }

    static void makeTribe(List<String> details,boolean useGUI=false) {
        DBG "makeTribe entered"

        if(TribeManager.TRIBES.size() >= TribeManager.MAX_TRIBES) {
            println "The maximum number of tribes, $TribeManager.MAX_TRIBES, has been reached. You cannot create more."
            return
        }

        if(details?.size() > 0 && details.first().toLowerCase() == "default") {
            println "Creating up to max # of tribes (configured as $TribeManager.MAX_TRIBES) with default settings." // TODO: Ask yes/no to proceed?
            int i = 0
            while(TribeManager.TRIBES.size() < TribeManager.MAX_TRIBES) {
                TribeManager.addDefaultTribe()
                i ++
            }
            println "Done. Total of $i new tribes created with default settings."
            return
        }

        println "Creating tribe. Please provide name."
        def nameInput = getInput().join(/ /)
        println "Name set as '$nameInput'. Please provide member count."
        boolean errorCatch = true
        def countInput = 0
        while(errorCatch) {
            try {
                def first = getInput().first()
                if(first == '') {
                    // default
                } else {
                    countInput = first as int
                }
                errorCatch = false
            } catch(ignored) {
                println "Unable to read your input as a number. Please enter only integers for member count."
            }
        }
        println "Count set to '$countInput' members. Please provide X and Y coordinates."
        errorCatch = true
        def locationInput,X = 0,Y = 0
        while(errorCatch) {
            try {
                locationInput = getInput()
                X = locationInput[0] as double
                Y = locationInput[1] as double
                def tribe = TribeManager.isTooClose(new Location(X,Y))
                errorCatch = tribe
                if(tribe) {
                    println "The coordinates you specified are too close to at least one other tribe."
                    println "Specifically, tribe `$tribe.name` with coordinates $tribe.location." +
                            " Please try again."
                    // TODO: Tell them what the existing ones are so they stand a chance
                }
            } catch(ignored) {
                println "Unable to parse your input as two numbers. Please enter two numbers only (decimals allowed)."
            }
        }
        println "Location set to ($X,$Y). Creating tribe with given details..."
        TribeManager.addTribe(nameInput,countInput,X,Y)
        println "Tribe created."
    }

    static void getTribes(List<String> details) {
        DBG "getTribes entered"
        TribeManager.printTribes()
    }

    /**
     * Turns debug messages on and off.
     */
    static void debug(List<String> details) {
        switch(details.first().toLowerCase()) {
            case 'on':
                DEBUG = true
                println "Debug messages on."
                break
            case 'off':
                DEBUG = false
                println "Debug messages off."
                break
            default:
                println "debug can be set to ON or OFF."
                break
        }
    }

    /**
     * Advance {@link #now} by 1 month.
     */
    static void advance(once = true) {
        if(once)
            println "Advancing 1 month..."
        TribeManager.makeBabies()
        TribeManager.killPeople()
        now++
    }

    /**
     * Advance {@link #now} by some number of months.
     * @param months Number of months to advance by.
     */
    static void advance(ArrayList<String> months) {
        def m = months.first() as int
        println "Advancing $m months..."
        println ""
        m.times {
            print "."
            advance(false)
        }
        println ""
        println "Done."
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
