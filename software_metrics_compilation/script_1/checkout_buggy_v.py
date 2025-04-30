import subprocess

#Command example
#defects4j checkout -p Chart -v 1b -w ./chart_1_buggy
#
commandP1 = "defects4j checkout -p "
commandP2 = " -v "
commandP3 = "b -w ./"
commandP4 = "_buggy"

"""
#extract Chart buggy versions
projectName = "Chart"
for i in range(1,27):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	#print(command.split())
	subprocess.run(command.split())
	
#extract Time buggy versions
projectName = "Time"
for i in range(1,28):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())

#extract Mockito buggy versions
projectName = "Mockito"
for i in range(1,39):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())

#extract Lang buggy versions
projectName = "Lang"
for i in range(1,66):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())

#extract Math buggy versions
projectName = "Math"
for i in range(1,107):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())

#extract Closure buggy versions
projectName = "Closure"
for i in range(1,134):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())
"""
	
#extract Time buggy versions
projectName = "Time"
for i in range(1,3):
	command = commandP1 + projectName + commandP2 + str(i) + commandP3 + projectName + "_" + str(i) + commandP4
	print("====>  " + command)
	subprocess.run(command.split())