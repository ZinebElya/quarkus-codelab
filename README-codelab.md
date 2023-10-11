# quarkus-codelab

## Expanding our Quarkus app

Now that we have a basic app going, it's time to implement some more stuff, so we can actually do things with it!

* Start off by creating a new Quarkus application, preferably through IntelliJ or with `quarkus create`. The artifactId can be called `Petinder`. The default app will come with all the dependencies we need, which is `quarkus-resteasy-reactive`. Don't worry about the other dependencies (yet).

So what are we going to create here? For now, we want you to make an application that returns the pets in our list whenever a call for this is made. We are going to do this by implementing a REST api. We'll guide you through this step-by-step. Do realize that REST services is coming up in a few days, so don't worry if some things are still unclear. The main purpose is showing how a Quarkus implementation works.

Let's take some time to think about what this application will need:
- We need to be able to fill up a list with pets
- We want to have an API that we can make calls to (right now we only need one call and this call should return the pets present in our list to us)

Knowing this, we want a Pet class, DTO and Mapper since we're going to use an API, a repository, a service for our business logic and a controller where we will define the logic for our API. This codelab will focus on this controller.

### Making our controller

Our controller is the point where calls are being made to. If you ever need to debug an application that runs an API or simply want to see how the flow of the app works, the controller is often the preferred point to start your journey. We will take a deeper dive into APIs in a few days, but we will build a simple controller now and guide you through this.
We'll just repeat what we said before -> do NOT worry if things are unclear. This is uncharted territory.

> Our controller will enable our application to run an API. You will see that this API will expose certain endpoints that external applications can reach. Upon reaching any of these endpoints, the logic we implemented for it will be executed. This can be the returning of certain information we fetch from our database, a new item we want to save,... Want to see
> an API in action? There are several public APIs available on the internet. A famous one is the `pokeapi`, which basically is a working Pokédex. You can find it at [pokeapi](https://pokeapi.co). Take a look at it, experiment with it. Keep an eye out for the shape of the url you are making requests to. This is the entire address that is made up when you fill in values
> in the textbox on the screen (e.g.: https://pokeapi.co/api/v2/pokemon/2, where pokemon/2 is something you provide yourself). Changing the value in the textbox will return different information. Check out the docs to see all available endpoints for this API.

This should give you a bit more of an idea how we want our API to work. See the class `ExampleResource` under `src/main/java/com.example`? That is controller!
Delete it: we will create a new (much better, or at the very least _just-as-good_) one.

* Create a class called `PetController`. We will have to annotate this class to determine the path where this api will operate. We do this by using the `@Path` annotation. It looks like this:
```java
  @Path("/pets")
  public class PetController {}
```
The value between the brackets`()` of our annotation is the exact address where this api will run. In our case, it will be `http://localhost:8080/pets`. Inside our controller, we will implement different methods for specific behavior that can be reached through this API. These methods can have paths as well. These paths will extend from the base path we just configured. Say we have a method A
that gets annotated with `@Path("/name")`, the exact url for reaching this method through our API will be `http://localhost:8080/pets/name`. The path configured at the top is our base path for this API. Paths configured above methods are deeper levels from this base path.

> Let's take another look at the pokeapi. Can you figure out how that controller would be built? What is the base path and can you find paths that might be configured on a method-level?

Let's create our method that will return Hello Pets.

* Create a method called `helloPets()`. This method needs to return a String "Hello Pets!".
* We need to annotate this method with the right mapping strategy.

> At class-level, we used `Path`. When working with the methods within our controller, we need to provide the "strategy" this method will utilize. Strategy sounds heavy, but it just means making explicit what kind of request this method will make.
> There are five possible kinds of requests:
> `GET` (get information from the backend)
> `POST` (post information to the backend, for example creating a user in a webapp. This is used to create an entire new entry)
> `PUT` (post information to the backend, but if an entry already exists, it will replace this specific entry. If it doesn't exist, a new entry will be made)
> `DELETE` (delete information from the backend)
> `PATCH` (change data in a specific entry that already exists in the backend)
>
> Providing this strategy can be done simply by annotating the method with the REST strategy: `@GET`, `@POST`, `@PUT`, `@DELETE`, `@PATCH`.

* Annotate the `helloPets()` method with the right mapping annotation. Make sure the endpoint for this method is http://localhost:8080/pets/hello.

### Done?

Start up your application. Navigate to [http://localhost:8080/pets/hello](http://localhost:8080/pets/hello): `Hello Pets!` should appear on your screen!


In this codelab, you learned:
* What an API is and how it works
* What the purpose of a controller is
* How we can configure our API and its endpoints
* What the possible HTTP request methods are and how we configure our methods to utilize such a request method

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Expanding our app

Up until now, we have only been implemented some placeholder logic to get acquainted with the controller and APIs. Time to add real functionality to this app!

Let's repeat what we started with in the previous codelab:

> So what are we going to create here? For now, we want you to make an application that returns the pets in our list whenever a call for this is made. We are going to do this by implementing a REST api. We'll guide you through this step-by-step. Do realize that REST services is coming up in a few days, so don't
worry if some things are still unclear. The main purpose is showing how a Spring Boot implementation works.
Let's take some time to think about what this application will need:
> - We need to be able to fill up a list with pets
> - We want to have an API that we can make calls to (right now we only need one call and this call should return the pets present in our list to us)  
    > Knowing this, we want a Pet class, DTO and Mapper since we're going to use an API, a repository, a service for our business logic and a controller where we will define the logic for our API.

### Defining our model

Let's begin by defining what a Pet needs.

* Make a Pet class with the following properties:
    * id
    * name
    * kind (our app will only accept specific kinds of pets, for now it will be cats, dogs and chickens)
    * profileText

* Create a `PetDTO` and `PetMapper` class. The `PetDTO` can have the same properties as our `Pet` class. The `PetMapper` needs two `toDTO` methods. One that transforms a `Pet` to a `PetDTO` and one that that transforms a List of `Pet`s to a List of `PetDTO`s. To do this, you'll have to provide a List of `Pet`s and return a List of `PetDTO`s.
  You can stream over the List of Pets and map each `Pet` to a `PetDTO` using the `toDTO` method. Afterwards, just collect it into a List. If you're stuck, the example below shows you how it's done. Just adapt it to your app.

```java
public List<ObjectDTO> toDTO(List<Object> objectList) {
    return objectList.stream().map(this::toDTO).collect(Collectors.toList());
}

public objectDTO toDTO(Object object){
    return new ObjectDTO(object.getProperty1(), object.getProperty2(), ...);
}
```

We are now able to create a Pet with the necessary properties. On top of that, we added a DTO and mapper for this, which enables us to use the DTO/Mappers design when responding calls made by our API. Unsure why this is used again? Ask us or take a look at the presentation given during the design patterns chapter.

### Making our repository

* Create a class called `PetRepository`. In this class, we will store the methods that will access our "database" and return the elements we want to see returned.
* For now, we only need one method. Create the `getAllPets()` method and make sure it returns a List of Pets. In the future, we will learn how to query our database and get our info that way, but for now we will hardcode this.

`List.of(new Pet 1, new Pet 2, ...)` seems like a good way to quickly get a list going.

### Making our service

* Create a class called `PetService`. In here, we will store all the business logic our app needs. For now, we only need one method and that is the `getAllPets()` method. You might think this is strange since we just created a similar method in our repository. We're going to implement it in a different way here.
  Make sure this method returns a list of PetDTOs. To get this list, return the result of the right `toDTO` method in our `PetMapper`. Make sure you pass on the list you just created in the `PetRepository` to this `toDTO` method.

### Adjusting our controller

Our controller only has one method for now. A placeholder method returning "Hello Pets!". Let's add another method!

* Add a method called `getAllPets()`. This method will return a List of PetDTOs. Inside the method, we will return the result of the `getAllPets()` method in our PetService.
* Make sure to use the correct mapping annotation for this method. We want to make this call to get all our pets whenever we go to `/pets`, no path value is needed here. However, we do want to get this information in the JSON format, so you can add `@Produces(MediaType.APPLICATION_JSON)` on top of your method (and add the necessary imports).

> `@Produces(MediaType.APPLICATION_JSON)` is used to specify the type of data this call will produce. In our case, we will receive our List of PetDTOs in JSON format. This is important when the returned data is going to be used by another application which expects JSON. Java will convert our object to JSON for us.

* Fire up your app and try to go to the [http://localhost:8080/pets](http://localhost:8080/pets) endpoint. If all goes well, you'll see your beautiful _Petinder_ backend explode. RIP billion-dollar idea 😭

No worries, we'll make your app rise from the dead in the next codelab!

In this codelab, you learned:
* Use DTO/Mapper as a pattern
* The basic structure of an application. Model, repository, service, controller and their responsibilities.
* Use `@Produces(MediaType.APPLICATION_JSON)` to configure the way information is returned or handled when using your controller (there are more options than APPLICATION_JSON, it depends on what the app making the request needs)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Resurrecting Petinder

Even though the compiler didn't warn us of anything and our app seems to boot up just fine, stuff goes horribly wrong when we try to make a call to our endpoint. This happens for a number of reasons. Let's take a look at what we (likely, some might have used dependency injection already) did in our `PetService`.

```java 
public class PetService {
  private PetMapper petMapper = new PetMapper();
  private PetRepository petRepository = new PetRepository();

  public List<PetDTO> getAllPets() {
    return petMapper.toDTO(petRepository.getAllPets());
  }
}
```

What is the issue? The problem is that the code above creates new instances of `PetMapper` and `PetRepository`, but these are not the instances managed by Quarkus. For that reason, some of the configuration (represented by annotations) of Quarkus will not be present! In the case of `PetMapper` this isn't too much of a problem, because `PetMapper` contains pure Java code. `PetRepository` however contains some configuration and needs to be configured to communicate correctly with non-Java code (i.e. the database), and so we really need the instance of `PetRepository` created and managed by Quarkus!

To get the instances of our controllers, services and repositories that are managed by Quarkus, we're going to tackle this in a different way. Time for dependency injection!

### Dependency Injection
A "simple" definition of dependency injection is the following:

> An injection is the passing of a dependency (a service) to a dependent object (a client). Passing the service to the client, rather than allowing a client to build or find the service (constructor dependency injection instead of initializing the dependency in the client by using the new keyword), is the fundamental requirement of the pattern.

To put it simply, this means that whenever a class is dependent on another class, dependency injection can be used to establish this dependency.

> There are three ways to do this:
> * Constructor injection: In this type of injection, the injector supplies dependency through the client class constructor.
> * Setter Injection / Property Injection: In this type of injection, the injector method injects the dependency to the setter method exposed by the client.
> * Interface Injection: In this type of injection, the injector uses an Interface to provide dependency to the client class. The clients must implement an interface that will expose a setter method which accepts the dependency.

We will focus on dependency injection through the constructor as it has become the recommended default. Let's rewrite our `PetService` class to use dependency injection.

* Instead of manually instantiating the classes `PetService` is dependent on, let's use dependency injection to achieve this. Do this by passing the dependencies to the constructor `PetService` and assigning them to your dependency fields. Ignore the compiler for now, it will complain but we will fix this soon.
* Do the same thing for any other classes that have dependencies.

### Beans

Once you try to inject these dependencies through the constructor, you'll notice the compiler starts complaining. It will tell you it can't find any bean of this type. That's because Quarkus has no clue about these classes.

> Quarkus is responsible for managing the objects of an application. It uses dependency injection to achieve "inversion of control". Every class that you will be using through dependency injection, will be managed by Quarkus. We have to tell Quarkus which classes are going to be part of this and as a result we will be able to inject the classes when needed, as long as they were correctly marked as such.
>
> It's basically us saying "Hey Quarkus, this PetMapper and PetService will be dependencies for certain classes in my codebase. Manage them please, so I can use them whenever needed.".

We refer to all the marked classes that behave in that way as `beans`.

Now that you know this, it makes sense to tell you that the classes we are `injecting` should always be `beans`. We can do this by annotating them. A basic annotation for this is `@ApplicationScoped`. Annotating our `PetMapper` with it, will make it part of the bean archive and ready to be injected. Next to `@ApplicationScoped`, the Jakarta EE annotation `@Path` also create beans, so Controllers don't need to be additionally annotated with `@ApplicationScoped`.

* Annotate all classes that need to be part of the ApplicationContext with `@ApplicationScoped`. We already annotated our `PetController` with `@Path`, so that class is already good.
* After doing this, try running your app again. Is it working now?

What you learned in this codelab:
* What dependency injection is, what the different types are and how to use constructor injection
* What CDI is and how dependency injection is an implementation of this
* What the ApplicationContext is and what its responsibility is
* What Beans are and why some classes are Beans and others aren't
* The different annotations used to define classes as Beans

----------------------------------------------------------------------------------------------------------------------------------------------

# Quarkus deliverable

Now that we have seen how a Quarkus app is written, let's have a first taste of what the end-product or "deliverable" of it is.
The deliverable of Java program is a JAR. Quarkus' JAR files come with an embedded Vert.x/Netty server.
This JAR can easily be spun up on a server of our choosing.

## How to

1. Run `quarkus build` or alternatively `mvn quarkus:build`
  - In your target folder, a few directories are generated. The one of interest is `target/quarkus-app`
2. From withing your target folder, execute command `java -jar quarkus-app.jar`
  - Make sure you killed any (stand-alone) Tomcat process (e.g. because you still have IntelliJ running your application). When the port 8080 is occupied, your embedded Vert.x/Netty won't be able to start. (Only one process per port is allowed...)
  - Navigate to http://localhost:8080
    - The message "Hello World!" should be shown

## JAR vs Fat JARs

We've generated a JAR, but it's not a so-called "Fat JAR". Fat JARs are executable JARs that contains all of our dependencies and which we can always run with `java -jar <JAR-NAME>.jar`. Here with Quarkus the produced JAR is definitely not a Fat JAR: it has dependencies outside itself, among which the `/lib` folder.

* Try to move the `quarkus-app.jar` file to another directory and execute the `java -jar` CLI command. The application will not start up successfully.
* Put the `quarkus-app.jar` back in the `target/quarkus-app` directory or re-execute `quarkus build`. This time move the whole `quarkus-app` directory to somewhere else and try again to execute the `java -jar` CLI command in the directory. The application will start up this time, because the dependencies are present.

So we've seen here how a Quarkus app can be compiled and moved in order to potentially deploy it on a server of our choice.
This is a pretty naïve and inconvenient way of distributing software however: we will see in a later chapter how Quarkus apps are meant to be built and distributed.

## Conclusion

With only a few lines of code, we have a fully operational Java web application.
- Building an executable JAR with Quarkus is easy.
- The output of `quarkus build` is a possible approach to create distributable software, but it is not the recommended nor most convenient way to do it.
