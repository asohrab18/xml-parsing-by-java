package com.rsystems;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadingRequestXml {

	private static final String INPUT_FILE_PATH = "C:/Users/Sohrab/xml_files/Request.xml";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(INPUT_FILE_PATH));
		document.getDocumentElement().normalize();

		NodeList paramNodeList = document.getElementsByTagName("param");
		for (int i = 0; i < paramNodeList.getLength(); i++) {
			Node paramNode = paramNodeList.item(i);
			if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element) paramNode;
				System.out.println("TagName = "+paramElement.getTagName().trim()+",\tTextContent: "+paramElement.getTextContent().trim());
			}
		}
	}
}
