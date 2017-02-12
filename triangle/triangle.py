import sys

def maxTrianglePath(filename):
    # maxColSum is the running max sum for each column
    # It will have num columns + 1 entries to support n + 1 comparisons
    maxColSum = [0,0]
    # File automatically closes when deleted
    f = open(filename, 'r')
    for rowNum, line in enumerate(f):
        # Convert string of space separated numbers into a list of ints
        row = list(map(int, line.split()))
        assert(len(row) == rowNum+1)
        prevColMax = 0
        for colNum in range(0, rowNum+1):
            currColMax = maxColSum[colNum]
            maxColSum[colNum] = row[colNum] + max(prevColMax, currColMax, maxColSum[colNum+1])
            prevColMax = currColMax
        maxColSum.append(0) 
    return max(maxColSum)

if len(sys.argv) >= 2:
    filename = sys.argv[1]
    print('Maximum total for', filename, 'is', maxTrianglePath(filename))
else:
    print('Must specify the triangle file path as the first command line parameter')
