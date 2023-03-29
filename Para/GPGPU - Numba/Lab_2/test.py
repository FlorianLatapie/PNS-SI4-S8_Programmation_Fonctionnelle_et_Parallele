import numpy as np

from CPU_version import scanCPU
from GPU_version import scanGPU

# random np array values for testing

for i in range(0, 100):
    my_input = np.random.randint(low=0, high=100, size=8, dtype=np.int32)
    my_input_copy = my_input.copy()
    
    expected_output = scanCPU(my_input)
    
    actual_output = scanGPU(my_input)
    
    if not np.array_equal(expected_output, actual_output):
        print("\033[0;31mTest failed\033[0m")
        print("for input:       " + str(my_input_copy))
        print("expected output: " + str(expected_output))
        print("output:          " + str(actual_output))
        exit(1)

print("\033[0;32mTest passed\033[0m")
