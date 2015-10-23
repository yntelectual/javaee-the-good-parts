# 02-jpa Assignment

your task is to resolve all the ```TODO`` statements in the code  
run the project build by invoking
```mvn clean install
```

Few helpful tips:

* Eclipse > Window > View > Tasks - will show you all the TODOs nicely
* disable limit in your Eclipse console
* Check the server output for errors during startup, every ERROR message is important
* There are more ways to solve the tasks, but it is possible to solve all of them without the need for any   
  additional classes or methods.
* do not forget to run it with JAVA_HOME environment variable pointing to jdk8 
* you can check what is the DB schema exported by your model by running ```mvn clean install -Dmaven.test.skip=true -Pschema-gen``` this will spill out an sql into target/schema/schema.sql

Good luck