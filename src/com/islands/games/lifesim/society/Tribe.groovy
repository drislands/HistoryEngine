package com.islands.games.lifesim.society

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

/**
 * Class representing a group of {@link Person}s that are socially linked.
 */
class Tribe implements Serializable {
    // List of members of this Tribe.
    final ArrayList<Person> members = new ArrayList<>()
    // Name of the Tribe. Subject to change as the Tribe evolves.
    String name
    // Names the Tribe has had over time, including the current one. Mapped to the Time the name was assigned.
    final HashMap<Time,String> historicalNames = new HashMap<>()

    // Technologies and magics that the tribe has a natural affinity for.
    final ArrayList<Affinity> affinities = new ArrayList<>()
    // Handicaps which the tribe is subjected to. Tech, magic that they are unable to utilize.
    final ArrayList<Handicap> handicaps = new ArrayList<>()

    /**
     * Create a new Tribe.
     * @param name The name the Tribe has at creation. The {@link #historicalNames} is given this name, and assigned the
     * current {@link Time}.
     * @param members Array of {@link Person}s making up the {@link #members} of this Tribe. Each Person has its
     * {@link Person#tribe} assigned to this Tribe.
     */
    Tribe(String name,ArrayList<Person> members) {
        historicalNames[(Time)Simulation.now.clone()] = name
        this.name = name
        for(member in members) {
            this.members.add(member)
            member.tribe = this
        }
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
}
