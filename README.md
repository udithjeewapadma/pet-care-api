# pet-care-api

Username: admin
Password: Kingsman@12345

Log into your SonarQube instance and navigate to My Account > Security.
Generate a new token by clicking Generate Tokens and copying it. Then replace the token as below.

mvn clean test verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=squ_f5cfa932e2afc2f5238e46eeabd51f563d0af2fa