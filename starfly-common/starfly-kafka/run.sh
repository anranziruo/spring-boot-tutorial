# shellcheck disable=SC1113
#/bin/bash
java -Dspring.profiles.active=prod -jar target/starfly-kafka-0.0.1-SNAPSHOT.jar JsonRun com_qudianread_home_kafka com_qudianread_home_group