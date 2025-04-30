import subprocess
import os

#Command example
#java -jar ck-0.2.1-SNAPSHOT-jar-with-dependencies.jar C:\Users\bombarli\Desktop\TUM\summer_2018\practical\metric-summary\metrics\scripts\execution-scenario\script_1\expected\Time\Time_2_buggy\src Time_2_buggy.csv

"""
for project in {"Chart","Time","Closure","Mockito","Math","Lang"}:
	dirs = next(os.walk("..\\script_1\\expected\\"+str(project)))[1]
	for n in dirs:
		command = "java -jar ck-0.2.1-SNAPSHOT-jar-with-dependencies.jar ..\\..\\..\\script_1\\expected\\"+str(project)+"\\" + str(n) + "\\" + "src " + str(n) +".csv"
		print("\n ====>  " + command)
		subprocess.run(command.split())
"""

dirs = next(os.walk("..\\script_1\\expected\\"+str(project)))[1]

for n in dirs:
	command = "java -jar ck-0.2.1-SNAPSHOT-jar-with-dependencies.jar ..\\..\\..\\script_1\\expected\\"+str(project)+"\\" + str(n) + "\\" + "src " + str(n) +".csv"
	print("\n ====>  " + command)
	subprocess.run(command.split())