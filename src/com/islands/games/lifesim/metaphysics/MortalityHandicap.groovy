package com.islands.games.lifesim.metaphysics

class MortalityHandicap extends Handicap{
    MortalityHandicap(String mod) {
        modifier = Float.parseFloat(mod)
    }

    float modifier
}
