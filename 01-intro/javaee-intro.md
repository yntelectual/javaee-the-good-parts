# intro

The primary goal of this course is to showcase the features of modern java ee
and provide a general, **very opinionated** guide to sustainable development for those who would like to build modern web based apps in java without jumping on any of the hipster wagons.

Apart from java ee we will also talk about tools and server setup that will make you productive and as future-proof as possible.

---

## scope

This course is definitely not covering the whole java ee spec, neither is it
going to be very exhaustive guide for the topics it touches. As I said, this guide *is opinionated*. The components we will cover are:

- JPA
- EJB
- CDI/Interceptors
- JAXRS
- WebSockets
- Servlet

The selection has been tailored to fit the most common requirements for web based apps. Notable omissions are JSF related technologies and more complex server side features like JCA, JTA or new Batch and JSON specs.

---

## prerequisites

From now on it is assumed that you have at least googled what java ee is, you are familiar with java as a language, feel comfortable working with some IDE and ever heard of maven.

In order to run the examples you will need to download and install the following:

- JDK v1.8x
- Maven v3.x
- Wildfly v9.x
- any IDE you feel most familiar with

---

## history

1999 J2EE 1.2 was released  
2005 Java EE 5 comes with changed name  
2009 Java EE 6 goes live - **EE stops being terrible**
2013 Java EE 7

Learn the 7 and you are good until at least 2017(scheduled release of JavaEE 8) :)

---

## parts

JavaEE 7 = 31 JSR specs created by [Java Community Process](jcp) approved by [Executive Comittee](jcpec)

![Parts of java ee spec][javaee7parts]

[javaee7parts]: http://blog.arungupta.me/wp-content/uploads/2013/10/javaee7-pancake.png
[jcpec]: https://www.jcp.org/en/participation/committee
[jcp]: https://www.jcp.org/en/procedures/overview

---

## alternatives

The obvious:
- [Spring Framework][spring]

Web frameworks:

- [Play Framework][play]
- [Spark][spark]
- [Drop Wizard][dropwizard]
- [Ninja Framework][ninja]
- Non-java frameworks for JVM: grails, scalatra, RoR(torguebox)

[spring]: http://projects.spring.io/spring-framework/
[play]: https://playframework.committee
[spark]: https://sparkjava.com
[dropwizard]: http://www.dropwizard.io
[ninja]: http://www.ninjaframework.org/

---

## application servers

Java EE certified server has to provide implementations for whole Java EE spec.

5 certified java EE 7 servers [wildfly, glassfish, Websphere, Cosminexus, TMax] as of 2015   
11 for java EE 6 [JBoss EAP, SAP netweaver, Fujitsu, Hitachi,...]

AS provides additional features - management, deployment, orchestration, etc.

---

## simplest setup

```
mvn archetype:generate com.binarytale.maven:javaee7-simple
```

... all you need is love(or 1 dependency)....

```xml
<dependencies>
  <dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>7.0</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

## what we get

You can now use all JavaEE compoenents.
![Sample project][projectstructure]

[projectstructure]: images/projectstructure.png

A note on boms/wildfly:  
If you know what your server is, it makes sense to use the BOMs provided by your server.
