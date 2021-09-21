
const form = document.getElementById('signup');
const numPessoas = form.elements['numpessoas'];
const email = form.elements['email'];

let NumPessoas = null;
let emailAddress = null;

NumPessoas = numPessoas.value;
emailAddress = email.value;
console.log("Email cadastrado: " + emailAddress);
console.log("Número de Pessoas: " + NumPessoas);

function getName(mesa) {
    var xhttp = new XMLHttpRequest();
    NumPessoas = numPessoas.value;
    emailAddress = email.value;
    console.log(emailAddress);
    console.log("Reserva de " + NumPessoas + " pessoas na mesa " + mesa);
    
    var recurso = "reserva";
    xhttp.open("POST", recurso, false);
    //xhttp.setRequestHeader('Content-Type', 'application/xml');
    
    try {
        xhttp.send(emailAddress);
        var Cliente = "";
        var xmlRec = xhttp.responseXML;
        var i = 0;
        var reserva = xmlRec.getElementsByTagName("RESERVA");
        Cliente = reserva[i].getElementsByTagName("CLIENTE")[0].childNodes[0].nodeValue;
        var msgConfirma = "Confirma a reserva da mesa " + mesa + " para " + Cliente + "?";
        if (confirm(msgConfirma)) {
            var codigo = prompt("Entre com o seu código de autorização");
            
            var MsgConfirmacao = "Confirmada a reserva da mesa " + mesa + " para " + NumPessoas + " pessoas em nome de " + Cliente;
            //alert(MsgConfirmacao);
            document.getElementById("cliente").innerHTML = MsgConfirmacao;
            document.getElementById("gazebo").style.backgroundColor = "#aeb6bf";
            document.getElementById("gazebo").innerHTML = "Reservada";
        }
        else {
            document.getElementById("cliente").innerHTML = "Por favor, escolha a mesa";
        }
        //document.getElementById("comcnv").style.color = CorFonte1(valor);
            
        console.log("Recebida Mensagem do Servidor: " + cliente);
        
    } catch(err) {
        console.log("Erro " + err);
    }
    
}

function clicou(numMesa) {
    var mensagem = "Confirma a reserva da mesa " + numMesa + "?";
    if (confirm(mensagem)) {
        alert("Reserva da Mesa " + numMesa + " Confirmada.");
    }
}

function trocar(parametro) {
    document.getElementById("mousemove").innerHTML = "Obrigado por passar o mouse";
    parametro.innerHTML = "Obrigado por passar o mouse";
}

function voltar(elemento) {
    document.getElementById("mousemove").innerHTML = "Passe o mouse aqui";
    elemento.innerHTML = "Passe o mouse aqui";
}

function load() {
    alert("Página carregada");
}





