from io import StringIO
import math
import os
import gc
import re

#ckPath without _buggy.csv
def dataProcessing(ckPath,spectraPath):
	
	with open(spectraPath+"class-spectra") as f:
		spectra = f.readlines()
	#[print(re.sub('\$.*#', '#', x)) for x in spectra]
	spectra = [(re.sub('\$.*#', '#', x)).strip().split("#") for x in spectra]
	
	classes = sorted(list(set([i[0] for i in spectra])))
	
	with open(ckPath+"_buggy.csv") as f:
		ck = f.readlines()
	#[print(re.sub('\$.*#', '#', x)) for x in spectra]
	ckMetrics = [x.strip().split(",") for x in ck]
	#output new ck metrics
	if os.path.exists(ckPath+"_src_paths"):
		os.remove(ckPath+"_src_paths")
	if os.path.exists(ckPath+"_buggy.csv"):
		os.remove(ckPath+"_buggy.csv")
	outputMetrics = open(ckPath+"_buggy.csv","a")
	outputPaths = open(ckPath+"_src_paths","a")
	outputMetrics.write("class,cbo,wmc,dit,noc,rfc,lcom,nom,nopm,nosm,nof,nopf,nosf,nosi,loc" + "\n")
	for x in range(1,len(ckMetrics)):
		if ckMetrics[x][1] in classes:
			outputPaths.write(str(ckMetrics[x][1])+","+str(ckMetrics[x][0])+ "\n")
			ckMetrics[x].pop(0)
			del ckMetrics[x][1]
			outputMetrics.write(','.join(map(str, ckMetrics[x])) + "\n")
	outputMetrics.close()
	outputPaths.close()
	
	return "DONE!"


"""
#extract Chart test suite characteristics at class level
projectName = "Chart"
for i in range(1,27):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()

#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,28):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()


#extract Mockito test suite characteristics at class level
projectName = "Mockito"
for i in range(1,39):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()

#extract Lang test suite characteristics at class level
projectName = "Lang"
for i in range(1,66):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()
	
#extract Math test suite characteristics at class level
projectName = "Math"
for i in range(1,107):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()

#extract Closure test suite characteristics at class level
projectName = "Closure"
for i in range(1,134):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing(projectName+"_"+str(i),projectName+"\\"+str(i)+"\\"))
	gc.collect()
"""

#extract Closure test suite characteristics at class level
projectName = "Time"
for i in range(1,3):
	print("====>  " + projectName+ "_" + str(i))
	print(dataProcessing("..\\script_4\\expected\\"+projectName+"_"+str(i),"..\\script_2\\expected\\gzoltars\\"+projectName+"\\"+str(i)+"\\"))
	gc.collect()