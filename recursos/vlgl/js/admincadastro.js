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
var recurso;
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

var NomeUsuarioAdmin;
var NomeAdmin;

var EstadoConfirma = "null";
var EstadoData = "null";
var EstadoCadastro = "null";
var EstadoNumPes = "null";
var EstadoHorario = "null";

var NomeUsuarioCliente = "null";
var NomeUsuarioClienteRec = "null";
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
    AdminResp = NomeUsuarioAdmin;
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

    let requisicao = new XMLHttpRequest();
    recurso = "admin/";
    requisicao.open("GET", recurso, true);
    requisicao.timeout = 2000;
    EscreveMsgEnvSrv();
    requisicao.send(null);
    
    requisicao.onload = function() {
        let XMLRec = requisicao.responseXML;
        grupo = XMLRec.getElementsByTagName("ADMIN");
        NomeUsuarioAdmin = grupo[0].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
        NomeAdmin = grupo[0].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        EscreveTexto("Admin: " + NomeAdmin, "nomeadmin");
        EscreveTexto("Servidor: recebidas informações do administrador", "infocom");
    };
    
    requisicao.ontimeout = function(e) {
        EscreveTexto("O servidor não respondeu à requisição", "infocom");
    };
    
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
    clienteOK = false;
    
    if (NomeUsuarioCliente != "") {
        
        let requisicao = new XMLHttpRequest();
        recurso = "cliente/" + NomeUsuarioCliente;
        requisicao.open("GET", recurso, true);
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(null);
    
        requisicao.onload = function() {
            let XMLRec = requisicao.responseXML;
            CarregaCliente(XMLRec);
            
            if (clienteOK) {
                EscreveTexto("Servidor: recebidas informações do cliente", "infocom");
                LimpaCamposInfo();
                EscreveInfoCliente();
                
                EscreveTexto("Atualiza", "botaocadastra");
            }
            else {
                EscreveTexto("Servidor: cliente não cadastrado", "infocom");
                LimpaCamposInfo();
                EscreveTexto("Cliente não cadastrado", "info1");
                EscreveTexto("Cadastra", "botaocadastra");
            }
         };
         
         requisicao.ontimeout = function(e) {
                EscreveTexto("O servidor não respondeu à requisição", "info1");
         };
    }
    else {
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
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
    LimpaCamposInfo();
    
    if (NomeUsuarioCliente != "") {
        if (NomeCliente != "") {
            if (CelularCliente != "") {
                if (confirm("Confirma o cadastro do cliente " + NomeUsuarioCliente + "?")) {
        
                    let requisicao = new XMLHttpRequest();
                    recurso = "cadastra";
                    requisicao.open("POST", recurso, true);
                    requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
                    requisicao.timeout = 2000;
                    EscreveMsgEnvSrv();
                    requisicao.send(MontaMsgJson());
        
                    requisicao.onload = function() {
                        let XMLRec = requisicao.responseXML;
                        CarregaCliente(XMLRec);
                
                        if (clienteOK) {
                            EscreveTexto("O cliente foi cadastrado", "infocom");
                            EscreveInfoCliente();
                        }
                        else {
                            LimpaCamposInfo();
                            EscreveTexto("Servidor: Falha ao cadastrar o cliente", "infocom");
                        }
                    };
                }
            }
            else {
                EscreveTexto("Entre com o número do celular do cliente", "info1");
            }
        }
        else {
            EscreveTexto("Entre com o nome completo do cliente", "info1");
        }
    }
    else {
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
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
    NomeUsuarioClienteRec = grupo[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
    NomeCliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
    CelularCliente = grupo[i].getElementsByTagName("CELULAR")[0].childNodes[0].nodeValue;
    Obs1Cliente = grupo[i].getElementsByTagName("OBS1")[0].childNodes[0].nodeValue;
    Obs2Cliente = grupo[i].getElementsByTagName("OBS2")[0].childNodes[0].nodeValue;
    
    IdosoCliente = grupo[i].getElementsByTagName("IDOSO")[0].childNodes[0].nodeValue;
    LocomocaoCliente = grupo[i].getElementsByTagName("LOCOMOCAO")[0].childNodes[0].nodeValue;
    ExigenteCliente = grupo[i].getElementsByTagName("EXIGENTE")[0].childNodes[0].nodeValue;
    GeneroCliente = grupo[i].getElementsByTagName("GENERO")[0].childNodes[0].nodeValue;
    
    AdminResp = grupo[i].getElementsByTagName("ADMINRSP")[0].childNodes[0].nodeValue;
        
    if (NomeUsuarioClienteRec == NomeUsuarioCliente) {
        clienteOK = true;
    }
    else {
        clienteOK = false;
    }
}

//*********************************************************************************************************************
// Nome da função: MontaMsgServ                                                                                       *
//                                                                                                                    *
// Data: 28/09/2021                                                                                                   *
//                                                                                                                    *
// Função: monta a mensagem de requisição ao servidor                                                                 *
//                                                                                                                    *
// Entrada: código da requisição                                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function MontaMsgJson() {
    
    if (Obs1Cliente == "") Obs1Cliente = "não informada";
    if (Obs1Cliente == "") Obs1Cliente = "não informada";
        
    var msgJson = "{\n" +
                  "  \"nomeUsuario\" : \"" + NomeUsuarioCliente + "\",\n" +
                  "  \"nome\" : \"" + NomeCliente + "\",\n" +
                  "  \"celular\" : \"" + CelularCliente + "\",\n" +
                  "  \"obs1\" : \"" + Obs1Cliente + "\",\n" +
                  "  \"obs2\" : \"" + Obs2Cliente + "\",\n" +
                  "  \"idoso\" : \"" + IdosoCliente + "\",\n" +
                  "  \"locomocao\" : \"" + LocomocaoCliente + "\",\n" +
                  "  \"exigente\" : \"" + ExigenteCliente + "\",\n" +
                  "  \"genero\" : \"" + GeneroCliente + "\",\n" +
                  "  \"adminResp\" : \"" + AdminResp + "\"\n" +
                  "}";
                  
    return msgJson;
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

    LimpaCamposInfo();
    if (clienteOK) {
        EscreveTexto("Nome de usuário: " + NomeUsuarioCliente, "info1");
        EscreveTexto("Nome Completo: " + NomeCliente, "info2");
        EscreveTexto("Celular: " + CelularCliente, "info3");
                                
        var sufixo = "o";
        if (GeneroCliente == "Feminino") sufixo = "a";                
        EscreveTexto("Idos" + sufixo + ": " + IdosoCliente, "info4");
        EscreveTexto("Dificuldade de locomoção: " + LocomocaoCliente, "info5");
        EscreveTexto("Exigente: " + ExigenteCliente, "info6");
        EscreveTexto("Gênero: " + GeneroCliente, "info7");
        EscreveTexto("Obs 1: " + Obs1Cliente, "info8");
        EscreveTexto("Obs 2: " + Obs2Cliente, "info9");
        EscreveTexto("Responsável pelo cadastro: " + AdminResp, "info10");
        EscreveTexto("Atualiza", "botaocadastra");
    }
    else {
        EscreveTexto("Cliente não cadastrado", "info1");
        EscreveTexto("Cadastra", "botaocadastra");
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

function EscreveMsgEnvSrv() {
    document.getElementById("infocom").innerHTML = "Enviada requisição. Aguardando resposta do servidor";
}

function EscreveMsgErrSrv() {
    document.getElementById("info1").innerHTML = "O Servidor não respondeu";
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








