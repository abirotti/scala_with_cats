object ServiceState {

  sealed trait ServiceState

  final class Started extends ServiceState

  final class Stopped extends ServiceState

  // State is a ServiceState
  class Service[State <: ServiceState] private () {
    // State is a T, and T is a Stopped, so State can only be Stopped
    def start[T >: State <: Stopped]() = this.asInstanceOf[Service[Started]]

    def stop[T >: State <: Started]() = this.asInstanceOf[Service[Stopped]]
  }

  object Service {
    def create() = new Service[Stopped]
  }

  val initial = Service.create()
  val started = initial.start()
}

object ApplesAndOranges {
  trait Fruit[T] {
    def compareTo(other: Fruit[T]) = true
  }

  class Orange extends Fruit[Orange]

  class Apple extends Fruit[Apple]

  val o = new Orange
  val a = new Apple
//a compareTo o
// cannot compare Apples to Oranges, you can only compare it to other Fruit[Orange] instances
}

object RequiresTeeth {
  trait Teeth {
    def bite(): Unit = ???
  }

  trait Pizza {
    this: Teeth =>

    def eat(): Unit = bite
  }
}
