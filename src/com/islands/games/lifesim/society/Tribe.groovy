package com.islands.games.lifesim.society

import com.islands.games.lifesim.Informable
import com.islands.games.lifesim.Location
import com.islands.games.lifesim.life.Person
import com.islands.games.lifesim.Simulation
import com.islands.games.lifesim.Time
import com.islands.games.lifesim.metaphysics.Advancement
import com.islands.games.lifesim.metaphysics.AdvancementManager
import com.islands.games.lifesim.metaphysics.Affinity
import com.islands.games.lifesim.metaphysics.AffinityManager
import com.islands.games.lifesim.metaphysics.Handicap
import com.islands.games.lifesim.metaphysics.Magic
import com.islands.games.lifesim.metaphysics.MagicHandicap
import com.islands.games.lifesim.metaphysics.MortalityHandicap
import com.islands.games.lifesim.metaphysics.Tech
import com.islands.games.lifesim.metaphysics.TechHandicap
import groovy.transform.Canonical

/**
 * Class representing a group of {@link Person}s that are socially linked.
 */
class Tribe implements Serializable, Informable {
    // List of members of this Tribe.
    final ArrayList<Person> members = new ArrayList<>()
    // Name of the Tribe. Subject to change as the Tribe evolves.
    String name
    // Names the Tribe has had over time, including the current one. Mapped to the Time the name was assigned.
    final ArrayList<Name> historicalNames = new ArrayList<>()

    // Technologies and magics that the tribe has a natural affinity for.
    final ArrayList<Affinity> affinities = new ArrayList<>()
    // Handicaps which the tribe is subjected to. Tech, magic that they are unable to utilize.
    final ArrayList<Handicap> handicaps = new ArrayList<>()

    Location location

    // By percent -- so 30 = 30%.
    int birthRate = 3
    float tribeMortality = 0.015


    /**
     * Create a new Tribe.
     * @param name The name the Tribe has at creation. The {@link #historicalNames} is given this name, and assigned the
     * current {@link Time}.
     * @param members Array of {@link Person}s making up the {@link #members} of this Tribe. Each Person has its
     * {@link Person#tribe} assigned to this Tribe.
     */
    Tribe(String name,ArrayList<Person> members,Location location) {
        historicalNames << new Name(Simulation.now.get(),name)
        this.location = location
        this.name = name
        for(member in members) {
            this.members.add(member)
            member.tribe = this
        }
    }

    void birth(Person father,Person mother) {
        Person p = new Person(Simulation.now.get(),father,mother)

        p.tribe = this
        members.add(p)

        mother.lastBirth = Simulation.now.get()
    }

    void addAffinities(affinities) {
        affinities.each { a ->
            Affinity A = AffinityManager.get(a)
            this.affinities.add(A)
        }
    }

    void addHandicaps(handicaps) {
        handicaps.each { h ->
            Handicap H
            Advancement a = AdvancementManager.get(h)
            if(a instanceof Magic) {
                H = new MagicHandicap(a)
            } else if(a instanceof Tech) {
                H = new TechHandicap(a)
            } else {
                H = new MortalityHandicap(h)
            }

            this.handicaps.add(H)
        }
    }

    ArrayList<Person> getLivingMembers() {
        members.findAll { p ->
            p.alive
        }
    }

    // TODO: Is this unchanging?
    float getYoungerMortality(Person p) {
        int monthsLowerBounds = (Person.AGE_OF_HEALTH[0] as int)
        // (x-100)^2 / (20 * 100)
        (0.01f) * Math.pow(p.age - monthsLowerBounds,2) / (20 * monthsLowerBounds)
                                                       //   ^ comes to 5% at age 0/0
    }

    // TODO: Is this unchanging?
    float getOlderMortality(Person p) {
        int monthsUpperBounds = (Person.AGE_OF_HEALTH[1] as int)
        // (x-180)^2 / (200 * 180)
        (0.01f) * Math.pow(p.age - monthsUpperBounds ,2) / (200 * monthsUpperBounds)
                                                       //     ^ 5% by 60/0, 15% by 90/0
    }

    Map info() {
        [
                name:name,
                members:members,
                historicalNames:historicalNames,
                affinities:affinities,
                handicaps:handicaps,
                location:location,
                birthrate:birthRate,
                mortality:tribeMortality
        ]
    }

    @Canonical
    class Name {
        Time time
        String name
    }
}
