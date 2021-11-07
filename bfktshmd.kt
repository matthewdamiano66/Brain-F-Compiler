//Sean Howe
//Matthew Damiano
interface Node {
    fun accept(visitor: Visitor)
}
interface Visitor {
    fun visit(left: Left)
    fun visit(right: Right)
    fun visit(increment: Increment)
    fun visit(decrement: Decrement)
    fun visit(input: Input)
    fun visit(output: Output)
    fun visit(loop: loop)
    fun visit(program: line)
}
class Left() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class Increment() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class Right() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class Decrement() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class Input() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class Output() : Node{
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
class loop() : Sequence() {
    override fun accept(visitor: Visitor) =  visitor.visit(this)
}
class line() : Sequence(){
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
abstract class Sequence() : Node{
    var values : MutableList<Node> = mutableListOf<Node>()
    fun add(node : Node) {
        values.add(node)
    }
}
class visitorinterpreter() : Visitor{
    var memory = Array<Byte>(30000) {0}
    var point = 0
    override fun visit(left: Left) {
        point--
    }
    override fun visit(right: Right) {
        point++
    }
    override fun visit(increment: Increment) {
        memory[point]++
    }
    override fun visit(decrement: Decrement) {
        memory[point]--
    }
    override fun visit(input: Input) {
        memory[point]
    }
    override fun visit(output: Output) {
        println(memory[point].toInt().toChar())
    }
    override fun visit(loop: loop) {
        var byte : Byte = 0
        while (memory[point] != byte) {
            for (node in loop.values)
                node.accept(this)
        }
    }
    override fun visit(program: line) {
        for (node in program.values){
            node.accept(this)
        }
    }
}
fun Parse(buf: StringBuffer, seq: Sequence): Sequence {
    var character: Char
    while (buf.length > 0) {
        character = buf[0]
        buf.deleteCharAt(0)
        when (character) {
            '<' -> seq.add(Left())
            '>' -> seq.add(Right())
            '+' -> seq.add(Increment())
            '-' -> seq.add(Decrement())
            '.' -> seq.add(Output())
            ',' -> seq.add(Input())
            '[' -> seq.add(Parse(buf, loop()))
            ']' -> return seq
        }
    }
    return seq
}
fun main() {
    val string = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
    val hello: Node = Parse(StringBuffer(string), line())
    hello.accept(visitorinterpreter())
}
