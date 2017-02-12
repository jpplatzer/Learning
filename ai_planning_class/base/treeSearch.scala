package treesearch {
    abstract class NodeState {
    }

    abstract class StateAction {
    }

    abstract class SearchProblem[S <: NodeState] {
        def initialState(): S
        def goalTest(s: S): Boolean
    }

    abstract class SearchNode[S <: NodeState, A <: StateAction](s: S, p: Option[SearchNode[S,A]],
        a: Option[A], c: Float, d: Int) {
        val state = s        
        val parent = p        
        val action = a        
        val cost = c        
        val depth = d
    }

    trait SearchStrategy[NS <: NodeState, SA <: StateAction, SN <: SearchNode[NS, SA]] {
        def initNode(ns: NS): SN
        def search(nodes: List[SN]): Option[SN]
    }
}

package object treesearch {
    def treeSearch[NS <: NodeState, SA <: StateAction, SN <: SearchNode[NS, SA], 
        SP <: SearchProblem[NS], SS <: SearchStrategy[NS, SA, SN]](p: SP, s: SS): List[SN] = {

        def repeatSearch(fringe: List[SN], p: SP, s: SS): List[SN] = {
            val searchNode = s.search(fringe)
            searchNode match {
                case None => List[SN]()
                case Some(n) => {
                    val newFringe = fringe :+ n
                    if (p.goalTest(n.state)) {
                        newFringe
                    }
                    else {
                        repeatSearch(newFringe, p, s)
                    }
                }
            }
        }

        val fringe = List[SN](s.initNode(p.initialState()))
        repeatSearch(fringe, p, s)
    }
    
    def showSearchResults[NS <: NodeState, SA <: StateAction, SN <: SearchNode[NS, SA]](nodes: List[SN]) = {
        if (!nodes.isEmpty) {
            type SearchNodeType = SearchNode[NS, SA]
            var nodeOpt: Option[SearchNodeType] = Some(nodes.last)
            var pathNodes = List[SearchNodeType]()
            while (!nodeOpt.isEmpty) {
                val node = nodeOpt.head
                pathNodes = node :: pathNodes
                nodeOpt = node.parent
            }
            
            println("Search created " + nodes.size + " nodes with " + pathNodes.size + " in the path:")
            println(pathNodes)
        }
        else {
            println("Search failed to find a path")
        }
    }
    
    /**
    function aStarTreeSearch(problem, h)
        fringe -> priorityQueue(new searchNode(problem.initialState))
        allNodes -> hashTable(fringe)
        loop
            if empty(fringe) then return failure
            node -> selectFrom(fringe)
            if problem.goalTest(node.state) then
                return pathTo(node)
            for successor in expand(problem, node)
                if not allNodes.contains(successor) then
                    fringe -> fringe + successor @ f(successor)
                    allNodes.add(successor)
    
    uses Scala PriorityQueue
    
    scala> import scala.collection.mutable.PriorityQueue
    //  import scala.collection.mutable.PriorityQueue

    scala> def diff(t2: (Int,Int)) = math.abs(t2._1 - t2._2)
    // diff: (t2: (Int, Int))Int

    scala> val x = new PriorityQueue[(Int, Int)]()(Ordering.by(diff))
    // x: scala.collection.mutable.PriorityQueue[(Int, Int)] = PriorityQueue()

    scala> x.enqueue(1 -> 1)

    scala> x.enqueue(1 -> 2)

    scala> x.enqueue(1 -> 3)
    
    
    def aStarTreeSearch[NS <: NodeState, SA <: StateAction, SN <: SearchNode[NS, SA], 
        SP <: SearchProblem[NS], NH <: NodeHash[SN]] (p: SP, h: NH): Option[SearchNode] = {
        
    }
    */
}