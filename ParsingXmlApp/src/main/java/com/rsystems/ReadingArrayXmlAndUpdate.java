package com.rsystems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
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

public class ReadingArrayXmlAndUpdate {

	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String filePath = "C:/Users/Sohrab/xml_files/ArrayResponse.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(filePath));
		document.getDocumentElement().normalize();

		Map<String, String> tagContentMap = new HashMap<String, String>();
		tagContentMap.put("transactionDescription", "AAAAAAAAAAAAAAAAAAAA");

		processAndSet(document, tagContentMap);

		// write the content
		DOMSource source = new DOMSource(document);

		FileOutputStream output = new FileOutputStream("C:/Users/Sohrab/xml_files/ArrayResponseOutput.xml");
		StreamResult result = new StreamResult(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		System.out.println("-----------Created File-----------");
	}

	public static void processAndSet(Document document, Map<String, String> tagContentMap) throws TransformerException {
		NodeList memberNodeList = document.getElementsByTagName("member");
		for (int i = 0; i < memberNodeList.getLength(); i++) {
			Node memberNode = memberNodeList.item(i);
			if (memberNode.getNodeType() == Node.ELEMENT_NODE) {
				Element memberElement = (Element) memberNode;
				for (Map.Entry<String, String> entry : tagContentMap.entrySet()) {
					NodeList childNodeList = memberElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							if (childElement.getTextContent().trim().equals(entry.getKey())) {
								for (int k = 0; k < childNodeList.getLength(); k++) {
									Node childNode2 = childNodeList.item(k);
									if (childNode2.getNodeType() == Node.ELEMENT_NODE) {
										Element childElement2 = (Element) childNode2;
										if (childElement2.getTagName().trim().equals("value")) {
											childElement2.setTextContent(entry.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}