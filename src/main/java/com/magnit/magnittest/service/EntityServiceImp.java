package com.magnit.magnittest.service;

import com.magnit.magnittest.entity.Entry;
import com.magnit.magnittest.repository.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class EntityServiceImp implements EntityService {
    private final StorageService storageService;

    @Override
    public void saveEntityNumberN(long N) {
        LongStream.range(1, N + 1).forEach(i -> {
            Entry entry = new Entry();
            entry.setField(i);
            try {
                storageService.saveEntity(entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String findAllEntitiesAndGetXml() throws ParserConfigurationException, TransformerException {
        List<Entry> entities = storageService.findAll();
        Document document = getDocument();
        Element root = document.createElement("entries");
        document.appendChild(root);
        fillRoot(entities, document, root);
        StringWriter writer = getStringWriter(document);
        return writer.toString();
    }

    private Document getDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    private void fillRoot(List<Entry> entities, Document document, Element root) {
        entities.forEach(entry -> {
            Element rootChild = document.createElement("entry");
            Element entryChild = document.createElement("field");
            root.appendChild(rootChild);
            rootChild.appendChild(entryChild);
            entryChild.appendChild(document.createTextNode(String.valueOf(entry.getField())));
        });
    }

    private StringWriter getStringWriter(Document document) throws TransformerException {
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer;
    }

    @Override
    public String xsltTransform() throws ParserConfigurationException, TransformerException {
        String xmlString = findAllEntitiesAndGetXml();
        try {
            Templates template = TransformerFactory.newInstance().newTemplates(new StreamSource
                    (new FileInputStream("xsltFormatter.xslt")));
            Transformer formatter = template.newTransformer();
            formatter.setOutputProperty(OutputKeys.INDENT, "yes");
            InputStream stream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            Source source = new StreamSource(stream);
            StreamResult output = new StreamResult(new ByteArrayOutputStream());
            formatter.transform(source, output);
            return output.getOutputStream().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getFieldSum() {
        List<Entry> entities = storageService.findAll();
        return entities.stream().mapToLong(Entry::getField).sum();
    }

}