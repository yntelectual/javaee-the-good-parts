## intro

where we are now:  
**JAX-RS 2.0** as of JavaEE7  

Major differences to JAX-RS 1.x:

* client api
* async server support
* filters & interceptors
* bean validation

---

## implementations & alternatives

* Jersey (reference)
* Resteasy (JBoss)
* apache cxf

Alternatives - Play, Ninja, VertX, Restlet,...

---

## what is it for

REST based java web services

ok, what is REST?

* architecture design, simple, scalable, intuitive
* stateless, using HTTP Verbs and domain Nouns - GET orders, POST comment
* simple interfaces, client-server, URI navigation
* HATEOAS
* self descriptive

An API that follows REST principles is called RESTful

---

## http refresher

* text based protocol
* request / response
* request = command + headers + body
* Methods - `GET`, `HEAD`, `POST`, `PUT`, `DELETE`, (`TRACE`, `OPTIONS`, `CONNECT`, `PATCH`)
* URL = `protocol://host:port/path?query`
* Statuses - 1xx INFO, 2xx OK, 3xx redirect, 4xx client error, 5xx server error

----

## http refresher 2

* Common headers : Accept, Authorization, Content-type,
* Common statuses: `200`, `201`, `204`, `401`, `403`, `404`, `500`, `503`
* Testing: curl, browser, postman
* `GET` - fetch resource, not side effect, no body
* `PUT` - store/resplace the entity under this URI
* `DELETE` - delete an existing resource
* `POST` - create a new resource or whatever

---

## terminology

* **Resource class** -JAX-RS annotated Web resource class
* **Root resource** - A resource class with `@Path`, root of the resource class tree
* **Request method designator** - @HttpMethod identifier
* **Resource method** - resource class method with a request method designator
* **Provider** - extend the capabilities of a JAXRS runtime, consumers, writers, ...
* **Filter** - provider used for filtering request and responses
* **Entity Interceptor** - provider intercepting message body readers and writers.

---

## initialization in web server

JAX-RS is not limited to JavaEE, it *should* run on any servlet container, or event JavaSE

Option 1 : `javax.ws.rs.Application` class

```java
@ApplicationPath("/rest")
public MyRestApp extends Application {
	//my REST API will be http://server/mywebcontext/rest
}
```

Option 2 : web.xml - register JAXRS servlet
```xml
<servlet-mapping>
	<servlet-name>javax.ws.rs.core.Application</servlet-name>
	<url-pattern>/*</url-pattern>
</servlet-mapping>
```
---

## resource class

* New instance for every request - `@RequestScoped`, `@Stateless`
* has to have `@Path`

```java
@Path("/orders")
public OrdersEndpoint {

	@GET
	@Produces("application/json")
	public List<Order> getAllOrders() {
		return someOrders;
	}
}
```

Can access
* `@MatrixParam` value of a URI matrix parameter
* `@QueryParam` value of a URI query parameter
* `@PathParam` value of a URI template parameter
* `@CookieParam` value of a cookie
* `@HeaderParam` value of a header
* `@Context` value of some container resource

---

## resource method

* has to have a resource method designator
* has to be public
* params need to be annotated(FormParam, QueryParam...), or they will be taken from request body
* can throw exceptions
* be consistent - @Method @Path ...

---

## URI design

`http://myserver/api/v1/orders/12345`

Final URI = `proto://server:port/WebContext/RESTAppPath/Resource/Method?QueryParams`

Path Params - URI templates
* `@Path("/users/{id:[0-9]+}")`
* `@PathParam("id") Long id` - as a field or param

Try to be RESTful
![URI design][uridesign]

[uridesign]: images/uridesign.png

---

## media types & content negotiation

Client declares in req header `Accept application/json, application/xml`, can have multiple values, order is important  
On server `@Produces @Consumes`  
Content type is considered during URI-resource matching e.g.  
```java
@Produces("application/json")@Path("/book") public Book getBookAsJSON()
@Produces("application/xml")@Path("/book") public Book getBookAsXML()
```
Same URI but resource is matched based on content type.

----

## message body readers & writers

* implementations of `MessageBodyReader` & `MessageBodyWriter`
* can have `@Provider` for auto discovery
* should specify what java objects and what media type they support
* `x/y < x/* < */*` - the more specific definition has precedence
* built in: String, byte[], InputStream,File,...
* Jackson is the default provider for Restaeasy and Jersey

Exception mapping providers

* implements `ExceptionMapper<T>`
* maps exceptions to Responses
* all unhandled exception will be caught by runtime

---

## filters & interceptors

* providers, executed at defined points
* filters - on of `ClientRequestFilter`,`ClientResponseFilter`, `ContainerRequestFilter`, `ContainerResponseFilter`
* filters - implement `filter`, chained, logging, auth, validation
* interceptors - `ReaderInterceptor#aroundReadFrom`, or `WriterInterceptor#aroundWriteTo`, modify data e.g. GZIP

---

## Binding

By default:

* 1 instance of each per JAXRS app
* bound globally
* `@Provider` annotated should be discovered automatically, or you can use manual config
* use @NameBinding if you want specific Binding(similiar to Qualifier)
```java
@NameBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Logged { }
@Provider
@Logged
class LoggingFilter implements ContainerRequestFilter{...}
```

---

## bits & pieces

Async - use EJBs
```java
@GET @Asynchronous
public void longRunningOp(@Suspended AsyncResponse ar) {
	executeLongRunningOp();
	ar.resume("Hello async world!");
}
```
or JAX-RS async
```java
@GET
public void longRunningOp(@Suspended final AsyncResponse ar) {
  executor.submit(
   new Runnable() {
     public void run() {
       executeLongRunningOp();
       ar.resume("Hello async world!");
     }
   });
  }
}
```

----

## bits & pieces cont.

*How to access info about invoked method(e.g. from filter)?*
```java
@Context
private ResourceInfo resInfo;
```

*Does it work with CDI?*  
Yes, if your app runs in CDI env

*Does it work with EJB?*  
Yes, if you app runs in EJB container.

*how to ignore unknown attributes(eg. from js client)*?  
Provider specific, Jackson: `@JsonIgnoreProperties(ignoreUnknown=true)`

---

## client api

Not very nice, but standard. Impl-specific are less verbose.

```java
Client client = ClientBuilder.newClient();
WebTarget target = client.target("http://localhost:9998").path("resource");
Form form = new Form();
form.param("x", "foo");
form.param("y", "bar");
MyResponse bean =
target.request(MediaType.APPLICATION_JSON_TYPE)
    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
        MyResponse.class);
```

---

## best practices & common issues

* Stack overflow when mapping mutually linked Entities
* missing providers in classpath
* unhandled exceptions - responses in HTML
* conflicting paths
* POST/PUT incomplete objects

Make your life easier

* stay consistent
* use GZIP
* get to know some nice APIs and API related projects - twitter, apiary,
 facebook,...

---

## useful links

* long but well explained [REST api design](https://www.parleys.com/play/designing-beautiful-rest-json-api)
* [http://swagger.io/]()
* [jax rs spec](http://download.oracle.com/otn-pub/jcp/jaxrs-2_0-edr3-spec/jaxrs-2_0-edr3-spec.pdf?)
* [jackson](https://github.com/FasterXML/jackson)
* [resteasy docs](http://docs.jboss.org/resteasy/docs/3.0.13.Final/)

---

## hands-on

* Import the `hands-on/SampleProjectJAXRS`
* `mvn clean install`
* add to Wildfly 9
* http://localhost:8080/samplerest
