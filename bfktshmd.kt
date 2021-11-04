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
    fun visit(loop: Loop)
    fun visit(program: Program)
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
class Loop() : Sequence() {
    override fun accept(visitor: Visitor) =  visitor.visit(this)
}
class Program() : Sequence(){
    override fun accept(visitor: Visitor) = visitor.visit(this)
}
abstract class Sequence() : Node{
    var values : MutableList<Node> = mutableListOf<Node>()
    fun add(node : Node) {
        values.add(node)
    }
}
class InterpreterVisitor() : Visitor{
    var mem = Array<Byte>(30000) {0}
    var pointer = 0
    override fun visit(left: Left) {
        pointer--
    }
    override fun visit(right: Right) {
        pointer++
    }
    override fun visit(increment: Increment) {
        mem[pointer]++
    }
    override fun visit(decrement: Decrement) {
        mem[pointer]--
    }
    override fun visit(input: Input) {
        mem[pointer]
    }
    override fun visit(output: Output) {
        println(mem[pointer].toInt().toChar())
    }
    override fun visit(loop: Loop) {
        var byte : Byte = 0
        while (mem[pointer] != byte) {
            for (node in loop.values)
                node.accept(this)
        }
    }
    override fun visit(program: Program) {
        for (node in program.values){
            node.accept(this)
        }
    }
}
fun doParse(buf: StringBuffer, seq: Sequence): Sequence {
    var c: Char
    while (buf.length > 0) {
        c = buf[0]
        buf.deleteCharAt(0)
        when (c) {
            '<' -> seq.add(Left())
            '>' -> seq.add(Right())
            '+' -> seq.add(Increment())
            '-' -> seq.add(Decrement())
            '.' -> seq.add(Output())
            ',' -> seq.add(Input())
            '[' -> seq.add(doParse(buf, Loop()))
            ']' -> return seq
        }
    }
    return seq
}
fun main(args: Array<String>) {
    val str =
        "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
    val hello: Node = doParse(StringBuffer(str), Program())
    hello.accept(InterpreterVisitor())
    //println(str)
    //println(hello)
}
