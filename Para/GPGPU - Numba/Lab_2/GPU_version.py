"""
CPU version
Write a function scanCPU(array) that takes a numpy array of int32 values of size n=2^m as a parameter and performs the up-sweep and down-sweep phases to compute the exclusive prefix. Make sure to verify each step of the calculation by printing the array.

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

Single thread block GPU version
Firstly, we will consider an array that can be processed by a single thread block. Therefore, its size should be at most 1024 elements.

The GPU version is obtained by starting from the CPU version written previously and identifying which parts can be executed in parallel and therefore by multiple threads. 

In the up-sweep and down-sweep phases, which parts can be executed in parallel?
Write a host function scanGPU(array) that copies the array to the device and prepares the call to the kernel that you will develop.
To parallelize the inner loop, we need to replace the loop index with the id of a thread.

Write a kernel scanKernel(array, n) that takes the array and its size as parameters and performs the calculation.
Test it with 4 elements and 4 threads and verify that everything works fine.
Test it with more than 16 elements and 16 threads and observe that the result is sometimes incorrect.
Find an explanation and propose a solution.
"""

from numba import cuda
import numba as nb
import numpy as np
import sys
import math

import numpy as np
from numba import cuda


def scanGPU(array, blocks_per_grid, threads_per_block):
    len_array = len(array)
    log2_len_array = int(np.log2(len_array))

    array = cuda.to_device(array)
    
    scanKernel[threads_per_block, blocks_per_grid](array, len_array, log2_len_array)
    
    return array.copy_to_host()
    
@cuda.jit
def scanKernel(array, n, m):
    # Up-sweep phase
    thread_id = cuda.grid(1)
    x = thread_id

    for d in range(0, m):
        k = n // 2**(d + 1) # simulating the second for loop but with threads instead incrementing the index
        if x < k:
            array[x * 2**(d + 1) + 2**(d + 1) - 1] += array[x * 2**(d + 1) + 2**d - 1]
        cuda.syncthreads()

    if thread_id == 0:
        array[n - 1] = 0

    # Down-sweep phase
    for d in range(m - 1, -1, -1):
        k = n // 2**(d + 1)
        if x < k:
            t = array[x * 2**(d + 1) + 2**d - 1]
            array[x * 2**(d + 1) + 2**d - 1] = array[x * 2**(d + 1) + 2**(d + 1) - 1]
            array[x * 2**(d + 1) + 2**(d + 1) - 1] += t
        cuda.syncthreads()
