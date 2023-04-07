package com.islands.games.lifesim.threats

import com.islands.games.lifesim.Location

/**
 * Management class for {link Threat} objects.
 */
class ThreatManager {
    final static ArrayList<Threat> THREATS = new ArrayList<>()
    final static Map<String,ThreatTemplate> THREAT_TEMPLATES = [:]
    // TODO: Store threats, create functions.

    /**
     *
     * @param name
     * @param templateName
     * @param location
     * @return
     */
    static Threat addThreat(String name, String templateName,Map location) {
        ThreatTemplate template = THREAT_TEMPLATES[templateName]
        Location L = new Location(location.x,location.y)

        Threat T = new Threat(name,template,L)
        THREATS << T

        return T
    }

    /**
     *
     * @param name
     * @param threatLevel
     * @return
     */
    static ThreatTemplate addTemplate(String name,float threatLevel) {
        ThreatTemplate T = new ThreatTemplate(name,threatLevel)

        THREAT_TEMPLATES[name] = T

        return T
    }
}
