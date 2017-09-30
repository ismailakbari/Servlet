/**
 * 
 */


var previousElement; //previous element had focus
function init(){
	//document.getElementById("username").focus();
	//document.forms['formLogin']['username'].focus();
	previousElement = document.forms['formLogin']['username'].getAttribute('name');
}

function checkUsername(element){
	var errorElement = document.getElementById("errorMessage");
	if(previousElement!="username")
		errorElement.innerHTML = "";
		
	//document.getElementById("test").innerHTML = "user=" + element.value;
	if(element.value.length==0)
		errorElement.innerHTML = "username cannot be empty";
	else{
		var firstChar = element.value.charAt(0);
		if(! ((firstChar>='a' && firstChar<='z'))|| ((firstChar>='A' && firstChar<='Z')))
			errorElement.innerHTML = "first char should be a letter";
	}	
	previousElement="username";
	//document.getElementById("test").innerHTML = previousElement;
}

function checkPassword(element){
	var errorElement = document.getElementById("errorMessage");
	if(previousElement!="password")
		errorElement.innerHTML = "";
	 
	if(element.value.length==0)
		errorElement.innerHTML = "password cannot be empty";
	else{
		var password = element.value;
		if(password.search(/[A-Z]/) && password.search(/[0-9]/)){
			//errorMessage.style.color = "green";
			//errorElement.innerHTML = "Strong password";
		}	
	}
	previousElement="password";
	//document.getElementById("test").innerHTML = previousElement;
}
/*
function resetErrorMessage(){
	var error = document.getElementById("errorMessage").innerHTML ;
	//document.getElementById("test").innerHTML = "error="+ error;
	if(error.length>0)
		document.getElementById("errorMessage").innerHTML = "";
}*/

function resetLoginMessage(){
	//reset login message
	var lm = document.getElementById("loginMessage") ;
	//document.getElementById("test").innerHTML = "test="+lm.innerHTML;	
	if(lm.length!=0)
		lm.innerHTML = "";	
}

			/* customer management */

/* check search fields */

function checkSearch(){
	var searchForm = document.forms['searchForm'];
	//var x = document.getElementById("myForm");
    var i;    
    var input;
    for (i = 0; i < searchForm.length; i++) {
    	input = searchForm.elements[i]; 
    	if(input.type==="text" && input.value!="" && input.value!=null)//if there was a non empty field    		
    		return true;
    }
    return false;	
}

