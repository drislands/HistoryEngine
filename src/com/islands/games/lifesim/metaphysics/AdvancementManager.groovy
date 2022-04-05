package com.islands.games.lifesim.metaphysics

import com.islands.games.lifesim.threats.Threat

/**
 * Management class for various advancements a Tribe can make.
 */
class AdvancementManager {
    static ArrayList<Tech> TECHS = new ArrayList<>()
    static ArrayList<Magic> MAGICS = new ArrayList<>()
    // TODO: MILITARY, HOW TO DO?

    /**
     * Gets the Advancement with the given name.
     */
    static Advancement get(String name) {
        ((TECHS + MAGICS /* + MILITARY */) as ArrayList<Advancement>).find {
            it.name == name
        }
    }

    /**
     * Gets all {@link Advancement} objects that have the given {@link Threat} as something they mitigate.
     */
    static ArrayList<Advancement> getMitigators(Threat t) {
        // TODO: check all advancements and make a list of which ones mitigate the given threat
    }
}
