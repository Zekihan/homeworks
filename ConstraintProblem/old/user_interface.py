import os

exec_dir = os.path.abspath(os.path.dirname(__file__))
clues = []
data = []
puzzles = []
for root, dirs, files in os.walk(exec_dir):
    print(files)
    for file in files:
        if('clues-' in file):
            clues.append(file)
        elif('data-' in file):
            data.append(file)
    break
for clue in clues:
    num = clue.split('.')[0].split('-')[1]
    if(clue.replace('clues-','data-') in data):
        puzzles.append(num)

print("The problems available in this directory: ", end='')
for puzzle in puzzles:
    print(puzzle, end=' ')
print("")

input_number = input("Choose a problem: ​")
if (input_number in puzzles):
    
else:
    print("This problem doesn't exists in this directory.")