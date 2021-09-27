//*********************************************************************************************************************
//                                                                                                                    *
//                       Programa Javascript para rodar no navegador de um administrador                              *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: permite ao administrador gerenciar o cadastramento dos clientes                                            *
//                                                                                                                    *
//*********************************************************************************************************************
//
var xhttp = new XMLHttpRequest();
var recurso = "reserva";
var i = 0;
var grupo;

const form = document.getElementById('cadastrocliente');
const nomeUsuarioCliente = form.elements['username'];
const nomeCliente = form.elements['nome'];
const celularCliente = form.elements['celular'];
const obs1Cliente = form.elements['obs1'];
const obs2Cliente = form.elements['obs2'];
const idosoCliente = form.elements['idoso'];
const locomocaoCliente = form.elements['locomocao'];
const exigenteCliente = form.elements['exigente'];
const generoCliente = form.elements['genero'];

var NomeAdministrador;

var EstadoConfirma = "null";
var EstadoData = "null";
var EstadoCadastro = "null";
var EstadoNumPes = "null";
var EstadoHorario = "null";

var NomeUsuarioCliente = "null";
var NomeCliente = "null";
var CelularCliente = "null";
var Obs1Cliente = "null";
var Obs2Cliente = "null";
var IdosoCliente = "null";
var LocomocaoCliente = "null";
var ExigenteCliente = "null";
var GeneroCliente = "null";
var AdminResp = "null";

var ClienteOK = false;


VerificaAdmin()

// Fim do Programa

function CarregaVariaveisFormulario() {
    NomeUsuarioCliente = nomeUsuarioCliente.value;
    NomeCliente = nomeCliente.value;
    CelularCliente = celularCliente.value;
    Obs1Cliente = obs1Cliente.value;
    Obs2Cliente = obs2Cliente.value;
    IdosoCliente = idosoCliente.value;
    LocomocaoCliente = locomocaoCliente.value;
    ExigenteCliente = exigenteCliente.value;
    GeneroCliente = generoCliente.value;
    
}

//*********************************************************************************************************************
// Nome da função: VerificaAdmin                                                                                      *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o programa inicia ou que o botão limpa é pressionado. A funão envia uma mensagem    *
//         para o servidor solicitando as informações do Administrador que fez login.                                 *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function VerificaAdmin() {    

    EscreveEnvMsgSrv();
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("carregaAdmin", "null", "null", "null", "null", "null"));
        var xmlRec = xhttp.responseXML;
        grupo = xmlRec.getElementsByTagName("ADMIN");
        NomeAdministrador = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        AdminResp = grupo[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
        EscreveTexto("Resposta do Servidor: informações do administrador", "infocom");
    
    } catch(err) {
        EscreveMsgErrSrv();
        console.log("Erro " + err);
    }
    EscreveTexto("Admin: " + NomeAdministrador, "nomeadmin");
    
}

//*********************************************************************************************************************
// Nome da função: VerificaCliente                                                                                    *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Verifica ao lado do campo Nome de usuário do      *
//         cliente no formulário. A função envia para o servidor o nome de usuário do cliente e o servidor deve       *
//         responder com os dados do cliente.                                                                         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function VerificaCliente() {
    
    CarregaVariaveisFormulario();
    
    LimpaCamposInfo();
    if (NomeUsuarioCliente == "") {
        clienteOK = false;
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
    }
    else {
        clienteOK = true;
        EscreveEnvMsgSrv();
        xhttp.open("POST", recurso, false);
        try {
            xhttp.send(MontaMsgServ("Cliente", NomeUsuarioCliente, "null", "null", "null", "null"));
            var xmlRec = xhttp.responseXML;
            CarregaCliente(xmlRec);
            
            EscreveInfoCliente();
            
            EscreveTexto("Resposta do Servidor: informações do cliente", "infocom");
        
        } catch(err) {
            EscreveMsgErrSrv();
            console.log("Erro " + err);
        }
    }
}

//*********************************************************************************************************************
// Nome da função: Cadastra                                                                                           *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Cadastra. A função envia para o servidor as       *
//         informações para cadastramento do cliente. O servidor deve responder com os dados do cliente.              *
//                                                                                                                    *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function Cadastra() {
    
    CarregaVariaveisFormulario();
    
    if (confirm("Confirma o cadastro do cliente?")) {
        EscreveEnvMsgSrv();
        xhttp.open("POST", recurso, false);
        try {
            xhttp.send(MontaMsgServ("Cliente"));
            var xmlRec = xhttp.responseXML;
            CarregaEstado(xmlRec);
            CarregaCliente(xmlRec);
            LimpaCamposInfo();
            EscreveInfoCliente();
        
            if (EstadoCadastro == "sim") {
                EscreveTexto("Resposta do Servidor: cliente cadastrado", "infocom");
            }
            else {
                EscreveTexto("Resposta do Servidor: falha ao cadastrar o cliente", "infocom");
            }
        
        } catch(err) {
            EscreveMsgErrSrv();
            console.log("Erro " + err);
        }
    }
}

//*********************************************************************************************************************
// Nome da função: CarregaEstado                                                                                      *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing do arquivo XML recebido do servidor, lê as informações de estado e carrega nas variáveis     *
//                                                                                                                    *
// Entrada: variável com a mensagem XML recebida                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaEstado(xmlMsg) {
    
    grupo = xmlMsg.getElementsByTagName("ESTADO");
    EstadoConfirma = grupo[i].getElementsByTagName("CONFIRMA")[0].childNodes[0].nodeValue;
    EstadoData = grupo[i].getElementsByTagName("DATA")[0].childNodes[0].nodeValue;
    EstadoCadastro = grupo[i].getElementsByTagName("CADASTRO")[0].childNodes[0].nodeValue;
    EstadoNumPes = grupo[i].getElementsByTagName("NUMPES")[0].childNodes[0].nodeValue;
    EstadoHorario = grupo[i].getElementsByTagName("HORARIO")[0].childNodes[0].nodeValue;
    
}

//*********************************************************************************************************************
// Nome da função: CarregaCliente                                                                                     *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing do arquivo XML recebido do servidor, lê as informações do cliente e carrega nas variáveis    *
//                                                                                                                    *
// Entrada: variável com a mensagem XML recebida                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaCliente(xmlMsg) {
    
    grupo = xmlMsg.getElementsByTagName("CLIENTE");
    NomeUsuarioCliente = grupo[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
    NomeCliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
    CelularCliente = grupo[i].getElementsByTagName("CELULAR")[0].childNodes[0].nodeValue;
    Obs1Cliente = grupo[i].getElementsByTagName("OBS1")[0].childNodes[0].nodeValue;
    Obs2Cliente = grupo[i].getElementsByTagName("OBS2")[0].childNodes[0].nodeValue;
    
    IdosoCliente = grupo[i].getElementsByTagName("IDOSO")[0].childNodes[0].nodeValue;
    LocomocaoCliente = grupo[i].getElementsByTagName("LOCOMOCAO")[0].childNodes[0].nodeValue;
    ExigenteCliente = grupo[i].getElementsByTagName("EXIGENTE")[0].childNodes[0].nodeValue;
    GeneroCliente = grupo[i].getElementsByTagName("GENERO")[0].childNodes[0].nodeValue;
    
    AdminResp = grupo[i].getElementsByTagName("ADMINRSP")[0].childNodes[0].nodeValue;
        
    if (NomeUsuarioCliente == "null") {
        clienteOK = false;
    }
    else {
        clienteOK = true;
    }
}

//*********************************************************************************************************************
// Nome da função: MontaMsgServ                                                                                       *
//                                                                                                                    *
// Data: 27/09/2021                                                                                                   *
//                                                                                                                    *
// Função: monta a mensagem de requisição ao servidor                                                                 *
//                                                                                                                    *
// Entrada: código da requisição                                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function MontaMsgServ(codigoMsg) {
    
    msgServ = "Codigo: " + codigoMsg + "\n" +
              "NomeUsuario: " + NomeUsuarioCliente + "\n" +
              "Nome: " + NomeCliente + "\n" +
              "Celular: " + CelularCliente + "\n" +
              "Obs1: " + CelularCliente + "\n" +
              "Obs2: " + CelularCliente + "\n" +
              "Idoso: " + IdosoCliente + "\n" +
              "Locomocao: " + LocomocaoCliente + "\n" +
              "Exigente: " + ExigenteCliente + "\n" +
              "Genero: " + GeneroCliente + "\n" +
              "AdminResp: " + AdminResp + "\n";
              
    return msgServ;
}

//*********************************************************************************************************************
// Nome da função: EscreveInfoCliente                                                                                 *
//                                                                                                                    *
// Data: 27/09/2021                                                                                                   *
//                                                                                                                    *
// Função: escreve as informações do cliente no campo de informações (janela direita)                                 *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function EscreveInfoCliente() {

    if (clienteOK) {
        EscreveTexto("Cliente Cadastrado", "info1");
        EscreveTexto("Nome de usuário: " + NomeUsuarioCliente, "info2");
        EscreveTexto("Nome Completo: " + NomeCliente, "info3");
        EscreveTexto("Celular: " + CelularCliente, "info4");
                                
        var sufixo = "o";
        if (GeneroCliente == "Feminino") sufixo = "a";                
        EscreveTexto("Idos" + sufixo + ": " + IdosoCliente, "info5");
        EscreveTexto("Dificuldade de locomoção: " + LocomocaoCliente, "info6");
        EscreveTexto("Exigente: " + ExigenteCliente, "info7");
        EscreveTexto("Gênero: " + GeneroCliente, "info8");
        EscreveTexto("Obs 1: " + Obs1Cliente, "info9");
        EscreveTexto("Obs 2: " + Obs2Cliente, "info10");
        EscreveTexto("Responsãvel pelo cadastro: " + AdminResp, "info12");
    }
    else {
        EscreveTexto("Cliente não cadastrado", "info1");
    }
}

//*********************************************************************************************************************
// Nome da função: EscreveTexto                                                                                       *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: escreve na tela uma mensagem                                                                               *
//                                                                                                                    *
// Entrada: string com a mensagem e string com o identificador do campo na tela (*/)id)                               *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function EscreveTexto(texto, idHTML) {
    document.getElementById(idHTML).innerHTML = texto;
}

function EscreveEnvMsgSrv() {
    document.getElementById("infocom").innerHTML = "Enviada requisição ao Servidor";
    document.getElementById("infocom").style.color = "#23257e";
}

function EscreveRspMsgSrv(msgrsp) {
    document.getElementById("infocom").innerHTML = "Resposta do Servidor: " + msgrsp;
    document.getElementById("infocom").style.color = "#23257e";
}

function EscreveMsgErrSrv() {
    document.getElementById("infocom").innerHTML = "O Servidor não respondeu";
    document.getElementById("infocom").style.color = "#23257e";
}

function LimpaCampoInfo(id) {
    document.getElementById(id).innerHTML = "                      ";
}

function LimpaCamposInfo() {
    document.getElementById("info1").innerHTML = "                                 ";
    document.getElementById("info2").innerHTML = "                                 ";
    document.getElementById("info3").innerHTML = "                                 ";
    document.getElementById("info4").innerHTML = "                                 ";
    document.getElementById("info5").innerHTML = "                                 ";
    document.getElementById("info6").innerHTML = "                                 ";
    document.getElementById("info7").innerHTML = "                                 ";
    document.getElementById("info8").innerHTML = "                                 ";
    document.getElementById("info9").innerHTML = "                                 ";
    document.getElementById("info10").innerHTML = "                                ";
    document.getElementById("info12").innerHTML = "                                ";
}

function LimpaCampoInfoForm(id) {
    document.getElementById(id).innerHTML = "                          ";
}








