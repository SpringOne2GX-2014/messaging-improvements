# messaging-improvements

This repo contains the code samples of the caching and messaging improvements in
Spring 4.1 talk.

## messaging

This is the main sample using the new `@EnableJms` and `@JmsListener` annotations
available as of Spring 4.1. Spring Boot 1.2.0.M1 has also been updated to auto
configure sensible defaults if necessary.

The application does not define a default `JmsListenerContainerFactory` and does
not need to specify `@EnableJms` either as those are inferred if the necessary
classes are available.

To run this sample, simply run `mvn spring-boot:run` on the command-line or better
yes you could open the project in your favorite IDE and run the main class

NOTE: once the message has been received, the application does not stop as the
listener container is still active.

## messaging-jms-script

This demonstrates the improved JMS support in the CLI. This sample connects on
an existing broker so you'll need to install HornetQ and declare a queue named
`s2gx2014`. Once you've done that, you can run the script:

```
spring run jms-sample.groovy
```

NOTE: Make sure you have updated your cli to Spring Boot 1.2.0.M1

Once the listener is up and running you can send a message using the main
app (`mvn spring-boot:run` would do).

## messaging-amqp-script

This demonstrates the improved AMQP support in the CLI. This sample connects on
an existing RabbitMQ instance so you'll need to make sure Rabbit is running on
your box

```
spring run amqp-sample.groovy
```

NOTE: AMQP improvements have not been released yet so you'll need `1.2.0.BUILD-SNAPSHOT`

Once the listener is up and running you can send a message using the main
app (`mvn spring-boot:run` would do).