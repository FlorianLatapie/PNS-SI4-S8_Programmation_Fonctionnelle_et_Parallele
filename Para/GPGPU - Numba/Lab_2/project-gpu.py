import numpy as np
from numba import cuda
import numba as nb
import math

THREAD_PER_BLOCK = 1024

def scanGPU(array):
    len_array = len(array)
    log2_len_array = int(math.ceil(math.log2(THREAD_PER_BLOCK)))

    blocks_per_grid = math.ceil(len_array / THREAD_PER_BLOCK)

    sum_array = np.zeros(blocks_per_grid, dtype=np.int32)
    test_array = np.zeros(blocks_per_grid, dtype=np.int32)
    
    d_array = cuda.to_device(array)
    d_sum_array = cuda.to_device(test_array)

    scanKernel[blocks_per_grid, THREAD_PER_BLOCK](d_array, d_sum_array, len_array, log2_len_array)

    cuda.synchronize()

    array = d_array.copy_to_host()
    test_array = d_sum_array.copy_to_host()

    if blocks_per_grid > 1:
        sum_array = scanGPU(test_array, THREAD_PER_BLOCK)
        for i in range(0, blocks_per_grid):
            array[i * THREAD_PER_BLOCK: min(len_array, (i+1)*THREAD_PER_BLOCK)] += sum_array[i]
        return array
    else:
        return array

@cuda.jit
def scanKernel(d_array, sum_array, len_array, log2_len_array):
    # Up-sweep phase
    cuda.syncthreads()
    thread_id = cuda.threadIdx.x
    global_id = cuda.grid(1)


    # using a shared array
    s_array = cuda.shared.array(THREAD_PER_BLOCK, dtype=nb.int32)
    if global_id < len_array:
        s_array[thread_id] = d_array[global_id]
    cuda.syncthreads()


    for d in range(log2_len_array):
        k = THREAD_PER_BLOCK // 2 ** (d + 1)  # simulating the second for loop but with threads instead incrementing the index
        if thread_id < k:
            s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
        cuda.syncthreads()
    cuda.syncthreads()


    if thread_id == 0:
        sum_array[cuda.blockIdx.x] = s_array[THREAD_PER_BLOCK - 1]
        s_array[THREAD_PER_BLOCK - 1] = 0

    cuda.syncthreads()

    # Down-sweep phase
    for d in range(log2_len_array - 1, -1, -1):
        k = THREAD_PER_BLOCK // 2 ** (d + 1)
        if thread_id < k:
            t = s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1]
            s_array[thread_id * 2 ** (d + 1) + 2 ** d - 1] = s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1]
            s_array[thread_id * 2 ** (d + 1) + 2 ** (d + 1) - 1] += t
        cuda.syncthreads()
    cuda.syncthreads()

    if thread_id < len_array:
        d_array[global_id] = s_array[thread_id]
    cuda.syncthreads()

if __name__ == "__main__":
    import sys
    import argparse

    parser = argparse.ArgumentParser()
    parser.add_argument("inputFile", help="single-line text file containing the list of values, separated by commas")
    parser.add_argument("--tb", help="optional size of a thread block", type=int)
    parser.add_argument("--independent", help="perform independent scans on sub-arrays", action="store_true")
    parser.add_argument("--inclusive", help="perform an inclusive scan", action="store_true")
    args = parser.parse_args()

    with open(args.inputFile) as f:
        array = np.array([int(x) for x in f.readline().split(',')])

    if args.tb:
        THREAD_PER_BLOCK = args.tb
    else:
        THREAD_PER_BLOCK = 1024

    if args.independent:
        blocks_per_grid = int(np.ceil(len(array) / THREAD_PER_BLOCK))
    else:
        blocks_per_grid = 1

    if args.inclusive:
        array = np.insert(array, 0)

    res = scanGPU(array)
    print(','.join(map(str, res)))
