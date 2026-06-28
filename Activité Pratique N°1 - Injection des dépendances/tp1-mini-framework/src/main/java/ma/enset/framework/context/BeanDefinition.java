package ma.enset.framework.context;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bean")
public class BeanDefinition {

    private String id;
    private String className;
    private List<PropertyDefinition> properties;
    private List<ConstructorArgDefinition> constructorArgs;

    @XmlAttribute(name = "id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlAttribute(name = "class")
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    @XmlElement(name = "property")
    public List<PropertyDefinition> getProperties() { return properties; }
    public void setProperties(List<PropertyDefinition> properties) { this.properties = properties; }

    @XmlElement(name = "constructor-arg")
    public List<ConstructorArgDefinition> getConstructorArgs() { return constructorArgs; }
    public void setConstructorArgs(List<ConstructorArgDefinition> constructorArgs) { this.constructorArgs = constructorArgs; }
}
