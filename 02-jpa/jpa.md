## intro

where we are now: **JPA 2.1** as of JavaEE7  

* JPA is specification implemented by **Hibernate, EclipseLink, DataNucleus**, etc.
* Not bound to app server, i.e. you can use it in standalone app
* ORM scope

---

## alternatives

* noSQL
* mybatis
* jOOQ
* Ebean

---

## mapping

* Annotations or XML  
* Always use annotations and know the most commonly used by heart: ```@Entity @Table @Column @XtoOne @XtoMany @Enumerated @Temporal @Id @ElementCollection```   
* Always use field access

other recommendations:

* always use explicit table names ```@Table(name="explicit")```
* for columns only be explicit if identifier is too long or a SQL reserved word  
  (key, order, input, etc. depending on the DB)
* ```@JoinColumn``` prefer explicit naming
* prefer explicit ```fetchType``` specification
* if you use implicit values, know them

----

## basic attributes

All not @Transient fields of these types:
* **String** (char, char[])	-> VARCHAR (CHAR, VARCHAR2, CLOB, TEXT)
* **Number** (BigDecimal, BigInteger, Integer, ...) ->	NUMERIC (NUMBER,INT,LONG,...)
* **int, long, float, double, short, byte**	-> NUMERIC (NUMBER, INT, LONG,...)
* **byte[]** ->	VARBINARY (BINARY, BLOB)
* **boolean (Boolean)**	-> BOOLEAN (BIT, SMALLINT, INT, NUMBER)
* **java.util.Date** ->	TIMESTAMP (DATE, DATETIME)
* **java.sql.Date**	-> DATE (TIMESTAMP, DATETIME)
* **java.sql.Time**	-> TIME (TIMESTAMP, DATETIME)
* **java.sql.Timestamp** ->	TIMESTAMP (DATETIME, DATE)
* **java.util.Calendar** ->	TIMESTAMP (DATETIME, DATE)
* **java.lang.Enum** ->	NUMERIC (VARCHAR, CHAR)

Everything else needs to be @Entity, @Embedable or collection

----

## customizations

```java
@Column(name=, length=,nullable=,precision=,scale=,unique=,updateable=)
```
```java
@Enumerated(STRING|ORDINAL)
```
```java
@Temporal(DATE|TIMESTAMP|TIME)
```

Since JPA 2.1 >
```java
@converter
```

Note: converters similiar to JSF converters
Always use enumerated String,
use @UniqueConstraint, not the @column annotation

---

## entities & embeddables

Entity is a domain object, not necessarily equivalent to DB table

* must have a persistent identity(id column in DB)
* property or field based mapping of attributes
* ```@Entity``` annotation must be present(or xml, but don't do that)
* can contain ```@Embeddable``` objects or ```@ElementCollection```  

Embeddable exists only as a part of some entity

* ```@Embeddable``` must be present
* can contain other Embeddables, relationships
* can be used in ```@ElementCollection``` - think map<,Embedable>
* can not be directly stored or queried

Note: when to use what,.. example with 18n, reuse, etc

---

## entity event callbacks

*	```@PostLoad```
*	```@PrePersist```
*	```@PostPersist```
*	```@PreUpdate```
*	```@PostUpdate```
*	```@PreRemove```
*	```@PostRemove```

These can be either on entity itself, or in separate class ```@EntityListeners({MyCoolListner.class})```

CDI in entity listeners only works in JavaEE7. If you are in EJB container,
you can still inject EJB specific context.

Use cases:
-	extra validation
- fill computed properties
- tracing

Note: use cases: computed properties - counts, etc,

---

## relationships

types

* bidirectional (has owning and inverse side)
* unidirectional (only owning side)

relationship cardinality

* ```@OneToOne```
* ```@OneToMany```
* ```@ManyToOne```
* ```@ManyToMany```

relationship mapping with ```@JoinColumn``` or ```@JoinTable```

----

## owner of relationship

* responsible for maintaining the relationship(FKs) in the DB
* bidirectional relationships are persisted based on references held by the
	owning side
* inverse side is indicated by ```mappedBy``` attribute(which points to the
	property of the Owner)
*	bidirectional ManyToOne/OneToMany - *many* side is always the owner

Common problems - relationship not stored in DB, references incorrect after loading, etc.

----

## lazy/eager loading

* design which relationship are loaded by default
* involves magic (proxies, bytecode manipulation)
* can be vendor specific - e.g. ```@OneToOne(fetch=LAZY)```
* can be overridden in JPQL - ```JOIN FETCH```
* eager != join (N+1 issue)
* Hibernate wont allow you to load multiple bags in single select
* Hibernate specific ```@FetchType``` - join, select, subselect, batch

---

## querying

* jpql(hql) - object oriented query language, not type safe
* criteria api - type safe, verbose
* native queries w/, w/o mapping to POJO/entity
* ordering with @OrderBy or jqpl ```order by``` - does not ensure in-memory sorting

Note: problems if the data are sorted when loaded from DB but not when you add new items.

---

## cascading

* cascades the operation from parent to child
* usually used for dependent relationship Order>OrderItem
* CascadeType
	* PERSIST
	* MERGE
	* REMOVE
	* REFRESH
	* ALL
* Orphan removal for collections

Note: Merge cascade will persist unmanaged entities
careful with cascade delete

---

## extra features

* validation
* envers
* indexes

---

## hands-on

Tasks for today:

1. import arquillian enabled project
2. runt the tests
3. play along

---
