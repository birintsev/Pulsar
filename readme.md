#Pulsar backend

 This is a backend part of Pulsar project.
 Pulsar project is a being developing as a web application for server monitoring 
 (both active and passive).

##Building/running the application in dev mode

 Run the script below in a folder you want the project to be built:
 `git clone https://tss2020.repositoryhosting.com/git/tss2020/t3.git/backend`
 Open cloned repository and run Maven task:
 `mvn clean package`
 If you do not have installed Maven in your system, 
 please refer the [guide](https://maven.apache.org/install.html)
 For now Apache Maven 3.6.3 is used.
 
 Then, navigated to newly created target folder, run the script below:
 `java -jar Pulsar.jar start -f /path/to/application.properties`
 Note, that a configuration file name may differ from the one given in example.
	
##Deploy instruction

 Deploying the application on dev/prod environments 
 does not have any differences. You can easily specify
 configuration file location by `-f` option of `start` command.

##Supported commands

 Below is a list of supported commands:
  
  1. 'start'
      1. '-f', '-file' - configuration file location
      (a set of overriding application properties, .properties file) 

##Project configuration

 Currently, '.properties'-formatted file-based configuration supported only.
 Below is a list of supported application properties:
 * `pulsar.database.connection.url`             -   database connection url 
                                                    (required)
 * `pulsar.database.connection.user`            -   database user that will be 
                                                    used when interacting with 
                                                    a DB (required)
 * `pulsar.database.connection.password`        -   database user profile
                                                    password
 * `pulsar.database.connection.driver_class`    -   JDBC dirver class (required)
 * `pulsar.database.dialect`                    -   Hibernate dialect 
                                                    (represents a type of
                                                    database used in Hibernate
                                                    to generate SQL statements 
                                                    for a particular
                                                    relational DB)
 * `server.port`                                -   a port number that an 
                                                    application instance 
                                                    will listen to 
                                                    (default value is 19991)
 * `pulsar.log.directory`                       -   a folder, where log files
                                                    will be stored 
                                                    (default value is 
                                                    a parent folder
                                                    for the running JAR)
                                                    
 **Note**, that properties above will take effect on application startup only.
 That is, if you use *another* command but `start`, *that command* set of 
 configurations (or a default one) considered as effective. Below is a list of 
 general properties to be used in the application:
 
 * `pulsar.user.phone_number.regexp`    -   a regular expression for
                                            a user phone number validation
 * `pulsar.user.password.regexp`        -   a regular expression for
                                            a user password validation
 * `pulsar.user.firstname.length.max`   -   a first name maximum length
                                            (a positive integer)
 * `pulsar.user.lastname.length.max`    -   a last name maximum length 
                                            (a positive integer)
 * `pulsar.user.username.regexp`        -   a regular expression for
                                            a username validation
 * `pulsar.email.regexp`                -   a regular expression 
                                            that will be used
                                            for email validation
 
 The properties above are easily configured. If you really need this,
 the only right place to implement changes is the 
 *${project.basedir}/src/main/resources/pulsar.properties* file.
 Put your values in that file prior building an artifact 
 (see the **Building/running the application in dev mode** section), 
 and they will take effect.
 
##Used software

 Here is a list of programms/3d parties libraries that were used:
  * [Javalin](https://javalin.io/)
  * [Hibernate](https://hibernate.org/)
  * [Maven](https://maven.apache.org/)
  * others

 A complete list of project dependencies you may freely check in the POM file.

##Agent

 Agent is a software, deployed on a client's server. 
 It represents a remote service, sending collected system state data
 to this application. Agent source code can be found
 [here](https://github.com/potapuff/agent).
 Basically, agent is an independent service and might be different
 for each client. However, a fact of another agent software use must be agreed 
 with Borys Kuzikov, Mykhailo Birintsev and not violate license rules, 
 Statements of Work document and terms of use.
 See examples of agent messages in the Statements of Work document.

##Statements of Work

 Project Statements of Work document could be found 
 [here](http://bit.ly/TSS20_MONIT)

##Issues

 If you have questions, suggestions or issues - feel free to contact us. 
 You may find a contact list in the end of this file.

##Authors & Contributors

 Author: Mykhailo Birintsev

##Contacts

 * Mykhailo Birintsev   -   [Skype](skype:leader228228),
                            [E-Mail](mailto:leader228228@gmail.com),
                            [GitHub](https://www.github.com/leader228228)
 * Borys Kuzikov        -   [GitHub](https://github.com/potapuff/agent)
 