package ma.enset.framework.context;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "constructor-arg")
public class ConstructorArgDefinition {

    private String ref;
    private String value;

    @XmlAttribute(name = "ref")
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }

    @XmlAttribute(name = "value")
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
