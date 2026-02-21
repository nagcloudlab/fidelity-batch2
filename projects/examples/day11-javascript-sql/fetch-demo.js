/*
 * Day 11 - Fetch API Demo (JavaScript)
 *
 * Demonstrates:
 * - fetch() GET request — load data from a remote API
 * - fetch() POST request — send data to a remote API
 * - Parsing JSON responses with response.json()
 * - DOM manipulation — dynamically updating the HTML page
 * - event.preventDefault() — stopping form reload
 * - Error handling with .catch()
 * - Template literals for building HTML strings
 *
 * This file works with fetch-demo.html.
 * Uses JSONPlaceholder (https://jsonplaceholder.typicode.com) as a free demo API.
 *
 * In a real banking app, replace the URLs with your Spring Boot API:
 *   const API_BASE = "http://localhost:8080/api/accounts";
 */


// =============================================
// 1. FETCH GET — Load users from the API
// =============================================

// Get references to HTML elements by their IDs
const loadBtn = document.getElementById("loadBtn");
const clearBtn = document.getElementById("clearBtn");
const usersBody = document.getElementById("usersBody");

// When the "Load Users" button is clicked...
loadBtn.addEventListener("click", function () {

    // Show loading message while we wait for the response
    usersBody.innerHTML = '<tr><td colspan="4" style="text-align:center;">Loading...</td></tr>';

    // fetch() sends a GET request to the URL
    // It returns a Promise — we chain .then() to handle the response
    fetch("https://jsonplaceholder.typicode.com/users")
        .then(function (response) {
            // response.json() parses the response body as JSON
            // This also returns a Promise, so we chain another .then()
            return response.json();
        })
        .then(function (users) {
            // 'users' is now a JavaScript array of user objects
            // Clear the table body
            usersBody.innerHTML = "";

            // Loop through each user and create a table row
            users.forEach(function (user) {
                // Create a new <tr> element
                const row = document.createElement("tr");

                // Set its inner HTML using a template literal (backticks)
                // Template literals allow ${variable} for inserting values
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.address.city}</td>
                `;

                // Append the row to the table body
                usersBody.appendChild(row);
            });
        })
        .catch(function (error) {
            // .catch() handles any errors (network failure, invalid URL, etc.)
            console.error("Error fetching users:", error);
            usersBody.innerHTML = '<tr><td colspan="4" style="text-align:center; color:red;">Failed to load data. Check console for details.</td></tr>';
        });
});

// When the "Clear Table" button is clicked, reset the table
clearBtn.addEventListener("click", function () {
    usersBody.innerHTML = '<tr><td colspan="4" style="text-align:center; color:#999;">Click "Load Users" to fetch data from the API</td></tr>';
});


// =============================================
// 2. FETCH POST — Send data to the API
// =============================================

// Get reference to the form
const createForm = document.getElementById("createForm");
const messageDiv = document.getElementById("message");

// Listen for the form's "submit" event
createForm.addEventListener("submit", function (event) {

    // CRITICAL: preventDefault() stops the browser from reloading the page.
    // Without this, the browser would do a traditional form submission
    // and navigate away from the page. We want JavaScript to handle it instead.
    event.preventDefault();

    // Collect form values into a JavaScript object
    const postData = {
        title: document.getElementById("title").value,
        body: document.getElementById("body").value,
        userId: 1   // demo value
    };

    // Send a POST request with the data
    fetch("https://jsonplaceholder.typicode.com/posts", {
        method: "POST",                                    // HTTP method
        headers: {
            "Content-Type": "application/json"             // tell the server we're sending JSON
        },
        body: JSON.stringify(postData)                     // convert JS object to JSON string
    })
        .then(function (response) {
            // Check the HTTP status code
            if (response.status === 201) {
                // 201 = Created (success)
                return response.json();
            } else {
                throw new Error("Failed to create. Status: " + response.status);
            }
        })
        .then(function (created) {
            // 'created' is the server's response (the newly created post)
            showMessage(
                "Created successfully! ID: " + created.id + ", Title: " + created.title,
                "success"
            );
            // Reset the form fields
            createForm.reset();
        })
        .catch(function (error) {
            // Handle errors (network failure, server error, etc.)
            showMessage("Error: " + error.message, "error");
        });
});

/**
 * Displays a message on the page.
 *
 * @param {string} text  - The message text to display
 * @param {string} type  - "success" (green) or "error" (red)
 */
function showMessage(text, type) {
    messageDiv.textContent = text;
    // Remove previous classes and add the appropriate one
    messageDiv.className = "";
    if (type === "success") {
        messageDiv.className = "message-success";
    } else {
        messageDiv.className = "message-error";
    }
}


// =============================================
// 3. COMPARISON: fetch() vs Postman vs curl
// =============================================
//
// | Postman          | curl                 | JavaScript fetch()                      |
// |------------------|----------------------|-----------------------------------------|
// | Enter URL        | curl URL             | fetch(URL)                              |
// | Choose method    | -X POST              | method: "POST"                          |
// | Set header       | (automatic)          | headers: {"Content-Type": "application/json"} |
// | Body (JSON)      | -d '{...}'           | body: JSON.stringify({...})             |
// | Click Send       | Press Enter          | .then(response => ...)                  |
// | See response     | Terminal output      | response.json() => data                 |
//
// fetch() is the JavaScript equivalent of what you did in Postman or curl.
// The concepts (URL, method, headers, body, response) are identical.


// =============================================
// 4. BANKING APP EXAMPLE (commented out)
// =============================================
// In a real banking app connected to Spring Boot (Day 8), you would use:
//
// const API_BASE = "http://localhost:8080/api/accounts";
//
// // GET all accounts
// fetch(API_BASE)
//     .then(response => response.json())
//     .then(accounts => {
//         // populate table with account data
//     });
//
// // POST — create a new account
// fetch(API_BASE, {
//     method: "POST",
//     headers: { "Content-Type": "application/json" },
//     body: JSON.stringify({
//         accountNumber: 1004,
//         holderName: "Sneha Reddy",
//         balance: 100000,
//         type: "CURRENT"
//     })
// })
// .then(response => response.json())
// .then(account => console.log("Created:", account));
//
// // DELETE an account
// fetch(API_BASE + "/1001", { method: "DELETE" })
//     .then(response => {
//         if (response.status === 204) {
//             console.log("Deleted successfully");
//         }
//     });
