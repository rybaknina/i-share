call mvn liquibase:dropAll -pl .
call mvn liquibase:update -pl .
call mvn clean install
