
import java.security.cert.PKIXRevocationChecker

import scala.annotation.tailrec

/** Напишите свои решения в тестовых функциях.
 *
 * Seq(1, 2) match {
 *   case head +: tail => ???
 *   case Nil          => ???
 *   case s            => ???
 * }
 *
 * https://www.scala-lang.org/api/2.12.0/scala/collection/Seq.html
 */
// Примечание: напишите функции с хвостовой рекурсией

object Sequence {

  /* a) Найдите последний элемент Seq.
   *
   */

  def funA[A](seq: Seq[A]): Option[A] ={
    seq match {
      case last +: Nil  => Option(last)
      case head +: tail => funA(tail)
    }
  }

  def testLastElement[A](seq: Seq[A]): Option[A] = funA(seq)

  /* b) Объедините две Seqs (то есть Seq(1, 2) и Seq(3, 4) образуют Seq((1, 3), (2, 4))) - если Seq длиннее игнорируйте оставшиеся элементы.
   *
   */

  def funB[A](a: Seq[A], b: Seq[A]): Seq[(A, A)] ={

    def loop[A](a: Seq[A], b: Seq[A], c: Seq[(A, A)]): Seq[(A, A)]= {
      a match {
        case ahead +: atail => b match {
          case blast +: Nil  => c:+(ahead,blast)
          case bhead +: btail => loop(atail,btail,c:+(ahead,bhead))
        }
        case Nil  => c
      }
    }
    loop(a,b,Nil)
  }

  def testZip[A](a: Seq[A], b: Seq[A]): Seq[(A, A)] = funB(a,b)

  /* c) Проверьте, выполняется ли условие для всех элементов в Seq.
   *
   */

  def funC[A](seq: Seq[A])(cond: A => Boolean): Boolean ={
    def loop[A](seq: Seq[A],flag: Boolean)(cond: A => Boolean): Boolean = {
      seq match {
        case head :: tail => loop(tail,flag && cond(head))(cond)
        case Nil => flag
      }
    }
    loop(seq,true)(cond)
  }

  def testForAll[A](seq: Seq[A])(cond: A => Boolean): Boolean = funC(seq)(cond)

  /* d) Проверьте, является ли Seq палиндромом
   *
   */

  def funD[A](seq: Seq[A]): Boolean={
    def loop[A](sseq: Seq[A], aseq: Seq[A]): Boolean = {
      sseq match {
        case head :: tail => loop(tail,aseq = head+:aseq)
        case Nil => seq.equals(aseq)
      }
    }
    loop(seq,Nil)
  }

  def testPalindrom[A](seq: Seq[A]): Boolean = false

  /* e) Реализуйте flatMap используя foldLeft.
   *
   */

  def funE[A, B](seq: Seq[A])(f: A => Seq[B]): Seq[B]={
    seq.foldLeft[Seq[B]](Nil)((acc, next) => f(next).++:(acc))
  }

  def testFlatMap[A, B](seq: Seq[A])(f: A => Seq[B]): Seq[B] = funE(seq)(f)
}
