package com.islands.games.lifesim.society

import com.islands.games.lifesim.Location
import com.islands.games.lifesim.Printable
import com.islands.games.lifesim.Simulation
import com.islands.games.lifesim.Time
import com.islands.games.lifesim.life.Person
import com.islands.games.lifesim.life.Sexual
import com.islands.games.lifesim.Random

class TribeManager implements Printable {

    // TODO: make configurable
    final static int MAX_TRIBES = 8
    final static ArrayList<String> DEFAULT_NAMES = [
                "The Calypsos",
                "The Skink Eaters",
                "Reginald von Bartleby's Boys",
                "The Revengers",
                "The Happy Hoagies",
                "Actually George R.R. Martin",
                "Cole Slaw Enthusiasts",
                "Oh you're approaching me?",
                "Comp4960",
                "The Guild"
            ]
    final static int DEFAULT_SIZE = 100
    final static double MINIMUM_TRIBE_DISTANCE = 20.0d


    // TODO: Read these values from config, in advanced settings.
    final static int STARTING_CHILD_AGE = 8 * Time.MONTHS_PER_YEAR
    final static int STARTING_ADULT_AGE = 22 * Time.MONTHS_PER_YEAR
    final static int STARTING_ELDER_AGE = 70 * Time.MONTHS_PER_YEAR

    static ArrayList<Tribe> TRIBES = new ArrayList<>()

    // The ratio of child : adult : elder, in that order.
    static String MEMBER_RATIO = "1:3:1"
    static Tribe addTribe(name,count,x,y) {
        ArrayList<Person> persons = generatePersons(count,MEMBER_RATIO)

        Tribe T = new Tribe(name,persons,x,y)
        TRIBES << T

        return T
    }

    /**
     * Checks to see if a given location is within the {@link #MINIMUM_TRIBE_DISTANCE} of any existing tribe.
     * @return True if it is within range of at least one tribe, false if it is not within range of any.
     */
    static Tribe isTooClose(Location l) {
        TRIBES.find{  it.location.inRange(l,MINIMUM_TRIBE_DISTANCE) }
    }

    static void addDefaultTribe() {
        DBG "Making tribe with default settings"
        def x,y
        DBG "generating x and y..."
        do {
            x = Random.nextDouble() * 1000 * (Random.nextBoolean() ? -1 : 1)
            y = Random.nextDouble() * 1000 * (Random.nextBoolean() ? -1 : 1)
            DBG "> $x,$y"
        } while(isTooClose(new Location(x,y)))

        def nameIndex = Random.nextInt() % DEFAULT_NAMES.size()
        def name = DEFAULT_NAMES[nameIndex]

        DEFAULT_NAMES.remove(name)
        DBG "name $name now in use"

        addTribe(name,DEFAULT_SIZE,x,y)
    }

    static ArrayList<Person> generatePersons(count,ratio) {
        def childPercent,adultPercent,elderPercent
        (childPercent,adultPercent,elderPercent) = getPercents(ratio)

        int childTotal = count * childPercent
        int adultTotal = count * adultPercent
        int elderTotal = count * elderPercent

        // Fix any carry values.
        int initialTotal = childTotal + adultTotal + elderTotal
        if(count > initialTotal) {
            def difference = count - initialTotal
            adultTotal += difference
        }

        ArrayList<Person> persons = new ArrayList<>()

        childTotal.times {
            persons << new Person(Simulation.now.get(),STARTING_CHILD_AGE)
        }
        adultTotal.times {
            persons << new Person(Simulation.now.get(),STARTING_ADULT_AGE)
        }
        elderTotal.times {
            persons << new Person(Simulation.now.get(),STARTING_ELDER_AGE)
        }

        return persons
    }

    static def getPercents(String ratio) {
        def split = ratio.split(":").collect{ it as int }
        int total = split.sum()
        float cRatio = split[0] / total
        float aRatio = split[1] / total
        float eRatio = split[2] / total

        [cRatio,aRatio,eRatio]
    }

    static void printTribes() {
        println "Total of ${TRIBES.size()} tribe(s). Printing details."
        TRIBES.each { t ->
            println "Tribe Name: $t.name"
            println "> Current count: ${t.livingMembers.size()}"
            printAgeMakeup(t)
            println "> Time of most recent naming: ${t.historicalNames[-1].time}"
            println "> Location: $t.location"
        }
    }

    static void printAgeMakeup(t) {
        ArrayList<Person> childList = [],adultList = [],elderList = []
        t.livingMembers.each { p ->
            if(p.isChild())
                childList << p
            else if(p.isElder())
                elderList << p
            else
                adultList << p
        }

        def childTotal = childList.size()
        def maleChildCount = childList.findAll{it.sex == Sexual.Sex.PLUG }.size()
        def femaleChildCount = childTotal - maleChildCount
        println ">> Children: $childTotal with $maleChildCount male and $femaleChildCount female"
        def adultTotal = adultList.size()
        def maleAdultCount = adultList.findAll{it.sex == Sexual.Sex.PLUG }.size()
        def femaleAdultCount = adultTotal - maleAdultCount
        println ">> Adults: $adultTotal with $maleAdultCount male and $femaleAdultCount female"
        def elderTotal = elderList.size()
        def maleElderCount = elderList.findAll{it.sex == Sexual.Sex.PLUG }.size()
        def femaleElderCount = elderTotal - maleElderCount
        println ">> Elders: $elderTotal with $maleElderCount male and $femaleElderCount female"
    }

    // TODO: Possibly preferred males at some point?
    static void makeBabies() {
        DBG "making babies for ${TRIBES.size()} tribe(s)"
        TRIBES.each { tribe ->
            DBG "tribe: $tribe.name"
            List<Person> breedableMales = []
            List<Person> breedableFemales = []

            tribe.livingMembers.each {
                if(it.breedable) {
                    it.sex == Sexual.Sex.PLUG ? breedableMales << it : breedableFemales << it
                }
            }

            DBG "Total of ${breedableFemales.size()} female member(s) for breeding"

            breedableFemales.each { female ->
                if(female.checkFecundity() && breedableMales.size() > 0) {
                    DBG "> fecundity check passed, making baby"
                    // TODO: make this a part of the Tribe logic
                    int maleIndex = Random.nextInt() % breedableMales.size()

                    Person male = breedableMales[maleIndex]

                    tribe.birth(male,female)
                }
            }
        }
    }

    static void killPeople() {
        DBG "time for murderin"
        TRIBES.each { tribe ->
            DBG "tribe: $tribe.name"
            tribe.livingMembers.each { p ->
                if(p.checkDeadification()) {
                    DBG "death, age $p.age"
                    p.kill()
                }
            }
        }
    }
}
