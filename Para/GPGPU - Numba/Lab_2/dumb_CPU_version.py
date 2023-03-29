def scanGPU(array):
    res = np.zeros_like(array)
    for i in range(len(array)):
        res[i] = sum(array[:i+1])