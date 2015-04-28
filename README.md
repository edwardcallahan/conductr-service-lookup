# ConductR Service Lookup 

Example of using ConductR's LocationService to perform service lookup

## About

The example contains two projects. Charon's Shop in play-front is a Play! Scala application. Ferry Service in akka-back is an Akka application using Akka-HTTP.

Charon's online store allows visitors to send messages to the deceased. The Delivery actor uses the LocationService to find the ferry service. The Delivery actor uses the ask pattern to ensure the message was successfully delivered. This enables Charon to ensure delivery before processing customers payments. 

## Running

Build both application bundles using bundle:dist

`sbt bundle:dist`

Load and start both bundles to ConductR. You should have two services available:

```bash
SERVICE              BUNDLE ID  BUNDLE NAME  STATUS
http://:9000/charon  386078b    charon-shop  Running
http://:9666/ferry   cc08f55    ferry-boat   Running
```
Perform a get on the index page of Charon's Shop.

```bash
http://127.0.0.1:9000/charon
```
Successful delivery will be noted by `Message Delivered` indicating successful communication with the Ferry Service.


