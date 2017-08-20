@XmlSchema(
        namespace = "http://www.gesmes.org/xml/2002-08-01",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "gesmes", namespaceURI = "http://www.gesmes.org/xml/2002-08-01"),
                @XmlNs(prefix = "", namespaceURI = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
        }
)

package com.avrios.sample.exchange.xml;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;

