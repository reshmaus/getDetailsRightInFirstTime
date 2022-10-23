const loginForm = document.getElementById('login-form')
const loginUsername = document.getElementById('login-username')
const loginPassword = document.getElementById('login-password')
const registerProviderCheckbox = document.getElementById('registry-provider-checkbox')

    if(registerProviderCheckbox.checked) {
        setCookie('isProvider', true)
    } else {
        setCookie('isProvider', false)
    }

    const headers = {
         'Content-Type':'application/json'
    }

    const baseUserUrl = 'http://localhost:8080/api/v1/users'
    const baseProviderUrl = 'http://localhost:8080/api/v1/providers'

    const handleSubmit = async (e) => {
       e.preventDefault()

       let nameType = registerProviderCheckbox.checked ?
       {
            providerName: loginUsername.value,
       }
       :
       {
            userName: loginUsername.value,
       }

       let bodyObj = {
           ...nameType,
           password: loginPassword.value
       }

       const loginApiUrl = registerProviderCheckbox.checked ? `${baseProviderUrl}/login` : `${baseUserUrl}/login`;

       const response = await fetch(loginApiUrl,{
               method:"POST",
               body: JSON.stringify(bodyObj),
               headers: headers
       })
          .catch(err => console.error(err.message))

       const responseArr = await response.json()

       if (response.status === 200){
           setCookie('loggedInUserId', responseArr[1])
           window.location.replace(responseArr[0])
       }

    }
    loginForm.addEventListener("submit", handleSubmit)

    const handleProviderToggle = (e) => {
       e.preventDefault()
       if(registerProviderCheckbox.checked){
           setCookie('isProvider', true)
       } else {
           setCookie('isProvider', false)
       }
    }


    registerProviderCheckbox.addEventListener("change", handleProviderToggle)