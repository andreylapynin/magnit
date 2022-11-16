package com.magnit.magnittest.service;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Service
public interface EntityService {

    void saveEntityNumberN(long N);

    String findAllEntitiesAndGetXml() throws IOException, XMLStreamException, ParserConfigurationException, SAXException, TransformerException;

    String xsltTransform() throws ParserConfigurationException, TransformerException;

    long getFieldSum();

}