let selectx = document.getElementById("XSelect")
let ytext = document.getElementById("YText")
let rradios = document.getElementsByName("RRadio")
let rradio = null

let xmlhttp = new XMLHttpRequest()

xmlhttp.onload = function(){
    console.log(xmlhttp.responseText)
    let responseJSON = JSON.parse(xmlhttp.responseText)
    let x, y, r, status
    x = responseJSON.X
    y = responseJSON.Y
    r = responseJSON.R
    status = responseJSON.Status
    newRow(x, y, r, status)
}


function newRow(x, y, r, status){
    let restable = document.getElementById("resulttable")
    let tbody = restable.getElementsByTagName("tbody")[0] 

    let cellx = document.createElement("td")
    let celly = document.createElement("td")
    let cellr = document.createElement("td")
    let cellstatus = document.createElement("td")
    let row = document.createElement("tr")

    cellx.innerHTML = x
    celly.innerHTML = y
    cellr.innerHTML = r
    cellstatus.innerHTML = status


    row.appendChild(cellx)
    row.appendChild(celly)
    row.appendChild(cellr)
    row.appendChild(cellstatus)
    tbody.appendChild(row)
    
}


for(var i=0; i<rradios.length; i++){
    if(rradios[i].checked){
        rradio = rradios[i]
    }
}


function getValuesFromDoc(){
    selectx = document.getElementById("XSelect")
    ytext = document.getElementById("YText")
    rradios = document.getElementsByName("RRadio")
    rradio = null
    for(let i=0; i<rradios.length; i++){
        if(rradios[i].checked){
            rradio = rradios[i]
            break
        }
    }
    
}


function submitFunc(){
    getValuesFromDoc()


    if(validation(ytext.value))
    {
        //console.log(true)
        let url = new URL("http://localhost:24585/fcgi-bin/Lab1.jar");
        url.searchParams.append("X", selectx.value)
        url.searchParams.append("Y", ytext.value)
        url.searchParams.append("R", rradio.value)
        xmlhttp.open("GET", url, true)
        xmlhttp.send(null)
    }
}


function validation(input){
    try{
       if(rradio.value == null) {
            console.log(rradio.value)
            return false
        }
    } catch (e){
        console.log("rradio exception")
        return false
    }

    if(input != "" && input != null){
        let i=0;
        let isDigit = true
        if(input[0] == "-"){
            i=1
        }
        for(; i<input.length; i++){
            let char = input[i]
            if(char >= 0 && char <= 9){
                //console.log(char)
                continue
            }
            else {
                //alert("notdigit")
                isDigit = false
            }
        }
        if(isDigit){
            let int = parseInt(input)
            if(int >= -5 && int <= 5){
                ytext.style.borderColor = "#777"
                return true
            }
            else{
                ytext.style.borderColor="red"
                return false
            }


            
        }
        else {
            ytext.style.borderColor="red"
            return false
        }

    }
}