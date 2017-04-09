MemCached
------------
This is a scaled down implementation of the memcache server that has support for the Get, Set, and Delete methods using
the Memcache Text Protocol.
It Includes support for Compare And Swap by implementing the GETS and CAS methods in the text protocol.
Support a configurable limit for the resource consumption of your service.
This implementation handles concurrency issues that arise in an environment with multiple simultaneous writers and readers using
read/write locks for the cache which is implemented using a concurrent hashmap.

Instruction for building and running the project
--------------------------------------------------
 * cd to the memcached directory
 * run ./gradlew build
 * cd build/libs
 * To Start server on default port 9000 run command "java -cp ./memcached-1.0-SNAPSHOT.jar com.slack.memcached.MemCached"or provide -p argument to start the server on a specified port.


Testing Performed
------------------
* The server was tested using basic positive and negative tests using Junit tests at "com.slack.server.MemServerTest"
* In addition one can perform additional tests by trying to run commands in parallel.
* Some other tests like Load, Stress etc can also be performed on the system to Benchmark the system and figure out its breaking points.

Monitoring and Managing the Service
------------------------------------
The service uses standard log4j logging. The log4j logs can be further forwarded to a service like SumoLogic or Splunk.
In addition the metrics/stats which are maintained as part of the server (examples in the com.slack.memcached.CommandHandler class)
 can be written to statsd on the localhost which can then be forwarded to a service like Datadog for display etc using Datadog clients.

 Performance, Reliability and Limitations
 -----------------------------------------
 The current implementation of the MemCache server has severe reliability issues.
 * If the server caches or is restarted all the entries in the cache are lost.
 One can run a Listener or a Sync process to have the cache be backed by a backing store which can be used to store the cache
 before shutting down the server or can also run Sync jobs to periodically backing the cache.
 Several options could be used for a backing store : blob store, distributed block store etc.
 * The current implementation does not have any limits on number of Socket connections it can accept from client which can cause
 system crash due to overuse of the allocated heap for the server. One can implement a throttling mechanism or have max number of
 acceptable connections to overome this limitation.
