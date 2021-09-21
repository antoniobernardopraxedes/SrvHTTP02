var xhttp = new XMLHttpRequest();
const form = document.getElementById('signup');
const userName = form.elements['username'];
const dataReserva = form.elements['data'];
const numPessoas = form.elements['numpessoas'];

var UserName;
var DataReserva;
var NumPessoas;
 
var Cliente;
var clienteOK = false;
document.getElementById("info1").innerHTML = "Bem Vindo à nossa página de reservas";
document.getElementById("info1").style.fontSize = "47px";
document.getElementById("info1").style.paddingLeft = "20px";

function Entrar() {
    UserName = userName.value;
    DataReserva = dataReserva.value;
    NumPessoas = numPessoas.value;
    
    var recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(UserName);
        var xmlRec = xhttp.responseXML;
        var i = 0;
        var reserva = xmlRec.getElementsByTagName("RESERVA");
        Cliente = reserva[i].getElementsByTagName("CLIENTE")[0].childNodes[0].nodeValue;
        if (Cliente == "null") {
            document.getElementById("info1").innerHTML = "Cliente não cadastrado";
        }
        else {
            clienteOK = true;
            //document.getElementById("cliente").innerHTML = "Cliente: " + Cliente;
            document.getElementById("info1").innerHTML = "Por favor, escolha a mesa";
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
            var codigo = prompt("Entre com o seu código de autorização");
            
            var MsgConfirmacao = "Confirmada a reserva da mesa " + mesa;
            document.getElementById("info1").innerHTML = MsgConfirmacao;
            document.getElementById("info1").style.fontSize = "47px";
            document.getElementById("info1").style.paddingLeft = "20px";
            //document.getElementById("info1").style.color = "#C70039";
            MsgConfirmacao = "para " + NumPessoas + " pessoas no dia " + DataReserva + ".";
            document.getElementById("info2").innerHTML = MsgConfirmacao;
            document.getElementById("info2").style.fontSize = "47px";
            document.getElementById("info2").style.paddingLeft = "20px";
            //document.getElementById("info2").style.color = "#C70039";
            MsgConfirmacao = "Cliente: " + Cliente;
            document.getElementById("info3").innerHTML = MsgConfirmacao;
            document.getElementById("info3").style.fontSize = "47px";
            document.getElementById("info3").style.paddingLeft = "20px";
            //document.getElementById("info3").style.color = "#C70039";
            
            document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
            document.getElementById(mesa).innerHTML = "Reservada";
        }
        else {
            document.getElementById("info1").innerHTML = "Por favor, escolha a mesa";
        }
    }
}






