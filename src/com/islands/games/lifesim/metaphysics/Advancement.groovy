package com.islands.games.lifesim.metaphysics

import com.islands.games.lifesim.threats.Threat

/**
 * Superclass for the various major advances a tribe can make in reducing mortality
 */
abstract class Advancement {
    String name

    ArrayList<Threat> threatsMitigated = new ArrayList<>()
}
