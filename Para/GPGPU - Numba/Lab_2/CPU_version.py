import numpy as np

def scanCPU(array):
    n = len(array)
    m = int(np.log2(n))

    # Up-sweep phase
    for d in range(0, m):
        for k in range(0, n - 1, 2 ** (d + 1)):
            array[k + 2 ** (d + 1) - 1] += array[k + 2 ** d - 1]

    # Down-sweep phase
    array[n - 1] = 0
    for d in range(m - 1, -1, -1):
        for k in range(0, n, 2 ** (d + 1)):
            tmp = array[k + 2 ** d - 1]
            array[k + 2 ** d - 1] = array[k + 2 ** (d + 1) - 1]
            array[k + 2 ** (d + 1) - 1] += tmp

    return array
