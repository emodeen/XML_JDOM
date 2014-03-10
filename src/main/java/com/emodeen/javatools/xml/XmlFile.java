package com.emodeen.javatools.xml;

import java.io.*;

import org.jdom.Document;

/**
 * @author Eric Modeen
 * This class encapsulates attributes of an XML file.
 *
 */
public class XmlFile extends File {
	
	private static final long serialVersionUID = 1L;
	private Document document;
	
	/**
	 * Constructor.
	 * @param pathname The path of the file.
	 * @param doc The XML Document in the file.
	 */
	public XmlFile(String pathname, Document doc) {
		
		super(pathname);
		
		this.document = doc;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}


}
