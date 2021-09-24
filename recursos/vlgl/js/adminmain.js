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
const horarioChegada = form.elements['hora'];

var AdminName;

var DataReserva;
var UserName;      // O nome de usuário do cliente inserido pelo administrador
var NumPessoas;
var HoraChegada;

var IdCliente;     // O nome de usuário do cliente recebido na mensagem XML
var NomeCliente;
var Info1Cliente;
var Info2Cliente;
var Info3Cliente;

var clienteOK = false;
var numPessoasOK;
var i = 0;
var grupo;
var recurso = "reserva";

var mesaSelecionada;

var OA00;   var NA00;   var HA00;
var OA01;   var NA01;   var HA01;
var OA02;   var NA02;   var HA02;
var OA03;   var NA03;   var HA03;
var OA04;   var NA04;   var HA04;
var OA05;   var NA05;   var HA05;
var OA06;   var NA06;   var HA06;
var OA07;   var NA07;   var HA07;
var OA08;   var NA08;   var HA08;
var OB09;   var NB09;   var HB09;
var OB10;   var NB10;   var HB10;
var OB11;   var NB11;   var HB11;
var OB12;   var NB12;   var HB12;
var OB13;   var NB13;   var HB13;
var OB14;   var NB14;   var HB14;
var OB15;   var NB15;   var HB15;
var OB16;   var NB16;   var HB16;

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
        xhttp.send(MontaMsgServ("carregaAdmin", "null", "null", "null", "null"));
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
    
    DataReserva = "25-09-2021";
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("Data", "null", DataReserva, "null", "null"));
        var xmlRec = xhttp.responseXML;
        CarregaMesas(xmlRec);
     
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
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("Cliente", UserName, "null", "null", "null"));
        var xmlRec = xhttp.responseXML;
        CarregaCliente(xmlRec);
        
        if (clienteOK) {
            EscreveTexto("Cliente: " + NomeCliente, "info1");
            LimpaCampoInfo("info2");
            LimpaCampoInfo("info3");
        }
        else {
            EscreveTexto("Cliente não cadastrado", "info1");
            LimpaCampoInfo("info2");
            LimpaCampoInfo("info3");
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
    
    DataReserva = "25-09-2021";
    
    UserName = userName.value;
    NumPessoas = numPessoas.value;
    HoraChegada = horarioChegada.value;
    
    numPessoasOK = false;
    let NumeroPessoas = parseInt(NumPessoas);
    if ((NumeroPessoas > 0) && (NumeroPessoas < 13)) { numPessoasOK = true; }
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("DataCliente", UserName, DataReserva, NumPessoas, HoraChegada));
        var xmlRec = xhttp.responseXML;
        CarregaCliente(xmlRec);
        CarregaMesas(xmlRec);
              
        if (clienteOK) {
            EscreveTexto("Cliente: " + NomeCliente, "info1");
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
            EscreveTexto("Cliente não cadastrado", "info1");
            LimpaCampoInfo("info2");
            LimpaCampoInfo("info3");
       }
        
    } catch(err) {
        EscreveTexto("O servidor não respondeu", "info1");
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
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function reservaMesa(mesa) {
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
        var msgConfirma = "Confirma a reserva da mesa " + impMesa + " para " + NomeCliente + " no dia " + DataReserva + "?";
        EscreveTexto(msgConfirma, "info1");
        LimpaCampoInfo("info2");
        LimpaCampoInfo("info3");
      }
    }
    else {
        EscreveTexto("Entre com o número de pessoas", "info2");
    }
    
}

//*********************************************************************************************************************
// Nome da função: Confirma                                                                                           *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário pressiona o botão Confirma. Envia uma mensagem para o servidor com todas  *
//         as informações sobre a solicitação da reserva da mesa. O servidor deve responder uma mensagem com essas    *
//         informações confirmando a reserva. Então, esta função lê as informações recebidas do servidor e            *
//         apresenta na tela.                                                                                         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function Confirma() {
    
    impMesa = mesaSelecionada;
    if (mesaSelecionada == "A00") {
        impMesa = "Gazebo";
    }
    
    var MsgConfirmacao = "Confirmada a reserva de " + NomeCliente + " da mesa " + impMesa + " para ";
    MsgConfirmacao = MsgConfirmacao + NumPessoas + " pessoas no dia " + DataReserva + ".";
    EscreveTexto(MsgConfirmacao, "info1");
    LimpaCampoInfo("info2");
    LimpaCampoInfo("info3");
    
    document.getElementById(mesaSelecionada).style.backgroundColor = "#aeb6bf";
    document.getElementById(mesaSelecionada).innerHTML = UserName;
    
}

//*********************************************************************************************************************
// Nome da função: CarregaMesas                                                                                       *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing do arquivo XML recebido do servidor, lê as informações de ocupação, número de pessoas e      *
//         horário de chegada e apresenta na tela.                                                                    *
//                                                                                                                    *
// Entrada: variável com a mensagem XML recebida                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaMesas(xmlMsg) {
    
    i = 0;
    grupo = xmlMsg.getElementsByTagName("MESAS");
    
    OA00 = grupo[i].getElementsByTagName("OA00")[0].childNodes[0].nodeValue;
        
    OA01 = grupo[i].getElementsByTagName("OA01")[0].childNodes[0].nodeValue;
    
    OA02 = grupo[i].getElementsByTagName("OA02")[0].childNodes[0].nodeValue;
    
    OA03 = grupo[i].getElementsByTagName("OA03")[0].childNodes[0].nodeValue;
    
    OA04 = grupo[i].getElementsByTagName("OA04")[0].childNodes[0].nodeValue;
    
    OA05 = grupo[i].getElementsByTagName("OA05")[0].childNodes[0].nodeValue;
    
    OA06 = grupo[i].getElementsByTagName("OA06")[0].childNodes[0].nodeValue;
    
    OA07 = grupo[i].getElementsByTagName("OA07")[0].childNodes[0].nodeValue;
    
    OA08 = grupo[i].getElementsByTagName("OA08")[0].childNodes[0].nodeValue;
    
    OB09 = grupo[i].getElementsByTagName("OB09")[0].childNodes[0].nodeValue;
    
    OB10 = grupo[i].getElementsByTagName("OB10")[0].childNodes[0].nodeValue;
    
    OB11 = grupo[i].getElementsByTagName("OB11")[0].childNodes[0].nodeValue;
    
    OB12 = grupo[i].getElementsByTagName("OB12")[0].childNodes[0].nodeValue;
    
    OB13 = grupo[i].getElementsByTagName("OB13")[0].childNodes[0].nodeValue;
    
    OB14 = grupo[i].getElementsByTagName("OB14")[0].childNodes[0].nodeValue;
    
    OB15 = grupo[i].getElementsByTagName("OB15")[0].childNodes[0].nodeValue;
    
    OB16 = grupo[i].getElementsByTagName("OB16")[0].childNodes[0].nodeValue;
    
    AtualizaMesa("A00", OA00);
    AtualizaMesa("A01", OA01);
    AtualizaMesa("A02", OA02);
    AtualizaMesa("A03", OA03);
    AtualizaMesa("A04", OA04);
    AtualizaMesa("A05", OA05);
    AtualizaMesa("A06", OA06);
    AtualizaMesa("A07", OA07);
    AtualizaMesa("A08", OA08);
    AtualizaMesa("B09", OB09);
    AtualizaMesa("B10", OB10);
    AtualizaMesa("B11", OB11);
    AtualizaMesa("B12", OB12);
    AtualizaMesa("B13", OB13);
    AtualizaMesa("B14", OB14);
    AtualizaMesa("B15", OB15);
    AtualizaMesa("B16", OB16);

}

function AtualizaMesa(mesa, habMesa) {
    if (habMesa == "livre") {
        document.getElementById(mesa).style.backgroundColor = "#33ff71";
    }
    else {
        document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
        document.getElementById(mesa).innerHTML = habMesa;
    }
}

//*********************************************************************************************************************
// Nome da função: CarregaCliente                                                                                     *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing do arquivo XML recebido do servidor, lê as informações do cliente, carrega as variáveis e    *
//         apresenta na tela.                                                                                         *
//                                                                                                                    *
// Entrada: variável com a mensagem XML recebida                                                                      *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaCliente(xmlMsg) {
    i = 0;
    grupo = xmlMsg.getElementsByTagName("CLIENTE");
    IdCliente = grupo[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
    NomeCliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
    Info1Cliente = grupo[i].getElementsByTagName("RES1")[0].childNodes[0].nodeValue;
    Info2Cliente = grupo[i].getElementsByTagName("RES2")[0].childNodes[0].nodeValue;
    Info3Cliente = grupo[i].getElementsByTagName("RES3")[0].childNodes[0].nodeValue;
        
    if (IdCliente == "null") {
        clienteOK = false;
    }
    else {
        clienteOK = true;
    }
    
}

function MontaMsgServ(codigoMsg, nomeUsuario, dataRes, numeroPes, horarioCheg) {
    
    msgServ = "Codigo: " + codigoMsg + "\n" +
              "DataReserva: " + dataRes + "\n" +
              "NomeUsuario: " + nomeUsuario + "\n" +
              "NumPessoas: " + numeroPes + "\n" +
              "HorarioChegada: " + horarioCheg + "\n";
              
    return msgServ;
}


function EscreveTexto(texto, idHTML) {
    document.getElementById(idHTML).innerHTML = texto;
}

function LimpaCampoInfo(id) {
    document.getElementById(id).innerHTML = "                      ";
}

function LimpaCampoInfoForm(id) {
    document.getElementById(id).innerHTML = "                  ";
}








