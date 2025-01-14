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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Hash" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}HexString128"/&gt;
 *         &lt;element name="HashType" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}HashType"/&gt;
 *         &lt;element name="KeyID" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}KeyID" minOccurs="0"/&gt;
 *         &lt;element name="IDCode" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}IdCodeType"/&gt;
 *         &lt;element name="Country" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}CountryType"/&gt;
 *         &lt;element name="Language" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}LanguageType"/&gt;
 *         &lt;element name="ServiceName" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}String20"/&gt;
 *         &lt;element name="MessageToDisplay" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}String0To40" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "hash",
        "hashType",
        "keyID",
        "idCode",
        "country",
        "language",
        "serviceName",
        "messageToDisplay"
})
@XmlRootElement(name = "MobileSignHashByIDCodeRequest")
public class MobileSignHashByIDCodeRequest {

    @XmlElement(name = "Hash", required = true)
    protected String hash;
    @XmlElement(name = "HashType", required = true)
    @XmlSchemaType(name = "string")
    protected HashType hashType;
    @XmlElement(name = "KeyID")
    @XmlSchemaType(name = "string")
    protected KeyID keyID;
    @XmlElement(name = "IDCode", required = true)
    protected String idCode;
    @XmlElement(name = "Country", required = true)
    protected String country;
    @XmlElement(name = "Language", required = true)
    @XmlSchemaType(name = "string")
    protected LanguageType language;
    @XmlElement(name = "ServiceName", required = true)
    protected String serviceName;
    @XmlElement(name = "MessageToDisplay")
    protected String messageToDisplay;

    /**
     * Gets the value of the hash property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHash(String value) {
        this.hash = value;
    }

    /**
     * Gets the value of the hashType property.
     *
     * @return possible object is
     * {@link HashType }
     */
    public HashType getHashType() {
        return hashType;
    }

    /**
     * Sets the value of the hashType property.
     *
     * @param value allowed object is
     *              {@link HashType }
     */
    public void setHashType(HashType value) {
        this.hashType = value;
    }

    /**
     * Gets the value of the keyID property.
     *
     * @return possible object is
     * {@link KeyID }
     */
    public KeyID getKeyID() {
        return keyID;
    }

    /**
     * Sets the value of the keyID property.
     *
     * @param value allowed object is
     *              {@link KeyID }
     */
    public void setKeyID(KeyID value) {
        this.keyID = value;
    }

    /**
     * Gets the value of the idCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIDCode() {
        return idCode;
    }

    /**
     * Sets the value of the idCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIDCode(String value) {
        this.idCode = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the language property.
     *
     * @return possible object is
     * {@link LanguageType }
     */
    public LanguageType getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     *
     * @param value allowed object is
     *              {@link LanguageType }
     */
    public void setLanguage(LanguageType value) {
        this.language = value;
    }

    /**
     * Gets the value of the serviceName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the messageToDisplay property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMessageToDisplay() {
        return messageToDisplay;
    }

    /**
     * Sets the value of the messageToDisplay property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMessageToDisplay(String value) {
        this.messageToDisplay = value;
    }

}
