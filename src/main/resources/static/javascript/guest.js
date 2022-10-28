const urlParams = new URLSearchParams(location.search);
const id = urlParams.get('id')

const guestForm = document.getElementById('guest-form')
const guestFirstname = document.getElementById('guest-firstname')
const guestLastname = document.getElementById('guest-lastname')
const guestStreet1 = document.getElementById('guest-street-1')
const guestStreet2 = document.getElementById('guest-street-2')
const guestZipCode = document.getElementById('guest-zip-code')
const guestCity = document.getElementById('guest-city')
const guestState = document.getElementById('guest-state')
const guestCountry = document.getElementById('guest-country')
const guestInsurance = document.getElementById('guest-insurance')
const guestType = document.getElementById('guest-type')
const guestAdditionalDetails = document.getElementById('guest-additionalDetails')
const guestCheckbox = document.getElementById('guest-checkbox')

    let counterToStopApiCall = 0;
    let interval = {}
    const baseGuestUrl = 'http://localhost:8080/api/v1/guest'

    const getGuestDetail = () => {
        axios.get(`${baseGuestUrl}/getById/${id}`)
            .then((res) => {
                const guestInfo = res.data;
                console.log("--- get by id guest Info --",guestInfo);
                if(guestInfo.id) {
                     guestFirstname.value = guestInfo.firstName ;
                     guestLastname.value = guestInfo.lastName ;
                     guestStreet1.value = guestInfo.street1 ;
                     guestStreet2.value = guestInfo.street2 ;
                     guestZipCode.value = guestInfo.zipCode ;
                     guestCity.value = guestInfo.city ;
                     guestState.value = guestInfo.state ;
                     guestCountry.value = guestInfo.country ;
                     guestInsurance.value = guestInfo.insurance ;
                     guestType.value = guestInfo.type ;
                     guestAdditionalDetails.value = guestInfo.additionalDetails ;
                }
            });
    };

    // Open call method, on js load
    if(id > 0) {
        getGuestDetail();
        interval = setInterval(function() {
           getGuestDetail();
           counterToStopApiCall++;
         }, 10000);

        if(counterToStopApiCall == 15){
            clearInterval(interval);
        }
    }

    const createUpdateSubmit = async (e) => {
       e.preventDefault()

       let bodyObj = {
           firstName: guestFirstname.value,
           lastName: guestLastname.value,
           street1: guestStreet1.value,
           street2: guestStreet2.value,
           zipCode: guestZipCode.value,
           city: guestCity.value,
           state: guestState.value,
           country: guestCountry.value,
           insurance: guestInsurance.value,
           type: guestType.value,
           additionalDetails: guestAdditionalDetails.value,
       }
       let apiUrl = `${baseGuestUrl}/create`;

       //Update Guest details with id
       if(id > 0) {
            apiUrl = `${baseGuestUrl}/update`;
            bodyObj = {
                ...bodyObj,
                id: id,
            }

            axios.put(apiUrl, bodyObj)
                 .then((res) => {
                     console.log("--Guest Detail--", res.data)
                     if(res.data.id){
                         getGuestDetail(); //Refresh data on page
                         //window.location.replace(`/guest.html?id=${res.data.id}`)
                     }
                 })
                 .catch(error => {
                     console.log(error.response.data.error)
                 })
       } else {
           // Create Guest
           axios.post(apiUrl, bodyObj)
                 .then((res) => {
                     console.log("--Guest Detail--", res.data)
                     if(res.data.id){
                         window.location.replace(`/guest.html?id=${res.data.id}`)
                     }
                 })
                  .catch(error => {
                      console.log(error.response.data.error)
                  })
       }
    }


     const handleRefreshToggle = (e) => {
       e.preventDefault()
       if(guestCheckbox.checked){
            counterToStopApiCall = 21;
            clearInterval(interval);
       } else {
             interval = setInterval(function() {
               getGuestDetail();
               counterToStopApiCall++;
             }, 5000);

            if(counterToStopApiCall == 15){
                clearInterval(interval);
            }
       }
    }

    guestCheckbox.addEventListener("change", handleRefreshToggle)
    guestForm.addEventListener("submit", createUpdateSubmit)

// Without Axios below code
//        const headers = {
//             'Content-Type':'application/json'
//        }
//       const response = await fetch(apiUrl, {
//               method: id > 0 ? "PUT" : "POST",
//               body: JSON.stringify(bodyObj),
//               headers: headers
//       })
//       .catch(err => console.error(err.message))
//
//       const responseArr = await response.json()
//
//       console.log("--Guest Detail--", responseArr)
//
//       if (response.status === 200){
//           window.location.replace(`/guest.html?id=${responseArr.id}`)
//       }


