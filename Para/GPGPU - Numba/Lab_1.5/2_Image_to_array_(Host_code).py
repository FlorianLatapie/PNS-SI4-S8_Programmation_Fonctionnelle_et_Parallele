"""
Image to array (Host code)
An RGB image can be represented as a 3D array in memory. The first 2 dimensions correspond to the width and height of the image and the third is a array of 3 integers representing the RGB values. Numpy has methods for converting images to arrays. 

1. Use the array(...) method from numpy to transform your image to an array
2. Print the dimensions (shape) of this array. Compare it to the dimensions of the original image. What do you observe?

Numpy swaps the height and width of an image. This can be problematic when implementing an algorithm, as you have to swap the first 2 coordinates. To avoid this, you can use transpose(1,0,2) and ascontiguousarray() to swap the first 2 dimensions. Ascontiguousarray() is necessary for proper memory transfer to the device. If you don't use it, after transpose() data are poorly organized in memory and the transfer may fail. 

1. Combine these methods to swap the first 2 dimensions of your array. 
2. Check that you now have the same order than in the original image
3. What is the drawback of this method?
"""

import numpy as np
from PIL import Image

image = Image.open("pillow-large.jpg")

array = np.array(image)
print(image.size)
print(array.shape)

array = np.ascontiguousarray(array.transpose(1, 0, 2))

print()
print(image.size)
print(array.shape)
