package com.islands.games.lifesim.life

import com.islands.games.lifesim.Time
import com.islands.games.lifesim.society.Tribe

/**
 * Class representing a person -- whether that means a human or demi-human or what-have-you.
 * NB: At time of creation, the expectation is that they will be human in the general sense.
 */
class Person extends Organism implements Sexual {
    // The Tribe this Person belongs to.
    Tribe tribe

    /**
     * Create a Person and set parents.
     * @param birth {@link Time} of this Person's birth.
     * @param father {@link Person} representing the male parent.
     * @param mother {@link Person} representing the female parent.
     */
    Person(Time birth,Person father,Person mother) {
        super(birth)
        this.father = father
        this.mother = mother
    }

    /**
     * Create a Person.
     * @param birth {@link Time} of this Person's birth.
     */
    Person(Time birth) {
        super(birth)
    }

    /**
     * Gets the mortality rate of the Person.
     * TODO. SHOULD BE DETERMINED BY ALGO BASED ON VARIOUS FACTORS
     * @return
     */
    float getMortality() {
        0.0
    }
}
