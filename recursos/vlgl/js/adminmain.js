//*********************************************************************************************************************
//                                                                                                                    *
//                       Programa Javascript para rodar no navegador de um administrador                              *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: permite ao administrador gerenciar as reservas das mesas.                                                  *
//                                                                                                                    *
//*********************************************************************************************************************
//
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
var numPessoasOK;
var i = 0;
var grupo;
var recurso = "reserva";

var mesaSelecionada;

VerificaAdmin()

// Fim do Programa


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

    xhttp.open("POST", recurso, false);
    try {
        xhttp.send("nomeAdmin");
        var xmlRec = xhttp.responseXML;
        grupo = xmlRec.getElementsByTagName("ADMIN");
        AdminName = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
    
    } catch(err) {
        console.log("Erro " + err);
    }

    document.getElementById("nomeadmin").innerHTML = "Admin: " + AdminName;
    LimpaCampoInfo("info1");
}

//*********************************************************************************************************************
// Nome da função: VerificaData                                                                                       *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Verifica ao lado do campo Data da reserva do      *
//         formulário. A função envia para o servidor a data e o servidor deve responder com o mapa de mesas.         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function VerificaData() {
    DataReserva = dataReserva.value;
    
    var codigo = "Data";
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ(codigo, UserName, DataReserva));
        var xmlRec = xhttp.responseXML;
        i = 0;
        grupo = xmlRec.getElementsByTagName("CLIENTE");
        Cliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
            
        grupo = xmlRec.getElementsByTagName("MESAS");
        var habMesa = grupo[i].getElementsByTagName("A00")[0].childNodes[0].nodeValue;
        corMesa("A00", habMesa);
        habMesa = grupo[i].getElementsByTagName("A01")[0].childNodes[0].nodeValue;
        corMesa("A01", habMesa);
        habMesa = grupo[i].getElementsByTagName("A02")[0].childNodes[0].nodeValue;
        corMesa("A02", habMesa);
        habMesa = grupo[i].getElementsByTagName("A03")[0].childNodes[0].nodeValue;
        corMesa("A03", habMesa);
        habMesa = grupo[i].getElementsByTagName("A04")[0].childNodes[0].nodeValue;
        corMesa("A04", habMesa);
        habMesa = grupo[i].getElementsByTagName("A05")[0].childNodes[0].nodeValue;
        corMesa("A05", habMesa);
        habMesa = grupo[i].getElementsByTagName("A06")[0].childNodes[0].nodeValue;
        corMesa("A06", habMesa);
        habMesa = grupo[i].getElementsByTagName("A07")[0].childNodes[0].nodeValue;
        corMesa("A07", habMesa);
        habMesa = grupo[i].getElementsByTagName("A08")[0].childNodes[0].nodeValue;
        corMesa("A08", habMesa);
            
        habMesa = grupo[i].getElementsByTagName("B09")[0].childNodes[0].nodeValue;
        corMesa("B09", habMesa);
        habMesa = grupo[i].getElementsByTagName("B10")[0].childNodes[0].nodeValue;
        corMesa("B10", habMesa);
        habMesa = grupo[i].getElementsByTagName("B11")[0].childNodes[0].nodeValue;
        corMesa("B11", habMesa);
        habMesa = grupo[i].getElementsByTagName("B12")[0].childNodes[0].nodeValue;
        corMesa("B12", habMesa);
        habMesa = grupo[i].getElementsByTagName("B13")[0].childNodes[0].nodeValue;
        corMesa("B13", habMesa);
        habMesa = grupo[i].getElementsByTagName("B14")[0].childNodes[0].nodeValue;
        corMesa("B14", habMesa);
        habMesa = grupo[i].getElementsByTagName("B15")[0].childNodes[0].nodeValue;
        corMesa("B15", habMesa);
        habMesa = grupo[i].getElementsByTagName("B16")[0].childNodes[0].nodeValue;
        corMesa("B16", habMesa);
        
        EscreveTexto("Recebido o mapa de mesas do dia " + DataReserva, "info1");
        
    } catch(err) {
        EscreveTexto("O servidor não respondeu", "info1");
        console.log("Erro " + err);
    }
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
    UserName = userName.value;
    
    var codigo = "Cliente";
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ(codigo, UserName, "null"));
        var xmlRec = xhttp.responseXML;
        i = 0;
        grupo = xmlRec.getElementsByTagName("CLIENTE");
        var NomeCliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        
        if (NomeCliente != "null") {
            if (NomeCliente != "naocadastrado") {
                clienteOK = true;
                EscreveTexto("Cliente: " + NomeCliente, "info1");
            }
            else {
                clienteOK = false;
                EscreveTexto("Cliente não cadastrado", "info1");
                LimpaCampoInfo("info2");
                LimpaCampoInfo("info3");
            }
        }
        
    } catch(err) {
        EscreveTexto("O servidor não respondeu", "info1");
        console.log("Erro " + err);
    }
}

//*********************************************************************************************************************
// Nome da função: Entra                                                                                              *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Entra ao lado do campo Número de pessoas do       *
//         formulário. A função envia para o servidor a data, o nome de usuário do cliente e o número de pessoas.     *
//         O servidor deve responder com os dados do cliente e o mapa de mesas.                                       *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function Entra() {
    DataReserva = dataReserva.value;
    UserName = userName.value;
    NumPessoas = numPessoas.value;
    
    var codigo = "DataCliente";
    if (UserName == "") { codigo = "Data"; }
    
    numPessoasOK = false;
    let NumeroPessoas = parseInt(NumPessoas);
    if ((NumeroPessoas > 0) && (NumeroPessoas < 13)) { numPessoasOK = true; }
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ(codigo, UserName, DataReserva));
        var xmlRec = xhttp.responseXML;
        i = 0;
        grupo = xmlRec.getElementsByTagName("CLIENTE");
        Cliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
            
        grupo = xmlRec.getElementsByTagName("MESAS");
        var habMesa = grupo[i].getElementsByTagName("A00")[0].childNodes[0].nodeValue;
        corMesa("A00", habMesa);
        habMesa = grupo[i].getElementsByTagName("A01")[0].childNodes[0].nodeValue;
        corMesa("A01", habMesa);
        habMesa = grupo[i].getElementsByTagName("A02")[0].childNodes[0].nodeValue;
        corMesa("A02", habMesa);
        habMesa = grupo[i].getElementsByTagName("A03")[0].childNodes[0].nodeValue;
        corMesa("A03", habMesa);
        habMesa = grupo[i].getElementsByTagName("A04")[0].childNodes[0].nodeValue;
        corMesa("A04", habMesa);
        habMesa = grupo[i].getElementsByTagName("A05")[0].childNodes[0].nodeValue;
        corMesa("A05", habMesa);
        habMesa = grupo[i].getElementsByTagName("A06")[0].childNodes[0].nodeValue;
        corMesa("A06", habMesa);
        habMesa = grupo[i].getElementsByTagName("A07")[0].childNodes[0].nodeValue;
        corMesa("A07", habMesa);
        habMesa = grupo[i].getElementsByTagName("A08")[0].childNodes[0].nodeValue;
        corMesa("A08", habMesa);
            
        habMesa = grupo[i].getElementsByTagName("B09")[0].childNodes[0].nodeValue;
        corMesa("B09", habMesa);
        habMesa = grupo[i].getElementsByTagName("B10")[0].childNodes[0].nodeValue;
        corMesa("B10", habMesa);
        habMesa = grupo[i].getElementsByTagName("B11")[0].childNodes[0].nodeValue;
        corMesa("B11", habMesa);
        habMesa = grupo[i].getElementsByTagName("B12")[0].childNodes[0].nodeValue;
        corMesa("B12", habMesa);
        habMesa = grupo[i].getElementsByTagName("B13")[0].childNodes[0].nodeValue;
        corMesa("B13", habMesa);
        habMesa = grupo[i].getElementsByTagName("B14")[0].childNodes[0].nodeValue;
        corMesa("B14", habMesa);
        habMesa = grupo[i].getElementsByTagName("B15")[0].childNodes[0].nodeValue;
        corMesa("B15", habMesa);
        habMesa = grupo[i].getElementsByTagName("B16")[0].childNodes[0].nodeValue;
        corMesa("B16", habMesa);
        
        if (codigo == "Data") {
            EscreveTexto("Recebido o mapa de mesas do dia " + DataReserva, "info1");
        }
        else {
            if (Cliente != "null") {
               if (Cliente != "naocadastrado") {
                   clienteOK = true;
                   EscreveTexto("Cliente: " + Cliente, "info1");
                   if (numPessoasOK) {
                       EscreveTexto("Escolha a mesa", "info2");
                       LimpaCampoInfo("info3");
                   }
                   else {
                       EscreveTexto("Entre com o número de pessoas", "info2");
                       LimpaCampoInfo("info3");
                   }
                   
               }
               else {
                   clienteOK = false;
                   EscreveTexto("Cliente não cadastrado", "info1");
                   LimpaCampoInfo("info2");
                   LimpaCampoInfo("info3");
               }
            }
        }
        
        console.log("Mensagem POST enviada: " + UserName);
    } catch(err) {
        console.log("Erro " + err);
    }
}

//*********************************************************************************************************************
// Nome da função: reservaMesa                                                                                        *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário seleciona uma mesa para reserva. Mostra um pop-up de confirma. Após a     *
//         confirmação, envia para o servidor a solicitação, espera resposta, e caso seja afirmativa, mostra uma      *
//         mensagem indicando a contirmação. Também muda a cor da mesa para cinza e coloca o Id do cliente.           *
//                                                                                                                    *
// Entrada: string com o nome da mesa (A0 a A8 e B9 a B16)                                                            *
//*********************************************************************************************************************
//
function reservaMesa(mesa, impMesa) {
    DataReserva = dataReserva.value;
    UserName = userName.value;
    NumPessoas = numPessoas.value;
    mesaSelecionada = mesa;
    impMesa = mesa;
    if (numPessoasOK) {
      if (clienteOK) {
        if (mesa == "A00") {
            impMesa = "Gazebo";
        }
        var msgConfirma = "Confirma a reserva da mesa " + impMesa + " para " + Cliente + " no dia " + DataReserva + "?";
        EscreveTexto(msgConfirma, "info2");
  
      }
    }
    else {
        EscreveTexto("Entre com o número de pessoas", "info2");
    }
    
}

function Confirma() {
    
    impMesa = mesaSelecionada;
    if (mesaSelecionada == "A00") {
        impMesa = "Gazebo";
    }
    
    var MsgConfirmacao = "Confirmada a reserva da mesa " + impMesa + " para " + NumPessoas + " pessoas no dia " + DataReserva;
    MsgConfirmacao = MsgConfirmacao + " no nome de " + Cliente + ".";
    EscreveTexto(MsgConfirmacao, "info2");
    
    document.getElementById(mesaSelecionada).style.backgroundColor = "#aeb6bf";
    document.getElementById(mesaSelecionada).innerHTML = UserName;
    
}

function EscreveTexto(texto, idHTML) {
    document.getElementById(idHTML).innerHTML = texto;
}

function corMesa(mesa, habMesa) {
    if (habMesa == "livre") {
        document.getElementById(mesa).style.backgroundColor = "#33ff71";
    }
    else {
        document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
        document.getElementById(mesa).innerHTML = habMesa;
    }
}

function LimpaCampoInfo(id) {
    document.getElementById(id).innerHTML = "                      ";
}

function LimpaCampoInfoForm(id) {
    document.getElementById(id).innerHTML = "                  ";
}


function MontaMsgServ(codigo, userName, dataReserva) {
    
    msgServ = "Codigo: " + codigo + "\n" +
              "UserName: " + userName + "\n" +
              "DataReserva: " + dataReserva + "\n" +
              "NumPessoas: " + NumPessoas;
              
    return msgServ;
}






