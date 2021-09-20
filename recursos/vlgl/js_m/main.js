
function clicou(numMesa) {
    var mensagem = "Confirma a reserva da mesa?";
    confirm(mensagem);
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



