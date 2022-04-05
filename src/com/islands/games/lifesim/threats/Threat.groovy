package com.islands.games.lifesim.threats

import com.islands.games.lifesim.Location
import com.islands.games.lifesim.metaphysics.AdvancementManager

/**
 * Class for threats a Tribe may face in the simulation.
 */
class Threat {
    String name
    String description

    // Location of this particular threat.
    Location location

    // How much mortality is impacted by this threat.
    float threatLevel

    def getMitigators() {
        AdvancementManager.getMitigators(this)
    }
}