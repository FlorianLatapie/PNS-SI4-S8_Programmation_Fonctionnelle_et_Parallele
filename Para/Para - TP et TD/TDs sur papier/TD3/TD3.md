# TD3, SI4 Parallel programming 2022-2023

## Exercice 1

Application of the Prefix, even Prefix Sum.

Consider this array of integers, where 0 values are non-significant, and ideally, should be suppressed.

`[7,0,0,9,0,1,0,0,0,3]`

Compute the new position of each non nul element, using the prefix sum algorithm. Show on this example how it works.

### Réponse exercice 1

On commence par remplacer les 0 par des 1, puis on applique la somme prefixe. On obtient donc le tableau suivant :

on prend un tableau intermédiaire "Flags" qui contient des 1 si la valeur n'est pas 0 :

```txt
Original  = [7,0,0,9,0,1,0,0,0,3]
Flags     = [1,0,0,1,0,1,0,0,0,1]
SumPrefix = [1,1,1,2,2,3,3,3,3,4]
```

algo :

```py 
for all i from 1 to n do in parallel
    if Original[i] != 0 then
        Flags[i] = 1
    else
        Flags[i] = 0
    # time = O(1) EREW

SumPrefix = SumPrefixFunction(Flags) # time = O(log(n)) EREW

for all i from 1 to n do in parallel
    if (A[i] != 0) then
        A[SumPrefix[i]] = A[i] # time = O(1) EREW
```

exo type exam 

## Exercice 2

Yet another application of Prefix parallel operation. Assume we have a linked tree, where each node holds three pointers:  
father[i], left[i], right[i].

By starting from the root of the tree, if one follows these pointers, it is possible to traverse the tree and visit each node/leaf, applying what is known as the Euler tour. The traversal will be a prefix visit, i.e, a depth-first traversal: once at each node, first visit the left children, and once coming back at that node, follow the pointer to visit the right children, and so on.

The problem is to compute the distance of each element of the tree, from the root, or the **distance to the root**. By following a depth first traversal, one can buil a list, this list length will be equal 3*total number of elements in the tree. If you associate to each element of this list some integer values, like -1, 0, or 1, and you apply a prefix sum, you should solve the problem easily. Of course, for each of 3n elements of the list, you can consider you associate a parallel processor.

Depict the algorithm you propose. Evaluate its complexity
