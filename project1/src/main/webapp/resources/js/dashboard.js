window.onload = function() {
    loadUser();
    loadTickets();
}

function loadUser() {
    let xhttp = new XMLHttpRequest

    xhttp.onreadystatechange = function() {
        if(xhttp.readyState==4 && xhttp.status==200) {
            let user = JSON.parse(xhttp.responseText);

            displayUser(user);
        }
    }

    xhttp.open('get', "http://localhost:9001/project1/dashboard");

    xhttp.send();
}

function loadTickets() {
    let xhttp = new XMLHttpRequest

    xhttp.onreadystatechange = function() {
        if(xhttp.readyState==4 && xhttp.status==200) {
            let allTickets = JSON.parse(xhttp.responseText);

            displayTickets(allTickets);
        }
    }

    xhttp.open('get', "http://localhost:9001/project1/tickets/employee");

    xhttp.send();
}

function displayTickets(allTickets) {
    for(let i= 0; i< allTickets.length; i++){

        // creating elements
        let newTR = document.createElement("tr");
        let newTH = document.createElement("th");

        let newTD1 = document.createElement("td");
        let newTD2 = document.createElement("td");
        let newTD3 = document.createElement("td");
        let newTD4 = document.createElement("td");
        let newTD5 = document.createElement("td");
        let newTD6 = document.createElement("td");
        let newTD7 = document.createElement("td");
        let newTD8 = document.createElement("td");

        // configure elements
        newTH.setAttribute("scope", "row");
        let myTextH = document.createTextNode(allTickets[i].id);
        let myTextD1 = document.createTextNode(allTickets[i].amount);
        let myTextD2 = document.createTextNode(allTickets[i].submitDate);
        let myTextD3 = document.createTextNode(allTickets[i].resolvedDate);
        let myTextD4 = document.createTextNode(allTickets[i].description);
        let myTextD5 = document.createTextNode(`${allTickets[i].author.firstName} ${allTickets[i].author.lastName}`);
        let myTextD6 = document.createTextNode(resolverString(allTickets[i].resolver));
        let myTextD7 = document.createTextNode(allTickets[i].status);
        let myTextD8 = document.createTextNode(allTickets[i].type);

        // appending elements
        newTH.appendChild(myTextH);
        newTD1.appendChild(myTextD1);
        newTD2.appendChild(myTextD2);
        newTD3.appendChild(myTextD3);
        newTD4.appendChild(myTextD4);
        newTD5.appendChild(myTextD5);
        newTD6.appendChild(myTextD6);
        newTD7.appendChild(myTextD7);
        newTD8.appendChild(myTextD8);

        newTR.appendChild(newTH);
        newTR.appendChild(newTD1);
        newTR.appendChild(newTD2);
        newTR.appendChild(newTD3);
        newTR.appendChild(newTD4);
        newTR.appendChild(newTD5);
        newTR.appendChild(newTD6);
        newTR.appendChild(newTD7);
        newTR.appendChild(newTD8);

        let tableBody = document.getElementById("ticketTableBody");
        tableBody.appendChild(newTR);
    }
}

function resolverString(resolver) {
    if(resolver == null) {
        return null;
    }else {
        return `${resolver.firstName} ${resolver.lastName}`;
    }
}

function displayUser(user) {
    document.getElementById("user").innerText += ` ${user.firstName} ${user.lastName}`;
}