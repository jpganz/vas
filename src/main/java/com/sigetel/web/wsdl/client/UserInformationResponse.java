package com.sigetel.web.wsdl.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userInformationResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="userInformationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bussinessPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groups" type="{http://authservices.ws.gt.tigo.com/}userInformationGroups" minOccurs="0"/>
 *         &lt;element name="last" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInformationResponse", propOrder = {
    "alias",
    "bussinessPhone",
    "department",
    "email",
    "errorMessage",
    "firt",
    "groups",
    "last",
    "mobilePhone",
    "title"
})
public class UserInformationResponse {

    protected String alias;
    protected String bussinessPhone;
    protected String department;
    protected String email;
    protected String errorMessage;
    protected String firt;
    protected UserInformationGroups groups;
    protected String last;
    protected String mobilePhone;
    protected String title;

    /**
     * Gets the value of the alias property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the bussinessPhone property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBussinessPhone() {
        return bussinessPhone;
    }

    /**
     * Sets the value of the bussinessPhone property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBussinessPhone(String value) {
        this.bussinessPhone = value;
    }

    /**
     * Gets the value of the department property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the errorMessage property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the firt property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFirt() {
        return firt;
    }

    /**
     * Sets the value of the firt property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFirt(String value) {
        this.firt = value;
    }

    /**
     * Gets the value of the groups property.
     *
     * @return
     *     possible object is
     *     {@link UserInformationGroups }
     *
     */
    public UserInformationGroups getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     *
     * @param value
     *     allowed object is
     *     {@link UserInformationGroups }
     *
     */
    public void setGroups(UserInformationGroups value) {
        this.groups = value;
    }

    /**
     * Gets the value of the last property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLast() {
        return last;
    }

    /**
     * Sets the value of the last property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLast(String value) {
        this.last = value;
    }

    /**
     * Gets the value of the mobilePhone property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the value of the mobilePhone property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMobilePhone(String value) {
        this.mobilePhone = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
