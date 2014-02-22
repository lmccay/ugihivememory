# Test Examples demonstrating memory leak from Hive connections from UGI.doAs

## Required changes for your environment:

* change the following to have your hive keytab location:
static final String KEYTABDIR = "/etc/security/keytabs/hive.service.keytab";

* change the following line to have your environment's principal:
static final String HIVE_PRINCIPAL = "hive/example.com@EXAMPLE.COM";

## Required for hive scenario:

* Hive metastore must be running
* HiveServer2 must be running

## To build the project:

* mvn clean install

## Run the hive - jdbc -> hiveserver2 test from a client machine with the following:

    hadoop jar {path/to/project/jar}}ugihivememory-1.0-SNAPSHOT.jar org.apache.hadoop.examples.HiveMemoryExample
