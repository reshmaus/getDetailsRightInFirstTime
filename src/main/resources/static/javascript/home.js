const loggedInUserId = getCookie('loggedInUserId')
const loggedInUserName = getCookie('loggedInUserName')
const isProvider = getBool(getCookie('isProvider'))

//DOM Elements
const userDetailForm = document.getElementById('user-detail-form')
const userDetailFormActual = document.getElementById('user-detail-form-actual')
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
const tableSharedDetails = document.getElementById("table-shared-details")
const viewSharedViewDetailsDiv = document.getElementById("view-share-view-details")
const tableSavedDetails = document.getElementById("table-saved-details")
const tableSharedBody = document.getElementById("table-shared-details-body")
const tableDetails = document.getElementById("table-details")
const showTables = document.getElementById("show_table")
const addNewType = document.getElementById("create-new-button")
const submitForm = document.getElementById("note-form")
const fetchShareUserDetails = document.getElementById("fetch-share-user-details")
const viewShareDetailsTable = document.getElementById("view-shared-details")
const viewShareUserDetails = document.getElementById("view-shared-user-details")
const typeOfUser = document.getElementById("type-of-user")
const noteContainer = document.getElementById("note-container")
const logout = document.getElementById("logout")
const guestCheckbox = document.getElementById('guest-checkbox')
const toggleRefresh = document.getElementById('toggle-refresh')
const formSubmitButton = document.getElementById('submit-button')

let counterToStopApiCall = 0
let localStorageKey = `U_${loggedInUserId}`
let onlyViewNoEditForm = false;

if(!loggedInUserId){
    alert("Please login")
    window.location.replace("/login.html")
}

const viewSharedUserDetails = (setValueToLocalStorage = "") => {
    let fetchExistingCollection = localStorage.getItem(localStorageKey) || setValueToLocalStorage || '';
    let storageCollection = fetchExistingCollection ? fetchExistingCollection.split(",") : [];
    let uniqueStorageCollection = [...new Set(storageCollection)]

    if(uniqueStorageCollection.length > 0){
        viewShareDetailsTable.classList.remove('hide')
         showTables.classList.remove('hide')
        tableSharedDetails.classList.remove('hide')
    } else {
        viewShareDetailsTable.classList.add('hide')
        tableSharedDetails.classList.add('hide')
    }
    tableSharedBody.innerHTML = "";

    for(let i=0; i < uniqueStorageCollection.length; i++){
        let splitDetails = uniqueStorageCollection[i].split('/');
        const tableRow = `<tr>
                             <th scope="row">${splitDetails[0]}</th>
                             <td><button class="btn btn-secondary" onclick=viewSharedGuestDetails('${uniqueStorageCollection[i].trim()}')> View </button></td>
                             <td><button class="btn btn-secondary" onclick=deleteSharedUserDetails('${uniqueStorageCollection[i].trim()}')> Delete </button></td>
                           </tr>
                          `;
        tableSharedBody.innerHTML += tableRow;
    }
}

const viewSharedGuestDetails = (sharedValue) => {
    let splitDetails = sharedValue.split('/')
    userDetailForm.classList.remove('hide')
    // Id and user id
    getUserDetail(splitDetails[2], true)
    onlyViewNoEditForm = true;
    enableToggleRefreshButton();
}

const deleteSharedUserDetails = (sharedValue) => {
    let fetchExistingCollection = localStorage.getItem(localStorageKey) || '';
    let storageCollection = fetchExistingCollection ? fetchExistingCollection.split(",") : [];
    var updatedSharedCollection = [];
    let uniqueStorageCollection = [...new Set(storageCollection)]
    for(let i=0; i < uniqueStorageCollection.length; i++){
        if(sharedValue != uniqueStorageCollection[i].trim()){
            updatedSharedCollection.push(uniqueStorageCollection[i])
        }
    }
    localStorage.setItem(localStorageKey, updatedSharedCollection.toString());

    let sharedSplit = sharedValue.split('/');
    if(sharedSplit.length > 0 && sharedSplit[2] == selectedId){
        userDetailForm.classList.add('hide')
        onlyViewNoEditForm = false;
     }
    viewSharedUserDetails(updatedSharedCollection.toString());
}

const enableToggleRefreshButton = () => {
    if(isProvider && selectedId > 0 && !onlyViewNoEditForm){
        toggleRefresh.classList.remove('hide')
    } else {
        toggleRefresh.classList.add('hide')
        // To stop the setInterval in case its active
        counterToStopApiCall = 21
    }

    if(onlyViewNoEditForm){
        var elements = userDetailFormActual.elements;
         for (var i = 0, len = elements.length; i < len; ++i) {
             elements[i].readOnly = true;
             elements[i].disabled = true;
         }
         formSubmitButton.classList.add('hide')
    } else {
        var elements = userDetailFormActual.elements;
         for (var i = 0, len = elements.length; i < len; ++i) {
             elements[i].readOnly = false;
             elements[i].disabled = false;
         }
         formSubmitButton.classList.remove('hide')
    }
}

if(isProvider){
    typeOfUser.innerHTML = `Hello Provider ${loggedInUserName}`
    localStorageKey = `P_${loggedInUserId}`
    let storedValue = localStorage.getItem(localStorageKey) || '';
    viewSharedViewDetailsDiv.classList.remove('hide')
    if(storedValue !== "") {
        viewSharedUserDetails(storedValue);
        viewShareDetailsTable.classList.remove('hide')
        showTables.classList.remove('hide')
    } else {
        viewShareDetailsTable.classList.add('hide')
    }
    showTables.classList.remove('hide')
    tableDetails.classList.remove('hide')
    addNewType.classList.remove('hide')
} else {
     viewSharedViewDetailsDiv.classList.add('hide')
    typeOfUser.innerHTML = `Hello User ${loggedInUserName}`
}
// Below code is for User Details
const baseUserDetailUrl = 'http://localhost:8080/api/v1/userDetail'
const baseGuestUrl = 'http://localhost:8080/api/v1/guest'

let selectedId = 0; // This has to be selected id

 const getUserDetail = (id, isUser= false) => {
        let getApi = `${baseUserDetailUrl}/getById/${id}`
        if(isProvider && !isUser){
            getApi = `${baseGuestUrl}/getById/${id}`
        }
        axios.get(getApi)
            .then((res) => {
                const userDetailInfo = res.data;
                console.log("--- get by id userDetail Info --",userDetailInfo);

                if(userDetailInfo.id) {
                     selectedId = userDetailInfo.id;
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
     onlyViewNoEditForm = false;
     enableToggleRefreshButton();
}

const deleteUserDetails = (id) => {
     let deleteApi = `${baseUserDetailUrl}/delete/${id}`
     if(isProvider){
         deleteApi = `${baseGuestUrl}/delete/${id}`
     }
     if(id == selectedId){
        enableToggleRefreshButton()
     }

     axios.delete(deleteApi)
     .then(res => {
         alert("Information deleted successfully.. ");
         getAllUserUserDetail();
         if(id === selectedId){
            userDetailForm.classList.add('hide')
         }
     });
}


const shareUserDetails = (id, selectedName) => {
    let shareUrl = `${selectedName}/${loggedInUserId}/${id}/user`
    if(isProvider){
        shareUrl = window.location.origin + `/guest.html?id=${id}`
    }

    // Copy the text inside the text field
    navigator.clipboard.writeText(shareUrl);

    // Alert the copied text
    alert("Copied user id and details if to clipboard for sharing : " + shareUrl);
}


const getAllUserUserDetail = () => {
        tableBody.innerHTML = "";

        let getApi = `${baseUserDetailUrl}/getAllByUserId/${loggedInUserId}`;
        if(isProvider){
             getApi = `${baseGuestUrl}/getAllGuestByProviderId/${loggedInUserId}`;
        }

        axios.get(getApi)
            .then((res) => {
                const userDetailInfo  = res.data;
                console.log("--- All user user detail Info --", userDetailInfo);
                if(userDetailInfo && userDetailInfo.length > 0){
                    tableDetails.classList.remove('hide')
                    showTables.classList.remove('hide')
                    addNewType.classList.remove('hide')
                    tableSavedDetails.classList.remove('hide')

                    for(let i=0; i<userDetailInfo.length; i++){
                        const tableRow = `<tr>
                                             <th scope="row">${userDetailInfo[i].type}</th>
                                             <td><button class="btn btn-secondary" onclick=shareUserDetails(${userDetailInfo[i].id},'${userDetailInfo[i].firstName}')> Share </button></td>
                                             <td><button class="btn btn-secondary" onclick=editUserDetails(${userDetailInfo[i].id})> Edit </button></td>
                                             <td><button class="btn btn-secondary" onclick=deleteUserDetails(${userDetailInfo[i].id})> Delete </button></td>
                                           </tr>
                                          `;

                        tableBody.innerHTML += tableRow;
                    }
                } else {
                    if(!isProvider) {
                        tableDetails.classList.add('hide')
                        addNewType.classList.add('hide')
                        addNewClick();
                    } else {
                        tableDetails.classList.remove('hide')
                        showTables.classList.remove('hide')
                        addNewType.classList.remove('hide')
                    }
                    tableSavedDetails.classList.add('hide')
                }
            });
    };

// Open call to method, on page and js load, beginning point of Js call
getAllUserUserDetail();

const createUpdateSubmit = async (e) => {
       e.preventDefault()
//       if(!userDetailType.value || !userDetailFirstname.value || !userDetailLastname.value){
//           errorDiv.classList.remove('hide')
//           return;
//       }
//       errorDiv.classList.add('hide')

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
       onlyViewNoEditForm = false;
       let createApi = `${baseUserDetailUrl}/create/${loggedInUserId}`;
       let updateApi = `${baseUserDetailUrl}/update`;
       if(isProvider){
            createApi = `${baseGuestUrl}/createGuestByProviderId/${loggedInUserId}`;
            updateApi = `${baseGuestUrl}/update`;
       }
       //Update userDetail details with id
       if(selectedId > 0) {
            bodyObj = {
                ...bodyObj,
                id: selectedId,
            }

            axios.put(updateApi, bodyObj)
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
           axios.post(createApi, bodyObj)
                 .then((res) => {
                     console.log("--Created User  Detail--", res.data)
                     if(res.data.id){
                        getAllUserUserDetail();
                        selectedId = res.data.id;
                        getUserDetail(res.data.id);
                        enableToggleRefreshButton()
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

const handleRefreshToggle = (e) => {
   e.preventDefault()
   if(guestCheckbox.checked){
         var elements = userDetailFormActual.elements;
         for (var i = 0, len = elements.length; i < len; ++i) {
             elements[i].readOnly = true;
             elements[i].disabled = true;
         }
         // This is where it keep calling api for every 2sec for auto refresh full details
         interval = setInterval(function() {
           getUserDetail(selectedId);
           counterToStopApiCall++;
         }, 2000);

        if(counterToStopApiCall == 15){
            clearInterval(interval);
        }
   } else {
        counterToStopApiCall = 21;
        clearInterval(interval);
        var elements = userDetailFormActual.elements;
        for (var i = 0, len = elements.length; i < len; ++i) {
             elements[i].readOnly = false;
             elements[i].disabled = false;
        }
   }
}


const fetchShareUserDetailsClick = (e) => {
   e.preventDefault()
   let sharedValue = viewShareUserDetails.value;
   let fetchExistingCollection = localStorage.getItem(localStorageKey) || '';
   let storageCollection = fetchExistingCollection ? fetchExistingCollection.split(",") : [];
   storageCollection.push(sharedValue.trim());

   var updatedSharedCollection = '';
   let uniqueStorageCollection = [...new Set(storageCollection)]

   localStorage.setItem(localStorageKey, uniqueStorageCollection.toString());

   viewSharedUserDetails(uniqueStorageCollection.toString());
}


guestCheckbox.addEventListener("change", handleRefreshToggle)
userDetailForm.addEventListener("submit", createUpdateSubmit)
addNewType.addEventListener("click", addNewClick)
fetchShareUserDetails.addEventListener("click", fetchShareUserDetailsClick)

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