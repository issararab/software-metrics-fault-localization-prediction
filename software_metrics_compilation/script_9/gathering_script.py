from io import StringIO
import math
import os
import gc
import re

#Since the classes for which metrics were computed in each step may differ in order and even in number
#I m geting the observations and extracting the class names, that is supposed to be unique, in order togather
#all the metrics of each class in a single observation
#Static refers to CK metrics
#Dynamic refers to SCMS metrics
#Coverage refers to Test Suite metrics
#Bug refers to bug characteristics metrics
#Label refers to the labels

def magicFun(inPath1,inPath2,inPath3,inPath4,inPath5):
	with open(inPath1) as f:
		spectra = f.readlines()
	static = [x.strip().split(",",1) for x in spectra]
	staticc = [i[0] for i in static]
	
	with open(inPath2) as f:
		spectra = f.readlines()
	dynamic = [x.strip().split(",",1) for x in spectra]
	
	with open(inPath3) as f:
		spectra = f.readlines()
	coverage = [x.strip().split(",",1) for x in spectra]
	coveragec = [i[0] for i in coverage]
	
	with open(inPath4) as f:
		spectra = f.readlines()
	bug = [x.strip().split(",",1) for x in spectra]
	bugc = [i[0] for i in bug]
	
	with open(inPath5) as f:
		spectra = f.readlines()
	label = [x.strip().split(",",1) for x in spectra]
	labelc = [i[0] for i in label]
	
	
	for x in range(1,len(dynamic)):
		print("*****************>" + str(x) +" " +str(len(dynamic)) )
		print(str(dynamic[x][0]))
		print(str(static[staticc.index(dynamic[x][0])][1]))
		print(str(dynamic[x][1]))
		print(str(coverage[coveragec.index(dynamic[x][0])][1]))
		print(str(label[labelc.index(dynamic[x][0])][1]))
		finalOutput.write(str(dynamic[x][0])+","+str(static[staticc.index(dynamic[x][0])][1])
												  +","+str(dynamic[x][1])
												  +","+str(bug[bugc.index(dynamic[x][0])][1])
												  +","+str(coverage[coveragec.index(dynamic[x][0])][1])
												  +","+str(label[labelc.index(dynamic[x][0])][1])
												  +"\n")
			
	
	
	return "Done!"
	
#print(df['Class_Name'][dict["Time"]])	
#magicFun("coverage\\Chart_1_class-labels","bug_chars\\Chart_1_bugChar.csv","class-labels\\Chart_1_labels.csv", "Chart",1)

if os.path.exists("data_set.csv"):
	os.remove("data_set.csv")
#ck , CMS, Bug, Coverage
finalOutput = open("data_set.csv","a")
finalOutput.write("class,cbo,wmc,dit,noc,rfc,lcom,nom,nopm,nosm,nof,nopf,nosf,nosi,loc,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers,num_Files,num_Classes,num_Methods,num_Lines,num_Added,num_Removed,num_Modified,num_Chunks,num_Failing_Tests,num_Repair_Actions,num_Repair_Patterns,First_Exception,Loc_Predicat,Loc_ConditionalBlock,Loc_FunctionBody,Loc_Constructor,Loc_LoopBody,Ncf,Nuf,Ncs,Ns,Nf,Ntsc,Ndsc,Nntc,runtime,passTestRatio,failTestRatio,TotPassTestRatio,TotFailTestRatio,NTestRunPerRT,Um,Md,Nmd,Gs,DDU,Jaccard,label\n")
"""
#set labels to Chart and assign bug characteristics to faulty classes
projectName = "Chart"
for i in range(1,27):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()

#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,28):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()


#extract Mockito test suite characteristics at class level
projectName = "Mockito"
for i in range(1,39):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()

#extract Lang test suite characteristics at class level
projectName = "Lang"
for i in range(1,66):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()
	
#extract Math test suite characteristics at class level
projectName = "Math"
for i in range(1,107):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()

#extract Closure test suite characteristics at class level
projectName = "Closure"
for i in range(1,134):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("ck-metrics\\"+ projectName+ "_" + str(i)+"_buggy.csv","CSMSTool\\"+ projectName+ "_" + str(i)+"_SCMS.csv","coverage\\"+ projectName+ "_" + str(i)+"_class-metrics","bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()
"""
#extract Closure test suite characteristics at class level
projectName = "Time"
for i in range(1,3):
	print("====>  " + projectName+ "_" + str(i))
	print(magicFun("..\\script_7\\expected\\"+ projectName+ "_" + str(i)+"_buggy.csv","..\\script_8\\expected\\"+ projectName+ "_" + str(i)+"_SCMS.csv","..\\script_6\\expected\\"+ projectName+ "_" + str(i)+"_class-metrics","..\\script_9\\expected\\bug_chars\\"+ projectName+ "_" + str(i)+"_bug_Char.csv","..\\script_9\\expected\\class-labels\\"+ projectName+ "_" + str(i)+"_labels.csv"))
	gc.collect()
finalOutput.close()