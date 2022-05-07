# Spring JPA @Query

- Spring JPA supporte :
    - JPQL (Java Persistence Query Language) 
        - langage de requête orienté objet indépendant de la plate-forme
        - défini dans le cadre de la spécification Jakarta Persistence (JPA ; anciennement Java Persistence API) 
        - JPQL est inspiré de SQL, et ses requêtes ressemblent aux requêtes SQL dans leur syntaxe, mais opèrent sur des objets d'entité JPA stockés dans une base de données relationnelle plutôt que directement sur des tables de base de données.
    - et Native Query (requête SQL)

```java
@Query("SELECT t FROM Tutorial t")
List<Tutorial> findAll();

@Query("SELECT t FROM Tutorial t WHERE t.published=true")
List<Tutorial> findByPublished();
```

- Si vous voulez faire des requêtes complexes, jetez un coup d'œil à Native SQL Query:  ```, nativeQuery = true```

```java
@Query(value = "SELECT * FROM tutorials", nativeQuery = true)
List<Tutorial> findAllNative();

@Query(value = "SELECT * FROM tutorials t WHERE t.published=true", nativeQuery = true)
List<Tutorial> findByPublishedNative();
```

- remarques:
    - Spring Data JPA n'ajuste pas la requête au dialecte SQL spécifique de la base de données, assurez-vous donc que la déclaration fournie est supportée par le SGBDR.
    - Spring Data JPA ne supporte pas actuellement le tri dynamique pour les requêtes natives, car il faudrait manipuler la requête déclarée, ce qu'il ne peut pas faire de manière fiable pour le SQL natif.


## where 

```java
@Query("SELECT t FROM Tutorial t")
List<Tutorial> findAll();

@Query("SELECT t FROM Tutorial t WHERE t.published=?1")
List<Tutorial> findByPublished(boolean isPublished);

@Query("SELECT t FROM Tutorial t WHERE t.title LIKE %?1%")
List<Tutorial> findByTitleLike(String title);

@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
List<Tutorial> findByTitleLikeCaseInsensitive(String title);
```
## Greater Than or Equal To

```java
@Query("SELECT t FROM Tutorial t WHERE t.level >= ?1")
List<Tutorial> findByLevelGreaterThanEqual(int level);

@Query("SELECT t FROM Tutorial t WHERE t.createdAt >= ?1")
List<Tutorial> findByDateGreaterThanEqual(Date date);
...
...
tutorials = tutorialRepository.findByDateGreaterThanEqual(new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-11"));
```

## JPA Query Between

```java
@Query("SELECT t FROM Tutorial t WHERE t.level BETWEEN ?1 AND ?2")
List<Tutorial> findByLevelBetween(int start, int end);

@Query("SELECT t FROM Tutorial t WHERE t.createdAt BETWEEN ?1 AND ?2")
List<Tutorial> findByDateBetween(Date start, Date end);
...
...
Date myDate1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-11");
Date myDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-11");
tutorials = tutorialRepository.findByDateBetween(myDate1, myDate2);
```

## JPA Query example with parameters

```java
@Query("SELECT t FROM Tutorial t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end")
List<Tutorial> findByLevelBetween(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isPublished);
...
...
tutorials = tutorialRepository.findByLevelBetween(3, 5, true);
```

## JPA Query Order By Desc/Asc

```java
@Query("SELECT t FROM Tutorial t ORDER BY t.level DESC")
List<Tutorial> findAllOrderByLevelDesc();

@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY t.level ASC")
List<Tutorial> findByTitleOrderByLevelAsc(String title);

@Query("SELECT t FROM Tutorial t WHERE t.published=true ORDER BY t.createdAt DESC")
List<Tutorial> findAllPublishedOrderByCreatedDesc();
...
...
tutorials = tutorialRepository.findByTitleOrderByLevelAsc("at");
```

## JPA Query Sort By

```java
@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
List<Tutorial> findByTitleAndSort(String title, Sort sort);

@Query("SELECT t FROM Tutorial t WHERE t.published=?1")
List<Tutorial> findByPublishedAndSort(boolean isPublished, Sort sort);
...
...
tutorials = tutorialRepository.findByTitleAndSort("at", Sort.by("level").descending());
tutorials = tutorialRepository.findByPublishedAndSort(false, Sort.by("level").descending());
```

## JPA Query Pagination

```java
@Query("SELECT t FROM Tutorial t")
Page<Tutorial> findAllWithPagination(Pageable pageable);
...
...
int page = 0;
int size = 3;
Pageable pageable = PageRequest.of(page, size);
tutorials = tutorialRepository.findAllWithPagination(pageable).getContent();
...
pageable = PageRequest.of(page, size, Sort.by("level").descending());
tutorials = tutorialRepository.findAllWithPagination(pageable).getContent();
```

## JPA Query Update

```java
@Transactional
@Modifying
@Query("UPDATE Tutorial t SET t.published=true WHERE t.id=?1")
int publishTutorial(Long id);
...
...
tutorialRepository.deleteAll();
Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11");
Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-26");
...
tutorialRepository.save(new Tutorial("Spring Data", "Tut#1 Description", 3, false, date1));
tutorialRepository.save(new Tutorial("Java Spring", "Tut#2 Description", 1, false, date2));
...
List<Tutorial> tutorials = new ArrayList<>();
tutorials = tutorialRepository.findAll();
...
tutorialRepository.publishTutorial(tutorials.get(0).getId()); // SET t.published=true
tutorialRepository.publishTutorial(tutorials.get(1).getId()); // SET t.published=true
tutorials = tutorialRepository.findByPublished(true);
```

## Run Spring JPA Query project

```java
package com.bezkoder.spring.query;
// import ...
@SpringBootApplication
public class SpringBootQueryExampleApplication implements CommandLineRunner {
  @Autowired
  TutorialRepository tutorialRepository;
  
  public static void main(String[] args) {
    SpringApplication.run(SpringBootQueryExampleApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // call tutorialRepository methods here
    // ...
    // ...
    tutorials = tutorialRepository.findByTitleOrderByLevelAsc("at");
    show(tutorials);
  }

  private void show(List<Tutorial> tutorials) {
    tutorials.forEach(System.out::println);
  }
}
```

## Spring Data Sort multiple Columns

- /api/tutorials
- /api/tutorials?sort=title,asc
- /api/tutorials?sort=published,desc&sort=title,asc
- /api/tutorials?page=0&size=3&sort=published,desc&sort=title,asc

- Pour nous aider à faire face à cette situation, Spring Data JPA fournit un moyen d'implémenter la pagination avec PagingAndSortingRepository.
- PagingAndSortingRepository étend CrudRepository afin de fournir des méthodes supplémentaires pour récupérer les entités en utilisant l'abstraction de tri. 
- Ainsi, vous pouvez ajouter un paramètre spécial Sort à votre méthode de requête.

```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {
  Iterable<T> findAll(Sort sort);
}
```

```java
// order by 'published' column - ascending
List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by("published"));

// order by 'published' column, descending
List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by("published").descending());

// order by 'published' column - descending, then order by 'title' - ascending
List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by("published").descending().and(Sort.by("title")));
```

- liste des instructions

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

exemples: ```Distinct, And, Or, Is, Equals, Between...```

## Paging and Sorting

## Controller with Sort/Order By Multiple Columns

- ?sort=column1,direction1
- ?sort=column1,direction1&sort=column2,direction2

```java
@RestController
@RequestMapping("/api")
public class TutorialController {

  @Autowired
  TutorialRepository tutorialRepository;
  private Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc")) {
      return Sort.Direction.ASC;
    } else if (direction.equals("desc")) {
      return Sort.Direction.DESC;
    }
    return Sort.Direction.ASC;
  }

  @GetMapping("/sortedtutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(defaultValue = "id,desc") String[] sort) {
    try {
      List<Order> orders = new ArrayList<Order>();
      if (sort[0].contains(",")) {
        // will sort more than 2 columns
        for (String sortOrder : sort) {
          // sortOrder="column, direction"
          String[] _sort = sortOrder.split(",");
          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
        }
      } else {
        // sort=[column, direction]
        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
      }
      List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by(orders));
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
```

# PrePersist 

an entity:

```java
    ...
    ...
    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
    }
```

# DTO Validation

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
```

```java
@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
private String ipAddress;

```

# code http 405

- la requete demande d'acceder à une methode de requete (GET, POST, PUT...) qui n'existe pas sur le serveur back


# eTag

- un tag que l'on met dans le head de la requete et qui represente des données de l'entité
- sur le serveur, le tag est comparé avec les données mis en cache si le tag est identique il renvoit les données du cache (car aucun changement)

```
Cache-Control: public, max-age=60, s-maxage=60
Vary: Accept
ETag: "85143f9cd38933556c486dd386f28a93"
```

# content negociation

- retour des données en json ? xml ? ...

# Spring Security & JWT

https://www.bezkoder.com/spring-boot-jwt-authentication/

```
POST	/api/auth/signup	  signup new account
POST	/api/auth/signin	  login an account
GET	  /api/test/all	      retrieve public content
GET	  /api/test/user	    access User’s content
GET	  /api/test/mod	      access Moderator’s content
GET	  /api/test/admin	    access Admin’s content
```


- ```WebSecurityConfigurerAdapter```:   
  - est le cœur de notre implémentation de la sécurité. Il fournit des configurations HttpSecurity pour configurer les cors, csrf, la gestion des sessions, les règles pour les ressources protégées. 
  - Nous pouvons également étendre et personnaliser la configuration par défaut qui contient les éléments ci-dessous.
    - L'interface ```UserDetailsService```:
      - possède une méthode permettant de charger l'utilisateur par son nom d'utilisateur et de retourner un objet UserDetails que Spring Security peut utiliser pour l'authentification et la validation.
      - UserDetails contient les informations nécessaires (telles que : nom d'utilisateur, mot de passe, autorités) pour construire un objet d'authentification.
    - ```UsernamePasswordAuthenticationToken```:
      - reçoit {nom d'utilisateur, mot de passe} de la demande de connexion, AuthenticationManager l'utilisera pour authentifier un compte de connexion.
    - ```AuthenticationManager```:
      - dispose d'un DaoAuthenticationProvider (avec l'aide de UserDetailsService & PasswordEncoder) pour valider l'objet UsernamePasswordAuthenticationToken. 
      - En cas de succès, AuthenticationManager renvoie un objet d'authentification entièrement rempli (y compris les autorités accordées).
    - ```OncePerRequestFilter```:
      - effectue une seule exécution pour chaque requête à notre API. 
      - Il fournit une méthode doFilterInternal() que nous implémenterons pour analyser et valider JWT, charger les détails de l'utilisateur (en utilisant UserDetailsService), vérifier l'autorisation (en utilisant UsernamePasswordAuthenticationToken).
    - ```AuthenticationEntryPoint```:
      - capturera les erreurs d'authentification.


# SPRING SECURITY

- En termes simples, Spring Security prend en charge la sémantique d'autorisation au niveau de la méthode.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## Enabling Method Security

/alternatives/methodsecurity/config/MethodSecurityConfig.java

```java
package com.acme.apitutorial.alternatives.methodsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

}
```

- ```prePostEnabled``` active les annotations pré/post de Spring Security.
- ```securedEnabled``` détermine si l'annotation @Secured doit être activée.
- ```jsr250Enabled``` nous permet d'utiliser l'annotation @RoleAllowed.


ou

```java
package com.acme.apitutorial.security;
...
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,  // controller -> @Secured("ROLE_VIEWER")
        jsr250Enabled = true,   // controller -> @RoleAllowed(....
        prePostEnabled = true)  // controller -> @PreAuthorize(
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
```

### @Secured

```java
@Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
public String getUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return securityContext.getAuthentication().getName();
}
```

### @RolesAllowed

```java
@RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })
public String getUsername() {
```

### @PreAuthorize, @PostAuthorize 

- @PreAuthorize et @PostAuthorize fournissent un contrôle d'accès basé sur des expressions. 
  - Ainsi, les prédicats peuvent être écrits à l'aide de SpEL (Spring Expression Language).
- @PreAuthorize vérifie l'expression donnée avant d'entrer dans la méthode
- @PostAuthorize la vérifie après l'exécution de la méthode et pourrait modifier le résultat.


```java
@PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
public String getUsernameInUpperCase() {
```

#### @PreAuthorize

```java
@PreAuthorize("#username == authentication.principal.username")
public String getMyRoles(String username) {
```

#### @PostAuthorize

- l'autorisation serait retardée après l'exécution de la méthode cible.
- De plus, cela permet d'accéder au résultat de la méthode :

```java
@PostAuthorize("#username == authentication.principal.username")
public String getMyRoles2(String username) {
```

- Ici, la méthode loadUserDetail ne s'exécutera avec succès que si le nom d'utilisateur du CustomUser retourné est égal au surnom du principal d'authentification actuel.

```java
@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser loadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);  // returnObject
}
```

### @PreFilter, @PostFilter

####  @PreFilter

-  @PreFilter pour filtrer un argument de collection avant d'exécuter la méthode

```java
@PreFilter("filterObject != authentication.principal.username")
public String joinUsernames(List<String> usernames) {     // filterObject <--> usernames
    return usernames.stream().collect(Collectors.joining(";"));
}
```

- Cependant, si la méthode possède plus d'un argument qui est un type de collection, nous devons utiliser la propriété filterTarget pour spécifier quel argument nous voulons filtre:

```java
@PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames")
public String joinUsernamesAndRoles(
  List<String> usernames, List<String> roles) {
     return usernames.stream().collect(Collectors.joining(";")) + ":" + roles.stream().collect(Collectors.joining(";"));
}
```

####  @PostFilter

-  filtrer la collection renvoyée par une méthode

```java
@PostFilter("filterObject != authentication.principal.username")
public List<String> getAllUsernamesExceptCurrent() {
    return userRoleRepository.getAllUsernames();
}
```

### Method Security Meta-Annotation

- Nous nous trouvons généralement dans une situation où nous protégeons différentes méthodes en utilisant la même configuration de sécurité.
- Dans ce cas, nous pouvons définir une méta-annotation de sécurité :


```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('VIEWER')")
public @interface IsViewer {
}
...
...
// use 
@IsViewer
public String getUsername4() {
    //...
}
```

### Security Annotation at the Class Level

- au niveau de la classe

```java
@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SystemService {

    public String getSystemYear(){
        //...
    }
```

###  Multiple Security Annotations on a Method

```java
@PreAuthorize("#username == authentication.principal.username")
@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser securedLoadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);
}
```

# Spring Security Roles and Permissions

https://www.javadevjournal.com/spring-security

- Rôles : Le rôle représente le rôle de haut niveau dans le système (par exemple ADMIN, MANAGER etc.), chaque rôle peut avoir des privilèges de bas niveau.
- Privilèges : Les privilèges définissent l'autorité de bas niveau pour un rôle (par exemple ADMIN peut lire/écrire/supprimer mais MANAGER peut seulement lire/modifier).
- construire un module de rôles et de privilèges autour de groupes d'utilisateurs. 
- Dans le cadre de toute application, les utilisateurs sont placés dans certains groupes,

- exemple :
  - Un utilisateur frontal devrait aller dans le groupe CUSTOMER.
  - Les utilisateurs back-end peuvent aller dans le groupe EMPLOYEE.
  - Nous pouvons créer une autre variation de l'utilisateur backend (par exemple ADMIN, MANAGER etc.)
  - Chaque utilisateur de l'application fera partie d'un certain groupe, et nous utiliserons ces groupes pour gérer les rôles et les permissions. 


Group.java

```java
@Entity
@Table(name = "principle_groups")
public class Group{
    
    //removed getter and setter to save space
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;
    private String name;

    @ManyToMany(mappedBy = "userGroups")
    private Set<UserEntity> users;
}
```

user.java

```java
@Entity
@Table(name = "user")
public class UserEntity {
  ...
  ...
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_groups",
            joinColumns =@JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"
    ))
    private Set<Group> userGroups= new HashSet<>();

    public Set<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<Group> userGroups) {
        this.userGroups = userGroups;
    }  
```

```
id    code          name
1     customer      Customer Group
2     admin         Admin Group
```

DefaultUserService.java

```java
...
        updateCustomerGroup(userEntity);
...
    private void updateCustomerGroup(UserEntity userEntity){
        Group group= groupRepository.findByCode("customer");
        userEntity.addUserGroups(group);
    }
```

```java

```

```java

```

```java

```

```java

```

