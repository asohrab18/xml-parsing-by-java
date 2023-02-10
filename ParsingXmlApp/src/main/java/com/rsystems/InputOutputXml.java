package com.rsystems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InputOutputXml {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File inputFile = new File("C:/Users/Sohrab/xml_files/input.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(inputFile);
		Node node = document.getElementsByTagName("supercars").item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			element.setAttribute("company", "Lamborgini");
			NodeList childNodeList = element.getChildNodes();
			for (int j = 0; j < childNodeList.getLength(); j++) {
				Node childNode = childNodeList.item(j);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) childNode;
					if (childElement.getTextContent().equals("Ferrari 101")) {
						childElement.setTextContent("Lamborgini 555");
					} else if (childElement.getTextContent().equals("Ferrari 202")) {
						childElement.setTextContent("Lamborgini 777");
					}
				}
			}
		}
		// write the content
		FileOutputStream output = new FileOutputStream("C:/Users/Sohrab/xml_files/output.xml");
		StreamResult result = new StreamResult(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);

		transformer.transform(source, result);
		System.out.println("-----------Modified File-----------");
	}
}
