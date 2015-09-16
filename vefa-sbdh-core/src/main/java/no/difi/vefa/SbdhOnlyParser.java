package no.difi.vefa;

import org.unece.cefact.namespaces.standardbusinessdocumentheader.ObjectFactory;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocumentHeader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Parses an XML input stream holding a &lt;StandardBusinessDocumentHeader&gt; <em>only</em>.
 * This parser will be faster than the SbdhFastParser when the input only
 * contains a single &lt;StandardBusinessDocumentHeader&gt;
 *
 * @see SbdhFastParser for a more lax parser
 *
 * Created by soc on 16.09.2015.
 */
 class SbdhOnlyParser implements SbdhParser {

    private final JAXBContext jaxbContext;

    public SbdhOnlyParser() {
        try {
            // Saves us grief when moving from Java SE to EE environment.
            jaxbContext = JAXBContext.newInstance("org.unece.cefact.namespaces.standardbusinessdocumentheader", ObjectFactory.class.getClassLoader());
        } catch (JAXBException e) {
            throw new IllegalStateException("Unable to create JAXBContext " + e.getMessage(), e);
        }
    }

    /**
     * Parses the <code>&lt;StandardBusinessDocumentHeader&gt;</code> XML fragment
     * held in the supplied input stream.
     *
     * @param inputStream positioned at the start of the first XML element.
     * @return
     */
    @Override
    public StandardBusinessDocumentHeader parse(InputStream inputStream) {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e1) {
            throw new IllegalStateException("Unable to create JAXB unmarshaller " + e1.getMessage(), e1);
        }

        JAXBElement element = null;
        try {
            element = (JAXBElement) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new IllegalStateException("Unable to parse input data into StandardBusinessDocumentHeader", e);

        }
        StandardBusinessDocumentHeader sbdh = (StandardBusinessDocumentHeader) element.getValue();
        return sbdh;
    }


}
