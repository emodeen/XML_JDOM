XML_JDOM
========

1.	Create a new Maven project in Eclipse, checking ‘Create a simple project’.
2.	Add a Group ID and Artifact ID on the next screen, and click ‘Finish’.
3.	In your Java project, create the following Java package in ‘/src/main/java’: com.emodeen.javatools.xml.
4.	Get the .java files from /src/main/java/com/emodeen/javatools/xml from this GitHub repository, and copy the files to the com.emodeen.javatools.xml package.
5.	Get the ‘XML_source’ folder from /src/main/resources/ from this GitHub repository, and copy the folder to ‘/src/main/resources/ in your project.
6.	Replace the pom.xml file in your project with the pom.xml from this GitHub repository.
7.	Right click on your project, and go to ‘Run As -> Maven install’.  This will install the project and create a jar file to run.
8.	Right click on the generated jar file in the ‘target’ directory, and specify to run it as a standard Java application, choosing com.emodeen.javatools.xml.XmlRegexReplace as the Main class.  In this dialog, click on ‘Arguments’, and specify the source directory where the XML files are located, followed by a space, followed by a target directory.
9.	Click ‘Run’, and you will see output to the console and the new XML files generated in your target directory.
