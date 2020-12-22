prepare:
	cd frontend && ng build --prod && cp -r dist/* ../backend/src/main/resources/static/
	
