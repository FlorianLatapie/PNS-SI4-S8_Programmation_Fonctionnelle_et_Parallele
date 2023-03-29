"""
Local vs global ID
The goal of this exercise is to get a better handling of local and global ID. We will first start in 1D.
Write a kernel coordinates1D() which prints a thread's local ID
Call your kernel with a grid size of 1 and 8 threads. What are the coordinates printed?
Set a grid size of 2, what do you observe?
Using the numba.grid() function, have each thread print its local and global ID.
Write some code to compute the global ID of the thread (hint: use the block size and block id)
In 2D coordinates the computation can be a bit more complicated so we won't do it "by hand".
Write a kernel coordinates2D() which prints a thread's local IDs
Call your kernel with a grid size of 1 and (4,2,1) threads. What are the coordinates printed?
Set a grid size of (2,2,1), what do you observe?
Using the numba.grid() function, have each thread print its local and global ID.
"""

from numba import cuda
import numba as nb
import sys

@cuda.jit
def coordinates1D():
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)
    computed_global_id = cuda.blockIdx.x * cuda.blockDim.x + local_id
    print("1d hello from thread ", global_id, "\t", computed_global_id, "\t", local_id)

@cuda.jit
def coordinates2D():
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)
    print("2d hello from thread ", local_id, "\t", global_id)

if __name__ == "__main__":
    coordinates1D[1,8]()
    coordinates2D[1,(4,2,1)]()