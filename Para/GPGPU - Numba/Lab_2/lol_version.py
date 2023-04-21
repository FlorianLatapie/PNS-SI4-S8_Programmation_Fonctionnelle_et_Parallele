import math
from numba import cuda
import numba as nb
import numpy as np
import sys

THREAD_BLOCK=256


@cuda.jit
def scanKernel(arrayGPU, n, m):
    if n != 2**m:
        size = 2**m
    else:
        size = n
    cuda.syncthreads()

    sharedArray = cuda.shared.array(THREAD_BLOCK, dtype=nb.int32)

    # Copy the values of the array to the shared array.
    sharedArray[cuda.threadIdx.x] = arrayGPU[cuda.threadIdx.x]
    
    # Wait for all the threads to finish copying.
    cuda.syncthreads()

    monteKernel(sharedArray, size, m)

    if cuda.grid(1) == 0:
        sharedArray[size - 1] = 0
    cuda.syncthreads()

    descenteKernel(sharedArray, size, m)

    # Copy the values of the shared array to the array.
    arrayGPU[cuda.threadIdx.x] = sharedArray[cuda.threadIdx.x]

    cuda.syncthreads()



@cuda.jit
def monteKernel(arrayGPU, n, m):
    x = cuda.grid(1) 
    for d in range(0, m):
        k = n // 2**(d + 1)
        if x < k:
            arrayGPU[x * 2**(d + 1) + 2**(d + 1) - 1] += arrayGPU[x * 2**(d + 1) + 2**d - 1]
        cuda.syncthreads()
    

@cuda.jit
def descenteKernel(arrayGPU, n, m):
    x = cuda.grid(1)
    for d in range(m - 1, -1, -1):
        k = n // 2**(d + 1)
        if x < k:
            t = arrayGPU[x * 2**(d + 1) + 2**d - 1]
            arrayGPU[x * 2**(d + 1) + 2**d - 1] = arrayGPU[x * 2**(d + 1) + 2**(d + 1) - 1]
            arrayGPU[x * 2**(d + 1) + 2**(d + 1) - 1] += t
        cuda.syncthreads()


def scanGPU(array, tb, bg):
    tb = 16
    bg = bg
    n = array.shape[0]
    m = math.ceil(math.log2(n))
    arrayGPU = cuda.to_device(array)
    scanKernel[bg, tb](arrayGPU, n, m)
    array = arrayGPU.copy_to_host()
    return array


def monte(array, n, m):
    for d in range(0, m):
        for k in range(0, n - 1, 2**(d + 1)):
            array[k + 2**(d + 1) - 1] += array[k + 2**d - 1]


def descente(array, n, m):
    array[n - 1] = 0
    for d in range(m - 1, -1, -1):
        for k in range(0, n, 2**(d + 1)):
            t = array[k + 2**d - 1]
            array[k + 2**d - 1] = array[k + 2**(d + 1) - 1]
            array[k + 2**(d + 1) - 1] += t


def scanCPU(array):
    n = array.shape[0]
    m = math.ceil(math.log2(n))
    if n != 2**m:
        array = np.append(array, [[0] * (2**m - n)])
        size = array.shape[0]
    else:
        size = n
    monte(array, size, m)
    descente(array, size, m)
    return array[:n]


def run():
    size = 256
    arrayGPU = np.random.randint(1, 10, size)
    arrayCPU = arrayGPU.copy()
    print("--------------------")
    print("Array initial")
    print(arrayGPU)
    print("--------------------")
    print("Array CPU")
    arrayCPU = scanCPU(arrayCPU)
    print(arrayCPU)
    print(arrayCPU.shape[0])
    print("--------------------")
    print("Array GPU")
    arrayGPU = scanGPU(arrayGPU, size, 1)  
    print(arrayGPU)
    print(arrayGPU.shape[0])
    print("--------------------")
    print("Array CPU == Array GPU")
    print(arrayGPU == arrayCPU)
    print("--------------------")
    print(math.ceil(math.log2(16)))


if __name__ == '__main__':
    run()