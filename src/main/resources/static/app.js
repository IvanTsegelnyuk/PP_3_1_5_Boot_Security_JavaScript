const mainTable = document.getElementById("mainTable");
const tableBody = document.getElementById("tableBody");
const adminPanel = document.getElementById("adminPanel");
const loginUserInfo = document.getElementById("loginUserInfo");
// adminPanel.innerHTML='Admin panel';

async function getAllUsers() {
    const result = await fetch ('http://localhost:8080/api/users')
    return result.json()
}

async function buildUserTable() {

    await fetch('http://localhost:8080/api/users')
        .then(res => res.json())
        .then(data => {
            console.log(data)
            const usersCount = data.length
            for (let i = 0; i < usersCount; i++) {
                const newTableRow = document.createElement("tr");
                newTableRow.classList.add("mainTableTr");
                for (let j = 0; j < mainTable.rows[0].cells.length; j++) {
                    newTableRow.appendChild(document.createElement("td"))
                }
                tableBody.appendChild(newTableRow)
            }

            for (let i = 1; i < mainTable.rows.length; i++) {
                const currentRowUser = data[i-1];
                // список ролей пользователя
                const array = []
                currentRowUser.roles.forEach((item) => {
                    array.push(item.simpleName);
                })
                //
                const deleteBtn = document.createElement("a");
                deleteBtn.setAttribute("type", "button");
                deleteBtn.setAttribute("class", "btn btn-danger");
                deleteBtn.setAttribute("data-bs-toggle", "modal");
                deleteBtn.setAttribute("data-bs-target", "#deleteModal");
                deleteBtn.innerText='Delete';
                deleteBtn.addEventListener("click",() => {
                    const inputId = document.querySelector("[id=delete_id]");
                    inputId.setAttribute("value", currentRowUser.id);
                    const inputName = document.querySelector("[id=delete_userName]");
                    inputName.setAttribute("value", currentRowUser.username);
                    const inputLastName = document.querySelector("[id=delete_lastName]");
                    inputLastName.setAttribute("value", currentRowUser.lastName);
                    const inputAge = document.querySelector("[id=delete_age]");
                    inputAge.setAttribute("value", currentRowUser.age);
                    const inputEmail = document.querySelector("[id=delete_email]");
                    inputEmail.setAttribute("value", currentRowUser.email);
                });
                const editBtn = document.createElement("button");
                editBtn.setAttribute("type", "button");
                editBtn.setAttribute("class", "btn text-white");
                editBtn.setAttribute("data-bs-toggle", "modal");
                editBtn.setAttribute("data-bs-target", "#editModal");
                editBtn.style.backgroundColor="#17a2b8";
                editBtn.innerText='Edit';
                editBtn.addEventListener("click", () => {
                    const inputId = document.querySelector("[id=edit_id]");
                    inputId.setAttribute("value", currentRowUser.id);
                    const inputName = document.querySelector("[id=edit_userName]");
                    inputName.setAttribute("value", currentRowUser.username);
                    const inputLastName = document.querySelector("[id=edit_lastName]");
                    inputLastName.setAttribute("value", currentRowUser.lastName);
                    // console.log(inputLastName);
                    const inputAge = document.querySelector("[id=edit_age]");
                    inputAge.setAttribute("value", currentRowUser.age);
                    const inputEmail = document.querySelector("[id=edit_email]");
                    inputEmail.setAttribute("value", currentRowUser.email);
                });
                mainTable.rows[i].cells[0].innerHTML = currentRowUser.id;
                mainTable.rows[i].cells[1].innerHTML = currentRowUser.username;
                mainTable.rows[i].cells[2].innerHTML = currentRowUser.lastName;
                mainTable.rows[i].cells[3].innerHTML = currentRowUser.age;
                mainTable.rows[i].cells[4].innerHTML = currentRowUser.email;
                mainTable.rows[i].cells[5].innerHTML = array.sort().join(' ');
                mainTable.rows[i].cells[6].appendChild(editBtn);
                mainTable.rows[i].cells[7].appendChild(deleteBtn);
            }
        })
}

const editForm = document.getElementById("editForm");

editForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(editForm);
    const obj = {};
    const roles = await getAllRoles();
    formData.forEach((key, value) => {
        if (value === "roles") {
            const newArray = [];
            newArray.push(roles[key])
            obj[value] = newArray;
        } else {
            obj[value] = key;
        }
    })
    console.log(obj);

    await fetch("http://localhost:8080/api/edit", {
        method: "PUT",
        headers: {
            "content-type" : "application/json"
        },
        body: JSON.stringify(obj)
    })

    const allTr = document.querySelectorAll(".mainTableTr");
    allTr.forEach((item) => {
        item.remove()
    })
    await buildUserTable();
})


const deleteForm = document.getElementById("deleteForm");
deleteForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(deleteForm);
    const id = formData.get("id");
    try {
        const url = `http://localhost:8080/api/delete/${id}`
        await fetch(url, {
            method: "DELETE",
        })
    } catch (error) {

    }

    const allTr = document.querySelectorAll(".mainTableTr");
    allTr.forEach((item) => {
        item.remove()
    })
    await buildUserTable();
})



fetch('http://localhost:8080/api/loginUser')
    .then(res => res.json())
    .then(data => {
        const array = [];
        for(let i = 0; i < data.roles.length; i++) {
            array.push(data.roles[i].simpleName);
        }
        loginUserInfo.innerText = `${data.email} with roles: ${array.sort().join(' ')}`
    })


const horizontals = document.querySelectorAll(".nav-link");
const tabContent = document.querySelectorAll(".tab-content")

horizontals.forEach(function (tab) {
    tab.addEventListener("click", function () {
        let currentBtn = tab;
        let tabId = currentBtn.getAttribute("data-tab")
        let currentTabContent = document.querySelector(tabId)

        if (!currentBtn.classList.contains('active')) {
            horizontals.forEach(function (tab) {
                tab.classList.remove('active')
            })

            tabContent.forEach(function (item) {
                item.style.display='none'
            })
            currentBtn.classList.add('active')
            currentTabContent.style.display=''
        }
    })
})

const newUserForm = document.querySelector("#newUserForm")


async function getAllRoles() {
    const result = await fetch('http://localhost:8080/api/roles')
    return result.json()
}

async function createNewUser(e) {
    e.preventDefault();
    const roles = await getAllRoles()
    const formData = new FormData(newUserForm);
    const obj = {};
    formData.forEach((key, value) =>{
        if (value === 'roles') {
            const newArray = [];
            newArray.push(roles[key]);
            obj['roles'] = newArray;
        } else {
            obj[value] = key;
        }
    });

    try {
        await fetch('http://localhost:8080/api/users', {
            method: "POST",
            headers: {
                'content-type' : 'application/json'
            },
            body: JSON.stringify(obj)
        })
    } catch (error) {

    }

    const allTr = document.querySelectorAll(".mainTableTr");
    allTr.forEach((item) => {
        item.remove()
    })
}
const nav1 = document.querySelector("[data-tab='#tab_1']");
const nav2 = document.querySelector("[data-tab='#tab_2']");

const tab1 = document.getElementById("tab_1");
const tab2 = document.getElementById("tab_2");

newUserForm.addEventListener("submit", async (e) => {
    await createNewUser(e);
    e.target.reset();
    nav1.classList.add("active");
    nav2.classList.remove("active");
    tab2.style.display='none';
    tab1.style.display='';
    await buildUserTable();
});


buildUserTable();