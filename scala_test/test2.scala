trait BaseMsg {
    def apply() = this
}

object BaseMsg {
    def apply: String = {"Base Msg"}
}

trait IntMsg extends BaseMsg {
    def apply(i: Int): String
}


object IntMsg {
}

def realMsg = IntMsg { i =>
    "Msg"
}
