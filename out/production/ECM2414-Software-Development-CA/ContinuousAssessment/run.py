import os
from rich.console import Console

c = Console()

# Get files in the directory
files = os.listdir()

available = {}
i = 1

# Clear console and print necessary things
c.clear()
c.print("[bold][underline]File to run")
c.rule()

# Print the available java apps to run
for file in files:
	if ".java" in file.lower():
		c.print(f"{i} . {file}")
		available[i] = file
		i += 1

c.print("\n")

usr = 0

def processInput(usr_input):
	# If there is an x in the input then the program needs to be run multiple times
	if "x" in str(usr):
		return usr_input[:1]
	elif " " in str(usr_input):
		# If there is a space then there are additional arguments
		return int(str(usr_input.split(" ")[0]))
	elif len(str(usr)) > 1 and not str(usr)[1].isnumeric():
		# Return this for iterations
		return 0
	
	return usr_input


# Loop while the input isn't accepted
while not int(processInput(usr)) in available.keys():
	usr = input()

# Functions to run the java file
def fileRunner(file: str, args: str, iteration=""):
	# Print the top text showing the file name arguments and iteration
	if args == "":
		c.rule(f"[green]{file}.java Output{iteration}")
	else:
		c.rule(f"[green]{file}.java Args: {args} Output{iteration}")

	c.print("\n")

	# Build the java file
	os.system(f"javac {file}.java")

	# Run the java file
	if args == "":
		os.system(f"java {file}")
	else:
		c.print(f"java {file} {args}")
		os.system(f"java {file} {args}")

	c.print("\n")

	# Print the final message
	c.rule(f"[red]{file}.java Output{iteration}{' ' if iteration != '' else ''}Finished")




c.clear()

# Parse input for arguments to pass to thefile
arguments = ""
if " " in usr:
	arguments = " ".join(usr.split(" ")[1:])

# Check for multiple iterations to run
if "x" in usr:
	file = available[int(usr[0])].split(".")[0]

	# Parse input for iterations if there is arguments
	if arguments == "":
		iterations = int(usr.split("x")[1])
	else:
		iterations = int(usr.split("x")[1].split(" ")[0])

	# Run the file for the specified amount of times
	try:
		for i in range(iterations):
			fileRunner(file, arguments, f" #{i + 1}")
	except KeyboardInterrupt:
		c.print("\n\n")
		c.rule("[red]Exiting")


else:
	# Run the file a single time

	# Parse for arguments
	if " " in str(usr):
		file = available[int(str(usr).split(" ")[0])].split(".")[0]
	else:
		file = available[int(usr)].split(".")[0]

	# Run the file
	fileRunner(file, arguments)
