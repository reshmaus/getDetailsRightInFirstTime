const loggedInUserId = getCookie('loggedInUserId')
const isProvider = getBool(getCookie('isProvider'))

//DOM Elements
const submitForm = document.getElementById("note-form")
const noteContainer = document.getElementById("note-container")

if(loggedInUserId){
    alert("Please login")
    window.location.replace("/login.html")
}

//Modal Elements
let noteBody = document.getElementById(`note-body`)
let updateNoteBtn = document.getElementById('update-note-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/notes/"

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        body: document.getElementById("note-input").value
    }
    await addNote(bodyObj);
    document.getElementById("note-input").value = ''
}

async function addNote(obj) {
    const urlToAdd = isProvider ? `${baseUrl}provider/${loggedInUserId}` : `${baseUrl}user/${loggedInUserId}`;
   const response = await fetch(urlToAdd, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getNotes(loggedInUserId);
    }
}

async function getNotes(loggedInUserId) {
   const urlToAdd = isProvider ? `${baseUrl}provider/${loggedInUserId}` : `${baseUrl}user/${loggedInUserId}`;
   await fetch(urlToAdd, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
}

async function handleDelete(noteId){
    await fetch(baseUrl + noteId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getNotes(loggedInUserId);
}

async function getNoteById(noteId){
    await fetch(baseUrl + noteId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleNoteEdit(noteId){
    let bodyObj = {
        id: noteId,
        body: noteBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getNotes(loggedInUserId);
}

const createNoteCards = (array) => {
    noteContainer.innerHTML = ''
    array.forEach(obj => {
        let noteCard = document.createElement("div")
        noteCard.classList.add("m-2")
        noteCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <p class="card-text">${obj.body}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getNoteById(${obj.id})" type="button" class="btn btn-primary"
                        data-bs-toggle="modal" data-bs-target="#note-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        noteContainer.append(noteCard);
    })
}
function handleLogout(){
    deleteCookie('loggedInUserId');
}

const populateModal = (obj) =>{
    noteBody.innerText = ''
    noteBody.innerText = obj.body
    updateNoteBtn.setAttribute('data-note-id', obj.id)
}

getNotes(loggedInUserId);

submitForm.addEventListener("submit", handleSubmit)

updateNoteBtn.addEventListener("click", (e)=>{
    let noteId = e.target.getAttribute('data-note-id')
    handleNoteEdit(noteId);
})