var xhttp = new XMLHttpRequest();
const form = document.getElementById('signup');
const userName = form.elements['username'];
const dataReserva = form.elements['data'];
const numPessoas = form.elements['numpessoas'];

var AdminName;
var UserName;
var DataReserva;
var NumPessoas;
var Cliente;
var clienteOK = false;
var i = 0;
var grupo;
var recurso = "reserva";
xhttp.open("POST", recurso, false);
try {
    xhttp.send("nomeAdmin");
    var xmlRec = xhttp.responseXML;
    grupo = xmlRec.getElementsByTagName("RESERVA");
    AdminName = grupo[i].getElementsByTagName("ADMIN")[0].childNodes[0].nodeValue;
    
} catch(err) {
    console.log("Erro " + err);
}


document.getElementById("nomeadmin").innerHTML = "Admin: " + AdminName;
document.getElementById("nomeadmin").style.fontSize = "43px";
document.getElementById("nomeadmin").style.paddingLeft = "20px";
document.getElementById("info1").innerHTML = "                                  ";
document.getElementById("info1").style.fontSize = "47px";
document.getElementById("info1").style.paddingLeft = "20px";

function Entrar() {
    UserName = userName.value;
    DataReserva = dataReserva.value;
    NumPessoas = numPessoas.value;
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(UserName);
        var xmlRec = xhttp.responseXML;
        i = 0;
        grupo = xmlRec.getElementsByTagName("RESERVA");
        Cliente = grupo[i].getElementsByTagName("CLIENTE")[0].childNodes[0].nodeValue;
        
        if (Cliente == "null") {
            document.getElementById("info1").innerHTML = "Cliente não cadastrado";
        }
        else {
            clienteOK = true;
            document.getElementById("info1").innerHTML = "Cliente: " + Cliente;
            document.getElementById("info1").style.fontSize = "47px";
            document.getElementById("info1").style.paddingLeft = "20px";
            
            document.getElementById("info2").innerHTML = "Escolha a mesa:";
            document.getElementById("info2").style.fontSize = "47px";
            document.getElementById("info2").style.paddingLeft = "20px";
        }
        console.log("Mensagem POST enviada: " + UserName);
    } catch(err) {
        console.log("Erro " + err);
    }
}

function reservaMesa(mesa) {
    
    if (clienteOK) {
        var msgConfirma = "Confirma a reserva da mesa " + mesa + " para " + Cliente + " no dia " + DataReserva + "?";
        if (confirm(msgConfirma)) {
            
            var MsgConfirmacao = "Confirmada a reserva da mesa " + mesa;
            document.getElementById("info2").innerHTML = MsgConfirmacao;
            document.getElementById("info2").style.fontSize = "47px";
            document.getElementById("info2").style.paddingLeft = "20px";
            //document.getElementById("info1").style.color = "#C70039";
            MsgConfirmacao = "para " + NumPessoas + " pessoas no dia " + DataReserva;
            document.getElementById("info3").innerHTML = MsgConfirmacao;
            document.getElementById("info3").style.fontSize = "47px";
            document.getElementById("info3").style.paddingLeft = "20px";
            
            document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
            document.getElementById(mesa).innerHTML = "Reservada";
        }
        else {
            document.getElementById("info1").innerHTML = "Por favor, escolha a mesa";
        }
    }
}






