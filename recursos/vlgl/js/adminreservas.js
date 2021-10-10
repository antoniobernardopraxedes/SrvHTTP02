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
        let dadosJson = JSON.parse(requisicao.responseText);
        NomeUsuarioAdmin = dadosJson.nomeUsuarioAdmin;
        EscreveTexto("Administrador: " + NomeUsuarioAdmin, "nomeadmin");
        EscreveTexto("Servidor: informações do administrador", "info1");
    };
    
    requisicao.ontimeout = function(e) {
        EscreveMsgErrSrv();
        console.log("Erro: " + e);
    };
 }

//*********************************************************************************************************************
// Nome da função: VerificaMesasData                                                                                  *
//                                                                                                                    *
// Data: 09/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Verifica ao lado do campo Data da reserva do      *
//         formulário. A função envia para o servidor a data e o servidor deve responder com o mapa de mesas.         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function VerificaMesasData() {
    
    CarregaVariaveisFormulario();
    
    if (DataReserva != "") {
       if (VerificaFormatoData(DataReserva)) {
        
           dataOK = false;
           let requisicao = new XMLHttpRequest();
           let recurso = "/vlgl/reservas/data/" + DataResEnvio;
           requisicao.open("GET", recurso, true);
           requisicao.timeout = 2000;
           EscreveMsgEnvSrv();
           requisicao.send(null);
    
           requisicao.onload = function() {
               CarregaVariaveisMesas(requisicao);
               dataOK = true;
               EscreveTexto("Servidor: mapa de reservas do dia " + DataReserva, "info1");
               EscreveTexto(DataReserva, "dataMapa");
               if (clienteOK) {
                    document.getElementById("botaoresconf").style.backgroundColor = "#0705a4";
                    document.getElementById("botaoresconf").style.color = "white";
                }
           };
         
           requisicao.ontimeout = function(e) {
               EscreveMsgErrSrv();
               console.log("Erro: " + e);
           };
        
        }
        else {
            EscreveTexto("Data inválida ou formato inválido. Use DD/MM/AAAA", "info1");
            document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
            document.getElementById("botaoresconf").style.color = "black";
        }
    }
    else {
        EscreveTexto("Entre com a data da reserva. Formato: DD/MM/AAAA", "info1");
        document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
        document.getElementById("botaoresconf").style.color = "black";
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
            CarregaDadosCliente(requisicao);
            
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
                if (dataOK) {
                    document.getElementById("botaoresconf").style.backgroundColor = "#0705a4";
                    document.getElementById("botaoresconf").style.color = "white";
                }
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Servidor: cliente não cadastrado", "info1");
                document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
                document.getElementById("botaoresconf").style.color = "black";
            }
         };
         
         requisicao.ontimeout = function(e) {
             EscreveMsgErrSrv();
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
// Função: é chamada cada vez que o usuário Admin pressiona o botão Reserva ao lado do campo Número de pessoas do       *
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
    else {
        if (!VerificaHoraChegada()) {
            EscreveTexto("Hora de chegada inválida (use hh:mm)", "info1");
            condicao = false;
        }
     
    }
    if (condicao) {
        EscreveTexto("Selecione a mesa para reserva", "info1");
        HabilitaSelMesa = true;
    }
}

//*********************************************************************************************************************
// Nome da função: VerificaHoraChegada                                                                                *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: verifica a consistência da hora digitada pelo usuário.                                                     *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: se a data está OK retorna true.                                                                             *
//*********************************************************************************************************************
//
function VerificaHoraChegada() {
    let horach = false;
    if ((HoraChegada.length == 5) && (HoraChegada[2] == ":")) {
        let hora = parseInt(HoraChegada[0] + HoraChegada[1]);
        let minuto = parseInt(HoraChegada[3] + HoraChegada[4]);
        if ((hora >= 0) && (hora <= 23) && (minuto >= 0) && (minuto <= 59)) horach = true;
    }
    return horach;
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
                    document.getElementById("botaoresconf").style.backgroundColor = "#e72118";
                    document.getElementById("botaoresconf").style.color = "white";
                }
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Entre com o número de pessoas", "info1");
            }
        }
        else { // Se o flag de habilitação de consulta de mesa é true, mostra os dados da mesa
            IdMesaConsulta = mesa;
            let requisicao = new XMLHttpRequest();
            let recurso = "reserva/consulta/" + DataResEnvio + mesa;
            requisicao.open("GET", recurso, true);
            requisicao.timeout = 2000;
            EscreveMsgEnvSrv();
            requisicao.send(null);
    
            requisicao.onload = function() {
                MostraDadosReserva(requisicao);
                EscreveTexto("Servidor: informações da reserva ", "info1");
            };
            
            requisicao.ontimeout = function(e) {
                EscreveMsgErrSrv();
                console.log("Erro: " + e);
            };
            
            consultaMesa = false;
            HabilitaExclusao = true;  // Após a consulta da mesa, habilita a função de exclusão de reserva
        }
        HabilitaSelMesa = false;
    }
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
    dataOK = false;
    let requisicao = new XMLHttpRequest();
    let recurso = "reserva";
    requisicao.open("POST", recurso, true);
    requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
    requisicao.timeout = 2000;
    EscreveMsgEnvSrv();
    requisicao.send(MontaMsgJson());
        
    requisicao.onload = function() {
        MostraDadosReserva(requisicao);
        EscreveTexto("Servidor: confirmação da reserva ", "info1");
    };
    
    requisicao.ontimeout = function(e) {
        EscreveMsgErrSrv();
        console.log("Erro: " + e);
    };
    
    BotaoReservaConfirma = false;
    EscreveTexto("Reserva", "botaoresconf");
    document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
    document.getElementById("botaoresconf").style.color = "black";
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
            
            LimpaCamposInfo();
            requisicao.onload = function() {
                MostraDadosReserva(requisicao);    
                EscreveTexto("Servidor: confirmada a exclusão da reserva", "info1");
                EscreveTexto("                                                  ", "campodownload");
            };
            
            requisicao.ontimeout = function(e) {
                EscreveMsgErrSrv();
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
       if (dataOK) {
           EscreveTexto("Selecione a mesa para consulta", "info1");
           HabilitaSelMesa = true;
           consultaMesa = true;
       }
       else {
           EscreveTexto("Verifique a data para consulta", "info1");
       }
    }
    else {
        EscreveTexto("Entre com a data da reserva e verifique", "info1");
    }
    
}

//*********************************************************************************************************************
// Nome da função: MostraDadosReserva                                                                                 *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing de uma mensagem recebida do servidor em formato Json, lê as informações da reserva da mesa   *
//         na data e apresenta as informações.                                                                        *
//                                                                                                                    *
// Entrada: mensagem Json recebida do servidor (texto)                                                                *                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function MostraDadosReserva(respostaJson) {
    
    let dadosJson = JSON.parse(respostaJson.responseText);
    let mesaSelecionada = dadosJson.mesaSelecionada;
    let dataReserva = dadosJson.dataReserva;
    let nomeUsuario = dadosJson.nomeUsuario;
    let nomeCliente = dadosJson.nomeCliente;
    let numPessoas = dadosJson.numPessoas;
    let horaChegada = dadosJson.horaChegada;
    let adminResp = dadosJson.adminResp;
    let horaRegistro = dadosJson.horaRegistro;
    let dataRegistro = dadosJson.dataRegistro;
                
    LimpaCamposInfo();
    if (nomeUsuario != "null") {
        EscreveTexto("Reserva da " + NomeMesa(mesaSelecionada) + " em " + dataReserva, "info2");
        EscreveTexto("Nome de usuário: " + nomeUsuario, "info3");
        EscreveTexto("Nome completo: " + nomeCliente, "info4");
        EscreveTexto("Número de pessoas: " + numPessoas, "info5");
        EscreveTexto("Horário de chegada: " + horaChegada, "info6");
        EscreveTexto("Responsável pela reserva: " + adminResp, "info7");
        EscreveTexto("Hora do registro da reserva: " + horaRegistro, "info8");
        EscreveTexto("Data do registro da reserva: " + dataRegistro, "info9");
        EscreveTexto("Baixar os dados da reserva para imprimir etiqueta", "campodownload");
    }
    else {
        EscreveTexto(NomeMesa(mesaSelecionada) + " livre", "info2");
    }
    AtualizaMesa(mesaSelecionada, numPessoas, nomeUsuario, horaChegada);
}

//*********************************************************************************************************************
// Nome da função: CarregaDadosCliente                                                                                *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing do arquivo XML recebido do servidor, lê as informações do cliente e carrega nas variáveis    *
//                                                                                                                    *
// Entrada: mensagem Json recebida do servidor                                                                        *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaDadosCliente(respostaJson) {
    
   let dadosJson = JSON.parse(respostaJson.responseText);
    NomeUsuarioCliente = dadosJson.nomeUsuario;
    NomeCliente = dadosJson.nome;
    CelularCliente = dadosJson.celular;
    Obs1Cliente = dadosJson.obs1;
    Obs2Cliente = dadosJson.obs2;
    IdosoCliente = dadosJson.idoso;
    LocomocaoCliente = dadosJson.locomocao;
    ExigenteCliente = dadosJson.exigente;
    GeneroCliente = dadosJson.genero;
    AdminResp = dadosJson.adminResp;
        
    if (NomeUsuarioCliente == "null") {
        clienteOK = false;
    }
    else {
        clienteOK = true;
    }
    
}

//*********************************************************************************************************************
// Nome da função: CarregaVariaveisMesas                                                                              *
//                                                                                                                    *
// Data: 09/10/2021                                                                                                   *
//                                                                                                                    *
// Função: faz o parsing da mensagem de resposta do servidor, lê as informações sobre as reservas de cada mesa,       *
//         carrega nas variáveis e atualiza o estado das mesas na tela.                                               *
//                                                                                                                    *
// Entrada: mensagem de resposta JSON do servidor (texto)                                                             *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaVariaveisMesas(msgResposta) {
    
    let dadosJson = JSON.parse(msgResposta.responseText);
    let numMesas = 17;
    let letra = "A";
    let idMesa = "";
    for (let j = 0; j < numMesas; j++) {
        if (j > 8) letra = "B";
        idMesa = letra + IntToStr2(j);
        
        let mesaSelecionada = dadosJson[0][j].mesaSelecionada;
        let dataReserva = dadosJson[0][j].dataReserva;
        let nomeUsuario = dadosJson[0][j].nomeUsuario;
        let nomeCliente = dadosJson[0][j].nomeCliente;
        let numPessoas = dadosJson[0][j].numPessoas;
        let horaChegada = dadosJson[0][j].horaChegada;
        let adminResp = dadosJson[0][j].adminResp;
        let horaRegistro = dadosJson[0][j].horaRegistro;
        let dataRegistro = dadosJson[0][j].dataRegistro;
            
        MesaNomeUsuario[j] = nomeUsuario;
        MesaNumPes[j] = numPessoas;
        MesaHoraCheg[j] = horaChegada;
        MesaAdminResp[j] = adminResp;
        MesaHoraReg[j] = horaRegistro;
        MesaDataReg[j] = dataRegistro;
        
        AtualizaMesa(idMesa, numPessoas, nomeUsuario, horaChegada);
    }
}

//*********************************************************************************************************************
// Nome da função: AtualizaMesa                                                                                       *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: atualiza as informações da mesa na tela (texto e cor)                                                      *
//                                                                                                                    *
// Entrada: identificador da mesa, número de pessoas, nome de usuário e hora de chegada da reserva                    *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function AtualizaMesa(idMesa, numPessoas, nomeUsuario, horaChegada) {
    
    if (nomeUsuario == "null") {
        document.getElementById(idMesa).style.backgroundColor = "#33ff71";
        document.getElementById(idMesa).innerHTML = NomeMesa(idMesa) + " " + CapacidadeMesa;
    }
    else {
        document.getElementById(idMesa).style.backgroundColor = "#aeb6bf";
        let infoMesa = NomeMesa(idMesa) + ": " + numPessoas + " pessoas " + nomeUsuario + " " + horaChegada;
        document.getElementById(idMesa).innerHTML = infoMesa;
    }
    
}

//*********************************************************************************************************************
// Nome da função: NomeMesa                                                                                           *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: carrega nas variáveis o nome da mesa e a capacidade.                                                       *
//                                                                                                                    *
// Entrada: identificador da mesa                                                                                     *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
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
// Nome da função: IntToStr2                                                                                          *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: converte um número inteiro positivo para uma string de dois dígitos (se o número é menor que 10, coloca    *
//         0 antes).                                                                                                  *
//                                                                                                                    *
// Entrada: número inteiro                                                                                            *
//                                                                                                                    *
// Saída: a string com o número no formato de dois dígitos                                                            *
//*********************************************************************************************************************
//
function IntToStr2(num) {
    
    if (num > 9) {
        return num;
    }
    else {
        return "0" + num;
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

//*********************************************************************************************************************
// Nome da função: Cancela                                                                                            *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada quando o administrador pressiona o botão Cancela. Cancela a última operação em andamento e       *
//         limpa a tela.                                                                                              *
//                                                                                                                    *
// Entrada: identificador da mesa, número de pessoas, nome de usuário e hora de chegada da reserva                    *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function Cancela() {
    
    EscreveTexto("Reserva", "botaoresconf");
    document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
    document.getElementById("botaoresconf").style.color = "black";
    LimpaCamposInfo();
    LimpaCampoInfo1();
    consultaMesa = false;
    HabilitaSelMesa = false;
    
}

function EscreveTexto(texto, idHTML) {
    document.getElementById(idHTML).innerHTML = texto;
}

function EscreveMsgEnvSrv() {
    document.getElementById("info1").innerHTML = "Enviada requisição. Aguardando resposta do servidor";
}

function EscreveMsgErrSrv() {
    document.getElementById("info1").innerHTML = "O servidor não respondeu à requisição";
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
    document.getElementById("info10").innerHTML = "                     ";
}

