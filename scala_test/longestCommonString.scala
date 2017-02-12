
def longestCommonString(strList: List[String]): Int = {
    def calcLcs(strList: List[String], maxLen: Int, idx: Int): Int = {
        val lenLimit = math.min(maxLen, strList(idx).size)
        var newMax = 0 
        for (i <- 0 until lenLimit; if strList(idx)(i) == strList(idx-1)(i)) {
            newMax += 1
        }
        val nexIdx = idx+1
        if (newMax == 0 || nexIdx == strList.size) {
            newMax
        }
        else {
            calcLcs(strList, newMax, nexIdx)
        }
    }
    
    if (strList.size > 0) {
        val maxLen = strList(0).size
        if (strList.size > 1) {
            calcLcs(strList, maxLen, 1)
        }
        else {
            maxLen
        }
        
    }
    else {
        0
    }
}

def showLcs(l: List[String]) = {
    val lcs = longestCommonString(l)
    println("LCS for " + l.toString + " is: " + lcs.toString)
}

val l1 = List("abcdefg", "abcdeasfdv", "abcd", "abcdeasf", "abcdef")
showLcs(l1)

val l2 = List("abcdefg", "abcdeasfdv", "abcdefghi", "abcdeasf", "abcdef")
showLcs(l2)
