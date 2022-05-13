# Expense Reimbursement System (ERS) Java

## Project Description

The ERS system is used to store reimburesment requests submitted by employees. Financial managers can view all tickets in the system and select pending tickets to then approve or deny. Once a ticket is approved or denied the change can be seen by all accounts.

## Technologies Used

* Java 11
* JDBC
* Javax Servlet
* Jackson Databind
* Junit
* Mockito
* H2 Database
* PostgreSQL
* HTML
* CSS
* Bootstrap 5
* JavaScript

## Features

List of features ready and TODOs for future development
* Login
* Logout
* Viewing own tickets as an employee.
* Viewing all tickets as a manager.
* Submitting a new ticket as an employee.
* Filtering tickets by status as a manager.
* Approving/Denying a ticket as a manager.

## Getting Started
   
* git clone https://github.com/RoweJake/Expense-Reimbursement-System-Java.git
* Import the project into a Java IDE such as Spring Tools Suite.
* Add a Tomcat v9.0 Server to the IDE workspace and add the project to the server.
* Create a database on the database instance you want the project to use.
* Run the project1Script.sql script on the database to create the schema used by the project as well as add some starting test data.
* Configure the following environment variables to provide credentials for the project to connect to your database instance.
* DATABASE_ENDPOINT - The endpoint and port of your database instance.
* DATABASE_USERNAME - The username for your database account.
* DATABASE_PASSWORD - The password for your database account.
* In the dao.ConnectionFactory.java file on line 17, change the final string in the url variable to match the name of your database.

## Usage

* Start the Tomcat server to launch the project.
* Browse to the server url to reach the login page.
* Enter a username and password to login to the application.
* The user will be redirected to the manager or employee dashboard based on their role.
* An invalid username or password will redirect the user to the badlogin page where they can return to the login page to try again.

### Employee

* Reimbursements are displayed on the dashboard.
* Click "Logout" to return to the login page.
* Click "New Ticket" to add a new reimbursement ticket.
* Enter the requested information on the new ticket page and click "Submit" to add a new ticket.
* Click "Cancel" on the new ticket page to return to the dashboard.

### Manager

* Reimbursements are displayed on the dashboard.
* Click "Logout" to return to the login page.
* Click "Filter" to reveal a dropdown that presents filter options.
* Click on a ticket to view the ticket details.
* Pending tickets will have an Approve/Deny option in their details. Select an option and click "Submit" to approve/deny a ticket.