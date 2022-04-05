package com.islands.games.lifesim.society

import com.islands.games.lifesim.life.Person

class TribeManager {
    static String MEMBER_RATIO = "1:3:1"
    static Tribe addTribe(name,count,location) {
        ArrayList<Person> persons = generatePersons(count,MEMBER_RATIO)

        Tribe T = new Tribe(name,persons)
    }

    static ArrayList<Person> generatePersons(count,ratio) {
        // TODO - using the ratio and the total count, create people of varying ages
    }
}
