package com.rsystems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadingSimpleXmlAndUpdate {

	private static final String INPUT_FILE_PATH = "C:/Users/Sohrab/xml_files/SimpleResponse.xml";
	private static final String OUTPUT_FILE_PATH = "C:/Users/Sohrab/xml_files/SimpleResponseOutput.xml";

	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		readResponseFile();
	}

	public static void readResponseFile() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(INPUT_FILE_PATH));
		document.getDocumentElement().normalize();

		Map<String, String> tagContentMap = new HashMap<String, String>();
		tagContentMap.put("clientId", "CCCCCC");
		tagContentMap.put("password", "*********");
		tagContentMap.put("resultCode", "101");
		tagContentMap.put("terminalID", "T118");
		tagContentMap.put("customerReference", "CR12345");
		
		processAndSetFileContent(tagContentMap, document);

		// write the content
		DOMSource source = new DOMSource(document);

		FileOutputStream output = new FileOutputStream(OUTPUT_FILE_PATH);
		StreamResult result = new StreamResult(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		System.out.println("-----------Created File-----------");
	}

	private static void processAndSetFileContent(Map<String, String> tagContentMap, Document document) {
		NodeList memberNodeList = document.getElementsByTagName("member");
		for (int m = 0; m < memberNodeList.getLength(); m++) {
			Node memberNode = memberNodeList.item(m);
			if (memberNode.getNodeType() == Node.ELEMENT_NODE) {
				Element memberElement = (Element) memberNode;
				NodeList childNodeList = memberElement.getChildNodes();
				for (int c = 0; c < childNodeList.getLength(); c++) {
					Node childNode = childNodeList.item(c);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						Iterator<Entry<String, String>> itr = tagContentMap.entrySet().iterator();
						while (itr.hasNext()) {
							Entry<String, String> entry = itr.next();
							if (childElement.getTagName().trim().equals("name")
									&& childElement.getTextContent().trim().equals(entry.getKey())) {
								for (int d = 0; d < childNodeList.getLength(); d++) {
									Node childNode2 = childNodeList.item(d);
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

	private static final String INPUT_FILE_PATH_NEW = "C:/Users/Sohrab/xml_files/InputFile.xml";
	private static final String OUTPUT_FILE_PATH_NEW = "C:/Users/Sohrab/xml_files/OutputFile.xml";

	public static void processByElementMethods()
			throws TransformerException, ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(INPUT_FILE_PATH_NEW));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("member");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println("myattr1 = " + element.getAttribute("myattr1"));
				System.out.println("myattr2 = " + element.getAttribute("myattr2"));
				System.out.println("myattr3 = " + element.getAttribute("myattr3"));
				System.out.println("TagName: " + element.getTagName());
				System.out.println("member has myattr1 attribute? " + element.hasAttribute("myattr1"));
				System.out.println("member has ourattr5 attribute? " + element.hasAttribute("ourattr5"));
				element.removeAttribute("myattr1");
				element.setAttribute("newAttr", "pqwer@123");
			}
		}
		// write the content
		DOMSource source = new DOMSource(document);

		FileOutputStream output = new FileOutputStream(OUTPUT_FILE_PATH_NEW);
		StreamResult result = new StreamResult(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		System.out.println("-----------Modified File-----------");
	}

	public static void processByNodeMethods()
			throws TransformerException, ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(INPUT_FILE_PATH_NEW));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("member");
		System.out.println("length of nodeList = " + nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap namedNodeMap = node.getAttributes();
				System.out.println("number of nodes in this map = " + namedNodeMap.getLength());
				System.out.println("NamedItem1: " + namedNodeMap.getNamedItem("myattr1"));
				System.out.println("NamedItem2: " + namedNodeMap.getNamedItem("myattr2"));
				System.out.println("NamedItem3: " + namedNodeMap.getNamedItem("myattr3"));
				for (int m = 0; m < namedNodeMap.getLength(); m++) {
					Node nnmItem = namedNodeMap.item(m);
					System.out.println("NodeType : " + nnmItem.getNodeType() + "\tNodeName : " + nnmItem.getNodeName()
							+ "\tContent : " + nnmItem.getTextContent());
					if (nnmItem.getNodeName().equals("myattr1")) {
						nnmItem.setTextContent("Z09");
					}
				}
				namedNodeMap.removeNamedItem("myattr2");
				System.out.println("BaseURI = " + node.getBaseURI());
				System.out.println();
				NodeList childnodeList = node.getChildNodes();
				for (int j = 0; j < childnodeList.getLength(); j++) {
					Node childnode = childnodeList.item(j);
					if (childnode.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println("NodeName: " + childnode.getNodeName() + "\tTextContent: "
								+ childnode.getTextContent() + "\tNodeType: " + childnode.getNodeType());
						if (childnode.getNodeName().equals("id")) {
							childnode.setTextContent("2023");
						}
						if (childnode.getNodeName().equals("name")) {
							childnode.setTextContent("Celina Gomez");
						}
						if (childnode.getNodeName().equals("value")) {
							childnode.setTextContent("Qwer@1234");
						}
						if (childnode.getNodeName().equals("city")) {
							childnode.setTextContent("Washington");
						}
						if (childnode.getNodeName().equals("country")) {
							childnode.setTextContent("USA");
						}
					}
				}
				System.out.println("\n------First Child-----");
				Node firstChildNode = node.getFirstChild();
				System.out.println("Name: " + firstChildNode.getNodeName() + ",\tTextContent: "
						+ firstChildNode.getTextContent().trim() + ",\tNodeType: " + firstChildNode.getNodeType());

				System.out.println("------Last Child-----");
				Node lastChildNode = node.getLastChild();
				System.out.println("Name: " + lastChildNode.getNodeName() + ",\tTextContent: "
						+ lastChildNode.getTextContent().trim() + ",\tNodeType: " + lastChildNode.getNodeType());
			}
		}
		// write the content
		DOMSource source = new DOMSource(document);

		FileOutputStream output = new FileOutputStream(OUTPUT_FILE_PATH_NEW);
		StreamResult result = new StreamResult(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		System.out.println("-----------Modified File-----------");
	}
}