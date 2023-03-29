"""
Image processing on GPU
A Black&White image can be represented as a 2D array where each element is a value between 0 and 254. So turning an RGB image to a B&W one boils down to converting the 3 RGB values to a single one. The formula to do so is the following
    B&W value =   (0.3 * R) + (0.59 * G) + (0.11 * B)

We will  write the CUDA version of this algorithm. In this version, a thread will be responsible for computing the new value of a pixel. As seen during lecture, we need to compute the grid size, set-up the memory transfer, and write the kernel. We will assume 2D blocks and Grids. 

Given a thread block of size (16,16,1) and an image, compute the grid size needed to process it. 
Write the code to transfer the image-array to the GPU. 
Allocate the array for the B&W image directly on the GPU.
Write the kernel and finish writing the full conversion. 
"""
from numba import cuda
import numba as nb
import numpy as np
import sys
import math

import numpy as np
from numba import cuda

from PIL import Image

def to_gray(rgb):
    R, G, B = rgb
    return (0.3 * R) + (0.59 * G) + (0.11 * B)

src_img = "pillow-large.jpg"
dst_img = src_img[:-4] + "-bw.jpg"
 
img = Image.open(src_img)
src = np.array(img)

height, width, _ = src.shape

dst = np.zeros((height, width), dtype=np.int8)
for i in range(height):
    for j in range(width):
        dst[i, j] = to_gray(src[i, j])

Image.fromarray(dst, mode="L").save(dst_img)
