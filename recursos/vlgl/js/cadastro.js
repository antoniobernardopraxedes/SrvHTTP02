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
recurso = "reserva";

const form = document.getElementById('signup');
const userName = form.elements['username'];
const dataReserva = form.elements['data'];
const numPessoas = form.elements['numpessoas'];
const horarioChegada = form.elements['hora'];

var AdminName;

var DataReserva;
var UserName;      // O nome de usuário do cliente inserido pelo administrador
var NumPessoas;
var NumeroPessoas;
var HoraChegada;

var IdCliente;     // O nome de usuário do cliente recebido na mensagem XML
var NomeCliente;
var Info1Cliente;
var Info2Cliente;
var Info3Cliente;
var Info4Cliente;

var dataOK = false;
var clienteOK = false;
var numPessoasOK;
var horaChegadaOK;
var i = 0;
var grupo;
var recurso = "reserva";

var MesaSelecionada;

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

    EscreveEnvMsgSrv();
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("carregaAdmin", "null", "null", "null", "null", "null"));
        var xmlRec = xhttp.responseXML;
        grupo = xmlRec.getElementsByTagName("ADMIN");
        AdminName = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        EscreveTexto("Resposta do Servidor: informações do administrador", "infocom");
    
    } catch(err) {
        EscreveMsgErrSrv();
        console.log("Erro " + err);
    }
    EscreveTexto("Admin: " + AdminName, "nomeadmin");
    
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
    
    if (DataReserva == "") {
        dataOK = false;
        EscreveTexto("Entre com a data da reserva", "info1");
    }
    else {
        dataOK = VerificaFormatoData(DataReserva);
    }
    
    if (dataOK) {
        EscreveEnvMsgSrv();
        xhttp.open("POST", recurso, false);
        try {
            xhttp.send(MontaMsgServ("Data", "null", DataReserva, "null", "null", "null"));
            var xmlRec = xhttp.responseXML;
            CarregaMesas(xmlRec);
            dataOK = true;
        
            LimpaCamposInfo();
            EscreveTexto("Resposta do Servidor: reservas do dia " + DataReserva, "infocom");
        
        } catch(err) {
            EscreveMsgErrSrv();
            console.log("Erro " + err);
        }
    }
    else {
        EscreveTexto("Data inválida", "info1");
    }
}

function VerificaFormatoData(dataR) {
    resultado = true;
    var dia = 0;
    var mes = 0;
    var ano = 0;
    let diasMes = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]; // Dias de cada mês
    if ((dataR.length != 10) || (dataR[2] != "/") || (dataR[5] != "/")) {
        resultado = false;
    }
    else {
        dia = parseInt(dataR[0] + dataR[1]);
        mes = parseInt(dataR[3] + dataR[4]);
        ano = parseInt(dataR[6] + dataR[7] + dataR[8] + dataR[9]);
        resto = ano % 4;
        if (ano < 2021) {
            resultado = false;
        }
        else {
            if ((mes < 1) || (mes > 12)) {
                resultado = false;
            }
            else {
                if (resto == 0) diasMes[2] = 29;
                if ((dia < 1) || (dia > diasMes[mes])) resultado = false;
            }
        }
    }
    
    return resultado;
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
    
    LimpaCamposInfo();
    if (UserName == "") {
        clienteOK = false;
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
    }
    else {
        clienteOK = true;
        EscreveEnvMsgSrv();
        xhttp.open("POST", recurso, false);
        try {
            xhttp.send(MontaMsgServ("Cliente", UserName, "null", "null", "null", "null"));
            var xmlRec = xhttp.responseXML;
            CarregaCliente(xmlRec);
            
            if (clienteOK) {
                EscreveTexto("Nome de usuário: " + UserName, "info1");
                EscreveTexto("Nome: " + NomeCliente, "info2");
                
                var sufixo = "o";
                if (Info4Cliente == "feminino") {
                    sufixo = "a";
                }
                
                EscreveTexto("Idos" + sufixo + ": " + Info1Cliente, "info3");
                EscreveTexto("Dificuldade de locomoção: " + Info2Cliente, "info4");
                EscreveTexto("Exigente: " + Info3Cliente, "info5");
            }
            else {
                EscreveTexto("Cliente não cadastrado", "info1");
            }
            EscreveTexto("Resposta do Servidor: informações do cliente", "infocom");
            document.getElementById("info6").style.color = "#23257e";
        
        } catch(err) {
            EscreveMsgErrSrv();
            console.log("Erro " + err);
        }
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
    HoraChegada = horarioChegada.value;
    
    LimpaCamposInfo();
    
    if (!dataOK) {
        EscreveTexto("Entre com a data da reserva", "info1");
    }
    else {
        if (!clienteOK) {
            EscreveTexto("Cliente não cadastrado", "info1");
        }
        else { 
            if (NumPessoas == "") {
                numPessoasOK = false;
                EscreveTexto("Entre com o número de pessoas", "info2");
            }
            else {
                var NumeroPessoas = parseInt(NumPessoas);
                if (NumeroPessoas < 1) {
                    numPessoasOK = false;
                    EscreveTexto("Entre com o número de pessoas", "info2");
                }
                else {
                    numPessoasOK = true;
                    if (NumeroPessoas > 12) {
                        NumPessoas = "12";
                        LimpaCamposInfo();
                        EscreveTexto("Número de pessoas maior que 12", "info3");
                        EScreveTexto("Verificar disponibilidade", "info4");
                    }
                    if (HoraChegada == "") {
                        horaChegadaOK = false;
                        EscreveTexto("Entre com o horário de chegada", "info1");
                    }
                    else {
                        horaChegadaOK = true;
                    }
                }
            }    
        }
    }
    
    if (dataOK && clienteOK && numPessoasOK && horaChegadaOK) {
       
        LimpaCamposInfo();
        EscreveTexto("Escolha a mesa", "info1");
    
        EscreveEnvMsgSrv();
        xhttp.open("POST", recurso, false);
        try {
            xhttp.send(MontaMsgServ("DataCliente", UserName, DataReserva, NumPessoas, HoraChegada, "null"));
            var xmlRec = xhttp.responseXML;
            CarregaCliente(xmlRec);
            CarregaMesas(xmlRec);
            EscreveTexto("Resposta do Servidor: data e cliente verificados", "infocom");
        
        } catch(err) {
            EscreveMsgErrSrv();
            console.log("Erro " + err);
        }
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
    MesaSelecionada = mesa;
    if (numPessoasOK) {
        if (clienteOK) {
            if (mesa == "A00") {
                impMesa = "Gazebo";
            }
            LimpaCamposInfo();
            EscreveTexto("Confirma a reserva da " + NomeMesa(mesa) + "?", "info1");
            EscreveTexto("Cliente: " + NomeCliente, "info2");
            EscreveTexto("Data: " + DataReserva, "info3");
            EscreveTexto("Número de pessoas: " + NumPessoas, "info4");
            EscreveTexto("Horário de chegada: " + HoraChegada, "info5");
        }
    }
    else {
        LimpaCamposInfo();
        EscreveTexto("Entre com o número de pessoas", "info1");
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
    
    DataReserva = dataReserva.value;
    
    UserName = userName.value;
    NumPessoas = numPessoas.value;
    HoraChegada = horarioChegada.value;
    
    EscreveEnvMsgSrv();
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ("Confirma", UserName, DataReserva, NumPessoas, HoraChegada, MesaSelecionada));
        var xmlRec = xhttp.responseXML;
        CarregaCliente(xmlRec);
        CarregaMesas(xmlRec);
        
        AtualizaMesa(MesaSelecionada, UserName, NumPessoas, HoraChegada);
        
        LimpaCamposInfo();
        EscreveTexto("Confirmada a reserva da " + NomeMesa(MesaSelecionada), "info1");
        EscreveTexto("Cliente: " + NomeCliente, "info2");
        EscreveTexto("Data: " + DataReserva, "info3");
        EscreveTexto("Número de pessoas: " + NumPessoas, "info4");
        EscreveTexto("Horário de chegada: " + HoraChegada, "info5");
        
        EscreveTexto("Resposta do Servidor: confirmação da reserva", "infocom");
        
        dataOK = false;
        clienteOK = false;
        numPessoasOK = false;
        horaChegadaOK = false;
    
    } catch(err) {
        EscreveMsgErrSrv();
        console.log("Erro " + err);
    }
    
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
    NA00 = grupo[i].getElementsByTagName("NA00")[0].childNodes[0].nodeValue;
    HA00 = grupo[i].getElementsByTagName("HA00")[0].childNodes[0].nodeValue;
     
    OA01 = grupo[i].getElementsByTagName("OA01")[0].childNodes[0].nodeValue;
    NA01 = grupo[i].getElementsByTagName("NA01")[0].childNodes[0].nodeValue;
    HA01 = grupo[i].getElementsByTagName("HA01")[0].childNodes[0].nodeValue;
    
    OA02 = grupo[i].getElementsByTagName("OA02")[0].childNodes[0].nodeValue;
    NA02 = grupo[i].getElementsByTagName("NA02")[0].childNodes[0].nodeValue;
    HA02 = grupo[i].getElementsByTagName("HA02")[0].childNodes[0].nodeValue;
    
    OA03 = grupo[i].getElementsByTagName("OA03")[0].childNodes[0].nodeValue;
    NA03 = grupo[i].getElementsByTagName("NA03")[0].childNodes[0].nodeValue;
    HA03 = grupo[i].getElementsByTagName("HA03")[0].childNodes[0].nodeValue;
    
    OA04 = grupo[i].getElementsByTagName("OA04")[0].childNodes[0].nodeValue;
    NA04 = grupo[i].getElementsByTagName("NA04")[0].childNodes[0].nodeValue;
    HA04 = grupo[i].getElementsByTagName("HA04")[0].childNodes[0].nodeValue;
    
    OA05 = grupo[i].getElementsByTagName("OA05")[0].childNodes[0].nodeValue;
    NA05 = grupo[i].getElementsByTagName("NA05")[0].childNodes[0].nodeValue;
    HA05 = grupo[i].getElementsByTagName("HA05")[0].childNodes[0].nodeValue;
    
    OA06 = grupo[i].getElementsByTagName("OA06")[0].childNodes[0].nodeValue;
    NA06 = grupo[i].getElementsByTagName("NA06")[0].childNodes[0].nodeValue;
    HA06 = grupo[i].getElementsByTagName("HA06")[0].childNodes[0].nodeValue;
    
    OA07 = grupo[i].getElementsByTagName("OA07")[0].childNodes[0].nodeValue;
    NA07 = grupo[i].getElementsByTagName("NA07")[0].childNodes[0].nodeValue;
    HA07 = grupo[i].getElementsByTagName("HA07")[0].childNodes[0].nodeValue;
    
    OA08 = grupo[i].getElementsByTagName("OA08")[0].childNodes[0].nodeValue;
    NA08 = grupo[i].getElementsByTagName("NA08")[0].childNodes[0].nodeValue;
    HA08 = grupo[i].getElementsByTagName("HA08")[0].childNodes[0].nodeValue;
    
    OB09 = grupo[i].getElementsByTagName("OB09")[0].childNodes[0].nodeValue;
    NB09 = grupo[i].getElementsByTagName("NB09")[0].childNodes[0].nodeValue;
    HB09 = grupo[i].getElementsByTagName("HB09")[0].childNodes[0].nodeValue;
    
    OB10 = grupo[i].getElementsByTagName("OB10")[0].childNodes[0].nodeValue;
    NB10 = grupo[i].getElementsByTagName("NB10")[0].childNodes[0].nodeValue;
    HB10 = grupo[i].getElementsByTagName("HB10")[0].childNodes[0].nodeValue;
    
    OB11 = grupo[i].getElementsByTagName("OB11")[0].childNodes[0].nodeValue;
    NB11 = grupo[i].getElementsByTagName("NB11")[0].childNodes[0].nodeValue;
    HB11 = grupo[i].getElementsByTagName("HB11")[0].childNodes[0].nodeValue;
    
    OB12 = grupo[i].getElementsByTagName("OB12")[0].childNodes[0].nodeValue;
    NB12 = grupo[i].getElementsByTagName("NB12")[0].childNodes[0].nodeValue;
    HB12 = grupo[i].getElementsByTagName("HB12")[0].childNodes[0].nodeValue;
    
    OB13 = grupo[i].getElementsByTagName("OB13")[0].childNodes[0].nodeValue;
    NB13 = grupo[i].getElementsByTagName("NB13")[0].childNodes[0].nodeValue;
    HB13 = grupo[i].getElementsByTagName("HB13")[0].childNodes[0].nodeValue;
    
    OB14 = grupo[i].getElementsByTagName("OB14")[0].childNodes[0].nodeValue;
    NB14 = grupo[i].getElementsByTagName("NB14")[0].childNodes[0].nodeValue;
    HB14 = grupo[i].getElementsByTagName("HB14")[0].childNodes[0].nodeValue;
    
    OB15 = grupo[i].getElementsByTagName("OB15")[0].childNodes[0].nodeValue;
    NB15 = grupo[i].getElementsByTagName("NB15")[0].childNodes[0].nodeValue;
    HB15 = grupo[i].getElementsByTagName("HB15")[0].childNodes[0].nodeValue;
    
    OB16 = grupo[i].getElementsByTagName("OB16")[0].childNodes[0].nodeValue;
    NB16 = grupo[i].getElementsByTagName("NB16")[0].childNodes[0].nodeValue;
    HB16 = grupo[i].getElementsByTagName("HB16")[0].childNodes[0].nodeValue;
    
    
    AtualizaMesa("A00", OA00, NA00, HA00);
    AtualizaMesa("A01", OA01, NA01, HA01);
    AtualizaMesa("A02", OA02, NA02, HA02);
    AtualizaMesa("A03", OA03, NA03, HA03);
    AtualizaMesa("A04", OA04, NA04, HA04);
    AtualizaMesa("A05", OA05, NA05, HA05);
    AtualizaMesa("A06", OA06, NA06, HA06);
    AtualizaMesa("A07", OA07, NA07, HA07);
    AtualizaMesa("A08", OA08, NA08, HA08);
    
    AtualizaMesa("B09", OB09, NB09, HB09);
    AtualizaMesa("B10", OB10, NB10, HB10);
    AtualizaMesa("B11", OB11, NB11, HB11);
    AtualizaMesa("B12", OB12, NB12, HB12);
    AtualizaMesa("B13", OB13, NB13, HB13);
    AtualizaMesa("B14", OB14, NB14, HB14);
    AtualizaMesa("B15", OB15, NB15, HB15);
    AtualizaMesa("B16", OB16, NB16, HB16);

}

function AtualizaMesa(idmesa, ocupacao, numPes, horCheg) {
    if (ocupacao == "livre") {
        document.getElementById(idmesa).style.backgroundColor = "#33ff71";
        document.getElementById(idmesa).innerHTML = NomeMesa(idmesa) + "                                ";
    }
    else {
        document.getElementById(idmesa).style.backgroundColor = "#aeb6bf";
        document.getElementById(idmesa).innerHTML = NomeMesa(idmesa) + ": " + numPes + " pessoas " + ocupacao + " " + horCheg;
    }
}

function NomeMesa(IdMesa) {
    nomMesa = "";
    if (IdMesa == "A00") nomMesa = "Gazebo";
    if (IdMesa == "A01") nomMesa = "Mesa A1";
    if (IdMesa == "A02") nomMesa = "Mesa A2";
    if (IdMesa == "A03") nomMesa = "Mesa A3";
    if (IdMesa == "A04") nomMesa = "Mesa A4";
    if (IdMesa == "A05") nomMesa = "Mesa A5";
    if (IdMesa == "A06") nomMesa = "Mesa A6";
    if (IdMesa == "A07") nomMesa = "Mesa A7";
    if (IdMesa == "A08") nomMesa = "Mesa A8";
 
    if (IdMesa == "B09") nomMesa = "Mesa B9";
    if (IdMesa == "B10") nomMesa = "Mesa B10";
    if (IdMesa == "B11") nomMesa = "Mesa B11";
    if (IdMesa == "B12") nomMesa = "Mesa B12";
    if (IdMesa == "B13") nomMesa = "Mesa B13";
    if (IdMesa == "B14") nomMesa = "Mesa B14";
    if (IdMesa == "B15") nomMesa = "Mesa B15";
    if (IdMesa == "B16") nomMesa = "Mesa B16";

    return nomMesa;
 
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
    Info1Cliente = grupo[i].getElementsByTagName("INFO1")[0].childNodes[0].nodeValue;
    Info2Cliente = grupo[i].getElementsByTagName("INFO2")[0].childNodes[0].nodeValue;
    Info3Cliente = grupo[i].getElementsByTagName("INFO3")[0].childNodes[0].nodeValue;
    Info4Cliente = grupo[i].getElementsByTagName("INFO4")[0].childNodes[0].nodeValue;
        
    if (IdCliente == "null") {
        clienteOK = false;
    }
    else {
        clienteOK = true;
    }
    
}

//*********************************************************************************************************************
// Nome da função: MontaMsgServ                                                                                       *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: monta a mensagem de requisição ao servidor                                                                 *
//                                                                                                                    *
// Entrada: código da requisição, data da reserva, nome de usuário do cliente, número de pessoas na mesa, horário     *
//          de chegada e mesa selecionada. Caso um ou mais campos não estejam dispóníveis no momento do envio da      *
//          solicitação, estes devem ser preenchidos a string "null"                                                  *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function MontaMsgServ(codigoMsg, nomeUsuario, dataRes, numeroPes, horarioCheg, mesaSel) {
    
    msgServ = "Codigo: " + codigoMsg + "\n" +
              "DataReserva: " + dataRes + "\n" +
              "NomeUsuario: " + nomeUsuario + "\n" +
              "NumPessoas: " + numeroPes + "\n" +
              "HorarioCheg: " + horarioCheg + "\n" +
              "MesaSelec: " + mesaSel + "\n";
              
    return msgServ;
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
}

function EscreveRspMsgSrv(msgrsp) {
    document.getElementById("infocom").innerHTML = "Resposta do Servidor" + msgrsp;
}

function EscreveMsgErrSrv() {
    document.getElementById("infocom").innerHTML = "O Servidor não respondeu";
}

function LimpaCampoInfo(id) {
    document.getElementById(id).innerHTML = "                      ";
}

function LimpaCamposInfo() {
    document.getElementById("info1").innerHTML = "                      ";
    document.getElementById("info2").innerHTML = "                      ";
    document.getElementById("info3").innerHTML = "                      ";
    document.getElementById("info4").innerHTML = "                      ";
    document.getElementById("info5").innerHTML = "                      ";
}

function LimpaCampoInfoForm(id) {
    document.getElementById(id).innerHTML = "                  ";
}








