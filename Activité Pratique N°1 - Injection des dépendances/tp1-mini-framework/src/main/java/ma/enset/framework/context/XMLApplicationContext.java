package ma.enset.framework.context;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conteneur IoC basé sur un fichier XML (similaire à ClassPathXmlApplicationContext de Spring)
 * Utilise JAXB pour le mapping Objet-XML (OXM)
 */
public class XMLApplicationContext implements ApplicationContext {

    private final Map<String, Object> beans = new HashMap<>();

    public XMLApplicationContext(String configFile) throws Exception {
        loadBeans(configFile);
    }

    private void loadBeans(String configFile) throws Exception {
        // Parsing XML avec JAXB (OXM)
        JAXBContext jaxbContext = JAXBContext.newInstance(
                BeansConfig.class, BeanDefinition.class,
                PropertyDefinition.class, ConstructorArgDefinition.class
        );
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream is = getClass().getClassLoader().getResourceAsStream(configFile);
        BeansConfig config = (BeansConfig) unmarshaller.unmarshal(is);

        // Première passe : créer toutes les instances
        for (BeanDefinition beanDef : config.getBeans()) {
            Class<?> clazz = Class.forName(beanDef.getClassName());

            List<ConstructorArgDefinition> ctorArgs = beanDef.getConstructorArgs();
            Object instance;

            if (ctorArgs != null && !ctorArgs.isEmpty()) {
                // Injection via constructeur
                instance = createViaConstructor(clazz, ctorArgs);
            } else {
                instance = clazz.getConstructor().newInstance();
            }
            beans.put(beanDef.getId(), instance);
        }

        // Deuxième passe : injection via setters (property)
        for (BeanDefinition beanDef : config.getBeans()) {
            if (beanDef.getProperties() == null) continue;
            Object instance = beans.get(beanDef.getId());

            for (PropertyDefinition prop : beanDef.getProperties()) {
                injectViaSetter(instance, prop);
            }
        }
    }

    private Object createViaConstructor(Class<?> clazz, List<ConstructorArgDefinition> ctorArgs) throws Exception {
        Object[] args = new Object[ctorArgs.size()];
        Class<?>[] types = new Class<?>[ctorArgs.size()];

        for (int i = 0; i < ctorArgs.size(); i++) {
            ConstructorArgDefinition arg = ctorArgs.get(i);
            if (arg.getRef() != null) {
                Object dep = beans.get(arg.getRef());
                args[i] = dep;
                types[i] = dep.getClass().getInterfaces().length > 0
                        ? dep.getClass().getInterfaces()[0]
                        : dep.getClass();
            }
        }
        return clazz.getConstructor(types).newInstance(args);
    }

    private void injectViaSetter(Object instance, PropertyDefinition prop) throws Exception {
        String setterName = "set" + Character.toUpperCase(prop.getName().charAt(0))
                + prop.getName().substring(1);

        if (prop.getRef() != null) {
            Object dep = beans.get(prop.getRef());
            // Chercher le setter qui accepte l'interface ou la classe
            for (Method method : instance.getClass().getMethods()) {
                if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                    method.invoke(instance, dep);
                    break;
                }
            }
        } else if (prop.getValue() != null) {
            for (Method method : instance.getClass().getMethods()) {
                if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                    method.invoke(instance, prop.getValue());
                    break;
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
