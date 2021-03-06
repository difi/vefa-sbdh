package no.difi.vefa.sbdh;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocumentHeader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StaxExtractorTest {

    @Test
    public void simple() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StandardBusinessDocumentHeader header = StaxExtractor.extract(getClass().getResourceAsStream("/peppol-bis-invoice-sbdh.xml"), byteArrayOutputStream);

        Assert.assertNotNull(header);

        JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<InvoiceType> o = unmarshaller.unmarshal(
                new StreamSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())),
                InvoiceType.class
        );
        Assert.assertEquals(o.getValue().getCustomizationID().getValue(), "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0");
    }
}
