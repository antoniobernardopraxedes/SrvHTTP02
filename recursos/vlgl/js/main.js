
const form = document.getElementById('signup');
const name = form.elements['name'];
const email = form.elements['email'];


function getName() {
    let fullName = name.value;
    let emailAddress = email.value;
    console.log(fullName);
    console.log(emailAddress);
    
    var xhr = new XMLHttpRequest();
    var uri = "recurso";
    xhr.open("POST", uri, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    
    try {
        xhr.send(fullName);
        if (xhttp.status == 200) {
            console.log("Recebida Mensagem Correta do Servidor");
            var xmlRec = xhttp.responseXML;
        }
        else {
            console.log("Recebida Mensagem de Erro do Servidor");
        }
        
    } catch(err) {
        console.log("Não foi Recebida Mensagem do Servidor");
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





