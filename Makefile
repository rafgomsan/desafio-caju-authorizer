up: builder
	echo "Running application and migration"
	docker-compose up db authorizer

db:
	echo "Running application and migration"
	docker-compose up db

builder:
	echo "Build container application"
	docker-compose build

down:
	echo "Shutdown the application"
	docker-compose down

migration: migration-clean
	echo "Running migration"
	./gradlew flywayMigrate -i

migration-info:
	echo "Running migration info"
	./gradlew flywayInfo -i

migration-clean:
	echo "Running migration clean"
	./gradlew flywayClean -i

