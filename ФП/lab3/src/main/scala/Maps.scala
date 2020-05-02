

/** Напишите вашу реализацию в тестовые функции.
  * 
  * https://docs.scala-lang.org/overviews/collections/maps.html
  */
object Maps {

  case class User(name: String, age: Int)

  /* a) В данной Seq[User] сгруппируйте пользователей по имени (`groupBy`) и вычислите средний возраст: `name -> averageAge`
   *    Вы можете реализовать ваше решение в теле тестовой функции. Не изменяйте сигнатуру.
   */

  def testGroupUsers(users: Seq[User]): Map[String, Int] = {
    var map: Map[String, Int]= Map()
    var a=users.groupBy(_.name)
    for (e <- a){
      var cc= e._2.toBuffer.foldLeft[Int](0)((acc, next) => acc + next.age
      )/e._2.toBuffer.size
      map+=(e._1->cc)
    }
    map
  }

  /* b) Дана `Map[String, User]` состоящая из имен пользователей `User`, сколько имен пользователей, содержащихся в Map, содержат подстроку "Adam"?
   *    Вы можете реализовать ваше решение в теле тестовой функции. Не изменяйте сигнатуру.
   */

  def testNumberFrodos(map: Map[String, User]): Int = {
    (for (elem <- map if elem._2.name.contains("Adam")) yield elem).size;
  }

  /* c) Удалите всех пользователей возраст которых менее 35 лет.
   *    Вы можете реализовать ваше решение в теле тестовой функции. Не изменяйте сигнатуру.
   */

  def testUnderaged(map: Map[String, User]): Map[String, User] = {
    map.filter(x => x._2.age>=35)
  }

}
