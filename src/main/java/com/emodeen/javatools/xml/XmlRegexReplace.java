/**
 * 
 */
package com.emodeen.javatools.xml;

import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.filter.ElementFilter;
import org.jdom.input.*;
import org.jdom.output.*;

/**
 * A class that finds the "virtue" regular expression in XML source files, replaces the regular expression, and writes a revised set of files.
 * @author Eric
 *
 */
public class XmlRegexReplace {

	
	private XmlDirectory sourceDir;
	private XmlDirectory targetDir;
	
	/**
	 */
	public XmlRegexReplace( String [] args) {

		super();
		
		this.sourceDir = new XmlDirectory( args[0]);
		this.targetDir = new XmlDirectory( args[1]);
	}



	/**
	 * @param args
	 * args[0] is the source directory for the XML files.
	 * args[1] is the target directory where the modified XML files will be written.
	 */
	public static void main(String[] args) {

		XmlRegexReplace xrr = new XmlRegexReplace( args);
		
		xrr.run();
	}
	
	/**
	 * The main method to run the program.
	 */
	public void run() {
		
        File [] files = null;
        List<XmlFile> targetFiles = new ArrayList<XmlFile>();
        List<XmlFile> sourceFiles = new ArrayList<XmlFile>(); 
        List<Element> elementList = new ArrayList<Element>();
        ListIterator<Element> elemItr = null;
        boolean docContainsRegex = false;
        int numRegexMatches = 0;
        String newElemTxt = null;
        
        SAXBuilder builder = new SAXBuilder();
        
        XmlFile sourceFile = null;
        XmlFile targetFile = null;
        Document sourceDoc = null;
        Document targetDoc = null;
        
        String sourceCanonicalPath = null;
        String targetCanonicalPath = null;
        String[] tokens = null;
        
        try {
        	sourceCanonicalPath = sourceDir.getCanonicalPath();
        }
        
        catch ( IOException ioe) {
        	System.out.println("Source directory can't be found");
        }
        
        try {
        	targetCanonicalPath = targetDir.getCanonicalPath();
        }
        
        catch ( IOException ioe) {
        	System.out.println("Target directory can't be found");
        }
        
        files = sourceDir.listFiles();
        
        // Iterate through all files in source directory
		for (int i = 0; i < files.length; i++) {
			
			docContainsRegex = false;
			numRegexMatches = 0;
			
			if ( files[i].toString().endsWith(".xml")) {
				
				sourceDoc = createDocument(builder, files[i]);
				targetDoc = createDocument(builder, files[i]);
				
				if (sourceDoc != null) {

					sourceFile = new XmlFile( sourceCanonicalPath + File.separator + files[i].getName(), sourceDoc);
					targetFile = new XmlFile( targetCanonicalPath + File.separator + files[i].getName(), targetDoc);
				
					sourceFiles.add( sourceFile);
					
					elementList = getElementList( targetDoc);
					elemItr = elementList.listIterator();	

					// Iterate over all of the <LINE> elements in the source doc
				    while(elemItr.hasNext()) {
				        	
				       	Element e1 = (Element)elemItr.next();
				       	
				        if ( !docContainsRegex) {
				        	if ( elemContainsRegex(e1)) {
				        		docContainsRegex = true;
				        	}
				        }
				        
				        tokens = e1.getText().split("\\s+");
				        
				        numRegexMatches += getNumMatches( tokens);
				        newElemTxt = getNewElemText( tokens);
				        e1.setText(newElemTxt);
				       	elemItr.set(e1);
				    }
					
					if ( docContainsRegex) {
						
						printReport( targetFile, numRegexMatches);
					}
					
					targetFiles.add( targetFile);
					writeDocToFile(targetFile);
				}
			}
		}
		
		sourceDir.setXmlFiles(sourceFiles);
		targetDir.setXmlFiles(targetFiles);
	}

	/**
	 * 
	 * @param file  The xml file to print the report for.
	 * @param numMatches The number of times the regex can be found in the file.
	 */
	public void printReport ( XmlFile file, int numMatches) {
		
		System.out.println( file.getName() + " contains " + numMatches + " occurrences of the regular expression");
	}
	
	/**
	 * 
	 * @param builder The SAXBuilder to build the Documents.
	 * @param file The file to create the Document from.
	 * @return The Document created.
	 */
	public Document createDocument( SAXBuilder builder, File file) {
		
		Document doc = null;
		
		try {
			doc = builder.build( file);
		} 
	
		catch (JDOMException e) {
			e.printStackTrace();
		} 
	
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * 
	 * @param sourceDoc The Document to get the Elements from.
	 * @return A list of LINE Elements contained in the Document.
	 */
	public List<Element> getElementList( Document targetDoc) {

		java.util.Iterator<Element> itr = null;
		List<Element> elementList = new ArrayList<Element>();
        
		itr = targetDoc.getDescendants(new ElementFilter("LINE"));

		// Store elements in a List because Iterator will not allow changing the elements during a traversal.
	    while( itr.hasNext()) {

	        Element e = (Element) itr.next();
	        elementList.add( e);
	    }
	    
	    return elementList;
	}
	
	/**
	 * 
	 * @param targetFile The file to write the Document to.
	 */
	public void writeDocToFile ( XmlFile targetFile) {
		
		try {

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(targetFile.getDocument(), new FileOutputStream( targetFile));
        } 
		
		catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	 * @param e1 The Element to check whether it contains the regex.
	 * @return Whether the Element contains the regex.
	 */
	public boolean elemContainsRegex( Element e1) {

		boolean containsRegex = false;
    	String origTxt = e1.getText();
    	
    	// Check if the <LINE> content contains the regular expression
    	if ( origTxt.matches(".*[vV][iI][rR][tT][uU][eE].*")) {
    		
    		containsRegex = true;
    	}
    	
    	return containsRegex; 
	}
	
	/**
	 * 
	 * @param tokens An array of the whitespace-separated tokens in an Element.
	 * @return The number of tokens in the Element containing the regex.
	 */
	public int getNumMatches( String[] tokens) {
		
		int numMatches = 0;
    	
    	for (int j = 0; j < tokens.length; j++) {
    		
			// If substring contains regex, increment the file's counter.
			if ( tokens[j].matches(".*[vV][iI][rR][tT][uU][eE].*")) {
				
				numMatches++;
			}
		}
    	
    	return numMatches; 
	}
	
	/**
	 * 
	 * @param tokens An array of the whitespace-separated tokens in an Element.
	 * @return A String containing the replacement text for the Element's content.
	 */
	public String getNewElemText( String [] tokens) {
		
		String newTxt = "";
    	String regex = "[vV][iI][rR][tT][uU][eE].*";
    	
    	for (int j = 0; j < tokens.length; j++) {
    		
			// Replace the matched string with the replacement string.
			tokens[j] = tokens[j].replaceAll(regex, "new_text");
			
			newTxt += tokens[j];
			
			if (j < (tokens.length)) {
				newTxt += " ";
			}
		}
    	
    	return newTxt; 
	}
}
