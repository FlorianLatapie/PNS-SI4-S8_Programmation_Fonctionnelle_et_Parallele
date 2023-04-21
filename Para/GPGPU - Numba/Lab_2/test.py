import os
import numpy as np

from CPU_version import scanCPU
from GPU_version import scanGPU
from dumb_CPU_version import scanDumb

from lol_version import scanGPU as lol_scanGPU

for i in range(100):
    #my_input = np.array([2, 3, 4, 6], dtype=np.int32)
    my_input = np.random.randint(low=0, high=100, size=16, dtype=np.int32)
    my_input_copy = my_input.copy()
    my_input_for_expected = my_input.copy()
    my_input_for_actual = my_input.copy()
    
    expected_output = scanDumb(my_input_for_expected)
    
    #actual_output = scanCPU(my_input_for_actual)
    actual_output = scanGPU(my_input_for_actual,len(my_input_for_actual),1)
    #actual_output = lol_scanGPU(my_input_for_actual,256,1)
    
    if not np.array_equal(expected_output, actual_output):
        print("\033[0;31mTest failed\033[0m")
        print("for input:       " + str(my_input_copy))
        print("expected output: " + str(expected_output))
        print("output:          " + str(actual_output))
        exit(1)
    else:
        print("\033[0;32mok",i,"\033[0m")


print("\033[0;32mTest passed\033[0m")

# using os, do command "echo $PWD"

yes_no = input("commit ?")
if yes_no == "y":
    os.system("git pull;git add . && git commit -m 'auto-commit from boole' && git push")
else:
    print("not commited")