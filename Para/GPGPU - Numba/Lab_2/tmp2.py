import numpy as np
from numba import cuda


@cuda.jit
def scanKernel(array, n):
    """
    Function called by the host to perform the scan calculations.
    :param array: The given array to apply the scan function.
    :param n: The size of the array.
    :return: The array after applying the scan function.
    """
    tid = cuda.threadIdx.x
    offset = 1

    # Up-sweep phase
    while offset < n:
        idx = 2 * offset * (tid + 1) - 1
        if idx < n:
            array[idx] += array[idx - offset]
        offset *= 2
        cuda.syncthreads()

    # Down-sweep phase
    if tid == 0:
        array[n - 1] = 0
    offset //= 2
    while offset > 0:
        idx = 2 * offset * (tid + 1) - 1
        if idx < n:
            tmp = array[idx - offset]
            array[idx - offset] = array[idx]
            array[idx] += tmp
        offset //= 2
        cuda.syncthreads()


def scanGPU(array):
    """
    Copies the array to the device and prepares the call to the kernel that you will develop.
    :param array: The array to apply the scan function.
    :return array: The array after applying the scan function.
    """
    n = len(array)

    # Copy the array from the host to the device
    d_array = cuda.to_device(array)

    # Compute block and grid sizes
    threads_per_block = 1024
    blocks_per_grid = (n + threads_per_block - 1) // threads_per_block

    # Call kernel function
    scanKernel[blocks_per_grid, threads_per_block](d_array, n)

    # Copy result back to host
    h_array = d_array.copy_to_host()

    return h_array


a = np.array([2, 3, 4, 6], dtype=np.int32)
print(scanGPU(a))
