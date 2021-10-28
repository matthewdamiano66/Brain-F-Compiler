interface CarElement {
    fun accept(visitor: CarElementVisitor)
}
interface CarElementVisitor {
    fun visit(body: Body)
    fun visit(car: Car)
    fun visit(engine: Engine)
    fun visit(wheel: Wheel)
}
class Wheel(val name: String) : CarElement {
    override fun accept(visitor: CarElementVisitor) = visitor.visit(this)
}
class Body() : CarElement {
    override fun accept(visitor: CarElementVisitor) = visitor.visit(this)
}
class Engine() : CarElement {
    override fun accept(visitor: CarElementVisitor) = visitor.visit(this)
}
class Car() : CarElement {
    var elements: List<CarElement> = listOf(
        Wheel("front left"), Wheel("front right"),
        Wheel("back left"), Wheel("back right"),
        Body(), Engine()
    )

    override fun accept(visitor: CarElementVisitor) {
        for (element: CarElement in elements) {
            element.accept(visitor)
        }
        visitor.visit(this)
    }
}
class CarElementDoVisitor() : CarElementVisitor {
    override fun visit(body: Body) = println("Moving my body")
    override fun visit(car: Car) = println("Starting my car")
    override fun visit(engine: Engine) = println("Starting my engine")
    override fun visit(wheel: Wheel) = println("Kicking ${wheel.name} wheel")
}
class CarElementPrintVisitor() : CarElementVisitor {
    override fun visit(body: Body) = println("Visiting body")
    override fun visit(car: Car) = println("Visiting car")
    override fun visit(engine: Engine) = println("Visiting engine")
    override fun visit(wheel: Wheel) = println("Visiting ${wheel.name} wheel")
}
fun main(){
    val car = Car()
    car.accept(CarElementPrintVisitor())
    println()
    car.accept(CarElementDoVisitor())
}