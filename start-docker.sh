#Stop all containers
#docker stop $(docker ps -a -q)

#Remove containers
#docker rm rso-storage

#Remove image
#docker rmi storage

#MicroService
mvn clean package
docker build -t storage .
docker run --name rso-storage -p 8083:8083 storage