/**
 * create-account.js — Handles the create account form submission.
 * Reads form fields, sends POST request with JSON body, shows result message.
 */

const API_BASE = "/api/accounts";

document.getElementById("createForm").addEventListener("submit", function(event) {
    event.preventDefault();    // CRITICAL: stop the page from reloading

    // Collect form data into a JavaScript object
    const account = {
        accountNumber: parseInt(document.getElementById("accNo").value),
        holderName: document.getElementById("holderName").value,
        balance: parseFloat(document.getElementById("initialDeposit").value),
        accountType: document.querySelector('input[name="accountType"]:checked').value
    };

    // Send to the REST API
    fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(account)
    })
    .then(response => {
        if (response.status === 201) {
            return response.json();
        } else {
            return response.json().then(err => {
                throw new Error(err.error || "Failed to create account");
            });
        }
    })
    .then(created => {
        showMessage(
            "Account #" + created.accountNumber + " created successfully for " + created.holderName + "!",
            "green"
        );
        document.getElementById("createForm").reset();
    })
    .catch(error => {
        showMessage(error.message, "red");
    });
});

/**
 * Displays a message on the page with the specified color.
 */
function showMessage(text, color) {
    const msg = document.getElementById("message");
    msg.textContent = text;
    msg.style.color = color;
}
