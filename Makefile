prepare:
	cd frontend && ng build --prod && rm ../backend/src/main/resources/* && cp -r dist/buddysystem/* ../backend/src/main/resources/static/
	
