import os
import datetime
import sys
import time
import shutil
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from pathlib import Path

# Check if at least one argument (folder name) is provided
if len(sys.argv) < 9:
    print("Usage: python my_script.py <folder_name>")
    sys.exit(1)

arg1 = sys.argv[1]
arg2 = sys.argv[2]
arg3 = sys.argv[3]
arg4 = sys.argv[4]
arg5 = sys.argv[5]
arg6 = sys.argv[6]
arg7 = sys.argv[7]
arg8 = sys.argv[8]
arg9 = sys.argv[9]

# Create the target directory if it doesn't exist
print(arg8)
print(arg9)

# Open the text file in write mode
with open('example.txt', 'w') as file:
    file.write('Hello, this is some content.')

output_file = Path(arg8)
output_file.parent.mkdir(exist_ok=True, parents=True)
output_file.write_text("FOOBAR")

# os.makedirs(os.path.dirname(arg8), exist_ok=True)
# with open(arg8, 'w') as text_file:
#     text_file.write("sas")


# Create the graph
def y(x, t):
    return np.sin(x) * np.cos(t)

fig = plt.figure()
ax1 = fig.add_subplot(131, projection='3d')
x1 = np.linspace(0, 2 * np.pi, 100)
t1 = np.linspace(0, 2 * np.pi, 100)
X1, T1 = np.meshgrid(x1, t1)
Y1 = y(X1, T1)
surf1 = ax1.plot_surface(X1, T1, Y1, cmap='viridis')
ax1.set_xlabel('X-axis')
ax1.set_ylabel('T-axis')
ax1.set_zlabel('Y-axis')
ax1.set_title('Graph 1')

# Save the graph
plt.savefig(arg9)

