package com.islands.games.lifesim.metaphysics

/**
 * Management class for all extant {@link Affinity} objects.
 */
class AffinityManager {
    // List of all affinities that have been created.
    static ArrayList<Affinity> AFFINITIES = new ArrayList<>()

    // TODO: Affinity constructor, adds to above list

    /**
     * Gets the {@link Affinity} with the given name.
     */
    static Affinity get(String a) {
        AFFINITIES.find {
            it.name == a
        }
    }
}
