/**
 *  http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html
 *  http://docs.groovy-lang.org/docs/latest/html/documentation/core-metaprogramming.html#categories
 *  https://www.baeldung.com/groovy-categories
 */

// affects tribes within 10.meters
// affects tribes everywhere

TribeManager.makeTribe("the closies",2)
TribeManager.makeTribe("the middies",9)
TribeManager.makeTribe("the farguys",15)
TribeManager.makeTribe("the outters",900)
TribeManager.makeTribe("the aliens",15000)




enum Whom { TRIBES }
def tribes = Whom.TRIBES
def close,far,all
use(DistanceCategory) {
  close = affects tribes within 10.meters
  far   = affects tribes within 10.kilometers
  all   = affects tribes everywhere
}
println "Close:"
close.each { t ->
  println "> $t.name : $t.where"
}
println "Far:"
far.each { t ->
  println "> $t.name : $t.where"
} 
println "All:"
all.each { t ->
  println "> $t.name : $t.where"
} 



//////////////////////////////////////

def affects(who) {
  new Affector(who:who)
}




class Affector {
  def who
  def within = { dist ->
    getManager(who).getLocal(dist)
  }
  def getEverywhere() {
    getManager(who).getLocal(-1)
  }
  
  def getManager(Whom w) {
    switch(w) {
      case Whom.TRIBES:
        return TribeManager
    }
  }
}


class Tribe { int where;String name }

class TribeManager { 
  static Tribes = []
  
  static Tribe makeTribe(name,where) {
    Tribe t = new Tribe(name:name,where:where)
    Tribes << t
    t
  }
  
  static getLocal(dist) {
    Tribes.findAll { t -> t.where < dist || dist < 0 }
  }
}

class DistanceCategory {
  static Integer getKilometers(Integer self) {
    self * 1000
  }
  static Integer getMeters(Integer self) {
    self
  }
}

//////////////////////////////////////