/**
 * transaction.js — Handles the deposit/withdraw form submission.
 * Determines the correct endpoint based on radio selection, sends POST request.
 */

const API_BASE = "/api/accounts";

document.getElementById("txnForm").addEventListener("submit", function(event) {
    event.preventDefault();    // CRITICAL: stop the page from reloading

    // Read form fields
    const accNo = document.getElementById("accNo").value;
    const amount = document.getElementById("amount").value;
    const txnType = document.querySelector('input[name="txnType"]:checked').value;

    // Determine the correct endpoint: deposit or withdraw
    const url = `${API_BASE}/${accNo}/${txnType}?amount=${amount}`;

    // Send POST request to the deposit/withdraw endpoint
    fetch(url, { method: "POST" })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 404) {
                throw new Error("Account #" + accNo + " not found");
            } else {
                return response.json().then(err => {
                    throw new Error(err.error || "Transaction failed");
                });
            }
        })
        .then(account => {
            const action = txnType === "deposit" ? "Deposited" : "Withdrawn";
            showMessage(
                `${action} Rs.${parseFloat(amount).toLocaleString()} | New Balance: Rs.${account.balance.toLocaleString("en-IN", { minimumFractionDigits: 2 })}`,
                "green"
            );
            document.getElementById("txnForm").reset();
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
