import os

x_file_path = "src/main/data/x.csv"
y_file_path = "src/main/data/y.csv"


def delete_file(path):
    if os.path.exists(path):
        os.remove(path)
    else:
        print("The file does not exist")


delete_file(x_file_path)
delete_file(y_file_path)

