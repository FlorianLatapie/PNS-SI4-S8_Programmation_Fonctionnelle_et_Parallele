"""
First kernel
Let's start by writing a very simple kernel without any data transfer in a 1D topology.
1. Write a kernel firstKernel() which simply prints "hello"
2. Call your kernel with a grid size of 1 and 4 threads. How many times is hello printed?
"""

from numba import cuda
import numba as nb
import sys

@cuda.jit
def firstKernel():
    print("hello")

if __name__ == '__main__':
    firstKernel[2, 8]()