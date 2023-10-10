import os
import datetime
import sys

# Check if at least one argument (folder name) is provided
if len(sys.argv) < 2:
    print("Usage: python my_script.py <folder_name>")
    sys.exit(1)


# Access the folder name argument (assuming it's the second argument)
directory_path = sys.argv[1] + '/'


# Get the current date and time
current_datetime = datetime.datetime.now()

# Format the date and time as a string
folder_name = current_datetime.strftime("%Y-%m-%d_%H-%M-%S")

# Create the full path for the new folder
full_path = os.path.join(directory_path, folder_name)

# Check if the directory already exists; if not, create it
if not os.path.exists(full_path):
    os.makedirs(full_path)
    print(f"Folder '{folder_name}' created in '{directory_path}'")
else:
    print(f"Folder '{folder_name}' already exists in '{directory_path}'")