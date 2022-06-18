run:
	mvn clean compile package
	docker-compose up --build -d