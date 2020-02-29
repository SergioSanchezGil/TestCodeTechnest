# TestCodeTechnest

Test code for TESCHNEST modeling an account service wit a REST API

##H2 embedded DB

On your choice of browser, enter http://localhost:8080/h2-console to access to the console

##Model

To define the columns of the Account entity, I have decided to create an enum for Currencies

##Controller

### Save/Update endpoint

To call the save/update endpoint (http://localhost:8080/accounts/) you need to send, as a post call, a JSON like this one:
	
	{
		"name": "Sergio",
		"currency": 1,
		"balance": 1,
		"treasury": false
	}
It response with the id of the Account created or -1 if there was a bad request error. Then you can see the Account added in the H2 console. 

