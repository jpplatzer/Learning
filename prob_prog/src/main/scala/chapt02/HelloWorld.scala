package chap02

import com.cra.figaro.language.{Flip, Select}			//#A
import com.cra.figaro.library.compound.If				//#A
import com.cra.figaro.algorithm.factored.VariableElimination	//#A

object HelloWorldFigaro {
  
  val sunnyToday = Flip(0.2)					//#B
  val greetingToday = If(sunnyToday, 				//#C
       Select(0.6 -> "Hello, world!", 0.4 -> "Howdy, universe!"),	//#C
       Select(0.2 -> "Hello, world!", 0.8 -> "Oh no, not again"))	//#C
  val sunnyTomorrow = If(sunnyToday, Flip(0.8), Flip(0.05))	//#D
  val greetingTomorrow = If(sunnyTomorrow,				//#E
       Select(0.5 -> "Hello, world!", 0.5 -> "Howdy, universe!"),	//#E
       Select(0.1 -> "Hello, world!", 0.9 -> "Oh no, not again"))	//#E

  def predict() {
    val result = VariableElimination.probability(greetingToday, "Hello, world!") // #F
    println("Today's greeting is \"Hello, world!\" " +
            "with probability " + result + ".")			//#G
  }

  def infer() {
    greetingToday.observe("Hello, world!")				//#H
    val result = VariableElimination.probability(sunnyToday, true)		//#F
    println("If today's greeting is \"Hello, world!\", today's " +
            "weather is sunny with probability " + result + ".")	//#G
  }

  def learnAndPredict() {
    val result = 
      VariableElimination.probability(greetingTomorrow, "Hello, world!")	//#F
    println("If today's greeting is \"Hello, world!\", " +		                               
            "tomorrow's greeting will be \"Hello, world!\" " +
            "with probability " + result + ".")			//#G
  }

  def ex1() {
    val goodSideOfBed = Flip(0.6)
    val result = VariableElimination.probability(goodSideOfBed, true) // #F
    println("On good side of the bed with probability " + result)
  }

  def ex2(sideOfBed: String) {
    if (sideOfBed == "bad") {
      greetingToday.observe("Oh no, not again")        //#H
    }
    predict()              //#I
    infer()                //#I
    learnAndPredict()            //#I
    greetingToday.unobserve()
  }
  
  def main(args: Array[String]) {					//#I
    // predict()							//#I
    // infer()								//#I
    // learnAndPredict()						//#I
    // ex1()
    ex2("good")
    ex2("bad")
  }
}

