package com.islands.games.lifesim.metaphysics

import groovy.transform.Canonical

/**
 * Class for individual Magic Handicaps a {@link com.islands.games.lifesim.society.Tribe} has. Constructed on a
 * case-by-case basis when a Tribe is constructed.
 */
@Canonical
class MagicHandicap extends Handicap {
    // The Magic advancement that the tribe is restricted from having access to.
    Magic restrictedMagic
}
