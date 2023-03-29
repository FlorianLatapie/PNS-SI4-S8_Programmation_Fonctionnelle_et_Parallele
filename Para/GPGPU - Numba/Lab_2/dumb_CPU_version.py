import numpy as np

def scanDumb(array):
    res = np.zeros_like(array)
    for i in range(1,len(array)):
        res[i] = array[i-1] + res[i-1]
    return res