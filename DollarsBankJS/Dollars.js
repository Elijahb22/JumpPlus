// Sample user data
const users = [
  {
    username: 'Eli',
    password: '1234',
    balance: 1000,
    transactions: [
      { type: 'deposit', amount: 500 },
      { type: 'withdraw', amount: 200 },
      { type: 'transfer', amount: 300 },
    ],
    customerInfo: {
      name: 'Eli Busick',
      email: 'Eli@gmail.com.com',
      address: 'Charlotte, NC',
    },
  },
  {
    username: 'Ava',
    password: '5678',
    balance: 1000,
    transactions: [
      { type: 'deposit', amount: 400 },
      { type: 'withdraw', amount: 600 },
      { type: 'transfer', amount: 100 },
    ],
    customerInfo: {
      name: 'Ava Teeters',
      email: 'Ava@gmail.com',
      address: 'Myrtle Beach, SC',
    },
  },
  
];

// Event listener for login form submission
document.getElementById("loginForm").addEventListener("submit", function (event) {
  event.preventDefault(); // Prevent form submission

  // Get the entered username and password
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  // Find the user in the array of users
  const user = users.find((u) => u.username === username && u.password === password);

  if (user) {
    // Display the dashboard and populate user data
    document.getElementById("customerName").textContent = user.name;
    document.getElementById("accountBalance").textContent = `Total Account Balance: $${user.accounts[0].balance.toFixed(
      2
    )}`;
    document.getElementById("customerInfo").textContent = `Name: ${user.name}\nUsername: ${user.username}`;

    // Display last 5 transactions
    const transactionList = document.getElementById("transactionList");
    transactionList.innerHTML = ""; // Clear previous transactions

    for (let i = 0; i < Math.min(user.accounts[0].transactions.length, 5); i++) {
      const transaction = user.accounts[0].transactions[i];
      const listItem = document.createElement("li");
      listItem.textContent = `${transaction.type}: $${transaction.amount.toFixed(2)}`;
      transactionList.appendChild(listItem);
    }

    // Show the dashboard and hide the login section
    document.getElementById("login").classList.add("hidden");
    document.getElementById("dashboard").classList.remove("hidden");
  } else {
    alert("Invalid username or password. Please try again.");
  }
});

// Event listener for deposit button
document.getElementById("depositBtn").addEventListener("click", function () {
  const amount = parseFloat(prompt("Enter the amount to deposit:"));
  if (!isNaN(amount) && amount > 0) {
    const user = users.find((u) => u.username === document.getElementById("username").value);
    user.accounts[0].balance += amount;
    user.accounts[0].transactions.push({ type: "Deposit", amount: amount });

    // Update account balance
    document.getElementById("accountBalance").textContent = `Total Account Balance: $${user.accounts[0].balance.toFixed(
      2
    )}`;

    // Update transaction list
    const transactionList = document.getElementById("transactionList");
    const listItem = document.createElement("li");
    listItem.textContent = `Deposit: $${amount.toFixed(2)}`;
    transactionList.insertBefore(listItem, transactionList.firstChild);
  } else {
    alert("Invalid amount. Please try again.");
  }
});

// Event listener for withdraw button
document.getElementById("withdrawBtn").addEventListener("click", function () {
  const amount = parseFloat(prompt("Enter the amount to withdraw:"));
  if (!isNaN(amount) && amount > 0) {
    const user = users.find((u) => u.username === document.getElementById("username").value);

    if (user.accounts[0].balance >= amount) {
      user.accounts[0].balance -= amount;
      user.accounts[0].transactions.push({ type: "Withdrawal", amount: amount });

      // Update account balance
      document.getElementById("accountBalance").textContent = `Total Account Balance: $${user.accounts[0].balance.toFixed(
        2
      )}`;

      // Update transaction list
      const transactionList = document.getElementById("transactionList");
      const listItem = document.createElement("li");
      listItem.textContent = `Withdrawal: $${amount.toFixed(2)}`;
      transactionList.insertBefore(listItem, transactionList.firstChild);
    } else {
      alert("Insufficient funds.");
    }
  } else {
    alert("Invalid");
  }
});

// Event listener for transfer button
document.getElementById("transferBtn").addEventListener("click", function () {
  const amount = parseFloat(prompt("Enter the amount to transfer:"));
  if (!isNaN(amount) && amount > 0) {
    const user = users.find((u) => u.username === document.getElementById("username").value);

    if (user.accounts[0].balance >= amount) {
      const recipientUsername = prompt("Enter the recipient's username:");
      const recipientUser = users.find((u) => u.username === recipientUsername);

      if (recipientUser) {
        user.accounts[0].balance -= amount;
        user.accounts[0].transactions.push({ type: "Transfer to " + recipientUsername, amount: amount });

        recipientUser.accounts[0].balance += amount;
        recipientUser.accounts[0].transactions.push({ type: "Transfer from " + user.username, amount: amount });

        // Update account balance
        document.getElementById("accountBalance").textContent = `Total Account Balance: $${user.accounts[0].balance.toFixed(
          2
        )}`;

        // Update transaction list for sender
        const transactionList = document.getElementById("transactionList");
        const listItem = document.createElement("li");
        listItem.textContent = `Transfer to ${recipientUsername}: $${amount.toFixed(2)}`;
        transactionList.insertBefore(listItem, transactionList.firstChild);

        // Update transaction list for recipient
        const recipientTransactionList = recipientUser.accounts[0].transactions;
        const recipientListItem = document.createElement("li");
        recipientListItem.textContent = `Transfer from ${user.username}: $${amount.toFixed(2)}`;
        recipientTransactionList.unshift({ type: "Transfer from " + user.username, amount: amount });

        alert(`Transfer of $${amount.toFixed(2)} to ${recipientUsername} is successful.`);
      } else {
        alert("Recipient not found. Please try again.");
      }
    } else {
      alert("Insufficient funds.");
    }
  } else {
    alert("Invalid amount. Please try again.");
  }
});
