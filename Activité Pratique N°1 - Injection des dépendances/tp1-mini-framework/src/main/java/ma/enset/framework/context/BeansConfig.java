package ma.enset.framework.context;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "beans")
public class BeansConfig {

    private List<BeanDefinition> beans;

    @XmlElement(name = "bean")
    public List<BeanDefinition> getBeans() { return beans; }
    public void setBeans(List<BeanDefinition> beans) { this.beans = beans; }
}
