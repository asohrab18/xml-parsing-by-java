package com.rsystems;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LaptopXmlReader {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String filePath = "C:/Users/Sohrab/xml_files/laptops.xml";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(filePath));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("laptop");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println(element.getTagName() + " : " + i + "\tname: " + element.getAttribute("name")+ "\tos: " + element.getAttribute("os"));
				NodeList childNodeList = element.getChildNodes();
				for (int c = 0; c < childNodeList.getLength(); c++) {
					Node childNode = childNodeList.item(c);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						System.out.println("    "+childElement.getTagName() + "\tvalue: " + childElement.getAttribute("value")+ "\tContent: " + childElement.getTextContent());
					}
				}
			}
			System.out.println("------------------------------------------------");
		}
	}
}