package com.islands.games.lifesim.external

import com.islands.games.lifesim.external.visual.Printable
import com.islands.games.lifesim.society.TribeManager
import com.islands.games.lifesim.threats.ThreatManager

class ScriptManager implements Printable {
    static def tribes = Whom.TRIBES
    static def threat = Whom.THREATS

    static void parseScript(String path) {
        parseScript(new File(path))
    }
    static void parseScript(File file) {
        def scriptText = file.text
    }

    static def getManager(Whom w) {
        switch(w) {
            case Whom.TRIBES:
                return TribeManager
            case Whom.THREATS:
                return ThreatManager
            // TODO: default case
        }
    }

    // create threat named "Mt Boom" with template:"Volcano" at 10,-10


    class Affector {

    }

    class Creator {
        def categoryCreated

        def action
        def instanceName
        def details = [:]
        def neededDetails = []
        def named = { name ->
            instanceName = name
            this
        }
        def with = { Map args ->
            details = args
            this
        }
        def at = { x, y ->
            Location l = new Location(x,y)
            validate()

            action(instanceName,*details.values(),l)
            DBG "Successfully created instance of $categoryCreated at $l called $instanceName with details: $details"
        }


        def validate() {
            neededDetails.each { deet ->
                if(! (deet in details.keySet())) {
                    // TODO: invalid! throw something!
                }
            }
        }

        Creator(who) {
            switch(who) {
                case Whom.THREATS:
                    categoryCreated = "Threat"
                    action = ThreatManager.&addThreat
                    neededDetails << "template"
                    break
                case Whom.TRIBES:
                    categoryCreated = "Tribe"
                    action = TribeManager.&addTribe
                    neededDetails << "count"
                    break
            }
        }
    }

    class DistanceCategory {
        static Double getKilometers(Integer self) {
            self * 1000
        }
        static Double getKilometers(Double self) {
            self * 1000
        }
        static Double getMeters(Integer self) {
            self
        }
        static Double getMeters(Double self) {
            self
        }
    }

    enum Whom {
        TRIBES,THREATS
    }
}
