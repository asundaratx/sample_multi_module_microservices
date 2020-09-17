# run this script on the docker terminal after running docker-compose
docker exec -it zipkinkafkazookeeper_kafka_1 bash
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic validateuser
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic user