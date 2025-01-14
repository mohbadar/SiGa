//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.26 at 05:42:17 PM EET 
//


package ee.openeid.siga.mobileid.model.mid;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessStatusType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProcessStatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EXPIRED_TRANSACTION"/&gt;
 *     &lt;enumeration value="USER_AUTHENTICATED"/&gt;
 *     &lt;enumeration value="USER_CANCEL"/&gt;
 *     &lt;enumeration value="SIGNATURE"/&gt;
 *     &lt;enumeration value="OUTSTANDING_TRANSACTION"/&gt;
 *     &lt;enumeration value="MID_NOT_READY"/&gt;
 *     &lt;enumeration value="PHONE_ABSENT"/&gt;
 *     &lt;enumeration value="SENDING_ERROR"/&gt;
 *     &lt;enumeration value="SIM_ERROR"/&gt;
 *     &lt;enumeration value="OCSP_UNAUTHORIZED"/&gt;
 *     &lt;enumeration value="INTERNAL_ERROR"/&gt;
 *     &lt;enumeration value="REVOKED_CERTIFICATE"/&gt;
 *     &lt;enumeration value="NOT_VALID"/&gt;
 *     &lt;enumeration value="PHONE_TIMEOUT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "ProcessStatusType")
@XmlEnum
public enum ProcessStatusType {

    EXPIRED_TRANSACTION,
    USER_AUTHENTICATED,
    USER_CANCEL,
    SIGNATURE,
    OUTSTANDING_TRANSACTION,
    MID_NOT_READY,
    PHONE_ABSENT,
    SENDING_ERROR,
    SIM_ERROR,
    OCSP_UNAUTHORIZED,
    INTERNAL_ERROR,
    REVOKED_CERTIFICATE,
    NOT_VALID,
    PHONE_TIMEOUT;

    public static ProcessStatusType fromValue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }

}
