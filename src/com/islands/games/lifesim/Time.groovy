package com.islands.games.lifesim

/**
 * Class representing...well, Time. As it passes and things happen.
 */
class Time {
    // The number of months in a year. Should not be changed once a world history is started.
    static final MONTHS_PER_YEAR = 10

    // Internal class tracking the grand Age of a given timestamp.
    Age age
    // Integer of the year.
    int year
    // Integer of the month.
    int month

    /**
     * Create a Time stamp.
     * @param age The {@link Age} to use.
     * @param year The year of the Time.
     * @param month The month of the Time.
     */
    Time(Age age,int year,int month) {
        this.age = age
        this.year = year
        this.month = month
    }

    /**
     * Create a Time stamp, defaulting to the latest {@link Age}.
     * @param year The year of the Time.
     * @param month The month of the Time.
     */
    Time(int year,int month) {
        this(Age.latest(),year,month)
    }

    /**
     * Converts the Time stamp into a human-readable string.
     */
    String toString() {
        "month $month, of the year $year, of $age.name"
    }

    /**
     * Internal class for tracking grand Ages in time.
     */
    static class Age {
        // Map of all Ages to have passed, including the current one.
        final static HashMap<Integer,Age> ages = new HashMap<>()
        // Tracks the latest Age. Starts at 0, but the first Age to be created will begin with 1.
        static int greatestAge = 0
        // The integer ID of the Age.
        final int value
        // The name of the Age. Form like "the First Age" or some such.
        final String name

        /**
         * Creates a new Age.
         * @param name
         */
        static void newAge(String name) {
            new Age(name)
        }

        /**
         * Create and name an Age.
         * @param name Name of the Age.
         */
        private Age(String name) {
            this.name = name
            value = ++greatestAge
            ages[value] = this
        }

        /**
         * Get the most recent -- AKA current -- Age.
         */
        static Age latest() {
            ages[greatestAge]
        }
    }
}
