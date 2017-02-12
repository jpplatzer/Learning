import treesearch._

package object nqueens {

    def nqSearch() {
        val numRows = 8
        val numCols = 8
        val lastRow = numRows - 1
        val board = new Array[Int](numRows)
        var searchCnt = 0

        def conforms(board: Array[Int], row: Int, col: Int, testRow: Int): Boolean = {
            if (testRow < 0) {
                true
            }
            else {
                val testCol = board(testRow)
                val deltaRow = row - testRow
                if (testCol == col || testCol == (col - deltaRow) || testCol == (col + deltaRow)) {
                    false
                }
                else {
                    conforms(board, row, col, testRow - 1)
                }
            }
        }
        
        /**
         Find a solution for starting with this row and for all subsequent rows
         and add it to the board.
         Return true if a solution is found, false otherwise
         */
        def findSolution(board: Array[Int], row: Int): Boolean = {
            var valid = false
            for (col <- 0 until numCols; if !valid) {
                searchCnt += 1
                if (conforms(board, row, col, row - 1)) {
                    board(row) = col
                    valid = row == lastRow || findSolution(board, row + 1)
                }
            }
            valid
        }

        if (findSolution(board, 0)) {
            println("Solution after " + searchCnt + " attempts: ")
            board.foreach(println)
        }
        else {
            println("No solution")
        }
    }
}

object test {
    def main(args: Array[String]) {
        import nqueens._
        nqSearch()
    }
}