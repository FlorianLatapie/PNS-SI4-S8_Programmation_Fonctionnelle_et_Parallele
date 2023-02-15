#Example from https://pythonprogramming.net/loading-file-data-matplotlib-tutorial/
#Lancer par ex l'IDE Python (installé sous windows, par ex ide shell python 3.9)
#Ouvrir ce script
#Run Customized avec le fichier demandé par le script, ici par ex  pour plot2D.py,
#tempsthreadabel30000.data


from mpl_toolkits import mplot3d
import numpy as np
import matplotlib.pyplot as plt
import sys
print("plot des résultats  de perf du calcul des primes des entiers < 30000")
fig = plt.figure()
#ax = plt.gca(projection='3d')
if len(sys.argv)==1 :
	print("Usage: \t plotPrime2D.py fichierData, preferably data are sorted by th number and increasing size array")
	exit()	
nomfich=sys.argv[1]
x, y,z= np.loadtxt(nomfich, delimiter=' ', unpack=True)
print(x)
print(y)
print(z)
valprec=z[0]
i=0
x1=[]
y1=[]
for zi in z:
	val=zi
	if (val==valprec):
		x1.append(x[i])
		y1.append(y[i])
		valprec=val
		i=i+1
	else: 
		print(x1)	
		print(y1)
		#plt.scatter(x1,y1)
		lab=str(valprec)+" th"
		plt.plot(x1,y1, marker='o', linestyle='dashed',label=lab)
		#preparer sous tab suivants
		x1=[]
		x1.append(x[i])
		y1=[]
		y1.append(y[i])
		valprec=val	
		i=i+1
print(x1)
print(y1)
lab=str(valprec)+" th"
plt.plot(x1,y1, marker='o', linestyle='dashed',label=lab)
		
plt.xlabel('taille tab')
plt.ylabel('tps//(microsec)')
plt.title('Performances du calcul des primalités')
plt.legend()
plt.show()
