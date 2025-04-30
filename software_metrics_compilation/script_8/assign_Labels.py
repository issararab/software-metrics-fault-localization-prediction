from io import StringIO
import math
import os
import gc
import re
import pandas as pd

from pandas import ExcelFile
 

 


#print(df['Class_Name'])

dict = {'Chart' : 0,'Closure' : 26,'Lang' : 159,'Math' : 224,'Mockito' : 330,'Time' : 368}

def magicFun(inPath,outPath,outPathL,project,buggyVersion):
	with open(inPath) as f:
		spectra = f.readlines()
	#[print(re.sub('\$.*#', '#', x)) for x in spectra]
	classes = [x.strip().split(",") for x in spectra]
	
	if os.path.exists(outPath):
		os.remove(outPath)
	if os.path.exists(outPathL):
		os.remove(outPathL)
	outputLabels = open(outPathL,"a")
	outputMetrics = open(outPath,"a")
	outputMetrics.write("class,num_Files,num_Classes,num_Methods,num_Lines,num_Added,num_Removed,num_Modified,num_Chunks,num_Failing_Tests,num_Repair_Actions,num_Repair_Patterns,First_Exception,Loc_Predicat,Loc_ConditionalBlock,Loc_FunctionBody,Loc_Constructor,Loc_LoopBody\n")
	outputLabels.write("class,label\n")
	for x in range(1,len(classes)):
		if classes[x][0] in df['Class_Name'][dict[project]+buggyVersion-1].split(","):
			outputMetrics.write(str(classes[x][0])+","+str(df['num_Files'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Classes'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Methods'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Lines'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Added'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Removed'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Modified'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Chunks'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Failing_Tests'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Repair_Actions'][dict[project]+buggyVersion-1])
												  +","+str(df['num_Repair_Patterns'][dict[project]+buggyVersion-1])
												  +","+str(df['First_Exception'][dict[project]+buggyVersion-1])
												  +","+str(df['Loc_Predicat'][dict[project]+buggyVersion-1])
												  +","+str(df['Loc_ConditionalBlock'][dict[project]+buggyVersion-1])
												  +","+str(df['Loc_FunctionBody'][dict[project]+buggyVersion-1])
												  +","+str(df['Loc_Constructor'][dict[project]+buggyVersion-1])
												  +","+str(df['Loc_LoopBody'][dict[project]+buggyVersion-1])
												  +"\n")
			outputLabels.write(str(classes[x][0])+","+"Perfect"+ "\n")
		elif int(classes[x][1])>= 1 and int(classes[x][1])<=5:
			outputMetrics.write(str(classes[x][0])+",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n")
			outputLabels.write(str(classes[x][0])+","+"Very-Good"+ "\n")
		elif int(classes[x][1])>= 6 and int(classes[x][1])<=10:
			outputMetrics.write(str(classes[x][0])+",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n")
			outputLabels.write(str(classes[x][0])+","+"Good"+ "\n")
		elif int(classes[x][1])>= 11 and int(classes[x][1])<=15:
			outputMetrics.write(str(classes[x][0])+",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n")
			outputLabels.write(str(classes[x][0])+","+"Fair"+ "\n")
		else:
			outputMetrics.write(str(classes[x][0])+",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\n")
			outputLabels.write(str(classes[x][0])+","+"Bad"+ "\n")
	outputLabels.close()
	outputMetrics.close()
	
	return "Done!"
	


df = pd.read_excel('..\\script_3\\Bug_Characteristics.xlsx', sheetname='Sheet1')

#print("Column headings:")
#print(df.columns)

if not os.path.exists("bug_chars"):
    os.makedirs("bug_chars")
if not os.path.exists("class-labels"):
    os.makedirs("class-labels")
	
"""
#Load the whole table containing the information about the bugs in each buggy version
df = pd.read_excel('bug_chars\\Bug_Characteristics.xlsx', sheetname='Sheet1')

#set labels to Chart and assign bug characteristics to faulty classes
projectName = "Chart"
for i in range(1,27):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()

#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,28):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()


#extract Mockito test suite characteristics at class level
projectName = "Mockito"
for i in range(1,39):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()

#extract Lang test suite characteristics at class level
projectName = "Lang"
for i in range(1,66):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()
	
#extract Math test suite characteristics at class level
projectName = "Math"
for i in range(1,107):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()

#extract Closure test suite characteristics at class level
projectName = "Closure"
for i in range(1,134):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("coverage\\"+ projectName+ "_" + str(i)+"_class-labels","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()
"""

	
#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,3):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("..\\script_6\\expected\\"+ projectName+ "_" + str(i)+"_class-ranks","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv", projectName,i))
	gc.collect()