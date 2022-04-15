package com.islands.games.lifesim

import com.islands.games.lifesim.society.Tribe
import com.islands.games.lifesim.society.TribeManager
import com.islands.games.lifesim.threats.ThreatManager

/**
 * Management class for REST endpoints and the method calls they will make.
 */
class EndpointManager {
    /**
     * Gets initial details as given by the user. Takes a JSON payload and turns that into actual info.
     */
    static void setInitialDetails(Map payload /*JSON object with map details.*/) {
        // The payload will have a number of keyed elements, with array values.
        payload.tribes.each { Map t ->
            // Each tribe has a name, number of members, location.
            Tribe T = TribeManager.addTribe(t.name,t.count,t.x,t.y)
            // TODO: Adult:Child:Elder ratio
            T.addAffinities(t.affinities)
            T.addHandicaps(t.handicaps)
            // TODO: roll these into the addTribe() method
        }
        payload.threats.each { Map t ->
            // Each threat has a name, location.
            ThreatManager.addThreat(t.name,t.location)
        }
        payload.resources.each { Map r ->
            // Each resource has a name, location.
            ResourceManager.addResource(r.name,r.location)
            // TODO: ResourceManager and Resource class
        }
    }

    /**
     * When a user wants to view existing settings, whether for viewing or updating, this is called.
     */
    static void getConfig() {
        // TODO
    }

    /**
     * Receives a payload of config information that is translated for the ConfigManager class.
     */
    static void updateConfig() {
        // TODO
    }

    /**
     * Gets current information on a specified tribe.
     */
    static void getTribeInfo() {
        // TODO
    }

    /**
     * Sets one or more values for a given tribe.
     */
    static void setTribeDetails() {
        // TODO
    }

    /**
     * Gets current, broad details about the entire simulation. Tribe pop, current time, etc.
     */
    static void getWorld() {
        // TODO
    }

    /**
     * Creates a file containing information about the sim for the user, and provides it for download.
     */
    static void saveSimToFile() {
        // TODO
    }
}


