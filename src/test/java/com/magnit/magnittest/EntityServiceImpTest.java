package com.magnit.magnittest;

import com.magnit.magnittest.entity.Entry;
import com.magnit.magnittest.repository.StorageService;
import com.magnit.magnittest.service.EntityServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EntityServiceImpTest {
    @InjectMocks
    private EntityServiceImp entityServiceImp;
    @Mock
    private StorageService storageService;

    @Test
    void findAllEntitiesAndGetXmlTest() throws ParserConfigurationException, TransformerException {
        when(storageService.findAll()).thenReturn(getEntriesTest());
        assertEquals(createXml(), entityServiceImp.findAllEntitiesAndGetXml());
    }

    @Test
    void xsltTransformTest() throws ParserConfigurationException, TransformerException {
        when(storageService.findAll()).thenReturn(getEntriesTest());
        assertEquals(createXmlTransform(), entityServiceImp.xsltTransform());
    }

    private String createXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                "<entries>\r\n" +
                "    <entry>\r\n" +
                "        <field>1</field>\r\n" +
                "    </entry>\r\n" +
                "    <entry>\r\n" +
                "        <field>2</field>\r\n" +
                "    </entry>\r\n" +
                "</entries>\r\n";
    }

    private String createXmlTransform() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<entries>\r\n" +
                "        \r\n" +
                "    <entry field=\"1\"/>\r\n" +
                "        \r\n" +
                "    <entry field=\"2\"/>\r\n" +
                "    \r\n" +
                "</entries>\r\n";
    }

    private List<Entry> getEntriesTest() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        return entries;
    }

}