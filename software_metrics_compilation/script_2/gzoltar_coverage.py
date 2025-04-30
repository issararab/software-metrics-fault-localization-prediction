import subprocess
import time

#Command example gets the matrix and spectra and computes the time needed to execute all the tests as a metric
#
#bash run_gzoltar.sh Lang 1 . developer
#
commandP1 = "bash run_gzoltar.sh "
commandP2 = " . developer"

"""
#extract Chart buggy versions
projectName = "Chart"
for i in range(1,27):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())

#extract Time buggy versions
projectName = "Time"
for i in range(1,28):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())


#extract Mockito buggy versions
projectName = "Mockito"
for i in range(1,39):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())

#extract Lang buggy versions
projectName = "Lang"
for i in range(1,66):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())

#extract Math buggy versions
projectName = "Math"
for i in range(1,107):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())

#extract Closure buggy versions
projectName = "Closure"
for i in range(1,134):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())
"""
#extract Time buggy versions
projectName = "Time"
for i in range(1,3):
	command = commandP1 + projectName + " " + str(i) + commandP2 
	print("====>  " + command)
	subprocess.run(command.split())