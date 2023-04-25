package com.islands.games.lifesim.threats

import com.islands.games.lifesim.external.Informable
import com.islands.games.lifesim.external.Location
import com.islands.games.lifesim.metaphysics.AdvancementManager

/**
 * Class for threats a Tribe may face in the simulation.
 */
class Threat implements Informable {
    String name
    String description

    ThreatTemplate template

    // Location of this particular threat.
    Location location

    /**
     *
     * @param name
     * @param template
     * @param location
     */
    Threat(String name,ThreatTemplate template,Location location) {
        this.name = name
        this.template = template
        this.location = location
    }

    def getMitigators() {
        AdvancementManager.getMitigators(template)
    }

    Map info() {
        [
                name:name,
                description:description,
                template:template,
                location:location,
                mitigators:mitigators
        ]
    }
}