# Open a terminal
docker-compose up

This should start mysql and phpmyadmin in seperate container 

For accessing phpmyadmin

http://localhost:8062/
User: user
Password: userpassword

For mysql
Url=jdbc:mysql://localhost:8061/demo-cart
User: user
Password: userpassword


For Api:
For JWT Authetication
http://localhost:8060/api/authenticate

For accessing users api use token from authentication response
http://localhost:8060/api/users