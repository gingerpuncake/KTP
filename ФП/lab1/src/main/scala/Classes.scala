//package exercise1


/* This task has no tests. It is an exercise for you to write different class structures.
 *
 a) Создать класс Animal, который имеет следующие поля:
 *      - name: String (название)
 *      - species: String (вид)
 *      - food: String
 *
 *    Синтаксис: class MyClass(val publicField: Int, privateField: String) {
 *              // остальные поля и методы
 *            }
 *
 * b) Создайте объект-компаньон для класса Animal и добавьте следующие сущности как поля:
 *      - cat, mammal, meat
 *      - parrot, bird, vegetables
 *      - goldfish, fish, plants
 *
 *    Синтаксис: object MyClass {
 *              // статические поля и методы
 *            }
 *
 * c) Добавьте следующие метод в Animals:
 *      def eats(food: String): Boolean
 *
 *     который проверяет ест ли животное определенную пищу
 *
 * d) Переопределите ваш класс Animal как трейт и создайте объекты класса-образца для Mammals, Birds и Fishs.
 *    Вам все еще нужно поле `species`?
 *
 * e) Добавьте следующие функции в объект-компаньон Animal:
 *      def knownAnimal(name: String): Boolean  // true если это имя одного из трех животных из (b)
 *      def apply(name: String): Option[Animal] // возвращает одно из трех животных в соответствии с именем (Some) или ничего (None), см. ниже
 *
 * f) Создайте трейт Food со следующими классами-образцами:
 *      - Meat
 *      - Vegetables
 *      - Plants
 *   и добавьте это в определение Animal. Так же добавьте объект-компаньон с методом apply():
 *      def apply(food: String): Option[Food]
 */


class Animal(names: String, foods: String)
{
  var name: String = null
  var food: String = null

  def eats(food: String): Boolean={
    if (food.equals(null))false
    else true
  }
}

object Animal {
  var Mammals = ("cat","meat")
  var Birds = ("parrot","vegetable")
  var Fishs = ("goldfish","plants")

  trait Food {
    case object Meat extends Food
    case object Vegetables extends Food
    case object Plants extends Food

    def apply(food: String): Option[Food] =
      return Some.apply(Food.this)

  }

  trait Animal {
    case object Mammal extends Animal
    case object Fish extends Animal
    case object Bird extends Animal

    def knownAnimal(name: String): Boolean = {
      if ((Mammal == "cat") || (Bird == "parrot") || (Fish == "goldfish")) true
      else false
    }

    def apply(name: String): Option[Animal] =
      return Some.apply(Animal.this)

  }


  sealed trait Option[A]
  {
    def isEmpty: Boolean
  }
  case class Some[A](a: A) extends Option[A]{
    val isEmpty = false
  }
  case class None[A]() extends Option[A]{
    val isEmpty = true
  }
}





