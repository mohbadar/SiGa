//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.26 at 05:42:17 PM EET 
//


package ee.openeid.siga.mobileid.model.mid;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}AbstractResponseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Challenge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "challenge"
})
@XmlRootElement(name = "MobileAuthenticateResponse")
public class MobileAuthenticateResponse
        extends AbstractResponseType {

    @XmlElement(name = "Challenge")
    protected String challenge;

    /**
     * Gets the value of the challenge property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * Sets the value of the challenge property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setChallenge(String value) {
        this.challenge = value;
    }

}
