import treesearch._

package mc_search {
    class MCSide(numM: Int, numC: Int, boatHere: Boolean) {
        val numMissionaries = numM
        val numCanniabals = numC
        val boat = boatHere
        def total(): Int = {numMissionaries + numCanniabals}
        override def toString = {numM + " missionaries, " + numC + " cannibals"}
        override def equals(r: Any) = {
            r.isInstanceOf[MCSide] && 
            numMissionaries == r.asInstanceOf[MCSide].numMissionaries && 
            numCanniabals == r.asInstanceOf[MCSide].numCanniabals &&
            boat == r.asInstanceOf[MCSide].boat
        }
        def conforms(): Boolean = {
            numMissionaries >= 0 &&
            numCanniabals >= 0 &&
            (numMissionaries == 0 || numMissionaries >= numCanniabals)
        }
    }

    class MCNodeState(l: MCSide, r: MCSide) extends NodeState {
        val left = l
        val right = r
        override def toString = {"Left: " + left + ", Right: " + right + 
            ", Boat: " + { left.boat match {
                        case false => "right"
                        case true => "left"
                    }
                }
            }
        override def equals(r: Any) = {
            r.isInstanceOf[MCNodeState] && 
            left == r.asInstanceOf[MCNodeState].left && 
            right == r.asInstanceOf[MCNodeState].right
        }
        def conforms(): Boolean = {
            left.conforms && right.conforms
        }
    }

    class MCAction(nm: Int, nc: Int) extends StateAction {
        val numM = nm
        val numC = nc
        assert(numM >= 0 && numC >= 0 && (numM + numC) <= 2)
        override def toString = {numM + " missionaries and " + numC + " cannibals "}
    }

    class MCSearchNode(s: MCNodeState, p: Option[MCSearchNode], a: Option[MCAction], c: Float, d: Int) extends 
        SearchNode[MCNodeState, MCAction](s, p, a, c, d) {
        var numActionsTried = 0    

        override def toString = {"Node action: " + action + ", state: " + state + "\n"}
        override def equals(r: Any) = {
            r.isInstanceOf[MCSearchNode] && 
            state == r.asInstanceOf[MCSearchNode].state
        }
    }

    class MCSearchProblem() extends SearchProblem[MCNodeState] {
        val left = new MCSide(3, 3, true)
        val right = new MCSide(0, 0, false)
        val initial = new MCNodeState(left, right)
        override def initialState = {initial}
        override def goalTest(s: MCNodeState) = {
            s.left.total() == 0 && s.right.total() == 6
        }
    }

    class MCSearchStrategy extends SearchStrategy[MCNodeState, MCAction, MCSearchNode] {
        override def initNode(ns: MCNodeState): MCSearchNode = {
            new MCSearchNode(ns, None, None, 0, 0)
        }

        override def search(nodes: List[MCSearchNode]): Option[MCSearchNode] = {
            // Tuple num cannibals, num missionaries
            val actionTuples = List((0,2), (0,1), (1,0), (1,1), (2,0))

            def applyAction(s: MCNodeState, a: MCAction): MCNodeState = {
                val lMult = s.left.boat match {
                    case false => 1
                    case true => -1
                }
                val rMult = lMult * -1
                val lSide = new MCSide(s.left.numMissionaries + a.numM * lMult, s.left.numCanniabals + a.numC * lMult, lMult > 0)
                val rSide = new MCSide(s.right.numMissionaries + a.numM * rMult, s.right.numCanniabals + a.numC * rMult, rMult > 0)
                new MCNodeState(lSide, rSide)
            }

            def nodeExists(state: MCNodeState): Boolean = {
                var exists = false
                for (idx <- nodes.length - 1 to 0 by -1; if !exists) {
                        exists = nodes(idx).state == state
                    }
                exists    
            }

            var nextNode: Option[MCSearchNode] = None
            for (node <- nodes; if nextNode == None) {
                for (tupleIdx <- node.numActionsTried until actionTuples.length; if nextNode == None) {
                    val actionTuple = actionTuples(tupleIdx)
                    val action = new MCAction(actionTuple._1, actionTuple._2)
                    val newState = applyAction(node.state, action)
                    if (newState.conforms() && !nodeExists(newState)) {
                        val newNode = new MCSearchNode(newState, Some(node), Some(action), 1, 1)
                        nextNode = Some(newNode)
                    }
                    node.numActionsTried += 1
                }
            }
            nextNode
        }
    }
}

object test {
    import mc_search._
    
    def main(args: Array[String]) {
        val problem = new MCSearchProblem()
        val strategy = new MCSearchStrategy()
        val nodes = treeSearch[MCNodeState, MCAction, MCSearchNode, 
            MCSearchProblem, MCSearchStrategy](problem, strategy)
        showSearchResults[MCNodeState, MCAction, MCSearchNode](nodes)
    }
}