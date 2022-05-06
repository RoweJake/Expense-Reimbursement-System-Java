window.onload = function() {
    document.getElementById("unfiltered").addEventListener("click", unfilter);
    document.getElementById("pending").addEventListener("click", filterPending);
    document.getElementById("approved").addEventListener("click", filterApproved);
    document.getElementById("denied").addEventListener("click", filterDenied);
    document.getElementById("updateTicket").addEventListener("click", updateStatus);
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

    xhttp.open('get', "http://localhost:9001/project1/tickets/manager");

    xhttp.send();
}

function displayTickets(allTickets) {
  let tableBody = document.getElementById("ticketTableBody");
  tableBody.innerHTML = "";

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
        newTR.setAttribute("data-bs-toggle", "modal");
        newTR.setAttribute("data-bs-target", "#exampleModal");
        newTR.addEventListener("click", selectTicket);
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

function selectTicket() {
    document.getElementById("modalTableBody").innerHTML = this.innerHTML;
    if(this.getElementsByTagName("td")[6].innerText == "Pending") {
      document.getElementById("approve").style.display = "";
      document.getElementById("deny").style.display = "";
      document.getElementById("approveLabel").style.display = "";
      document.getElementById("denyLabel").style.display = "";
      document.getElementById("updateTicket").style.display = "";
    }else {
      document.getElementById("approve").style.display = "none";
      document.getElementById("deny").style.display = "none";
      document.getElementById("approveLabel").style.display = "none";
      document.getElementById("denyLabel").style.display = "none";
      document.getElementById("updateTicket").style.display = "none";
    }
}

function updateStatus() {
  let xhttp = new XMLHttpRequest

  xhttp.onreadystatechange = function() {
      if(xhttp.readyState==4 && xhttp.status==200) {
          let allTickets = JSON.parse(xhttp.responseText);

          displayTickets(allTickets);
      }
  }

  xhttp.open('post', "http://localhost:9001/project1/tickets/update");

  let statusObj = {
    "id" : getId(),
    "status" : getStatus()
  }
  xhttp.setRequestHeader("content-type", "application/json");
  xhttp.send(JSON.stringify(statusObj));
}

function getId() {
  return document.getElementById("modalTableBody").getElementsByTagName("th")[0].innerText;
}

function getStatus() {
  for(let i = 0; i < document.getElementsByName("status").length; i++) {
    if(document.getElementsByName("status")[i].checked) {
      return document.getElementsByName("status")[i].value;
    }
  }
}

function unfilter() {
    let filter, table, tr, td, i, txtValue;
    filter = "";
    table = document.getElementById("ticketTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[6];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }       
    }
}

function filterPending() {
    let filter, table, tr, td, i, txtValue;
    filter = "Pending";
    table = document.getElementById("ticketTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[6];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }       
    }
}

function filterApproved() {
    let filter, table, tr, td, i, txtValue;
    filter = "Approved";
    table = document.getElementById("ticketTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[6];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }       
    }
}

function filterDenied() {
    let filter, table, tr, td, i, txtValue;
    filter = "Denied";
    table = document.getElementById("ticketTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[6];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }       
    }
}