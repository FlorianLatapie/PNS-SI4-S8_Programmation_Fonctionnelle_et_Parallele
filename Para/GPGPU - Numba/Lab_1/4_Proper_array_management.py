"""
Memory transfer
In this exercise you will instantiate an array on the host, send it to the device. A kernel will write in the array and finally the host will get the data back.
Instantiate an array of size 32 on the host and fill it with 0
Write the code to send the array to the device
Write a kernel where each thread write its local ID in the corresponding array cell. For example, thread with local ID 4 will do array[4]=4
Write the code to copy back the array after the execution of the kernel and print its content
Call your kernel with a grid size of 1 and and 32 threads. Is it working?
"""

from numba import cuda
import numba as nb
import numpy as np
import sys
import math

import numpy as np
from numba import cuda

# Instantiate an array of size 32 on the host and fill it with zeroes
nb = 32
host_array = np.zeros(30)

print("Host array before kernel: ", host_array)

# Allocate memory on the device and transfer the host array to it
device_array = cuda.to_device(host_array)

# Define a kernel to write into the device array
@cuda.jit
def kernel(device_array):
    # Get the thread index
    idx = cuda.grid(1)
    # Write into the device array
    if (idx < len(device_array)):
        device_array[idx] = idx

# Launch the kernel with one block and 32 threads
kernel[1, nb](device_array)

# Transfer the device array back to the host
cuda.synchronize()
host_array = device_array.copy_to_host()

print("Host array after kernel: ", host_array)

    