# TP1 — Injection des Dépendances

## C'est quoi l'injection des dépendances ?

Imaginez que vous avez une voiture. La voiture a besoin d'un moteur pour fonctionner.

**Mauvaise façon (couplage fort) :**
La voiture fabrique elle-même son moteur. Si on veut changer de moteur, on doit toucher au code de la voiture.

**Bonne façon (couplage faible) :**
On donne le moteur à la voiture de l'extérieur. La voiture ne sait même pas quel type de moteur elle utilise, elle sait juste qu'elle en a un.

C'est exactement ce qu'on fait dans ce TP avec les interfaces `IDao` et `IMetier`.

---

## Ce qu'on a construit

### Partie 1 — Les 4 façons d'injecter des dépendances

On a créé deux interfaces :
- **IDao** : représente l'accès aux données (peut être une BDD, un capteur, un web service...)
- **IMetier** : représente la logique métier (fait un calcul en utilisant IDao)

`MetierImpl` ne connaît que `IDao` (l'interface), pas `DaoImpl` (la classe concrète). C'est le couplage faible.

#### 1. Instanciation statique (`Pres1.java`)
On crée les objets à la main dans le code. Simple, mais pas flexible.
```java
DaoImpl dao = new DaoImpl();
MetierImpl metier = new MetierImpl();
metier.setDao(dao);
```

#### 2. Instanciation dynamique (`Pres2.java`)
On lit le nom de la classe depuis un fichier `config.txt`. On peut changer l'implémentation sans toucher au code Java, juste en modifiant le fichier texte.
```
# config.txt
ma.enset.dao.DaoImpl
ma.enset.metier.MetierImpl
```

#### 3. Spring version XML (`PresSpringXML.java`)
Spring lit le fichier `applicationContext.xml` et crée les objets lui-même. On n'instancie plus rien à la main.
```xml
<bean id="dao" class="ma.enset.dao.DaoImpl"/>
<bean id="metier" class="ma.enset.metier.MetierImpl">
    <property name="dao" ref="dao"/>
</bean>
```

#### 4. Spring version Annotations (`PresSpringAnnotations.java`)
Plus besoin de XML. On met juste `@Component` sur les classes et `@Autowired` sur les dépendances. Spring trouve tout seul.
```java
@Component("dao")
public class DaoImpl implements IDao { ... }

@Component("metier")
public class MetierImpl implements IMetier {
    @Autowired
    public MetierImpl(IDao dao) { ... }
}
```

---

### Partie 2 — Mini Framework IoC (fait maison)

On a reproduit le comportement de Spring nous-mêmes pour comprendre comment ça marche en interne.

Notre mini framework sait :
- Lire un fichier XML et créer les beans définis dedans (grâce à JAXB)
- Scanner un package et trouver automatiquement les classes annotées avec `@Component`
- Injecter les dépendances de **3 façons** :
  - Via le **constructeur**
  - Via le **setter**
  - Via l'**attribut directement** (field injection)

#### Comment utiliser notre mini framework ?

**Version XML :**
```java
ApplicationContext context = new XMLApplicationContext("beans.xml");
IMetier metier = (IMetier) context.getBean("metier");
System.out.println(metier.calcul());
```

**Version Annotations :**
```java
ApplicationContext context = new AnnotationApplicationContext("ma.enset.app");
IMetier metier = (IMetier) context.getBean("metier");
System.out.println(metier.calcul());
```

---

## Structure du projet

```
tp1-injection/               ← Partie 1 (Spring)
├── dao/
│   ├── IDao.java            ← Interface : getData()
│   ├── DaoImpl.java         ← Implémentation base de données
│   └── DaoImplV2.java       ← Implémentation capteur
├── metier/
│   ├── IMetier.java         ← Interface : calcul()
│   └── MetierImpl.java      ← Logique métier (couplage faible)
├── presentation/
│   ├── Pres1.java           ← Injection statique
│   ├── Pres2.java           ← Injection dynamique
│   ├── PresSpringXML.java   ← Spring XML
│   └── PresSpringAnnotations.java ← Spring Annotations
└── resources/
    └── applicationContext.xml

tp1-mini-framework/          ← Partie 2 (notre propre framework)
├── framework/
│   ├── annotations/
│   │   ├── @Component       ← Marquer un bean
│   │   └── @Autowired       ← Marquer une dépendance
│   └── context/
│       ├── XMLApplicationContext       ← Lit le XML (JAXB)
│       └── AnnotationApplicationContext ← Scan les annotations
└── app/ (application de test)
```

---

## Comment lancer

**Partie 1 — Injection statique :**
```
Exécuter la classe : ma.enset.presentation.Pres1
```

**Partie 1 — Spring XML :**
```
Exécuter la classe : ma.enset.presentation.PresSpringXML
```

**Partie 1 — Spring Annotations :**
```
Exécuter la classe : ma.enset.presentation.PresSpringAnnotations
```

**Partie 2 — Mini Framework XML :**
```
Exécuter la classe : ma.enset.app.presentation.PresXML
```

**Partie 2 — Mini Framework Annotations :**
```
Exécuter la classe : ma.enset.app.presentation.PresAnnotations
```

---

## Ce qu'on a appris

- Le **couplage faible** : dépendre des interfaces, pas des implémentations
- L'**inversion de contrôle (IoC)** : c'est le framework qui crée les objets, pas nous
- La **réflexion Java** : créer des objets à partir de leur nom (String) en utilisant `Class.forName()`
- Comment **Spring** fonctionne en interne
