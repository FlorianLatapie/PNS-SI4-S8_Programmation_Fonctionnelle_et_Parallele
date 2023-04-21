"""
CPU version
Write a function scanCPU(array) that takes a numpy array of int32 values of size n=2^m as a parameter and performs the up-sweep and down-sweep phases to compute the exclusive prefix. Make sure to verify each step of the calculation by printing the array.
"""
"""
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
"""
"""
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
"""
import numpy as np
from numba import cuda


def scanGPU(array, blocks_per_grid, threads_per_block):
    len_array = len(array)
    log2_len_array = int(np.log2(len_array))

    array = cuda.to_device(array)

    scanKernel[threads_per_block, blocks_per_grid](array, len_array, log2_len_array)

    return array.copy_to_host()


@cuda.jit
def scanKernel(array, len_array, log2_len_array):
    # Up-sweep phase
    thread_id = cuda.grid(1)

    for d in range(log2_len_array):
        k = len_array // 2 ** (d + 1)  # simulating the second for loop but with threads instead incrementing the index
        if thread_id < k:
            array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
        cuda.syncthreads()

    if thread_id == 0:
        array[len_array - 1] = 0

    # Down-sweep phase
    for d in range(log2_len_array - 1, -1, -1):
        k = len_array // 2 ** (d + 1)
        if thread_id < k:
            t = array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
            array[thread_id * 2 ** (d + 1) + 2 ** d - 1] = array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1]
            array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += t
        cuda.syncthreads()
"""
"""
Shared-Memory single thread block

By default, a kernel uses global memory which is the slowest. We will modify our kernel to use shared memory. The size is limited but sufficient to load the array processed by our thread block.

Declare a shared array of size equal to the number of threads in the block and containing int32 dtype values at the beginning of your kernel.
After the declaration, add the code so that each thread copies an element from global memory to the shared array.
Modify your kernel to work on shared memory.
At the end of your kernel, copy the information from shared memory to global memory and verify that everything works fine.
"""

import numpy as np
from numba import cuda
import numba as nb


def scanGPU(array, blocks_per_grid, threads_per_block):
    len_array = len(array)
    log2_len_array = int(np.ceil(np.log2(len_array)))
    full_len = 2 ** log2_len_array
    array = np.lib.pad(array, (0, full_len - len_array), 'maximum')

    array = cuda.to_device(array)

    scanKernel[threads_per_block, blocks_per_grid](array, full_len, log2_len_array)

    return array.copy_to_host()[0:len_array]


@cuda.jit
def scanKernel(array, len_array, log2_len_array):
    # Up-sweep phase
    cuda.syncthreads()
    thread_id = cuda.threadIdx.x

    s_array = cuda.shared.array(shape=256, dtype=nb.int32)
    s_array[thread_id] = array[thread_id]
    cuda.syncthreads()


    for d in range(log2_len_array):
        k = len_array // 2 ** (d + 1)  # simulating the second for loop but with threads instead incrementing the index
        if thread_id < k:
            s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
        cuda.syncthreads()
    cuda.syncthreads()


    if thread_id == 0:
        s_array[len_array - 1] = 0

    cuda.syncthreads()

    # Down-sweep phase
    for d in range(log2_len_array - 1, -1, -1):
        k = len_array // 2 ** (d + 1)
        if thread_id < k:
            t = s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
            s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1] = s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1]
            s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += t
        cuda.syncthreads()
    cuda.syncthreads()

    array[thread_id] = s_array[thread_id]
    cuda.syncthreads()



