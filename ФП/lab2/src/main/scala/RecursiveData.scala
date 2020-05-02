

sealed trait List[A]
case class Cons[A](head: A, tail: List[A]) extends List[A]
case class Nil[A]() extends List[A]

/** Напишите свои решения в виде функций. */
object RecursiveData {

  // a) Реализуйте функцию, определяющую является ли пустым `List[Int]`.

  def ListIntEmpty(list: List[Int]): Boolean =
    list match {
    case Nil() => true
    case Cons(head, tail) => false
  }

  // используйте функцию из пункта (a) здесь, не изменяйте сигнатуру

  def testListIntEmpty(list: List[Int]): Boolean = ListIntEmpty(list)

  // b) Реализуйте функцию, которая получает head `List[Int]`или возвращает -1 в случае если он пустой.

  def ListIntHead(list: List [Int]): Int = list match {
    case Cons(head, tail) => head
    case Nil() => -1
  }

  // используйте функцию из пункта (a) здесь, не изменяйте сигнатуру

  def testListIntHead(list: List[Int]): Int = ListIntHead(list)

  // c) Можно ли изменить `List[A]` так чтобы гарантировать что он не является пустым?

  // Для того , чтобы изменить List так, чтобы гарантировать , что он не является пустым
  // нужно использовать Nil[A](head: A) место Nil[A]()

  /* d) Реализуйте универсальное дерево (Tree) которое хранит значения в виде листьев и состоит из:
   *      node - левое и правое дерево (Tree)
   *      leaf - переменная типа A
   */

  sealed trait Tree[A]
  case class TCons[A](leaf: A, node: (Tree[A],Tree[A])) extends Tree[A]
  case class TNil[A]() extends Tree[A]

}
