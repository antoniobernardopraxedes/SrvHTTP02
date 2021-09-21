
const form = document.getElementById('signup');
const email = form.elements['email'];
const numPessoas = form.elements['numpessoas'];
const dataReserva = form.elements['data'];
var NumPessoas;
var emailAddress;
var DataReserva; 
var Cliente;

function Entrar() {
    var xhttp = new XMLHttpRequest();
    NumPessoas = numPessoas.value;
    emailAddress = email.value;
    DataReserva = dataReserva.value;
    
    var recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(emailAddress);
        var xmlRec = xhttp.responseXML;
        var i = 0;
        var reserva = xmlRec.getElementsByTagName("RESERVA");
        Cliente = reserva[i].getElementsByTagName("CLIENTE")[0].childNodes[0].nodeValue;
        document.getElementById("cliente").innerHTML = "Cliente: " + Cliente;
        document.getElementById("info").innerHTML = "Por favor, escolha a mesa";
        
    } catch(err) {
        console.log("Erro " + err);
    }
}

function getName(mesa) {
    console.log(emailAddress);
    console.log("Reserva de " + NumPessoas + " pessoas na mesa " + mesa);
    
        var msgConfirma = "Confirma a reserva da mesa " + mesa + " para " + Cliente + " no dia " + DataReserva + "?";
        if (confirm(msgConfirma)) {
            var codigo = prompt("Entre com o seu código de autorização");
            
            var MsgConfirmacao = "Confirmada a reserva da mesa " + mesa + " para " + NumPessoas + " pessoas no dia " + DataReserva;
            //alert(MsgConfirmacao);
            document.getElementById("info").innerHTML = MsgConfirmacao;
            document.getElementById("gazebo").style.backgroundColor = "#aeb6bf";
            document.getElementById("gazebo").innerHTML = "Reservada";
        }
        else {
            document.getElementById("cliente").innerHTML = "Por favor, escolha a mesa";
        }
            
        console.log("Recebida Mensagem do Servidor: " + cliente);
    
}






