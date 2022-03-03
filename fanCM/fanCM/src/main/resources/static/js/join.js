let CHECK_STATUS = false;
let LOGIN_ID_STATUS = false;

async function checkDupleLoginId(){

    let inputLoginId = document.querySelector("#loginId");
    let loginId = inputLoginId.value;

    await fetch("http://localhost:8085/members/check/id?loginId=" + loginId)
    .then(

        (response) => {
            return response.json();
        }
    )
    .then(

        (data) => {

            let idCheck = data;

            console.log("idCheck")
            console.log(idCheck);
            console.log("idCheck.status")
            console.log(idCheck.status);

            if(idCheck.status || loginId === ""){

            LOGIN_ID_STATUS = false;
            console.log(LOGIN_ID_STATUS);
            alert("이미 존재하는 아이디 입니다.");

            }else{

            LOGIN_ID_STATUS = true;
            console.log(LOGIN_ID_STATUS);
            alert("가입할 수 있는 아이디 입니다.")

            }
        }
    )
    .catch(
        (error) => {
            console.log(error);
        }
    )
}

function checkStatus(){

    if(LOGIN_ID_STATUS){
        CHECK_STATUS = true;
    }else{
        CHECK_STATUS = false;
    }

    if( !CHECK_STATUS ){
    alert("중복확인을 해주시기 바랍니다.");
    return false;
    }

}
