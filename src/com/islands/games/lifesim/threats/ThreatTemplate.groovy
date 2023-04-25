package com.islands.games.lifesim.threats

import com.islands.games.lifesim.external.Informable
import com.islands.games.lifesim.external.visual.Printable

/**
 * Class for general kinds of threats that a Tribe may face in the simulation.
 */
class ThreatTemplate implements Printable, Informable {
    String name
    String description = null

    // How much mortality is impacted by this kind of threat.
    float threatLevel

    ThreatTemplate(String name,float threatLevel) {
        this.name = name
        this.threatLevel = threatLevel
    }

    void setDescription(String desc) {
        if(description != null) {
            DBG "Description for ThreatTemplate $name already set, but now resetting."
            DBG "> Old: $description"
            DBG "> New: $desc"
        }
        description = desc
    }

    Map info() {
        [
                name:name,
                description:description,
                threatLevel:threatLevel
        ]
    }
}
