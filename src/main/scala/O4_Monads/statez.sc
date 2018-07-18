  import cats.data.State
  val myAge = State[Int, String] { age =>
    age + 1 -> s"my age was $age before being increased"
  }

  myAge.run(40).value

  type CalcState[A] = State[List[Int], A]

  def operate(operation: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case a :: b :: tail =>
        val res: Int = operation(a, b)
        (res :: tail) -> res
      case _ => throw new RuntimeException("something went really wrong")
    }

  def evalOne(sym: String): CalcState[Int] = sym match {
    case "+"    => operate(_+_)
    case "-"    => operate(_-_)
    case "*"    => operate(_*_)
    case "/"    => operate(_/_)
    case number => addValue(number.toInt)
  }

  def addValue(number: Int): CalcState[Int] = State[List[Int], Int] {
    stack =>
      (number +: stack) -> number
  }

  val program = for {
    _ <- evalOne("1")
    _ <- evalOne("2")
    answer <- evalOne("+")
  } yield answer

  program.runA(Nil).value
