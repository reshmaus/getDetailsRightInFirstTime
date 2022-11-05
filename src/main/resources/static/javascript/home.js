const loggedInUserId = getCookie('loggedInUserId')
const isProvider = getBool(getCookie('isProvider'))

//DOM Elements
const userDetailForm = document.getElementById('user-detail-form')
const userDetailFirstname = document.getElementById('user-detail-firstname')
const userDetailLastname = document.getElementById('user-detail-lastname')
const userDetailStreet1 = document.getElementById('user-detail-street-1')
const userDetailStreet2 = document.getElementById('user-detail-street-2')
const userDetailZipCode = document.getElementById('user-detail-zip-code')
const userDetailCity = document.getElementById('user-detail-city')
const userDetailState = document.getElementById('user-detail-state')
const userDetailCountry = document.getElementById('user-detail-country')
const userDetailInsurance = document.getElementById('user-detail-insurance')
const userDetailType = document.getElementById('user-detail-type')
const userDetailAdditionalDetails = document.getElementById('user-detail-additionalDetails')
const tableBody = document.getElementById("table-body")
const tableDetails = document.getElementById("table-details")
const addNewType = document.getElementById("create-new-button")
const submitForm = document.getElementById("note-form")
const errorDiv = document.getElementById("error-div")
const noteContainer = document.getElementById("note-container")
const logout = document.getElementById("logout")

if(!loggedInUserId){
    alert("Please login")
    window.location.replace("/login.html")
}

// Below code is for User Details
const baseUserDetailUrl = 'http://localhost:8080/api/v1/userDetail'

let selectedId = 0; // This has to be selected id

 const getUserDetail = (id) => {
        axios.get(`${baseUserDetailUrl}/getById/${id}`)
            .then((res) => {
                const userDetailInfo = res.data;
                console.log("--- get by id userDetail Info --",userDetailInfo);
                if(userDetailInfo.id) {
                     userDetailFirstname.value = userDetailInfo.firstName ;
                     userDetailLastname.value = userDetailInfo.lastName ;
                     userDetailStreet1.value = userDetailInfo.street1 ;
                     userDetailStreet2.value = userDetailInfo.street2 ;
                     userDetailZipCode.value = userDetailInfo.zipCode ;
                     userDetailCity.value = userDetailInfo.city ;
                     userDetailState.value = userDetailInfo.state ;
                     userDetailCountry.value = userDetailInfo.country ;
                     userDetailInsurance.value = userDetailInfo.insurance ;
                     userDetailType.value = userDetailInfo.type ;
                     userDetailAdditionalDetails.value = userDetailInfo.additionalDetails ;
                }
            });
    };

const editUserDetails = (id) => {
     selectedId = id;
     getUserDetail(id);
     userDetailForm.classList.remove('hide')
}

const deleteUserDetails = (id) => {
     axios.delete(`${baseUserDetailUrl}/delete/${id}`)
     .then(res => {
         alert("Information deleted successfully.. ");
         getAllUserUserDetail();
         userDetailForm.classList.add('hide')
     });
}


const shareUserDetails = (id) => {
     // Copy the text inside the text field
     navigator.clipboard.writeText(`User/${loggedInUserId}/${id}`);

     // Alert the copied text
     alert("Copied user id and details if to clipboard for sharing : " + `User/${loggedInUserId}/${id}`);
}


const getAllUserUserDetail = () => {
         tableBody.innerHTML = "";
        axios.get(`${baseUserDetailUrl}/getAllByUserId/${loggedInUserId}`)
            .then((res) => {
                const userDetailInfo  = res.data;
                console.log("--- All user user detail Info --", userDetailInfo);
                if(userDetailInfo && userDetailInfo.length > 0){
                    tableDetails.classList.remove('hide')
                    addNewType.classList.remove('hide')
                    for(let i=0; i<userDetailInfo.length; i++){
                        const tableRow = `<tr>
                                                 <th scope="row">${userDetailInfo[i].type}</th>
                                                 <td><button class="btn btn-secondary" onclick=shareUserDetails(${userDetailInfo[i].id})> Share </button></td>
                                                 <td><button class="btn btn-secondary" onclick=editUserDetails(${userDetailInfo[i].id})> Edit </button></td>
                                                 <td><button class="btn btn-secondary" onclick=deleteUserDetails(${userDetailInfo[i].id})> Delete </button></td>
                                               </tr>
                                             `;

                        tableBody.innerHTML += tableRow;
                    }
                } else {
                    tableDetails.classList.add('hide')
                    addNewType.classList.add('hide')
                    addNewClick();
                }
            });
    };

// Open call to method, on page and js load, beginning point of Js call
getAllUserUserDetail();

const createUpdateSubmit = async (e) => {
       e.preventDefault()
       if(!userDetailType.value || !userDetailFirstname.value || !userDetailLastname.value){
           errorDiv.classList.remove('hide')
           return;
       }
       errorDiv.classList.add('hide')

       let bodyObj = {
           firstName: userDetailFirstname.value,
           lastName: userDetailLastname.value,
           street1: userDetailStreet1.value,
           street2: userDetailStreet2.value,
           zipCode: userDetailZipCode.value,
           city: userDetailCity.value,
           state: userDetailState.value,
           country: userDetailCountry.value,
           insurance: userDetailInsurance.value,
           type: userDetailType.value,
           additionalDetails: userDetailAdditionalDetails.value,
       }
       let apiUrl = `${baseUserDetailUrl}/create/${loggedInUserId}`;

       //Update userDetail details with id
       if(selectedId > 0) {
            apiUrl = `${baseUserDetailUrl}/update`;
            bodyObj = {
                ...bodyObj,
                id: selectedId,
            }

            axios.put(apiUrl, bodyObj)
                 .then((res) => {
                     console.log("--Updated User Detail--", res.data)
                     if(res.data.id){
                         getUserDetail(res.data.id);
                         getAllUserUserDetail();
                     }
                 })
                 .catch(error => {
                     console.log(error.response.data.error)
                 })
       } else {
           // Create userDetail
           axios.post(apiUrl, bodyObj)
                 .then((res) => {
                     console.log("--Created User  Detail--", res.data)
                     if(res.data.id){
                        getAllUserUserDetail();
                        selectedId = res.data.id;
                        getUserDetail(res.data.id);
                     }
                 })
                  .catch(error => {
                      console.log(error.response.data.error)
                  })
       }
    }

const addNewClick = (e) => {
    if(e) { e.preventDefault() }
    selectedId = 0;
    userDetailForm.classList.remove('hide')

    userDetailFirstname.value = "";
    userDetailLastname.value = "";
    userDetailStreet1.value = "";
    userDetailStreet2.value = "";
    userDetailZipCode.value = "";
    userDetailCity.value = "";
    userDetailState.value = "";
    userDetailCountry.value = "";
    userDetailInsurance.value = "";
    userDetailType.value = "";
    userDetailAdditionalDetails.value = "";
}

userDetailForm.addEventListener("submit", createUpdateSubmit)
addNewType.addEventListener("click", addNewClick)

// Below this line is the code from class for note
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
        noteCard.innerHTML = `
            <div class="card d-flex" >
                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                    <p class="card-text mb-1">${obj.body}</p>
                    <div class="d-flex justify-content-end">
                        <button class="btn btn-link me-2 btn-sm" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getNoteById(${obj.id})" type="button" class="btn btn-link btn-sm"
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
function handleLogout(e){
    e.preventDefault()
    deleteCookie('loggedInUserId')
    window.location.replace("/login.html")
}

const populateModal = (obj) =>{
    noteBody.innerText = ''
    noteBody.innerText = obj.body
    updateNoteBtn.setAttribute('data-note-id', obj.id)
}

getNotes(loggedInUserId);

submitForm.addEventListener("submit", handleSubmit)
logout.addEventListener("click", handleLogout)

updateNoteBtn.addEventListener("click", (e)=>{
    let noteId = e.target.getAttribute('data-note-id')
    handleNoteEdit(noteId);
})