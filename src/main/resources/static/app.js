const mainTable = document.getElementById("mainTable");
const tableBody = document.getElementById("tableBody");
const adminPanel = document.getElementById("adminPanel");
const adminPanelContainer = document.getElementById("adminPanelContainer");
const adminPanelCreateUserContainer = document.getElementById("adminPanelCreateUserContainer");
const loginUserInfo = document.getElementById("loginUserInfo");
adminPanel.innerHTML='Admin panel';


const rowCount = mainTable.rows.length
const cellsCount = mainTable.rows[0].cells.length


async function getAllUsers() {
    const result = await fetch ('http://localhost:8080/api/users')
    return result.json()
}

function getDeleteModal(id) {
    const div1 = document.createElement("div");
    div1.setAttribute("class", "modal fade");
    div1.style.fontWeight="bold";
    // div1.style.display="block";
    // div1.setAttribute("aria-modal", "true");
    // div1.setAttribute("role", "dialog");
    div1.setAttribute("id", `deleteModal${id}`);
    div1.setAttribute("tabindex", "-1");
    div1.setAttribute("aria-labelledby", "exampleModalLabelDelete");
    div1.setAttribute("aria-hidden", "true");
    // mainTable.appendChild(div1);

    const div2 = document.createElement("div");
    div2.setAttribute("class", "modal-dialog");
    div1.appendChild(div2);

    const div3 = document.createElement("div");
    div3.setAttribute("class", "modal-content");
    div2.appendChild(div3);

    const div4 = document.createElement("div");
    div4.setAttribute("class", "modal-header");
    div3.appendChild(div4);

    const h5 = document.createElement("h5");
    h5.setAttribute("class", "modal-title");
    h5.setAttribute("id", "exampleModalLabelDelete");
    h5.innerText="Delete user";
    div4.appendChild(h5);

    const closeBtn = document.createElement("button");
    closeBtn.setAttribute("type", "button");
    closeBtn.setAttribute("class", "btn-close");
    closeBtn.setAttribute("data-bs-dismiss", "modal");
    closeBtn.setAttribute("aria-label", "Close");
    div4.appendChild(closeBtn);

    const form = document.createElement("form");
    div3.appendChild(form);
    form.setAttribute("id", "modalForm");
    // const createdForm = document.getElementById("modalForm");
    // console.log(form);
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        console.log(id)
        const formData = new FormData(form)
        // console.log(formData)
        formData.forEach((key, value) => {
            console.log(`${value} : ${key}`)
        })
        // deleteUser(id, form)
    });





    const div5 = document.createElement("div");
    div5.setAttribute("class", "col-md");
    form.appendChild(div5);
    div5.style.paddingLeft="100px";
    div5.style.paddingRight="100px";

    const labelDeleteId = document.createElement("label");
    labelDeleteId.setAttribute("for", "delete_id");
    labelDeleteId.setAttribute("class", "form-label");
    labelDeleteId.innerText="Id";
    div5.appendChild(labelDeleteId);

    const inputDelete = document.createElement("input");
    inputDelete.setAttribute("class", "form-control");
    inputDelete.setAttribute("type", "text");
    inputDelete.setAttribute("value", id);
    inputDelete.setAttribute("id", "delete_id");
    inputDelete.setAttribute("name", "delete_name");
    inputDelete.disabled=true;
    div5.appendChild(inputDelete);


    const modalFooter = document.createElement("div");
    modalFooter.setAttribute("class", "modal-footer");
    form.appendChild(modalFooter);

    const footerCloseBtn = document.createElement("button");
    footerCloseBtn.setAttribute("type", "button");
    footerCloseBtn.setAttribute("class", "btn btn-secondary");
    footerCloseBtn.setAttribute("data-bs-dismiss", "modal");
    footerCloseBtn.innerText="Close";
    modalFooter.appendChild(footerCloseBtn);

    const footerDeleteBtn = document.createElement("button");
    footerDeleteBtn.setAttribute("type", "submit");
    footerDeleteBtn.setAttribute("class", "btn btn-primary");
    footerDeleteBtn.innerText="Delete";
    modalFooter.appendChild(footerDeleteBtn);

    return div1;
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
                const deleteBtn = document.createElement("a");
                deleteBtn.setAttribute("type", "button");
                deleteBtn.setAttribute("class", "btn btn-danger");
                deleteBtn.setAttribute("data-bs-toggle", "modal");
                deleteBtn.setAttribute("data-bs-target", `#deleteModal${data[i-1].id}`);
                deleteBtn.innerText='Delete';
                deleteBtn.onclick = function () {
                    const modal = getDeleteModal(data[i-1].id)
                    mainTable.appendChild(modal);
                    // modal.style.display='block';
                }
                const editBtn = document.createElement("button");
                editBtn.setAttribute("class", "btn text-white");
                editBtn.style.backgroundColor="#17a2b8";
                editBtn.innerText='Edit';
                mainTable.rows[i].cells[0].innerHTML = data[i - 1].id;
                mainTable.rows[i].cells[1].innerHTML = data[i - 1].username;
                mainTable.rows[i].cells[2].innerHTML = data[i - 1].lastName;
                mainTable.rows[i].cells[3].innerHTML = data[i - 1].age;
                mainTable.rows[i].cells[4].innerHTML = data[i - 1].email;
                mainTable.rows[i].cells[5].innerHTML = `${data[i - 1].roles[0].simpleName}`;
                mainTable.rows[i].cells[6].appendChild(editBtn);
                mainTable.rows[i].cells[7].appendChild(deleteBtn);
                deleteBtn.onclick = function () {
                    const modal = getDeleteModal(data[i-1].id)
                    mainTable.rows[i].cells[7].appendChild(modal);
                    // this.click();
                }
                // const modal = getDeleteModal(data[i-1].id)
                // mainTable.rows[i].cells[7].appendChild(modal);
            }
        })
}
buildUserTable();

fetch('http://localhost:8080/api/loginUser')
    .then(res => res.json())
    .then(data => {
        let result = '';
        for(let i = 0; i < data.roles.length; i++) {
            // console.log(data.roles[i])
            result += data.roles[i].simpleName + " ";
        }
        loginUserInfo.innerHTML = `${data.email} with roles: ${result.trim()}`
        console.log(data)
    })


const horizontals = document.querySelectorAll(".nav-link");
const tabContent = document.querySelectorAll(".tab-content")

const selectedRoles = document.querySelector('#roles')



horizontals.forEach(function (tab) {
    tab.addEventListener("click", function () {
        let currentBtn = tab;
        let tabId = currentBtn.getAttribute("data-tab")
        let currentTabContent = document.querySelector(tabId)
        console.log(currentTabContent)
        console.log(tabId)

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
// const deleteModal = document.querySelector()
// const formUserName = newUserForm.firstNameInput
const lastNameInput = document.querySelector('#lastNameId')
const emailInput = document.querySelector('#emailId')
const ageInput = document.querySelector('#ageId')
const passwordInput = document.querySelector('#passwordId')
const addUserBtn = document.querySelector("#addUserBtn")


async function getAllRoles() {
    const result = await fetch('http://localhost:8080/api/roles')
    return result.json()
}

async function createNewUser(e) {
    e.preventDefault();
    let createdUser;
    const roles = await getAllRoles()
    // console.log(roles)
    const formData = new FormData(newUserForm);
    // console.log(formData);
    const obj = {};
    formData.forEach((key, value) =>{
        // console.log(`${value} : ${key}`)
        if (value === 'roles') {
            const newArray = [];
            newArray.push(roles[key]);
            console.log(newArray)
            obj['roles'] = newArray;
        } else {
            obj[value] = key;
        }
    });
    console.log(obj)
    createdUser = obj;


    try {
        await fetch('http://localhost:8080/api/users', {
            method: "POST",
            headers: {
                'content-type' : 'application/json'
            },
            body: JSON.stringify(createdUser)
        }).then(res => res.json())
            .then(data => console.log(data))
    } catch (error) {

    }

    const allTr = document.querySelectorAll(".mainTableTr");
    allTr.forEach((item) => {
        item.remove()
    })
}

function deleteUser(id, form) {
    const formData = new FormData(form);
    console.log(formData)
    // const data = formData.get("delete_id");
    formData.forEach((key, value) => {
        console.log(key)
        console.log(value)
    })
    // console.log(data);
}


newUserForm.addEventListener("submit", async (e) => {
    await createNewUser(e);
    const nav1 = document.querySelector("[data-tab='#tab_1']");
    const nav2 = document.querySelector("[data-tab='#tab_2']");
    // console.log(nav1);
    // console.log(nav2);
    nav1.classList.add("active");
    nav2.classList.remove("active");

    const tab1 = document.getElementById("tab_1");
    const tab2 = document.getElementById("tab_2");

    tab2.style.display='none';
    tab1.style.display='';
    await buildUserTable();
});


