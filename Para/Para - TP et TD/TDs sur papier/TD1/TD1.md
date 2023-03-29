# TD1, SI4 Parallel programming 2022-2023

## Exercice 1

In the course, we have shown 2 versions of the **parallel computation of the maximum of `n` values**.

```txt
pourchaque 1 < i < n en parallèle 
   m[i] = TRUE 
pourchaque 1 < i,j < n en parallèle 
   si (t[i] < t[j])
      m[i] = FALSE
pourchaque 1 < i < n en parallèle 
   si (m[i] == TRUE)
      max = t[i] 
```

1. Explain why the proposed `CRCW PRAM` algorithm that is using $n^2$ processors can indeed compare all needed values together in just one single phase.
   Remember that to find the maximum of a set of n values, each of them must at some point be compared to all the others.  
   Why in the sequential algorithm that you can easily write down, the time complexity ends up being in $O(n)$ (and not $O(n^2)$).

2. Explain why the proposed `CRCW PRAM` algorithm has to allow `CR` ?

3. Explain why the proposed `CRCW PRAM` algorithm has to allow `Arbitrary CW` ?

4. Sketch how to simulate a `CR` of this algorithm, on an `ER` `PRAM`.
   For the `CR` simulation, write down the complete algorithm, assuming the value to be copied to the `n` processors, is stored in an array of size `n`, at index `0`.  
   Assume that $n=2^m$, so, you can iterate in $O(m)$ parallel steps. Highlight why the simulation has only $O(log(n))$ parallel time complexity, given the number of data is n.

5. Sketch how to do the same for the Arbitrary `CW`, on an `EW` `PRAM`.

### Réponse exercice 1

1. La complexité de l'algorithme est de $O(1)$ car chaque boucle est parallélisée et donc exécutée en un temps constant
   Dans l'algorithme séquentiel, la complexité est en $O(n)$ parce qu'on a besoin de traverser tout le tableau avec un
   accumulateur stockant le maximum. À la fin, la valeur correspond au maximum du tableau.

   **Depuis le pdf de corrrection de la prof :**

   Explication de l'algorithme :
   pour chaque valeur du tableau en paralèle, on vient la comparer à toutes les autres, seules les valeurs ne sont pas plus petites ne sont pas notées false, il ne reste plus qu'à trouver la valeur true qui est par définition la plus grande.

   Une approche "brute force" en séquentiel peut être utilisée pour avoir un algo en $O(n)$ :

   ```txt
   max = -inf
   pour chaque valeur du tableau en séquentiel
      si (t[i] > max)
         max = t[i]
   ```

   À la fin max contient la valeur maximale du tableau

2. ```txt
   i = 3 j = 4 // 1 proc T[3] T[4]   
   i = 4 j = 6 // autre proc T[4] T[6]
   ```

   Dans le pire des cas une valeur T[k] va être lue simultanément par n-1 processeurs

3. Arbitrary CW $\rightarrow$ process lisent T[k], $1 \leq k \lt$ T[k] avec une autre valeur $\rightarrow$ T[1]  
   **Exemple :** 2 < 1 $\rightarrow$ T[k] < T[1] $\rightarrow$ FALSE  
   False dans m[k], en parallèle, on veut la meme valeur FALSE donc arbitrary  PRAM est suffisante en CW

   Ce que Quentin à noté :  
   L'algorithme proposé doit permettre CR car on doit lire la même valeur par plusieurs processeurs, dans le but d'exécuter une autre boucle en O(1) :

   ```txt
   T[i] < T[j] // dans la seconde boucle
   ```

   Pendant cette instruction nous avons n processeurs qui lisent la même valeur de la cellule du tableau

   **Depuis le pdf de corrrection de la prof :**

   On peux CW car soit on n'écrit rien soit on met false dans la case correspondante à la valeur du tableau, donc on peut écrire en parallèle.

4. Simulation `CR` de T[k] pour $O(n)$ processeurs  
   1 processeur lit seulment 1 T[k]  

$$
\begin{aligned}
A[0] &\rightarrow A[1] et A[2] \\
&\space\space\space\space \rightarrow \text{proc 1 duplique A[1] en A[3] et A[4]} \\
&\space\space\space\space \rightarrow \text{proc 2 duplique A[2] en A[5] et A[6]} \\
\end{aligned}
$$

   Copier la valeur de x dans A[0] vers A[0...7] sur une EREW PRAM

   ![diagramme question 4](question4.drawio.png)  
   **Algo**  
   $n = 2^m$, $A$ de taille $n$, $A[0]$ contient $x$ à copier dans chaque $A[i]$  

   ```py
   n = 2m 
   for i = 0 to (m-1):
      for each j = 0 to (2^i)-1 do in parallel:
         A[j + 2^i] = A[j]
   ```

   **Autre réponse pour la question 4**
   Chaque step qui necessitait une lecture concurrente sur la CR PRAM a été simulé par 1 alg qui "duplique" n fois chaque adresse mémoire à la ligne par n processeurs en un tableau de "n" cases, où chacun lit en exclusif avec ER.

   ![diagramme question 4 v2](question4-2.drawio.png)  
   $O(log_2 \space n)$

   Application de l'algorithme :

   ```py
   i = 0 -> j = 0 à 0
      A[1] = A[0]
   i = 1 -> j = 0 à (2^1)-1 = 0 à 1
      A[2] = A[0 + 2^1] = A[0]
      A[3] = A[1 + 2^1] = A[1]
   i = 2 -> j = 0 à (2^2)-1 = 0 à 3
      A[4] = A[0 + 2^2] = A[0]
      A[5] = A[1 + 2^2] = A[1]
      A[6] = A[2 + 2^2] = A[2]
      A[7] = A[3 + 2^2] = A[3]
   ```

   Pour 8 valeurs, on utilise au maximum 4 processeurs = n/2

$$
\begin{aligned}
Work &= 4log(2^m)O(1) \\
&= \frac{2^m}{2}mO(1) \\
2^{m-1} &= \frac{n}{2} \\
\end{aligned}
$$

   l'algorithme n'est pas optimal car le travail est égal à O(n*log(n)) comparé au meilleur algo séquentiel qui est O(n).

5. Plusieurs processeurs peuvent écrire une valeur unique `false` donc CW Arbitrary est suffisant pour le fonctionnement de l'algorithme.  
Besoin d'un algo de compactage.
Combien compte l'algo de compactage ? **Question type DS**  

## Exercice 2

![algorithme](image-001.png)

```py
# init 
for each proessor in # do
   if next[i] = NIL then 
      d[i] = 0 
   else 
      d[i] = 1
# main loop
while (\exists object i t.q next[i] != NIL) do
   for each proessor in # do
      if next[i] != NIL then
         d[i] = d[i] + d[next[i]]
         next[i] = next[next[i]]
```

1. What does the following algorithm applied to a chained linked list of elements compute?  
Start with this list once initialized  
![linked list](image-000.png)
2. What is its parallel time complexity on a PRAM (considering you can use the most powerful PRAM you need) ?  
   Hint : how
   many times is the condition of the while loop executed ?
3. Which PRAM variant is needed at least, not to increase the parallel time complexity ?  
   Hint: is it possible that 2 processes read data of the same list item at the same PRAM instruction ? (consider an
   instruction, eg, an addition with two operands as being one single instruction, i.e., do not decompose even more one
   such operation, like an addition, into its corresponding assembly code)

### Réponse exercice 2

1. L'algorithme calcule la position de chaque noeud par rapport à la fin de la liste
2. Chaque étape est $O(1)$  
   La complexité est $O(log(n))$  
   Le nombre de processeurs utilisés est $O(n)$  
   L'algorithme n'est pas "travail optimal" car le travail est $O(n \times log(n))$ comparé au meilleur algo séquentiel qui est $O(n)$.  
   On arrête l'algorithhme quand il ne reste plus de noeud à traiter dans la liste. À chaque itération on réduit la taille de la liste de 1. Donc à la fin on doirt réduit la taille de la liste de n-1 fois, ce qui correspond au nombre d'itérations de la boucle while.
3. Pour la lecture, on a besoin d'un ER si on est parfaitement cadencé au niveau des instructions. Pour l'écriture, on a aussi besoin d'un EW.  
   Enfin , le PRAM dont on a besoin est EREW.

## Exercice 3

In the algorithm provided for the parallel computation of the maximum (version 2), the course has shown a classical way
to derive a work optimal PRAM algorithm.  
Write down, using the pseudo PRAM language, the proposed work optimal algorithm.

### Réponse exercice 3

$$
n = 2 ^ m \space \text{m est une puissance de 2}
$$

$$
\begin{aligned}
n &= 2^4 &= 16 \\
m &= 4 &= 2^2 \\
\end{aligned}
$$

$$
\frac{n}{log(n)} = \frac{2^m}{m} = \text{nb de processerurs et chaque processeur a } log(n) = m \text{ données à traiter}
$$

```py
for each j from indice 2^m to (2^m+1)-1, j=j+m in parallel do
   // ça enrol (2^m)/m processeurs
   x = j/m
   variable privée max = A[j]
   for k = j+1 to (j+m-1) do # en séquentiel
      if A[k] > max:
         max = A[k]
   A[x] = max


// Algo max v2
for (k = log(2^m/m) - 1; k >= 0; k--):
    for each j from 2^k to 2^k+1 - 1 in parallel do
        A[j] = max(A[2j], A[2j+1])
```
