echo "Clean up backend"
find ./backend/src/main/resources/static -delete


echo "Building frontend"
cd frontend && ng build --prod 

echo "Copy"
mkdir ./../backend/src/main/resources/static/
cp -r dist/buddysystem/* ./../backend/src/main/resources/static/

echo "Build jar"
cd ../backend && mvn clean compile package
