/**
 * accounts.js — Loads account data from REST API and populates the table.
 * Provides delete functionality for each account row.
 */

const API_BASE = "/api/accounts";

// Load accounts when the page opens
window.addEventListener("DOMContentLoaded", loadAccounts);

/**
 * Fetches all accounts from the API and builds table rows dynamically.
 */
function loadAccounts() {
    fetch(API_BASE)
        .then(response => response.json())
        .then(accounts => {
            const tbody = document.getElementById("accountsBody");
            tbody.innerHTML = "";    // clear existing rows before reloading

            if (accounts.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No accounts found</td></tr>';
                return;
            }

            accounts.forEach(acc => {
                const row = document.createElement("tr");

                // Determine badge class based on account type
                const badgeClass = acc.accountType === "SAVINGS" ? "badge-savings" : "badge-current";

                row.innerHTML = `
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td><span class="badge ${badgeClass}">${acc.accountType}</span></td>
                    <td>${acc.balance.toLocaleString("en-IN", { minimumFractionDigits: 2 })}</td>
                    <td>
                        <button class="btn btn-danger btn-sm"
                                onclick="deleteAccount(${acc.accountNumber})">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error loading accounts:", error);
        });
}

/**
 * Deletes an account by account number after user confirmation.
 * Refreshes the table after successful deletion.
 */
function deleteAccount(accNo) {
    if (!confirm("Are you sure you want to delete account #" + accNo + "?")) {
        return;
    }

    fetch(API_BASE + "/" + accNo, { method: "DELETE" })
        .then(response => {
            if (response.status === 204) {
                loadAccounts();    // refresh the table
            } else {
                alert("Failed to delete account #" + accNo);
            }
        })
        .catch(error => {
            console.error("Error deleting account:", error);
        });
}
