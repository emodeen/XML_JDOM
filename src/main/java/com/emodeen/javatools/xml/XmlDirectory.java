package com.emodeen.javatools.xml;

import java.io.File;
import java.util.List;

/**
 * @author Eric
 * A class representing a directory of XML files.
 *
 */
public class XmlDirectory extends File {
	
	private static final long serialVersionUID = 1L;
	private List<XmlFile> xmlFiles;

	/**
	 * Constructor.
	 * @param pathname The directory path.
	 */
	public XmlDirectory(String pathname) {
		
		super(pathname);
		
		// Create the directory if it does not exist.
		// The best practice is to not check for the existence of the directory before creating it. 
		// An exception will not be thrown if the directory already exists.
		this.mkdir();
	}

	/**
	 * @return the xmlFiles
	 */
	public List<XmlFile> getXmlFiles() {
		return xmlFiles;
	}

	/**
	 * @param xmlDocs the xmlFiles to set
	 */
	public void setXmlFiles(List<XmlFile> files) {
		this.xmlFiles = files;
	}

}
