package exercise1

/** Напишите отдельные функции, решающие поставленную задачу.
  *
  * Синтаксис:
  *   // метод
  *   def myFunction(param0: Int, param1: String): Double = // тело
  *
  *   // значение
  *   val myFunction: (Int, String) => Double (param0, param1) => // тело
  */
object Functions {

  /* a) Напишите функцию, которая рассчитывает площадь окружности
   *    r^2 * Math.PI
   */
  def SCircle(radius: Double): Double ={
    return Math.pow(radius,2)*Math.PI;
  }


  // примените вашу функцию из пункта (a) здесь, не изменяя сигнатуру
  def testCircle(r: Double): Double = SCircle(r)



  /* b) Напишите карированную функцию которая рассчитывает площадь прямоугольника a * b.
   */
  def SRectangleK(a: Double)(b: Double): Double ={
    return a*b
  }



  // примените вашу функцию из пукта (b) здесь, не изменяя сигнатуру
  def testRectangleCurried(a: Double, b: Double): Double = SRectangleK(a)(b)


  // c) Напишите не карированную функцию для расчета площади прямоугольника.
  def SRectangleN(a: Double, b: Double): Double ={
    return a*b
  }



  // примените вашу функцию из пункта (c) здесь, не изменяя сигнатуру
  def testRectangleUc(a: Double, b: Double): Double = SRectangleN(a,b)
}
