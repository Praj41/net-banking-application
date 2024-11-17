// DOM Elements
const accountSelect = document.getElementById('accountSelect');
const transactionBody = document.getElementById('transactionBody');

// Placeholder for JWT token, replace with actual token retrieval logic
const jwtToken = localStorage.getItem('jwt');

// Load accounts into the dropdown
async function loadAccounts() {
    try {
        const response = await fetch('http://localhost:8080/getUserAccounts', {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        const accounts = await response.json();

        // Populate dropdown with accounts
        accounts.forEach(account => {
            const option = document.createElement('option');
            option.value = account.accountId;  // Adjust based on your API response structure
            option.text = `Account ${account.accountId}`;
            accountSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching accounts:', error);
    }
}

// Fetch and display transactions for the selected account
async function loadTransactions(accountId) {
    try {
        const response = await fetch(`http://localhost:8080/transactions?accountId=${accountId}`, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        const transactions = await response.json();

        // Clear existing rows
        transactionBody.innerHTML = '';

        // Populate table with transaction data
        transactions.forEach(transaction => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${transaction.transactionDate}</td>
                <td>${transaction.id.referenceNo}</td>
                <td>${transaction.amount}</td>
                <td>${transaction.receivingAccountId.accountId}</td>
            `;
            transactionBody.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching transactions:', error);
        transactionBody.innerHTML = '<tr><td colspan="4">Failed to load transactions.</td></tr>';
    }
}

// Event Listener for account selection
accountSelect.addEventListener('change', (event) => {
    const accountId = event.target.value;
    if (accountId) {
        loadTransactions(accountId);
    }
});

// Initial load of accounts
loadAccounts();
