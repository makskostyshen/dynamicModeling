import sympy as sp
import numpy as np
import scipy
import matplotlib.pyplot as plt
import sys
from pathlib import Path
import ast
from mpl_toolkits.mplot3d import Axes3D


x, t, xs, ts = sp.symbols('x t xs ts')

def save_files(args, plot, net):
    # Create the target directory if it doesn't exist
    print(args[7])
    print(args[8])

    # Open the text file in write mode


    output_file = Path(args[7])
    output_file.parent.mkdir(exist_ok=True, parents=True)
    output_file.write_text("result")


    plt.savefig(args[8])


def y1(x, t):
    return sp.cos(x).evalf() + sp.sin(t).evalf()

def y2(x, t):
    return 1 + x**2 + 1 / 4 * t

state_function_dictionary = {
    "1" : y1,
    "2" : y2}




if len(sys.argv) < 9:
    print("Usage: python my_script.py <folder_name>")
    sys.exit(1)

arg1 = sys.argv[1]
arg2 = state_function_dictionary[sys.argv[2]]
arg3 = sys.argv[3]
arg4 = ast.literal_eval(sys.argv[4])
arg5 = float(sys.argv[5])
arg6 = ast.literal_eval(sys.argv[6].replace("x","1").replace("t","0"))
arg7 = ast.literal_eval(sys.argv[7].replace("x","1").replace("t","0"))
arg8 = sys.argv[8]
arg9 = sys.argv[9]
args = [arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9]





def y(x, t):
    return args[1](x, t)


def u():
    return sp.diff(y(x, t), t) - sp.diff(y(x, t), x, 2)


def G(x, t, xs, ts):
    return sp.Piecewise((1 / sp.sqrt(4 * sp.pi * abs(t-ts)) * sp.exp(-(abs(x-xs)**2) / (4 * abs(t-ts))), t >= 0), (0, True))


x1 = args[3][0]
x2 = args[3][1]
T = args[4]
time0 = []
x0 = []

for el in args[6]:
   time0.append(el[0])
   x0.append(el[1])

timeg = []
xg = []

for el in args[5]:
   timeg.append(el[0])
   xg.append(el[1])


k = len(x0)

Y0 = []

for i in range(len(x0)):
    Y0.append(y(x0[i], 0))
Y0 = np.array(Y0)

Yg = []

for i in range(len(xg)):
    Yg.append(y(xg[i], timeg[i]))

Yg = np.array(Yg)

Y = np.concatenate((Y0, Yg), axis=0)


A11 = []
A12 = []
A21 = []
A22 = []

for l in range(len(x0)):
    A11.append([G(x0[l], 0, xs, ts)])

for l in range(len(xg)):
    A12.append([G(xg[l], 0, xs, ts)])

for l in range(len(x0)):
    A21.append([G(x0[l], time0[l], xs, ts)])

for l in range(len(xg)):
    A22.append([G(xg[l], timeg[l], xs, ts)])


A11 = np.array(A11)
A12 = np.array(A12)
A21 = np.array(A21)
A22 = np.array(A22)
n = len(A11)

A = []

for i in range(n):
    lst = [A11[i][0], A12[i][0]]
    A.append(lst)

for i in range(n):
    lst = [A21[i][0], A22[i][0]]
    A.append(lst)

A = np.array(A)


P111 = np.matmul(A11, A11.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P111[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1, x2, -10, 0.01)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10**6
        elif np.isinf(lst) and lst < 0:
            lst = -10**6
        elif np.isnan(lst):
            lst = 0
        P111[i][j] = lst



P112 = np.matmul(A12, A12.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P112[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1 - 10, x1, 0.01, T)[0] + scipy.integrate.dblquad(f_lam, x2, x2 + 10, 0.01, T)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10 ** 6
        elif np.isinf(lst) and lst < 0:
            lst = -10 ** 6
        elif np.isnan(lst):
            lst = 0
        P112[i][j] = lst


P11 = P111 + P112



P121 = np.matmul(A11, A21.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P121[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1, x2, -10, 0.01)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10 ** 6
        elif np.isinf(lst) and lst < 0:
            lst = -10 ** 6
        elif np.isnan(lst):
            lst = 0
        P121[i][j] = lst



P122 = np.matmul(A12, A22.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P122[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1 - 10, x1, 0.01, T)[0] + scipy.integrate.dblquad(f_lam, x2, x2 + 10, 0.01, T)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10 ** 6
        elif np.isinf(lst) and lst < 0:
            lst = -10 ** 6
        elif np.isnan(lst):
            lst = 0
        P122[i][j] = lst



P12 = P121 + P122


P211 = np.matmul(A21, A11.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P211[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1, x2, -10, 0.01)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10**6
        elif np.isinf(lst) and lst < 0:
            lst = -10**6
        elif np.isnan(lst):
            lst = 0
        P211[i][j] = lst



P212 = np.matmul(A22, A12.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P212[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1 - 10, x1, 0.01, T)[0] + scipy.integrate.dblquad(f_lam, x2, x2 + 10, 0.01, T)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10 ** 6
        elif np.isinf(lst) and lst < 0:
            lst = -10 ** 6
        elif np.isnan(lst):
            lst = 0
        P212[i][j] = lst



P21 = P211 + P212


P221 = np.matmul(A21, A21.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P221[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1, x2, -10, 0.01)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10**6
        elif np.isinf(lst) and lst < 0:
            lst = -10**6
        elif np.isnan(lst):
            lst = 0
        P221[i][j] = lst



P222 = np.matmul(A22, A22.T)


for i in range(n):
    for j in range(n):
        f_lam = sp.lambdify([ts, xs], P222[i][j])
        lst = scipy.integrate.dblquad(f_lam, x1 - 10, x1, 0.01, T)[0] + scipy.integrate.dblquad(f_lam, x2, x2 + 10, 0.01, T)[0]
        if np.isinf(lst) and lst > 0:
            lst = 10 ** 6
        elif np.isinf(lst) and lst < 0:
            lst = -10 ** 6
        elif np.isnan(lst):
            lst = 0
        P222[i][j] = lst



P22 = P221 + P222


P = []

for i in range(n):
    lst = np.concatenate((P11[i], P12[i]), axis=0)
    P.append(lst)

for i in range(n):
    lst = np.concatenate((P21[i], P22[i]), axis=0)
    P.append(lst)


P = sp.Matrix(P)




inverse = np.array(P.inv())



A0 = []

for i in range(n):
    A0.append([A11[i][0]])

for i in range(n):
    A0.append([A21[i][0]])


A0 = np.array(A0).T



Ag = []

for i in range(n):
    Ag.append([A12[i][0]])

for i in range(n):
    Ag.append([A22[i][0]])


Ag = np.array(Ag).T



u0 = np.matmul(np.matmul(A0, inverse), Y)[0]





ug = np.matmul(np.matmul(Ag, inverse), Y)[0]



x_start = 0
t_start = 0
h = 1
x_arrays = np.array([x_start + i*h for i in range(0, 3)])
t_arrays = np.array([t_start + i*h for i in range(0, 3)])

y_res = []
X1, T1 = np.meshgrid(x_arrays, t_arrays)


for i in range(len(x_arrays)):
    for j in range(len(t_arrays)):
        f_lam = sp.lambdify([ts, xs], u0*G(x, t, xs, ts).subs(t >= 0, True).subs({x: x_arrays[i], t: t_arrays[j]}))
        y0 = scipy.integrate.dblquad(f_lam, x1, x2, -10, -0.01)[0]

        f_lam = sp.lambdify([ts, xs], ug*G(x, t, xs, ts).subs(t >= 0, True).subs({x: x_arrays[i], t: t_arrays[j]}))
        yg = scipy.integrate.dblquad(f_lam, x1 - 10, x1, 0.01, T)[0] + scipy.integrate.dblquad(f_lam, x2, x2 + 10, 0.01, T)[0]


        #f_lam = sp.lambdify([ts, xs], u().subs({"x": xs, "t": ts})*G(x, t, xs, ts).subs(t >= 0, True).subs({x: x_arrays[i], t: t_arrays[j]}))
        #yinf = scipy.integrate.dblquad(f_lam, x1, x2, 0.01, T)[0]
        #result = y0 + yg + yinf
        result = y0 + yg
        y_res.append([x_arrays[i], t_arrays[j], result])

y_res = np.array(y_res)



n = len(y_res)


xline = []
tline = []
yline = []

for i in range(n):
    xline.append(y_res[i][0])

for i in range(n):
    tline.append(y_res[i][1])

for i in range(n):
    yline.append(y_res[i][2])

print(xline)
print(tline)
print(yline)

xline = np.array(xline)
tline = np.array(tline)
yline = np.array(yline)

real_res = []
for i in range(len(xline)):
    real_res.append(y(xline[i], tline[i]))

real_res = np.array(real_res)


fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.plot_trisurf(xline, tline, yline, linewidth=0, antialiased=False, label = "Наша модель")

ax.scatter(xline, tline, real_res, c='red', marker='o', label = "Реальні значення")

ax.set_xlabel('x')
ax.set_ylabel('t')
ax.set_zlabel('y')
ax.legend()
save_files(args, ax, "result")