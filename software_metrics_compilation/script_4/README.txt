Before running this bash script, please follow the organizational steps bellow:

First, copy all the compressed files generated from gezoltar in folder "script_2" to a folder named "runtimes" in this curent folder

Second, change the naming of those compressed files by just deleting the word developer in the file's name
	Ex: "Time-1-developer-gzoltar-files.tar.gz" => "Time-1-gzoltar-files.tar.gz"
	An example of two files in the folder "runtimes" is presented for better explanat ion

Third, run the following command on the linux sub-system: $> bash collect_sbfl_runtimes.sh runtimes
	(you can run the script for test on the 2 files)


The output will be in the form of a csv file named "sbfl_runtimes.csv" as shown in the folder "expected"