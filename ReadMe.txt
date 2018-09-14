Consider the input of all custoer is in transaction.csv file
Back end
========
* create a folder RaboBank
* Paste the input file in that folder(renamed record file as trasaction.csv = considering this input file with all customer transaction records)
* Create a new environment variable called RaboFileLocation = D:\RaboBank(Which is having input  file location)



RaboBank
|
-Archive   - once the transaction data processed then that file move Archive Dir
-Customerfile 
	-XML - xml type customer file created for the account transaction
	-CSV - csv type customer file created for the account transaction
	-ZIP - every customer account xml,csv file zipped
-Failed - we will get the failed transaction report in this folder

Folder creation .docx attached (Folder structure need to create.docx)
Output for BE/FE attached as documents

As we used eureka in this assignment.
Please start EurekaRaboApp first
and then start RaboCustStmtApp

*POM Build script also attached.
Note: After build Please make sure transaction.csv available in specified path

BackEnd URL
Generate Report - http://localhost:8484/statement/generate
Download Report - http://localhost:8484/statement/getFile/{accountno}?fileType=
		http://localhost:8484/statement/getFile/NL74ABNA0248990274?fileType=CSV
		http://localhost:8484/statement/getFile/NL74ABNA0248990274?fileType=XML
		http://localhost:8484/statement/getFile/NL74ABNA0248990274

FE URL -http://localhost:4200
