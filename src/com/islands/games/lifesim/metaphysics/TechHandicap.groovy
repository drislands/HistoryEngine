package com.islands.games.lifesim.metaphysics

import groovy.transform.Canonical

/**
 * Class for individual Tech Handicaps a {@link com.islands.games.lifesim.society.Tribe} has. Constructed on a
 * case-by-case basis when a Tribe is constructed.
 */
@Canonical
class TechHandicap extends Handicap{
    // The Tech advancement that the tribe is restricted from having access to.
    Tech restrictedTech
}
