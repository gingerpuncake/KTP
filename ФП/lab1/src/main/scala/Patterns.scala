package exercise1

object PatternMatching {

  sealed trait Hand
  case object Rock    extends Hand
  case object Paper   extends Hand
  case object Scissor extends Hand

  sealed trait Result
  case object Win  extends Result
  case object Lose extends Result
  case object Draw extends Result

  sealed trait Food
  case object Meat       extends Food
  case object Vegetables extends Food
  case object Plants     extends Food

  sealed trait Animal {
    val name: String
    val food: Food
  }
  case class Mammal(name: String, food: Food, weight: Int) extends Animal
  case class Fish(name: String, food: Food)                extends Animal
  case class Bird(name: String, food: Food)                extends Animal



  /* a) Напишите функцию, которая ставит в соответствие числу строку слудеющим образом:
   * Если:
   *     1 => "it is one"
   *     2 => "it is two"
   *     3 => "it is three"
   *     иначе => "what's that"
   */

  def a(num: Int): String={
    num match {
         case 1 => "it is one"
         case 2 => "it is two"
         case 3 => "it is three"
         case _ => "what's that"
    }
  }

  // примените вашу функцию из пункта (a) здесь, не изменяя сигнатуру
  def testIntToString(value: Int): String = a(value)

  /* b) Напишите функцию которая возвращает true если переменная `value` принимает значение:
   *     "max" или "Max
   *     "moritz" или "Moritz"
   */

  def b(value: String): Boolean = {
    value match {
      case "max"  => true
      case "Max"  => true
      case "moritz" => true
      case "Moritz"  => true
      case _ => false
    }
  }

  // примените функции из пункта (b) здесь, не изменяя сигнатуру
  def testIsMaxAndMoritz(value: String): Boolean = b(value)

  // c) Напишите функцию проверки является ли `value` четным 
  def c(value: Int): Boolean={
    value%2 match {
      case 0  => true
      case 1  => false
    }
  }

  // примените функции из пункта (c) здесь, не изменяя сигнатуру
  def testIsEven(value: Int): Boolean = c(value)

  /* d) Напишите функцию, моделирующую игру в Камень ножницы бумага
   *     1. камень побеждает ножницы
   *     2. ножницы побеждают бумагу
   *     3. бумага побеждает камень
   *    Выиграет ли игрок `a`?
   */

  def d(a: Hand, b: Hand): Result={
    a match{
      case Rock   => {
        b match{
          case Rock    => Draw
          case Paper   => Lose
          case Scissor => Win
        }
      }
      case Paper  => {
        b match{
          case Rock     => Win
          case Paper    => Draw
          case Scissor  => Lose
        }
      }
      case Scissor  => {
        b match{
          case Rock     => Lose
          case Paper    => Win
          case Scissor  => Draw
        }
      }
    }
  }


  // примените вашу функцию из пункта (d) здесь, не изменяя сигнатуру
  def testWinsA(a: Hand, b: Hand): Result = d(a: Hand, b: Hand)

  // Примечание: используйте определение Animals

  // e) Верните вес (weight: Int) объекта Mammal, иначе верните -1.

  def e(animal: Animal): Int ={
    animal match {
      case Mammal(name, food, weight)=> weight
      case _ => -1
    }
  }

  // примените функцию из пункта (e) здесь, не изменяйте сигнатуру
  def testExtractMammalWeight(animal: Animal): Int = e(animal)


  // f) Измените поле еда объектов классов Fishes и Birds на Plants, класс Mammels оставьте неизмененным.

  def f(animal: Animal): Animal={
    var a = animal
    animal match {
      case Fish(name, food) => a = Fish(name,Plants)
      case Bird(name, food) => a = Bird(name,Plants)
      case Mammal(name, food, weight) => a = Mammal(name,food,weight)
    }
    a
  }

  // примените функцию из пункта (f) здесь, не изменяйте сигнатуру
  def testUpdateFood(animal: Animal): Animal = f(animal)

}