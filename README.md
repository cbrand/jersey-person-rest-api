# Person REST API #

Java implementation of a person REST API.

This is a toy programm demonstrating some functionalities of jersey for a computer science "api programming" class. 

It is my first try to build something in jersey so it is for sure not best practice and has none authentication or authorization layers implemented into it.

The api does the following:

- Offers a /person with search, create, update and delete functionality.
- Offers an /person/{id}/orders API with search, create, update and delete functionality.
- Stores everything in RAM and has some backends to access the stored in RAM data.
- The search APIs provides a limit and offset functionality.

