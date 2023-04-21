import getopt, sys
import numpy

def usage():
  print("Usage: " +sys.argv[0]+" <power of 2>")

def randomArray(power_of_two, min=-100, max=100):
   return numpy.random.randint(min,max,2**power_of_two, dtype=numpy.int32)


if __name__ =='__main__':
    if len(sys.argv) != 2:
        usage()
        sys.exit(2)
    else:
       arr = randomArray(int(sys.argv[1]))
       print(numpy.array2string(arr, separator=",",threshold=arr.shape[0]).strip('[]').replace('\n', '').replace(' ',''))
