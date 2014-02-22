## Test Examples demonstrating memory leak from Hive connections from UGI.doAs

### Required changes for your environment:

* change the following to have your hive keytab location:
static final String KEYTABDIR = "/etc/security/keytabs/hive.service.keytab";

* change the following line to have your environment's principal:
static final String HIVE_PRINCIPAL = "hive/example.com@EXAMPLE.COM";

### Required for hive scenario:

* Hive metastore must be running
* HiveServer2 must be running

### To build the project:

* mvn clean install

### Run the hive - jdbc -> hiveserver2 test from a client machine with the following:
    Set any addition classpath required for Hive interaction - had to export the following:
    export HADOOP_CLASSPATH=/usr/lib/hive/lib/hive-jdbc-0.11.0.1.3.3.0-59.jar:/usr/lib/hive/lib/libthrift-0.9.0.jar:/usr/lib/hive/lib/hive-cli-0.11.0.1.3.3.0-59.jar:/usr/lib/hive/lib/hive-shims-0.11.0.1.3.3.0-59.jar:/usr/lib/hive/lib//hive-service-0.11.0.1.3.3.0-59.jar
    
    hadoop jar {path/to/project/jar}}ugihivememory-1.0-SNAPSHOT.jar org.apache.hadoop.examples.HiveMemoryExample
