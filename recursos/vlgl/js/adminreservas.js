//*********************************************************************************************************************
//                                                                                                                    *
//                       Programa Javascript para rodar no navegador de um administrador                              *
//                                                                                                                    *
// Data: 12/10/2021                                                                                                   *
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

// Variáveis globais
var NomeUsuarioAdmin;
var CapacidadeMesa;
var IdMesaConsulta;
var dataOK = false;
var clienteOK = false;
var numPessoasOK = false;
var horaChegadaOK = false;
var consultaMesa = false;
var HabilitaExclusao = false;

// Objeto que recebe do servidor as informações do cliente
const Cliente = { nomeUsuario: "null",
                  nome: "null",
                  celular: "null",
                  obs1: "null",
                  obs2: "null",
                  idoso: "null",
                  locomocao: "null",
                  exigente: "null",
                  gerero: "null",
                  adminResp: "null" };

// Objeto que recebe do servidor as informações da reserva
const ReservaRec = { mesaSelecionada: "null",
                     dataReserva: "null",
                     nomeUsuario: "null",
                     nomeCliente: "null",
                     numPessoas: "null",
                     horaChegada: "null",
                     adminResp: "null",
                     horaRegistro: "null",
                     dataRegistro: "null" };

// Objeto que contém as informações necessárias para solicitação de reserva de mesa
const SolicitaReservaMesa = { nomeUsuario: "null",
                              nomeCliente: "null",
                              dataReserva: "null",
                              numPessoas: "null",
                              horaChegada: "null",
                              mesaSelecionada: "null",
                              adminResp: "null" };

VerificaAdmin()

// Fim do Programa

//*********************************************************************************************************************
// Nome da função: CarregaVariaveisFormulario                                                                         *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: carrega as informações do formulário nas variáveis globais correspondentes                                 *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CarregaVariaveisFormulario() {
    DataReserva = dataReserva.value;
    NomeUsuarioCliente = userName.value;
    NumPessoas = numPessoas.value;
    HoraChegada = horarioChegada.value;
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
// Saída: a variável global dataOK, se true infdorma que a data foi verificada e o arquivo existe no servidor.        *
//*********************************************************************************************************************
//
function VerificaMesasData() {

    DataReserva = dataReserva.value;  // Lê a data digitada pelo administrador no formulário
    dataOK = false;
    consultaMesa = false;

    if (DataReserva == "") {
        EscreveTexto("Entre com a data da reserva. Formato: DD/MM/AAAA", "info1");
        document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
        document.getElementById("botaoresconf").style.color = "black";
    }
    else {
        if (VerificaFormatoData(DataReserva)) {
            let requisicao = new XMLHttpRequest();
            let recurso = "/vlgl/reservas/data/" + ConverteFormatoData(DataReserva);
            requisicao.open("GET", recurso, true);
            requisicao.timeout = 2000;
            EscreveMsgEnvSrv();
            requisicao.send(null);

            requisicao.onload = function() {
                let dadosJson = JSON.parse(requisicao.responseText);
                let numMesas = 17;
                let letra = "A";
                let idMesa = "";
                for (let j = 0; j < numMesas; j++) {
                    if (j > 8) letra = "B";
                    idMesa = letra + IntToStr2(j);
                    let nomeUsuario = dadosJson[0][j].nomeUsuario;
                    let numPessoas = dadosJson[0][j].numPessoas;
                    let horaChegada = dadosJson[0][j].horaChegada;
                    AtualizaMesa(idMesa, numPessoas, nomeUsuario, horaChegada);
               }

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
}

//*********************************************************************************************************************
// Nome da função: VerificaFormatoData                                                                                *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
//                                                                                                                    *
// Função: verifica a consistência da data digitada pelo usuário.                                                     *
//                                                                                                                    *
// Entrada: data a ser verificada no formato DD/MM/AAAA                                                               *
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
// Nome da função: ConverteFormatoData                                                                                *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
//                                                                                                                    *
// Função: converte uma data no formato DD/MM/AAAA para o formato DD-MM-AAAA para envio ao servidor como recurso      *
//                                                                                                                    *
// Entrada: data no formato DD/MM/AAAA                                                                                *
//                                                                                                                    *
// Saída: data no formato DD-MM-AAAA                                                                                  *
//*********************************************************************************************************************
//
function ConverteFormatoData(dataR) {
    dia = parseInt(dataR[0] + dataR[1]);
    mes = parseInt(dataR[3] + dataR[4]);
    ano = parseInt(dataR[6] + dataR[7] + dataR[8] + dataR[9]);
    var Dia;
    if (dia < 10) { Dia = "0" + dia; }
    else { Dia = dia; }
    var Mes;
    if (mes < 10) { Mes = "0" + mes; }
    else { Mes = mes; }

    return (Dia + "-" + Mes + "-" + ano);
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
// Saída: variável global clienteOK se o cliente está cadastrado.                                                     *
//*********************************************************************************************************************
//
function VerificaCliente() {

    NomeUsuarioCliente = userName.value;
    clienteOK = false;
    consultaMesa = false;

    if (NomeUsuarioCliente == "") {
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
    }
    else {
        let requisicao = new XMLHttpRequest();
        let recurso = "cadastro/cliente/" + NomeUsuarioCliente;
        requisicao.open("GET", recurso, true);
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(null);

        requisicao.onload = function() {
            let dadosJson = JSON.parse(requisicao.responseText);
            Cliente.nomeUsuario = dadosJson.nomeUsuario;
            Cliente.nome = dadosJson.nome;
            Cliente.celular = dadosJson.celular;
            Cliente.obs1 = dadosJson.obs1;
            Cliente.obs2 = dadosJson.obs2;
            Cliente.idoso = dadosJson.idoso;
            Cliente.locomocao = dadosJson.locomocao;
            Cliente.exigente = dadosJson.exigente;
            Cliente.gerero = dadosJson.genero;
            Cliente.adminResp = dadosJson.adminResp;

            if (Cliente.nomeUsuario == "null") {
                LimpaCamposInfo();
                EscreveTexto("Servidor: cliente não cadastrado", "info1");
                document.getElementById("botaoresconf").style.backgroundColor = "#e7e5e5";
                document.getElementById("botaoresconf").style.color = "black";
                clienteOK = false;
            }
            else {
                EscreveTexto("Servidor: informações do cliente", "info1");
                EscreveTexto("Nome de usuário: " + Cliente.nomeUsuario, "info2");
                EscreveTexto("Nome completo: " + Cliente.nome, "info3");
                EscreveTexto("Celular: " + Cliente.celular, "info4");

                let sufixo = "o";
                if (Cliente.genero == "feminino") sufixo = "a";

                EscreveTexto("Idos" + sufixo + ": " + Cliente.idoso, "info5");
                EscreveTexto("Dificuldade de locomoção: " + Cliente.locomocao, "info6");
                EscreveTexto("Exigente: " + Cliente.exigente, "info7");
                EscreveTexto("Obs 1: " + Cliente.obs1, "info8");
                EscreveTexto("Obs 2: " + Cliente.obs2, "info9");
                if (dataOK) {
                    document.getElementById("botaoresconf").style.backgroundColor = "#0705a4";
                    document.getElementById("botaoresconf").style.color = "white";
                }
                clienteOK = true;
            }
         };

         requisicao.ontimeout = function(e) {
             EscreveMsgErrSrv();
             console.log("Erro: " + e);
         };
    }
}

//*********************************************************************************************************************
// Nome da função: PreparaReserva                                                                                     *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Reserva ao lado do campo Número de pessoas do     *
//         formulário. A função verifica se todas as informações da reserva foram inseridas corretamente e habilita   *
//         a seleção de mesa.                                                                                         *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function PreparaReserva() {

    LimpaCamposInfo();

    if (dataReserva.value == "") {
        EscreveTexto("Entre com a data da reserva", "info1");
        dataOK = false;
    }
    else {
        if (!dataOK) {
            EscreveTexto("Verifique a data da reserva", "info1");
        }
        else {
            if (userName.value == "") {
                EscreveTexto("Entre com o nome de usuário do cliente", "info1");
                ClienteOK = false;
            }
            else {
                if (!clienteOK) {
                    EscreveTexto("Verifique o cliente", "info1");
                }
                else {
                    if (numPessoas.value == "") {
                        EscreveTexto("Entre com o número de pessoas", "info1");
                    }
                    else {
                        let NumeroPessoas = parseInt(numPessoas.value);
                        if (NumeroPessoas < 1) {
                            EscreveTexto("Número de pessoas inválido", "info1");
                        }
                        else {
                            numPessoasOK = true;
                            if (horarioChegada.value == "") {
                                EscreveTexto("Entre com o horário de chegada", "info1");
                            }
                            else {
                                if (!VerificaHoraChegada(horarioChegada.value)) {
                                    EscreveTexto("Hora de chegada inválida (use hh:mm)", "info1");
                                }
                                else {// Se as informações estão OK, carrega no objeto SolicitaReservaMesa
                                    SolicitaReservaMesa.nomeUsuario = userName.value;
                                    SolicitaReservaMesa.nomeCliente = Cliente.nome;
                                    SolicitaReservaMesa.dataReserva = ConverteFormatoData(dataReserva.value);
                                    SolicitaReservaMesa.numPessoas = numPessoas.value;
                                    SolicitaReservaMesa.horaChegada = horarioChegada.value;
                                    SolicitaReservaMesa.adminResp = NomeUsuarioAdmin;

                                    // Habilita a seleção da mesa para reserva
                                    EscreveTexto("Selecione a mesa para reserva", "info1");
                                    HabilitaSelMesa = true;
                                }
                            }
                        }
                    }
                }
            }
        }
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
function VerificaHoraChegada(horaCh) {
    let horach = false;
    if ((horaCh.length == 5) && (horaCh[2] == ":")) {
        let hora = parseInt(horaCh[0] + horaCh[1]);
        let minuto = parseInt(horaCh[3] + horaCh[4]);
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

        if (consultaMesa) { // Se é consulta de dados da reserva, solicita os dados ao servidor e apresenta
          IdMesaConsulta = mesa;
          let requisicao = new XMLHttpRequest();
          let recurso = "reserva/consulta/" + ConverteFormatoData(DataReserva) + mesa;
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
        else {  // Se é seleção de mesa para reserva, guarda as informações e habilita o botão confirma
          let msgConfRes = "Confirma a reserva da " + NomeMesa(mesa) + " para " + SolicitaReservaMesa.nomeCliente;
          msgConfRes = msgConfRes + " em " + DataReserva + "?";
          if (confirm(msgConfRes)) {
              SolicitaReservaMesa.mesaSelecionada = mesa;
              ConfirmaReserva(mesa);
              consultaMesa = false;
          }
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
function ConfirmaReserva(mesaSelecionada) {

    let requisicao = new XMLHttpRequest();
    let recurso = "reserva";
    requisicao.open("POST", recurso, true);
    requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
    requisicao.timeout = 2000;

    EscreveMsgEnvSrv();
    requisicao.send(JSON.stringify(SolicitaReservaMesa));

    requisicao.onload = function() {
        MostraDadosReserva(requisicao);
        EscreveTexto("Servidor: confirmação da reserva ", "info1");
        dataOK = false;
        clienteOK = false;
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
    ReservaRec.mesaSelecionada = dadosJson.mesaSelecionada;
    ReservaRec.dataReserva = dadosJson.dataReserva;
    ReservaRec.nomeUsuario = dadosJson.nomeUsuario;
    ReservaRec.nomeCliente = dadosJson.nomeCliente;
    ReservaRec.numPessoas = dadosJson.numPessoas;
    ReservaRec.horaChegada = dadosJson.horaChegada;
    ReservaRec.adminResp = dadosJson.adminResp;
    ReservaRec.horaRegistro = dadosJson.horaRegistro;
    ReservaRec.dataRegistro = dadosJson.dataRegistro;

    LimpaCamposInfo();
    if (ReservaRec.nomeUsuario != "null") {
        EscreveTexto("Reserva da " + NomeMesa(ReservaRec.mesaSelecionada) + " em " + ReservaRec.dataReserva, "info2");
        EscreveTexto("Nome de usuário: " + ReservaRec.nomeUsuario, "info3");
        EscreveTexto("Nome completo: " + ReservaRec.nomeCliente, "info4");
        EscreveTexto("Número de pessoas: " + ReservaRec.numPessoas, "info5");
        EscreveTexto("Horário de chegada: " + ReservaRec.horaChegada, "info6");
        EscreveTexto("Responsável pela reserva: " + ReservaRec.adminResp, "info7");
        EscreveTexto("Hora do registro da reserva: " + ReservaRec.horaRegistro, "info8");
        EscreveTexto("Data do registro da reserva: " + ReservaRec.dataRegistro, "info9");
        EscreveTexto("Baixar os dados da reserva para imprimir etiqueta", "campodownload");
    }
    else {
        EscreveTexto(NomeMesa(ReservaRec.mesaSelecionada) + " livre", "info2");
    }
    AtualizaMesa(ReservaRec.mesaSelecionada, ReservaRec.numPessoas, ReservaRec.nomeUsuario, ReservaRec.horaChegada);
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

    //NomeDaMesa = nomMesa;
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
