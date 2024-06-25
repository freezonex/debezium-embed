# Debezium Embed

**Debezium Embed** is a Java library that allows you to integrate Debezium's change data capture (CDC) functionality directly into your applications. Capture real-time database changes and stream them seamlessly for responsive, data-driven applications. Supports multiple databases like MySQL, PostgreSQL, and MongoDB.

## Key Features

- **Real-Time Data Streaming:** Capture database changes in real-time and stream them to your application.
- **Seamless Integration:** Embed Debezium into your Java application with minimal configuration.
- **Support for Multiple Databases:** Works with popular databases such as MySQL, PostgreSQL, MongoDB, and more.
- **Resilient and Reliable:** Ensures data consistency and reliability, even in the face of failures.
- **Extensible:** Easily extend and customize the functionality to suit your specific use case.

## Use Cases

- **Data Synchronization:** Keep data in sync across different systems and services in real-time.
- **Event-Driven Architectures:** Enable event-driven architectures by capturing and reacting to database changes.
- **Data Replication:** Replicate data across multiple databases for redundancy and load balancing.
- **Auditing and Compliance:** Track changes to data for auditing and compliance purposes.

## Getting Started

To get started with Debezium Embed, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven or Gradle for dependency management

### Installation

Add the following dependency to your Maven `pom.xml`:

```xml
<dependency>
    <groupId>io.debezium</groupId>
    <artifactId>debezium-embedded</artifactId>
    <version>2.6.0.Final</version>
</dependency>
