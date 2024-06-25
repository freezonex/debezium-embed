package com.supos.app.service;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.config.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DebeziumService {

    @PostConstruct
    public void startDebeziumEngine() {
        // Define the configuration for the Debezium Engine with MySQL connector
        Properties props = new Properties();
        props.setProperty("name", "engine");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");

        // General Kafka properties
        //props.setProperty("bootstrap.servers", "supcononenorth.fortiddns.com:30902");

        // Use file for offset storage, have problem
        // props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        // props.setProperty("offset.storage.file.filename", "/tmp/offsets.dat");

        // Use memory for offset storage
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.MemoryOffsetBackingStore");

        // Use Kafka for offset storage
        /*props.setProperty("offset.storage", "org.apache.kafka.connect.storage.KafkaOffsetBackingStore");
        props.setProperty("offset.storage.topic", "debezium_offsets");
        props.setProperty("offset.storage.bootstrap.servers", "supcononenorth.fortiddns.com:30902");
        props.setProperty("offset.storage.partitions", "1");
        props.setProperty("offset.storage.replication.factor", "1");*/

        props.setProperty("offset.flush.interval.ms", "60000");

        // Database configuration
        //props.setProperty("database.hostname", "supcononenorth.fortiddns.com");
        //props.setProperty("database.port", "32307");
        props.setProperty("database.hostname", "127.0.0.1");
        props.setProperty("database.port", "6033");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "root1234");
        props.setProperty("database.server.id", "1234");
        props.setProperty("database.server.name", "local");
        props.setProperty("database.whitelist", "hongzhi");
        props.setProperty("table.whitelist", "hongzhi.kafka_test");

        // Topic prefix
        props.setProperty("topic.prefix", "dbserver1");

        // Use file for schema history
        props.setProperty("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory");
        props.setProperty("schema.history.internal.file.filename", "/Users/hongzhi/go/src/freezonex/debezium-embed/storage/schemahistory.dat");

        // Use Kafka for schema history
        /*props.setProperty("schema.history.internal", "io.debezium.storage.kafka.history.KafkaSchemaHistory");
        props.setProperty("schema.history.internal.kafka.topic", "dbserver1.schema-changes.history");
        props.setProperty("schema.history.internal.kafka.bootstrap.servers", "supcononenorth.fortiddns.com:30902");*/

        // Snapshot mode
        props.setProperty("snapshot.mode", "schema_only");

        // Create the engine with this configuration
        Configuration config = Configuration.from(props);

        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(config.asProperties())
                .notifying(record -> {
                    System.out.println(record);
                }).build();

        // Run the engine asynchronously
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);

        // Add a shutdown hook to stop the engine gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                engine.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
