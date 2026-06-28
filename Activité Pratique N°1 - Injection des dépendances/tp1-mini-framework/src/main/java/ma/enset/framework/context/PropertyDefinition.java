package ma.enset.framework.context;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class PropertyDefinition {

    private String name;
    private String ref;
    private String value;

    @XmlAttribute(name = "name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlAttribute(name = "ref")
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }

    @XmlAttribute(name = "value")
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
