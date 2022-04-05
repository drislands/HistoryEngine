package com.islands.games.lifesim.metaphysics

/**
 * Class for individual mortality Handicaps a {@link com.islands.games.lifesim.society.Tribe} has. Constructed on a
 * case-by-case basis when a Tribe is constructed.
 */
class MortalityHandicap extends Handicap{
    MortalityHandicap(String mod) {
        modifier = Float.parseFloat(mod)
    }

    // The effect this handicap has on the affected Tribe.
    float modifier
}
