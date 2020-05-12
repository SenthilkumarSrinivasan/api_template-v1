## api_template-v1

Sample application for REST APIs

#### To run the application locally :

1) Clone/Download the repo to your local machine.

2) Execute the command to start running the application

        ./gradlew clean build bootrun
        
        OR 
        
        ./gradlew clean build
        java -Dspring.profiles.active=default -jar build/libs/api_template-v1-0.0.1-SNAPSHOT.jar
        
3) Log files can be located in the directory - /logs