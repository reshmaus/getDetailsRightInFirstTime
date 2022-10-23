const registerForm = document.getElementById('register-form')
const registerUsername = document.getElementById('register-username')
const registerPassword = document.getElementById('register-password')
const registerEmail = document.getElementById('register-email')
const registerPhoneNumber = document.getElementById('register-phone-number')
const registerProviderCheckbox = document.getElementById('registry-provider-checkbox')
const registerProviderAdditionalDetails = document.getElementById('provider-additional-details')

const registerStreet1 = document.getElementById('register-street-1')
const registerStreet2 = document.getElementById('register-street-2')
const registerZipCode = document.getElementById('register-zip-code')
const registerCity = document.getElementById('register-city')
const registerState = document.getElementById('register-state')
const registerCountry = document.getElementById('register-country')

    const headers = {
          'Content-Type':'application/json'
    }

    const baseUserUrl = 'http://localhost:8080/api/v1/users'
    const baseProviderUrl = 'http://localhost:8080/api/v1/providers'

    const handleSubmit = async (e) => {
       e.preventDefault()

       let providerAdditionalDetail = registerProviderCheckbox.checked ?
       {
         providerName: registerUsername.value,
         street1: registerStreet1.value,
         street1: registerStreet2.value,
         zipcode: registerZipCode.value,
         city: registerCity.value,
         state: registerState.value,
         country: registerCountry.value,
       }
       :
       {};

       let bodyObj = {
           userName: registerUsername.value,
           password: registerPassword.value,
           email: registerEmail.value,
           phoneNumber: registerPhoneNumber.value,
           ...providerAdditionalDetail
           }

       const registerApiUrl = registerProviderCheckbox.checked ? `${baseProviderUrl}/register` : `${baseUserUrl}/register`;
       const response = await fetch(registerApiUrl, {
               method:"POST",
               body: JSON.stringify(bodyObj),
               headers: headers
       })
          .catch(err => console.error(err.message))

       const responseArr = await response.json()

       if (response.status === 200){
           window.location.replace(responseArr[0])
          }

    }

    const handleToggle = (e) => {
           e.preventDefault()
           if(registerProviderCheckbox.checked){
                registerProviderAdditionalDetails.classList.remove('hide')
           } else {
                registerProviderAdditionalDetails.classList.add('hide')
           }
        }


    registerForm.addEventListener("submit", handleSubmit)
    registerProviderCheckbox.addEventListener("change", handleToggle)