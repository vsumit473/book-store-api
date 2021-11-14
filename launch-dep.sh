echo "buiding docker image locally"
docker build -t smart-dubai-book.jar .
echo "image is created.."
echo "now running the smart-dubai-book application"
docker run -d -p 9090:8080 smart-dubai-book.jar
sleep 7s
echo "Smart dubai book store application is up!"
