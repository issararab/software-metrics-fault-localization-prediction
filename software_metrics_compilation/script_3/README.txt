First extract the project from ck-master.zip.

Second, open your cmd on windows and move to "cd ck-master"

Third, run: "mvn clean compile assembly:single" to generate the target folder

Fourth, move the python script "ext_ck_metrics.py" in the main "script_4" folder to the target folder of the project
	(this step was done since the jar file directly was raising some issues :/ )
	
Fifth, run "python ext_ck_metrics.py" on your windows command line once on the "target" folder


the writing of the script depends on how you wants to organize the data for future usage.
Just make sure you have JAVA 1.7
and Python 3


You must get the same output in this folder as the one in folder "expected".