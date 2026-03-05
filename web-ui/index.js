

// using DOM api
// --------------------

const greetBtn = document.querySelector(".btn-primary");
const cardBody = document.querySelector(".card-body");

greetBtn.addEventListener("click", () => {
    const hour = new Date().getHours();
    let greeting;
    if (hour < 12) {
        greeting = "Good Morning!";
    } else if (hour < 18) {
        greeting = "Good Afternoon!";
    } else {
        greeting = "Good Evening!";
    }
    const greetingElement = document.createElement("h2");
    greetingElement.textContent = greeting;
    cardBody.appendChild(greetingElement);
});

// DOM + Fetch API
// --------------------
const top5TodosBtn = document.querySelector(".btn-secondary");

top5TodosBtn.addEventListener("click", async () => {
    try {
        const response = await fetch("https://jsonplaceholder.typicode.com/todos?_limit=5");
        const todos = await response.json();
        const top5TodosBody = document.getElementById("top5-todos-body");
        top5TodosBody.innerHTML = "";
        todos.forEach(todo => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${todo.id}</td>
                <td>${todo.title}</td>
                <td>${todo.completed}</td>
            `;
            top5TodosBody.appendChild(row);
        });
    } catch (error) {
        console.error("Error fetching top 5 todos:", error);
    }
});