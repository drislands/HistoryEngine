package com.islands.games.lifesim.life

import com.islands.games.lifesim.external.visual.Printable
import com.islands.games.lifesim.external.Simulation
import com.islands.games.lifesim.external.Time
import com.islands.games.lifesim.society.Tribe

import com.islands.games.lifesim.external.Random

/**
 * Class representing a person -- whether that means a human or demi-human or what-have-you.
 * NB: At time of creation, the expectation is that they will be human in the general sense.
 */
class Person extends Organism implements Sexual, Printable {

    final static Tuple<Integer> AGE_OF_HEALTH = new Tuple(
            10 * Time.MONTHS_PER_YEAR,
            18 * Time.MONTHS_PER_YEAR)
    final static int AGE_OF_ADULTHOOD = 18 * Time.MONTHS_PER_YEAR
    final static int AGE_OF_ELDERHOOD = 45 * Time.MONTHS_PER_YEAR
    final static int BIRTH_COOLDOWN_PERIOD = 1 * Time.MONTHS_PER_YEAR

    Time lastBirth = null

    // The Tribe this Person belongs to.
    Tribe tribe
    // This value is in months.
    int ageOffset = 0

    /**
     * Create a Person and set parents.
     * @param birth {@link Time} of this Person's birth.
     * @param father {@link Person} representing the male parent.
     * @param mother {@link Person} representing the female parent.
     */
    Person(Time birth,Person father,Person mother) {
        super(birth)
        // sex = ((Math.random() as int) % 2) ? Sex.PLUG : Sex.OUTLET
        this.father = father
        this.mother = mother
    }

    /**
     * Create a Person.
     * @param birth {@link Time} of this Person's birth.
     */
    Person(Time birth,int ageOffset) {
        super(birth)
        // sex = ((Math.random() as int) % 2) ? Sex.PLUG : Sex.OUTLET
        this.ageOffset = ageOffset
    }

    /*
     * Gets the mortality rate of the Person.
     * TODO. SHOULD BE DETERMINED BY ALGO BASED ON VARIOUS FACTORS
     * @return
     */
    float getMortality() {
        float mortality
        if(age < (AGE_OF_HEALTH[0] as int)) {
            mortality = tribe.getYoungerMortality(this)
        } else if(age > (AGE_OF_HEALTH[1] as int)) {
            mortality = tribe.getOlderMortality(this)
        } else {
            mortality = 0f
        }

        mortality + tribe.tribeMortality
    }

    int getAge() {
        (Simulation.now - birth) + ageOffset
    }

    boolean isChild() {
        getAge() < AGE_OF_ADULTHOOD
    }

    boolean isElder() {
        getAge() >= AGE_OF_ELDERHOOD
    }

    boolean isBreedable() {
        // Not an adult? Not having babies.
        if(child || elder)
            return false
        // A guy, or never had a baby? Default able to have babies.
        if(sex == Sex.PLUG || lastBirth == null)
            return true

        // Otherwise, check to make sure not on cooldown from previous birth.
        return Simulation.now - lastBirth >= BIRTH_COOLDOWN_PERIOD
    }

    /**
     * The algorithm to determine the ability to give birth.
     */
    boolean checkFecundity() {
        // TODO: Almost definitely will need serious tweaking and consideration
        Math.abs(Random.nextInt()) % 100 < tribe.birthRate
    }

    boolean checkDeadification() {
        // TODO: better name
        Math.abs(Random.nextInt()) % 10000 < ((getMortality() * 100 ) )
    }
}
