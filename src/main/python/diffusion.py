import os

import numpy as np
import sys


N = int(sys.argv[1])
dt = float(sys.argv[2])
lam = float(sys.argv[3])
x = np.linspace(int(sys.argv[4]),int(sys.argv[5]),N)
steps= int(sys.argv[6])


def save_line_to_csv(file_path, array):
  if os.path.exists(file_path):
    append_write = 'a+'
  else:
    append_write = 'w+'
  file = open(file_path,append_write)
  line = ""
  for element in array:
    line = line+str(element)+","
  file.writelines(line+"\n")
  file.close()


A = np.zeros((N,N))
y = np.zeros(N)
ynp1 = np.zeros(N)

dx = x[1]-x[0]
c = lam*dt/dx

for i in range(0,N):
  if(abs(x[i])<0.3):
   y[i] = 1.0

def delta(i,j):
  if(i==j):
    return 1
  else:
    return 0


for i in range(0,N):
  for j in range(0,N):
    if(lam<0):
      A[i,j] = (1-c)*delta(i,j)+c*delta(i,j-1)
    else:
      A[i,j] = (1+c)*delta(i,j)-c*delta(i,j+1)

def boundary_conditions(y):
  help1 = y[0]
  y[0] = y[-1]
  y[-1] = help1
  return y

invA = np.linalg.inv(A)


for n in range(0,steps):
  print(n)
  ynp1 = np.matmul(invA,y)
  y = ynp1.copy()
  y = boundary_conditions(y)
  save_line_to_csv("src/main/data/y.csv", y)

save_line_to_csv("src/main/data/x.csv", x)
exit(0)
