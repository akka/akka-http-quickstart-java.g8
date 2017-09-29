JSON marshalling
----------------

When exercising the app, you interacted with JSON payloads. How does the example app convert data between JSON format
and data that can be used by Scala classes? Fur this purposes akka.http has `akka.http.javadsl.marshallers.jackson.Jackson`

We're using the [Jackson](https://github.com/FasterXML/jackson) library here, along with akka-http wrapper that provides
marshallers  `Jackson.marshaller()`. It does ot provide type safety like `Spray` on compile time
but it still allows to to marshall data to JSON and back.

In majority of cases you'll just need to add marshaller to `complete` call like following
`complete(StatusCodes.OK, performed, Jackson.marshaller()` to be able to product JSON response.
To fill object with JSON data we you'll need to create unmarshaller with class as attribute 
`Jackson.unmarshaller(User.class)`

Data transfer classes that we are using to interact with actors does not have any links to Jackson itself:

@@snip [UserRegistryActor.java]($g8src$/java/com/lightbend/akka/http/sample/UserRegistryActor.java) { #user-case-classes }

@@@ note
  
    While we used Jackson JSON in this example, various other libraries can be used
    Each library comes with different trade-offs in performance and user-friendlieness. 
    Still Jakson is the default Java marshaller
  
@@@ 

Now that we've examined the example app thoroughly, let's test a few the remaining use cases.

