package com.islands.games.lifesim.life

/**
 * Trait a thing can implement that indicates it is able to live and die.
 */
trait Mortal {
    /**
     * Must be implemented, gives the likelihood of the thing dying at any given point.
     * TODO: BASED ON WHAT RATE OF TIME?
     */
    abstract float getMortality()
}