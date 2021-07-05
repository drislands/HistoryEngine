package com.islands.games.lifesim.society

import com.islands.games.lifesim.life.Person
import com.islands.games.lifesim.Simulation
import com.islands.games.lifesim.Time

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

    /**
     * Create a new Tribe.
     * @param name The name the Tribe has at creation. The {@link #historicalNames} is given this name, and assigned the
     * current {@link Time}.
     * @param members Array of {@link Person}s making up the {@link #members} of this Tribe. Each Person has its
     * {@link Person#tribe} assigned to this Tribe.
     */
    Tribe(String name,Person... members) {
        historicalNames[(Time)Simulation.now.clone()] = name
        this.name = name
        for(member in members) {
            this.members.add(member)
            member.tribe = this
        }
    }
}
