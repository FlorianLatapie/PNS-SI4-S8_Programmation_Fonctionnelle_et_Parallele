"""
Larger arrays
Modify your code so it can now work on an arbitrary large array. Use 32 threads in a block and compute the required grid size to have a correct execution.
"""

from numba import cuda
import numba as nb
import numpy as np
import sys
import math

import numpy as np
from numba import cuda

import numpy as np
from numba import cuda

# Instantiate an array of size n on the host and fill it with zeroes
n = 1000000
host_array = np.zeros(n)

print("Host array before kernel: ", host_array)

# Allocate memory on the device and transfer the host array to it
device_array = cuda.to_device(host_array)

# Define the number of threads per block
threads_per_block = 32

# Compute the number of blocks needed to cover the entire array
blocks_per_grid = math.ceil(n / threads_per_block)

# Define a kernel to write into the device array
@cuda.jit
def kernel(device_array):
    # Get the thread index
    idx = cuda.grid(1)
    # Write into the device array
    if idx < len(device_array):
        device_array[idx] = idx

# Launch the kernel with the computed grid size and 32 threads per block
kernel[blocks_per_grid, threads_per_block](device_array)

# Transfer the device array back to the host
cuda.synchronize()
host_array = device_array.copy_to_host()

print("Host array after kernel: ", host_array)
