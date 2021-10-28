// return type is Sequence which is an abstract class for program and loop node
fun doParse(buf: StringBuffer, seq: Sequence): Sequence {
    // Store a single character
    var c: Char
    // As long as the string buffer isn't empty
    while (buf.length > 0) {
        // consume one character from the start of the buffer
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
    // Parsing string and create a tree
    val str =
        "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
    // BrainF__k version hello word sent to buf
    // doParse will create a tree with program() as a root (program is a root node)
    val hello: Node = doParse(StringBuffer(str), Program())
    //hello.accept(new PrintVisitor());
    println()
    // Print hello world from interpreter
    hello.accept(InterpreterVisitor())
}