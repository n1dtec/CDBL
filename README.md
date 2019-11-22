# CDBL
Currency Description By User's Current Location

###### Project Information

This project contains the implementation of APIs to display the currency information of the country which the user is calling the API from.

To run this project, use the following steps:
1. Fork this repository
2. Clone your forked repository on your machine
3. Import the project in IntelliJ IDEA Ultimate
4. Install a server to run the project like Glassfish or Apache
5. Set the URL to be used by server as "http://localhost:8080/MyRESTApp/"

###### APIs

This project has the implementation of the following APIs with the help of other publicly-available APIs:
1. /currencyInfo - returns the description of the currency which the user is calling the API from
2. /currencyCode/{IP} - returns the information about the country from the input IP address which is sent as Path Param
3. /ip - returns the user's current IP address
