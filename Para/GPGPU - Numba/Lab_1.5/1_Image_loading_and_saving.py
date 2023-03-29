"""
Image loading and saving
In order to process images, we need to load them from disk, perform some computation, and save the result. To do so, we will use the Python Pillow (PIL) module.

Use the open(...) method to load an image (jpg) from disk
Use save(...) to save it as another name. Congratulation, you have a fancy way to copy an image. 
Print the width and height of the image which you can get reading the attribute size of your loaded image. 
"""

from numba import cuda
import numba as nb
import numpy as np
import sys
import math

import numpy as np
from numba import cuda

import PIL
from PIL import Image

# Load an image from disk
img = Image.open('pillow-large.jpg')

# Save it as another name
img.save('pillow-large-copy.jpg')

# Print the width and height of the image
print(img.size)