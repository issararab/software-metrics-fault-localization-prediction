from io import StringIO
import math
import os
import gc
import re


	
def magicFunction(rTime,srcPath,outPath):
	runtime = int(rTime)
	with open(srcPath+"matrix") as f:
		matrix = f.readlines()
	matrix = [x.strip().split() for x in matrix]

	with open(srcPath+"spectra") as f:
		spectra = f.readlines()
	#[print(re.sub('\$.*#', '#', x)) for x in spectra]
	spectra = [(re.sub('\$.*#', '#', x)).strip().split("#") for x in spectra]
	#m2 = [x.split() for x in m2]
	
	"""
	print(len(matrix))
	print(len(matrix[0]))
	print(matrix[0][len(matrix[0])-1])

	print(len(spectra))
	print(len(spectra[0]))
	print(spectra[0][len(spectra[0])-1])
	"""
	#get the list of classes for which the tests did cover
	classes = sorted(list(set([i[0] for i in spectra])))
	#for cl in classes:
	#	print(cl)
	"""
	print(len(classes))
	print(classes[0])
	"""
	#Distinct number of statements covered by the test suite in a class
	Ndsc = [0]*len(classes)

	#Initialize the classMatrix (make sure all the values are zeros)????????????
	classMatrix = [[0]*(len(classes)+1) for x in range(len(matrix))]
	classMatrixBinary = [[0]*(len(classes)+1) for x in range(len(matrix))]
	classSpectra = []

	print("class matrix rows ==>" + str(len(classMatrix)))
	print("class matrix columns ==>" + str(len(classMatrix[0])))
	#Assign the test label pass/fail to the classMatrix

	#List of failing tests
	listFT = []
	cnt = 0
	for x in range(len(matrix)):
		classMatrix[x][len(classes)] = matrix[x][len(matrix[0])-1]
		classMatrixBinary[x][len(classes)] = matrix[x][len(matrix[0])-1]
		if matrix[x][len(matrix[0])-1] == "-":
			listFT.append(x)
	print("1")
	#Number of failing tests
	numFT = len(listFT)

	distinctStList = []
	#Generate the class matrix and spectra
	for x in range(len(classes)):
		print(len(classes)-x)
		classSpectra.append([])
		distinctSt = set([])
		for y in range(len(matrix)):
			#print(len(matrix)-y)
			statementsCovered = str(y+1)+"#"
			testCover = 0
			for z in range(len(spectra)):
				#print(len(spectra))
				if spectra[z][0] == classes[x]:
					if statementsCovered[len(statementsCovered)-1] == "#":
						statementsCovered += str(spectra[z][1])
					else:
						statementsCovered += "," + str(spectra[z][1])
					distinctSt.add(int(spectra[z][1]))
					testCover += int(matrix[y][z])
			classMatrix[y][x] = testCover
			if testCover != 0:
				classMatrixBinary[y][x] = 1
				classSpectra[x].append(statementsCovered)
		Ndsc[x] = len(distinctSt)
		distinctStList.append(",".join(map(str,sorted(distinctSt))))
				
	print("2")		
	#for x in range(len(classMatrix)):
	#	print(*classMatrix[x])

	#output the matricies 	

	if os.path.exists(srcPath+"class-matrix"):
		os.remove(srcPath+"class-matrix")
	outputBn = open(srcPath+"class-matrix","a")
	for i in range(len(classMatrix)):
		lineBn = ""
		for j in range(len(classMatrixBinary[i])):
			if j+1 == len(classMatrixBinary[i]):
				lineBn += str(classMatrixBinary[i][j]) + "\n"
			else:
				lineBn += str(classMatrixBinary[i][j]) + " "
		outputBn.write(lineBn)
	outputBn.close()
	print("3")
	#output the spectra
	if os.path.exists(srcPath+"class-spectra"):
		os.remove(srcPath+"class-spectra")
	outputSpDSt = open(srcPath+"class-spectra","a")
	for x in range(len(classSpectra)):
		outputSpDSt.write(str(classes[x]) + "#" + distinctStList[x] + "\n")
	outputSpDSt.close()
	#How many times the class was covered by the tests (Sum all the row not 0&1)
	testClassCall = 0
	print("4")
	#How many tests did cover the class (sum ones horizantally)
	testClassCoverage = 0

	#the test that covers the maximum runs of statements on a class (index of max column)
	testMaxClassCoverage = 0

	#the average of runs done by all the tests on a class (the total sum [1] divided by [2])
	classAverageCoverage = 0 #if testClassCoverage != 0: testClassCall/testClassCoverage

	#the number of tests with the maximum number of runs (Sum the # of tests of [3]
	majorTests = []
	numOfMajorTests = len(majorTests)
	#
	#The metrics in the slides (5-SBFL)
	#



	##Computation
	#Number of failed test cases that cover the class
	Ncf = [0]*len(classes)
	#Number of failed test cases that do not cover the class
	Nuf = [0]*len(classes)
	#Number of successful test cases that cover the class
	Ncs = [0]*len(classes)
	#Number of succesfful test
	Ns = [0]*len(classes)
	#Number of failed test
	Nf = [0]*len(classes)
	#Additional metrics
	Jaccard = [0]*len(classes) 
	DStar = [0]*len(classes)
	Tarantula = [0]*len(classes)
	Ochiai = [0]*len(classes)
	#Total number of statements in the class covered by the test suit
	Ntsc = [0]*len(classes)

	###Metrics from the paper 






	#Distinct number of statements covered by the test suite in a class
	#Ndsc = [0]*len(classes)
	#Total number of test cases
	Nntc = [len(classMatrix)]*len(classes)

	print("5")
	for i in range(len(classMatrix)):
		testResult = classMatrix[i][len(classes)]
		for j in range(len(classes)):
			if testResult == "+":
				Ns[j] += 1
				if classMatrixBinary[i][j] == 1:
					Ncs[j] += 1
			else:
				Nf[j] += 1
				if classMatrixBinary[i][j] == 1:
					Ncf[j] += 1
				else:
					Nuf[j] += 1
				
			Ntsc[j] += classMatrix[i][j]


	for i in range(len(classes)):
		Jaccard[i] = Ncf[i]/(Ncf[i]+Nuf[i]+Ncs[i])
	


	#Compute journal metrics
	ones = 0
	N = Nntc[0]
	M = len(classes)
	listDistinctCol = []
	listDistinctRow = []
	listCol = ["" for j in range(len(classes))]
	listRow = []
	print("6")
	for i in range(len(classMatrix)-1):
		strRow = ""
		for j in range(len(classes)):
			strRow += str(classMatrixBinary[i][j])
			listCol[j] += str(classMatrixBinary[i][j])
			if classMatrixBinary[i][j] == 1:
				ones += 1
		listRow.append(strRow)

	listDistinctCol = list(set(listCol))
	listDistinctRow = list(set(listRow))

	#Uniqueness metric
	Um = [len(listDistinctCol)/M]*len(classes)
	#Matrix density
	Md = [ones/(M*N)]*len(classes)
	#Normalized matrix density
	p = ones/(M*N)
	Nmd = [1-abs(1-2*p)]*len(classes)
	#Gini Simpson
	numerator = 0
	for st in listDistinctRow:
		numerator += listRow.count(st) * (listRow.count(st)-1) 
	if N-1 == 0:
		Gs = [1 - (numerator)/(N*N)]*len(classes)
	else:
		Gs = [1 - (numerator)/(N*(N-1))]*len(classes)
	#Density Diversity Uniquness
	DDU = [Nmd[0]*Gs[0]*Um[0]]*len(classes)

	#Ratios
	passTestRatio = [0]*len(classes)
	failTestRatio = [0]*len(classes)
	TotPassTestRatio = [0]*len(classes)
	TotFailTestRatio = [0]*len(classes)
	NTestRunPerRT = [0]*len(classes)
	print("7")
	for i in range(len(classes)):
		if (Ncs[i]+Ncf[i]) == 0:
			passTestRatio[i] = 0
			failTestRatio[i] = 0
		else:
			passTestRatio[i] = Ncs[i]/(Ncs[i]+Ncf[i])
			failTestRatio[i] = Ncf[i]/(Ncs[i]+Ncf[i])
		TotPassTestRatio[i] = Ncs[i]/Nntc[i]
		TotFailTestRatio[i] = Ncf[i]/Nntc[i]
		NTestRunPerRT[i] = (Ncs[i]+Ncf[i])/runtime
	#output
	if os.path.exists(outPath+"class-metrics"):
		os.remove(outPath+"class-metrics")
	outputMetrics = open(outPath+"class-metrics","a")
	outputMetrics.write("Class,Ncf,Nuf,Ncs,Ns,Nf,Ntsc,Ndsc,Nntc,runtime,passTestRatio,failTestRatio,TotPassTestRatio,TotFailTestRatio,NTestRunPerRT,Um,Md,Nmd,Gs,DDU,Jaccard" + "\n")
	for x in range(len(Ncf)):
		outputMetrics.write(str(classes[x])+","+ str(Ncf[x-1])+","+str(Nuf[x-1])+","+str(Ncs[x-1])+","+str(Ns[x-1])+","+str(Nf[x-1])+","+str(Ntsc[x-1])+","+str(Ndsc[x-1])+","+str(Nntc[x-1])+","+str(runtime)+","+str(passTestRatio[x-1])+","+str(failTestRatio[x-1])+","+str(TotPassTestRatio[x-1])+","+str(TotFailTestRatio[x-1])+","+str(NTestRunPerRT[x-1])+","+str(Um[x-1])+","+str(Md[x-1])+","+str(Nmd[x-1])+","+str(Gs[x-1])+","+str(DDU[x-1])+","+str(Jaccard[x-1])+ "\n")
	outputMetrics.close()

	uniqueJaccard = sorted(list(set(Jaccard)), reverse=True)
	#print(uniqueJaccard)
	#Rank
	Rank = [0]*len(classes)
	for i in range(len(classes)):
		for j in range(len(uniqueJaccard)):
			if Jaccard[i] == uniqueJaccard[j]:
				Rank[i] = j+1
				break
	#labels
	if os.path.exists(outPath+"class-ranks"):
		os.remove(outPath+"class-ranks")
	outputMetrics = open(outPath+"class-ranks","a")
	outputMetrics.write("Class,Rank" + "\n")
	for x in range(len(Ncf)):
		outputMetrics.write(str(classes[x])+","+str(Rank[x-1])+"\n")
	outputMetrics.close()
	return "Done!"
	
with open("..\\script_5\\expected\\sbfl_runtimes.csv") as f:
	runTt = f.readlines()
runT = [x.strip().split(",") for x in runTt]

"""
runTIndex = 0

#extract Chart test suite characteristics at class level
projectName = "Chart"
for i in range(1,27):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()

#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,28):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()


#extract Mockito test suite characteristics at class level
projectName = "Mockito"
for i in range(1,39):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()

#extract Lang test suite characteristics at class level
projectName = "Lang"
for i in range(1,66):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()
	
#extract Math test suite characteristics at class level
projectName = "Math"
for i in range(1,107):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()

#extract Closure test suite characteristics at class level
projectName = "Closure"
for i in range(1,134):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()
"""
runTIndex = 0
#extract Time test suite characteristics at class level
projectName = "Time"
for i in range(1,3):
	print("====>  " + projectName+ "_" + str(i))
	runTIndex +=1
	print(runTIndex)
	print(magicFunction(runT[runTIndex][2],"..\\script_2\expected\\gzoltars\\"+projectName+"\\"+str(i)+"\\",projectName+"_"+str(i)+"_"))
	gc.collect()