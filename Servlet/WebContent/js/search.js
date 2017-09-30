/**
 * 
 */


/*--------------------search.jsp----------------*/

function editCustomer(editButton){	

	var tableRow = editButton.parentNode.parentNode; //tr.td.label
	var i=1;
	var inner= tableRow.cells[0].outerHTML;
	for(i=1;i<tableRow.cells.length-2; i++){ //all columns except 1st and the last 2
		inner +=  "<td><input type='text' value='" + tableRow.cells[i].innerHTML +"'/></td>"
		//tableRow.cells[i].type=inner;
		//console.log("inner="+inner);
	}
	//show save, hide edit
	//edit, save, delete
	//editButton.style.visibility = "hidden";
	//editButton.style.display = "none";
	
	tableRow.getElementsByTagName("Button")[0].style.display = "none"; //edit
	tableRow.getElementsByTagName("Button")[1].style.display = "block"; //save
	inner+= tableRow.cells[i].outerHTML + tableRow.cells[i+1].outerHTML; 
	tableRow.innerHTML = inner;
	//console.log("inner="+inner);

}



function saveCustomer(saveButton){
	
	var customerInfo="";
	var tableRow = saveButton.parentNode.parentNode; //tr.td.input
	var i=1;
	var customerInfo =[];
	var inner= tableRow.cells[0].outerHTML;
	for(i=1;i<tableRow.cells.length-2; i++){ //all columns except 1st and the last 2		
		var value = tableRow.cells[i].getElementsByTagName("input")[0].value ;
		inner +=  "<td>" + value +"</td>"
		customerInfo[i-1]= value;
		
		//tableRow.cells[i].type=inner;
		//console.log("inner="+inner);
	}
	//show save, hide edit
	//edit, save, delete
	//editButton.style.visibility = "hidden";
	//editButton.style.display = "none";
	
	tableRow.getElementsByTagName("Button")[0].style.display = "block"; //edit
	tableRow.getElementsByTagName("Button")[1].style.display = "none"; //save
	inner+= tableRow.cells[i].outerHTML + tableRow.cells[i+1].outerHTML; 
	
	
	//update the customer
	var xhttp;    
	 /* if (str == "") {
	    document.getElementById("txtHint").innerHTML = "";
	    return;
	  }*/
	  xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var result = parseInt(this.responseText);// 0 or 1
	    	console.log("result="+result);
	    	if(result==1) //update the row
	    		tableRow.innerHTML = inner;
	    	else
	    		alert("Could not update. please try again.");
	    }
	  };
	  var getdate = new Date();  //Used to prevent caching during ajax call
	  xhttp.open("POST", "UpdateCustomer.jsp", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  //var params = "customerInfo="+JSON.stringify(customerInfo);  
	  xhttp.send("customerInfo="+customerInfo);
	
}



function addCustomer(addButton){	
	
	var tableRow = addButton.parentNode.parentNode; //tr.td.input
	var i=1;
	var customerInfo =[];
	var inner= tableRow.cells[0].outerHTML;
	for(i=1;i<tableRow.cells.length-1; i++){ //all columns except 1st and last 		
		var value = tableRow.cells[i].getElementsByTagName("input")[0].value ;
		inner +=  "<td>" + value +"</td>"
		customerInfo[i-1]= value;
		
		//tableRow.cells[i].type=inner;
		//console.log("inner="+inner);
	}
	//check empty values
	//console.log(customerInfo[0].length)
	if(customerInfo[0].length==0 ||customerInfo[1].length==0
			||customerInfo[3].length==0 ||customerInfo[4].length==0 ) //customerID, firstName, lastName and address can not be NULL
		return false;
	
	inner+="<td style='background-color:#45B39D'>" 
				+ "<button name='editButton' style='font-size:36px; color:#45B39D' onclick='editCustomer(this)'> <i class='material-icons'>mode_edit</i></button>"
				+ "<button name='saveButton' style='font-size:36px; color:#45B39D; display: none;' onclick='saveCustomer(this)'> <i class='material-icons'>save</i></button>"
			+"</td>"
			+"<td style='background-color:#45B39D'><button name='deleteButton' style='font-size:36px; color:#45B39D' onclick='deleteCustomer(this)'> <i class='material-icons'>delete</i></button>"
			+"</td>" ;
	
	//show save, hide edit
	//edit, save, delete
	//editButton.style.visibility = "hidden";
	//editButton.style.display = "none";
	
	//tableRow.getElementsByTagName("Button")[0].style.display = "block"; //edit
	//tableRow.getElementsByTagName("Button")[1].style.display = "none"; //save
	//inner+= tableRow.cells[i].outerHTML + tableRow.cells[i+1].outerHTML; 
	
	//tableRow.innerHTML = inner;
	//update the customer
	var tableRowCopy = tableRow.cloneNode(true); //make a copy of the row and add it to the end of the table
	tableRowCopy.cells[0].innerHTML = parseInt(tableRowCopy.cells[0].innerHTML) + 1; 
	var inputs = tableRowCopy.getElementsByTagName("input");
	console.log("inputs="+inputs.length);
	for(i=0; i<inputs.length; i++) //empty the text boxes
		inputs[i].value="";
	var xhttp;    
	 /* if (str == "") {
	    document.getElementById("txtHint").innerHTML = "";
	    return;
	  }*/
	  xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var result = parseInt(this.responseText);// 0 or 1
	    	console.log("result="+result);
	    	if(result==1){ //update the row
	    		tableRow.innerHTML = inner;
	    		tableRow.parentNode.appendChild(tableRowCopy);
	    	}	
	    	else
	    		alert("Could not add the customer. please try again.");
	    }
	  };
	  var getdate = new Date();  //Used to prevent caching during ajax call
	  xhttp.open("POST", "addCustomer.jsp", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  //var params = "customerInfo="+JSON.stringify(customerInfo);  
	  xhttp.send("customerInfo="+customerInfo);
	
}


function deleteCustomer(deleteButton){
	
	var customerInfo="";
	var tableRow = deleteButton.parentNode.parentNode; //tr.td.input
	var i=1;
	var customerInfo =[];
	//var inner= tableRow.cells[0].outerHTML;
	for(i=1;i<tableRow.cells.length-2; i++){ //all columns except 1st and two last ones 		
		var value = tableRow.cells[i].innerHTML ;
		//inner +=  "<td>" + value +"</td>"
		customerInfo[i-1]= value;
		
		//tableRow.cells[i].type=inner;
		//console.log("inner="+inner);
	}
	
	var xhttp;    
	 /* if (str == "") {
	    document.getElementById("txtHint").innerHTML = "";
	    return;
	  }*/
	  xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var result = parseInt(this.responseText);// 0 or 1
	    	console.log("delete result="+result);
	    	if(result==1){ //update the row
	    		
	    		tableRow.parentNode.removeChild(tableRow);
	    	}	
	    	else
	    		alert("Could not remove the customer. please try again.");
	    }
	  };
	  var getdate = new Date();  //Used to prevent caching during ajax call
	  xhttp.open("POST", "deleteCustomer.jsp", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  //var params = "customerInfo="+JSON.stringify(customerInfo);  
	  xhttp.send("customerInfo="+customerInfo);
	
}
