
object DoIt {
	def apply(op: Int => String) = (i: Int) => {
		println("Doing it")
		op(i)
	}
}

def realOp(in: Int)  = DoIt { i => 
	"Input value: " + in.toString + " and this is doing it for: " + i.toString
}

def dumbOp = DoIt { i => "This is dumb for: " + i.toString}

println(realOp(1)(2))
println(dumbOp(3))
