package com.islands.games.lifesim.life

/**
 * Trait a thing can implement that indicates it is able to live and die.
 */
trait Mortal {
    boolean alive = true
    boolean hasEverDied = false

    /**
     * Must be implemented, gives the likelihood of the thing dying at any given point.
     * TODO: BASED ON WHAT RATE OF TIME?
     */
    abstract float getMortality()

    void kill() {
        alive = false
        hasEverDied = true
    }
    /**
     * TBD if we implement this in any practical sense. Including for completeness, since we do have a magic system.
     */
    void resurrect() {
        alive = true
    }
}