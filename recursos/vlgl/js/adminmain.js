//*********************************************************************************************************************
//                                                                                                                    *
//                       Programa Javascript para rodar no navegador de um administrador                              *
//                                                                                                                    *
// Data: 30/09/2021                                                                                                   *
//                                                                                                                    *
// Função: permite ao administrador gerenciar as reservas das mesas.                                                  *
//                                                                                                                    *
//*********************************************************************************************************************
//
const form = document.getElementById('signup');
const userName = form.elements['username'];
const dataReserva = form.elements['data'];
const numPessoas = form.elements['numpessoas'];
const horarioChegada = form.elements['hora'];

var NomeUsuarioAdmin;
var NomeAdmin;

var DataReserva;
var DataResEnvio;  // Variável de data da reserva para solicitação ao servidor no formato DD-MM-AAAA
var UserName;      // O nome de usuário do cliente inserido pelo administrador
var NumPessoas;
var NumeroPessoas;
var HoraChegada;

//var IdCliente;     // O nome de usuário do cliente recebido na mensagem XML


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

var dataOK = false;
var clienteOK = false;
var numPessoasOK = false;
var horaChegadaOK = false;
var consultaMesa = false;
var BotaoReservaConfirma = false; // false = Reserva e true = Confirma
var HabilitaExclusao = false;

MesaNomeUsuario = new Array(17);
MesaNomeCliente = new Array(17);
MesaNumPes = new Array(17);
MesaHoraCheg = new Array(17);
MesaAdminResp = new Array(17);
MesaHoraReg = new Array(17);
MesaDataReg = new Array(17);

for (let i = 0; i < 17; i++) {
    MesaNomeUsuario[i] = "null";
    MesaNomeCliente[i] = "null";
    MesaNumPes[i] = "null";
    MesaHoraCheg[i] = "null";
    MesaAdminResp[i] = "null";
    MesaHoraReg[i] = "null";
    MesaDataReg[i] = "null";
}

var MesaSelecionada;
var NomeDaMesa;
var CapacidadeMesa;
var IdMesaConsulta;

VerificaAdmin()

// Fim do Programa

function CarregaVariaveisFormulario() {
    
    DataReserva = dataReserva.value;
    NomeUsuarioCliente = userName.value;
    UserName = NomeUsuarioCliente;
    NumPessoas = numPessoas.value;
    HoraChegada = horarioChegada.value;
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
    let recurso = "admin/";
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
        EscreveTexto("Servidor: informações do administrador", "info1");
    };
    
    requisicao.ontimeout = function(e) {
        EscreveTexto("O servidor não respondeu à requisição", "info1");
    };
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
    
    consultaMesa = false;
    CarregaVariaveisFormulario();
    consultaMesa = false;
    
    if (DataReserva != "") {
       if (VerificaFormatoData(DataReserva)) {
            
            let requisicao = new XMLHttpRequest();
            let recurso = "reservas/data/" + DataResEnvio;
            requisicao.open("GET", recurso, true);
            requisicao.timeout = 2000;
            EscreveMsgEnvSrv();
            requisicao.send(null);
                
            requisicao.onload = function() {
                let XMLRec = requisicao.responseXML;
                CarregaMesas(XMLRec);
                dataOK = true;
                if (consultaMesa) {
                    EscreveTexto("Selecione a mesa para consulta", "info1");
                }
                else {
                    EscreveTexto("Recebido mapa de reservas do dia " + DataReserva, "info1");
                }
                EscreveTexto(DataReserva, "dataMapa");
            };
            
            requisicao.ontimeout = function(e) {
                EscreveTexto("O servidor não respondeu à requisição", "info1");
            };
        }
        else {
            EscreveTexto("Data inválida ou formato inválido. Use DD/MM/AAAA", "info1");
        }
    }
    else {
        EscreveTexto("Entre com a data da reserva. Formato: DD/MM/AAAA", "info1");
    }
}

//*********************************************************************************************************************
// Nome da função: VerificaFormatoData                                                                                *
//                                                                                                                    *
// Data: 28/09/2021                                                                                                   *
//                                                                                                                    *
// Função: verifica a consistência da data digitada pelo usuário. Também gera a variável DataResEnvio para            *
//         envio ao servidor.                                                                                         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: se a data está OK retorna true.                                                                             *
//*********************************************************************************************************************
//
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
    if (resultado) {
        var Dia;
        if (dia < 10) { Dia = "0" + dia; }
        else { Dia = dia; }
        var Mes;
        if (mes < 10) { Mes = "0" + mes; }
        else { Mes = mes; }
        
        DataResEnvio = Dia + "-" + Mes + "-" + ano;
        
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
    
    consultaMesa = false;
    CarregaVariaveisFormulario();
    clienteOK = false;
    
    if (UserName != "") {
        
        let requisicao = new XMLHttpRequest();
        let recurso = "cadastro/cliente/" + NomeUsuarioCliente;
        requisicao.open("GET", recurso, true);
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(null);
    
        requisicao.onload = function() {
            let XMLRec = requisicao.responseXML;
            CarregaCliente(XMLRec);
            
            if (clienteOK) {
                EscreveTexto("Recebidas as informações do cliente", "info1");
                EscreveTexto("Nome de usuário: " + NomeUsuarioCliente, "info2");
                EscreveTexto("Nome completo: " + NomeCliente, "info3");
                EscreveTexto("Celular: " + CelularCliente, "info4");
                
                let sufixo = "o";
                if (GeneroCliente == "feminino") sufixo = "a";
                
                EscreveTexto("Idos" + sufixo + ": " + IdosoCliente, "info5");
                EscreveTexto("Dificuldade de locomoção: " + LocomocaoCliente, "info6");
                EscreveTexto("Exigente: " + ExigenteCliente, "info7");
                EscreveTexto("Obs 1: " + Obs1Cliente, "info8");
                EscreveTexto("Obs 2: " + Obs2Cliente, "info9");
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Cliente não cadastrado", "info1");
            }
         };
         
         requisicao.ontimeout = function(e) {
             EscreveTexto("O servidor não respondeu à requisição", "info1");
             console.log("Erro: " + e);
         };
    }
    else {
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
    }
}

//*********************************************************************************************************************
// Nome da função: ReservaConfirma                                                                                    *
//                                                                                                                    *
// Data: 30/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Reserva ou Confirma ao lado do campo Número       *
//         de pessoas do formulário. Chama a função Reserva ou a função Confirma de acordo o sestado do flag          *
//         BotaoReservaConfirma                                                                                       *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function ReservaConfirma() {
    
    consultaMesa = false;
    if (BotaoReservaConfirma) {
        ConfirmaReserva();
        
    }
    else {
        Reserva();
    }
}

//*********************************************************************************************************************
// Nome da função: Reserva                                                                                            *
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
function Reserva() {
 
    CarregaVariaveisFormulario();
    let condicao = true;
    
    if (DataReserva == "") {
        EscreveTexto("Entre com a data da reserva", "info1");
        condicao = false;
    }
    
    if (!dataOK && condicao) {
        EscreveTexto("Verifique a data da reserva", "info1");
        condicao = false;
    }
    
    if ((NomeUsuarioCliente == "") && condicao) {
        EscreveTexto("Entre com o nome do cliente", "info1");
        condicao = false;
    }
    if (!clienteOK && condicao) {
        EscreveTexto("Verifique o cliente", "info1");
        condicao = false;
    }
    if ((NumPessoas == "") && condicao) {
        EscreveTexto("Entre com o número de pessoas", "info1");
        condicao = false;
    }
    else {
        let NumeroPessoas = parseInt(NumPessoas);
        if (NumeroPessoas < 1) {
            condicao = false;
            EscreveTexto("Entre com o número de pessoas", "info1");
        }
        else {
            if (NumeroPessoas > 12) {
                NumPessoas = "12";
                LimpaCamposInfo();
                EscreveTexto("Número de pessoas maior que 12" - Verificar, "info1");
            }
            numPessoasOK = true;
        }
    }
    if ((HoraChegada == "") && condicao) {
        EscreveTexto("Entre com o horário de chegada", "info1");
        condicao = false;
    }
     
    if (condicao) {
        EscreveTexto("Selecione a mesa para reserva", "info1");
        HabilitaSelMesa = true;
    }
}

//*********************************************************************************************************************
// Nome da função: SelecionaMesa                                                                                      *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário seleciona uma mesa para reserva. Mostra um pop-up de confirma. Após a     *
//         confirmação, envia para o servidor a solicitação, espera resposta, e caso seja afirmativa, mostra uma      *
//         mensagem indicando a contirmação. Também muda a cor da mesa para cinza e coloca o Id do cliente.           *
//                                                                                                                    *
// Entrada: o identificador da mesa (A00 a A08 e B09 a B16)                                                           *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function SelecionaMesa(mesa) {
    console.log("HabilitaSelMesa = " + HabilitaSelMesa + " - consultaMesa = " + consultaMesa);
    if (HabilitaSelMesa) {
        if (!consultaMesa) {
    
            DataReserva = dataReserva.value;
            NomeUsuarioCliente = userName.value;
            NumPessoas = numPessoas.value;
            MesaSelecionada = mesa;
    
            if (numPessoasOK) {
                if (clienteOK) {
                    LimpaCamposInfo();
                    EscreveTexto("Confirma a reserva da " + NomeMesa(mesa) + "?", "info1");
                    EscreveTexto("Cliente: " + NomeCliente, "info2");
                    EscreveTexto("Data: " + DataReserva, "info3");
                    EscreveTexto("Número de pessoas: " + NumPessoas, "info4");
                    EscreveTexto("Horário de chegada: " + HoraChegada, "info5");
                    BotaoReservaConfirma = true;
                    EscreveTexto("Confirma", "botaoresconf");
                }
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Entre com o número de pessoas", "info1");
            }
        }
        else { // Se o flag de habilitação de consulta de mesa é true, mostra os dados da mesa
            EscreveTexto("Detalhes da reserva:", "info1");
            IdMesaConsulta = mesa;
            LimpaCamposInfo();
            let numMesa = parseInt(mesa[1] + mesa[2]);
            if (MesaNomeUsuario[MesaSelecionada] != "livre") {
                EscreveTexto("Reserva da " + NomeMesa(mesa) + " para " + DataReserva, "info2");
                EscreveTexto("Nome de usuário: " + MesaNomeUsuario[numMesa], "info3");
                EscreveTexto("Nome completo: " + MesaNomeCliente[numMesa], "info4");
                EscreveTexto("Número de pessoas: " + MesaNumPes[numMesa], "info5");
                EscreveTexto("Horário de chegada: " + MesaHoraCheg[numMesa], "info6");
                EscreveTexto("Responsável pela reserva: " + MesaAdminResp[numMesa], "info7");
                EscreveTexto("Data do registro da reserva: " + MesaDataReg[numMesa], "info8");
                EscreveTexto("Hora do registro da reserva: " + MesaHoraReg[numMesa], "info9");
                consultaMesa = false;
                HabilitaExclusao = true;  // Após a consulta da mesa, habilita a função de exclusão de reserva
            }
            else {
                EscreveTexto(NomeMesa(mesa) + " livre", "info2");
            }
        }
        HabilitaSelMesa = false;
    }
    console.log("consultaMesa = " + consultaMesa);
}

//*********************************************************************************************************************
// Nome da função: ConfirmaReserva                                                                                    *
//                                                                                                                    *
// Data: 30/09/2021                                                                                                   *
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
function ConfirmaReserva() {
    
    CarregaVariaveisFormulario();
    VerificaFormatoData(dataReserva);
    
    let requisicao = new XMLHttpRequest();
    let recurso = "reservas";
    requisicao.open("POST", recurso, true);
    requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
    requisicao.timeout = 2000;
    EscreveMsgEnvSrv();
    requisicao.send(MontaMsgJson());
    
    requisicao.onload = function() {
        let XMLRec = requisicao.responseXML;
        
        CarregaMesas(XMLRec);
        AtualizaMesa(MesaSelecionada, UserName, NumPessoas, HoraChegada);
        
        grupo = XMLRec.getElementsByTagName("ESTADO");
        let confirmaEstado = grupo[0].getElementsByTagName("RESERVA")[0].childNodes[0].nodeValue;
        let mesaEstado = grupo[0].getElementsByTagName("MESA")[0].childNodes[0].nodeValue;
        let dataEstado = grupo[0].getElementsByTagName("DATA")[0].childNodes[0].nodeValue;
        let nomeUsuarioEstado = grupo[0].getElementsByTagName("ID")[0].childNodes[0].nodeValue;
        let nomeEstado = grupo[0].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        let numpesEstado = grupo[0].getElementsByTagName("NUMPES")[0].childNodes[0].nodeValue;
        let horarioEstado = grupo[0].getElementsByTagName("HORARES")[0].childNodes[0].nodeValue;
        
        if (confirmaEstado == "sim") {
            LimpaCamposInfo();
            EscreveTexto("Servidor: confirmação da reserva ", "info1");
            EscreveTexto("Confirmada a reserva da " + NomeMesa(mesaEstado), "info2");
            EscreveTexto("Data: " + dataEstado, "info3");
            EscreveTexto("Nome de usuário: " + nomeUsuarioEstado, "info4");
            EscreveTexto("Nome completo: " + nomeEstado, "info5");
            EscreveTexto("Número de pessoas: " + numpesEstado, "info6");
            EscreveTexto("Horário de chegada: " + horarioEstado, "info7");
        }
        else {
            EscreveTexto("Houve falha na confirmação da reserva ", "info1");
        }
        
        //dataOK = false;
        //clienteOK = false;
        //numPessoasOK = false;
        //horaChegadaOK = false;
    };
    
    requisicao.ontimeout = function(e) {
        EscreveTexto("O servidor não respondeu à requisição", "info1");
        console.log("Erro: " + e);
    };
    BotaoReservaConfirma = false;
    EscreveTexto("Reserva", "botaoresconf");
}

//*********************************************************************************************************************
// Nome da função: ExcluiReserva                                                                                      *
//                                                                                                                    *
// Data: 01/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário pressiona o botão Exclui. Envia uma mensagem para o servidor com          *
//         as informações sobre a solicitação de exclusão reserva da mesa. O servidor deve responder uma mensagem     *
//         confirmando a exclusão da reserva.                                                                         *
//         apresenta na tela.                                                                                         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function ExcluiReserva() {
    
    if (HabilitaExclusao) {  // Se o flag de habilitação de exclusão é true, envia solicitação ao servidor
        let MsgExclusao = "Confirma a exclusão da reserva da " + NomeMesa(IdMesaConsulta);
        MsgExclusao = MsgExclusao + " na data " + DataReserva + "?";
        if (confirm(MsgExclusao)) {
            
            let requisicao = new XMLHttpRequest();
            let recurso = "reserva/exclui/" + DataResEnvio + "/" + IdMesaConsulta;
            requisicao.open("DELETE", recurso, true);
            requisicao.timeout = 2000;
            EscreveMsgEnvSrv();
            requisicao.send(null);
    
            requisicao.onload = function() {
                let XMLRec = requisicao.responseXML;
                grupo = XMLRec.getElementsByTagName("ESTADO");
                let confirmaExclusao = grupo[0].getElementsByTagName("EXCLUI")[0].childNodes[0].nodeValue;
                let dataReservaExcluida = grupo[0].getElementsByTagName("DATA")[0].childNodes[0].nodeValue;
                let adminExclusao = grupo[0].getElementsByTagName("ADMINRESP")[0].childNodes[0].nodeValue;
                let idMesaExcluida = grupo[0].getElementsByTagName("MESA")[0].childNodes[0].nodeValue;
                let horaExclusao = grupo[0].getElementsByTagName("HORAREG")[0].childNodes[0].nodeValue;
                let dataExclusao = grupo[0].getElementsByTagName("DATAREG")[0].childNodes[0].nodeValue;
            
                if (confirmaExclusao) {
                    LimpaCamposInfo();
                    EscreveTexto("Servidor: confirmada a exclusão da reserva", "info1");
                    EscreveTexto("A reserva da " + NomeMesa(idMesaExcluida) + " foi excluída", "info2");
                    EscreveTexto("Data da reserva excluída: " + dataReservaExcluida, "info3");
                    EscreveTexto("Responsável: " + adminExclusao, "info4");
                    EscreveTexto("Hora da exclusão: " + horaExclusao, "info5");
                    EscreveTexto("Data da exclusão: " + dataExclusao, "info6");
                    CarregaMesas(XMLRec);
                    dataOK = true;
                    EscreveTexto(DataReserva, "dataMapa");
                }
                
                
                
                
                
                
            };
         
            requisicao.ontimeout = function(e) {
                EscreveTexto("O servidor não respondeu à requisição", "info1");
                console.log("Erro: " + e);
            };
            consultaMesa = false;
        }
    }
    else {
     EscreveTexto("Antes de excluir, é preciso consultar a mesa", "info1");
    }
    
}

//*********************************************************************************************************************
// Nome da função: ConsultaReservaMesa                                                                                *
//                                                                                                                    *
// Data: 30/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário pressiona o botão Consulta. Solicita ao usuário que clique em uma mesa    *
//         e mostra todas as informações da reserva daquela mesa.                                                     *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function ConsultaReservaMesa() {
 
    CarregaVariaveisFormulario();
    LimpaCamposInfo();
    
    if (DataReserva != "") {
       VerificaData();
       HabilitaSelMesa = true;
       consultaMesa = true;
    }
    else {
        EscreveTexto("Entre com a data da reserva e verifique", "info1");
    }
    console.log("consultaMesa = " + consultaMesa);
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
function CarregaMesas(msgXML) {
    
    let grupo = msgXML.getElementsByTagName("MESAS");
    
    let numMesas = 17;
    let letra = "A";
    let sufixo = "";
    for (let i = 0; i < numMesas; i++) {
        if (i > 8) letra = "B";
        
        sufixo = letra + IntToStr2(i);
        
        MesaNomeUsuario[i] = grupo[0].getElementsByTagName("NOU" + sufixo)[0].childNodes[0].nodeValue;
        MesaNomeCliente[i] = grupo[0].getElementsByTagName("NOC" + sufixo)[0].childNodes[0].nodeValue;
        MesaNumPes[i] = grupo[0].getElementsByTagName("NUP" + sufixo)[0].childNodes[0].nodeValue;
        MesaHoraCheg[i] = grupo[0].getElementsByTagName("HOC" + sufixo)[0].childNodes[0].nodeValue;
        MesaAdminResp[i] = grupo[0].getElementsByTagName("ADR" + sufixo)[0].childNodes[0].nodeValue;
        MesaHoraReg[i] = grupo[0].getElementsByTagName("HOR" + sufixo)[0].childNodes[0].nodeValue;
        MesaDataReg[i] = grupo[0].getElementsByTagName("DTR" + sufixo)[0].childNodes[0].nodeValue;
        
        AtualizaMesa(sufixo, MesaNomeUsuario[i], MesaNumPes[i], MesaHoraCheg[i]);
    }
}

function IntToStr2(num) {
    
    if (num > 9) {
        return num;
    }
    else {
        return "0" + num;
    }
}

function AtualizaMesa(idmesa, ocupacao, numPes, horCheg) {
    if (ocupacao == "livre") {
        document.getElementById(idmesa).style.backgroundColor = "#33ff71";
        document.getElementById(idmesa).innerHTML = NomeMesa(idmesa) + " " + CapacidadeMesa;
    }
    else {
        document.getElementById(idmesa).style.backgroundColor = "#aeb6bf";
        document.getElementById(idmesa).innerHTML = NomeMesa(idmesa) + ": " + numPes + " pessoas " + ocupacao + " " + horCheg;
    }
}

function NomeMesa(IdMesa) {
    let nomMesa = "";
    let capMesa = "";
    
    if (IdMesa == "A00") {
        nomMesa = "Mesa Gazebo";
        capMesa = "(2 ou 4 pessoas)";
    }
    if (IdMesa == "A01") {
        nomMesa = "Mesa A1";
        capMesa = "(8 a 12 pessoas)";
    }
    if (IdMesa == "A02") {
        nomMesa = "Mesa A2";
        capMesa = "(5 ou 6 pessoas)";
    }
    if (IdMesa == "A03") {
        nomMesa = "Mesa A3";
        capMesa = "(4 a 6 pessoas)";
    }
    if (IdMesa == "A04") {
        nomMesa = "Mesa A4";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "A05") {
        nomMesa = "Mesa A5";
        capMesa = "(2 a 15 pessoas)";
    }
    if (IdMesa == "A06") {
        nomMesa = "Mesa A6";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "A07") {
        nomMesa = "Mesa A7";
        capMesa = "(2 a 15 pessoas)";
    }
    if (IdMesa == "A08") {
        nomMesa = "Mesa A8";
        capMesa = "(2 a 15 pessoas)";
    }
 
    if (IdMesa == "B09") {
        nomMesa = "Mesa B9";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B10") {
        nomMesa = "Mesa B10";
        capMesa = "(4 ou 5 pessoas)";
    }
    if (IdMesa == "B11") {
        nomMesa = "Mesa B11";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B12") {
        nomMesa = "Mesa B12";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B13") {
        nomMesa = "Mesa B13";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B14") {
        nomMesa = "Mesa B14";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B15") {
        nomMesa = "Mesa B15";
        capMesa = "(2 a 4 pessoas)";
    }
    if (IdMesa == "B16") {
        nomMesa = "Mesa B16";
        capMesa = "(2 a 4 pessoas)";
    }

    NomeDaMesa = nomMesa;
    CapacidadeMesa = capMesa;
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
    
    let i = 0;
    let grupo = xmlMsg.getElementsByTagName("CLIENTE");
    
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
// Nome da função: MontaMsgJson                                                                                       *
//                                                                                                                    *
// Data: 24/09/2021                                                                                                   *
//                                                                                                                    *
// Função: monta a mensagem de requisição ao servidor no formato Json                                                 *
//                                                                                                                    *
// Entrada: código da requisição, data da reserva, nome de usuário do cliente, número de pessoas na mesa, horário     *
//          de chegada, mesa selecionada e administrador responsável. Caso um ou mais campos não estejam dispóníveis  *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function MontaMsgJson() {
        
    var msgJson = "{\n" +
                  "  \"nomeUsuario\" : \"" + UserName + "\",\n" +
                  "  \"nomeCliente\" : \"" + NomeCliente + "\",\n" +
                  "  \"dataReserva\" : \"" + DataResEnvio + "\",\n" +
                  "  \"numPessoas\" : \"" + NumPessoas + "\",\n" +
                  "  \"horaChegada\" : \"" + HoraChegada + "\",\n" +
                  "  \"mesaSelecionada\" : \"" + MesaSelecionada + "\",\n" +
                  "  \"adminResp\" : \"" + NomeUsuarioAdmin + "\"\n" +
                  "}";
                  
    return msgJson;
}

function EscreveTexto(texto, idHTML) {
    document.getElementById(idHTML).innerHTML = texto;
}

function EscreveMsgEnvSrv() {
    document.getElementById("info1").innerHTML = "Enviada requisição. Aguardando resposta do servidor";
}

function EscreveMsgErrSrv() {
    document.getElementById("info1").innerHTML = "O Servidor não respondeu";
}

function LimpaCampoInfo1() {
    document.getElementById("info1").innerHTML = "                      ";
}

function LimpaCamposInfo() {
    document.getElementById("info2").innerHTML = "                      ";
    document.getElementById("info3").innerHTML = "                      ";
    document.getElementById("info4").innerHTML = "                      ";
    document.getElementById("info5").innerHTML = "                      ";
    document.getElementById("info6").innerHTML = "                      ";
    document.getElementById("info7").innerHTML = "                      ";
    document.getElementById("info8").innerHTML = "                      ";
    document.getElementById("info9").innerHTML = "                      ";
    document.getElementById("info10").innerHTML = "                      ";
    document.getElementById("info11").innerHTML = "                      ";
}
