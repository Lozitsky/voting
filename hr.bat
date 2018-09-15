call -DskipTests=true clean package
call java -DDATABASE_URL="postgres://user:password@localhost:5432/voting_system" -jar target/dependency/webapp-runner.jar target/*.war