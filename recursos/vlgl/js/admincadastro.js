//*********************************************************************************************************************
//                                                                                                                    *
//                       Programa Javascript para rodar no navegador de um administrador                              *
//                                                                                                                    *
// Data: 14/10/2021                                                                                                   *
//                                                                                                                    *
// Função: permite ao administrador gerenciar o cadastramento dos clientes                                            *
//                                                                                                                    *
//*********************************************************************************************************************
//
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

var NomeUsuarioAdmin = "null";

// Objeto que recebe as informações do cliente locais (lidas do formulário e do ambiente local)
const Cliente = { nomeUsuario: "null",
                  nome: "null",
                  celular: "null",
                  obs1: "null",
                  obs2: "null",
                  idoso: "null",
                  locomocao: "null",
                  exigente: "null",
                  genero: "null",
                  adminResp: "null" };

// Objeto que recebe as informações do cliente enviadas pelo servidor
const ClienteRec = { nomeUsuario: "null",
                     nome: "null",
                     celular: "null",
                     obs1: "null",
                     obs2: "null",
                     idoso: "null",
                     locomocao: "null",
                     exigente: "null",
                     genero: "null",
                     adminResp: "null" };

var ClienteOK = false;
var Atualiza = false;

VerificaAdmin()

// Fim do Programa


//*********************************************************************************************************************
// Nome da função: VerificaAdmin                                                                                      *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
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
        EscreveTexto("Servidor: recebidas informações do administrador", "infocom");
    };

    requisicao.ontimeout = function(e) {
        EscreveTexto("O servidor não respondeu à requisição", "infocom");
    };
}

//*********************************************************************************************************************
// Nome da função: VerificaCliente                                                                                    *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
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

    clienteOK = false;
    LimpaCamposInfo();

    if (nomeUsuarioCliente.value == "") {
        EscreveTexto("Entre com o nome de usuário do cliente", "info1");
    }
    else {
        Cliente.nomeUsuario = nomeUsuarioCliente.value;
        let requisicao = new XMLHttpRequest();
        let recurso = "cadastro/cliente/" + Cliente.nomeUsuario;
        requisicao.open("GET", recurso, true);
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(null);

        requisicao.onload = function() {
            CarregaDadosCliente(requisicao);

            if (clienteOK) {
                EscreveTexto("Servidor: recebidas informações do cliente", "infocom");
                LimpaCamposInfo();
                EscreveInfoCliente();
                Atualiza = true;
                EscreveTexto("Atualiza", "botaocadastra");
            }
            else {
              EscreveTexto("Servidor: cliente não cadastrado", "infocom");
              LimpaCamposInfo();
              EscreveTexto("Cadastra", "botaocadastra");
              Atualiza = false;
            }
         };

         requisicao.ontimeout = function(e) {
             EscreveTexto("O servidor não respondeu à requisição", "infocom");
             console.log("Erro: " + e);
         };
    }

}

//*********************************************************************************************************************
// Nome da função: CadastraAtualiza                                                                                   *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Cadastra. Se o cliente não está cadastrado,       *
//         a função envia para o servidor as informações para cadastramento do cliente. Se o cliente já está          *
//         cadastrado, atualiza o cadastro. O servidor deve responder com os dados do cliente.                        *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CadastraAtualiza() {

    LimpaCamposInfo();

    if (nomeUsuarioCliente.value == "") {
        EscreveTexto("Entre com o nome de usuário do cliente", "infocom");
    }
    else {
        Cliente.nomeUsuario = nomeUsuarioCliente.value;
        Cliente.adminResp = NomeUsuarioAdmin;

        if (Atualiza) { // Se o cliente está cadastrado, atualiza o cadastro com os dados
            AtualizaCadastroCliente();
        }
        else { // Se o cliente não está cadastrado, é obrigatório entrar com o nome e o número do celular
            if (nomeCliente.value == "") {
                EscreveTexto("Entre com o nome completo do cliente", "infocom");
            }
            else {
                if (celularCliente.value == "") {
                    EscreveTexto("Entre com o número do celular do cliente", "infocom");
                }
                else {
                    CadastraCliente();
                }
            }
        }
    }
}

//*********************************************************************************************************************
// Nome da função: AtualizaCadastroCliente                                                                            *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
//                                                                                                                    *
// Função: Se o cliente já está cadastrado, atualiza o cadastro. O servidor deve responder com os dados do cliente.   *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function AtualizaCadastroCliente() {

    if (confirm("Confirma a atualização do cadastro do cliente " + Cliente.nomeUsuario + "?")) {

        if (nomeCliente.value == "") { Cliente.nome = "null"; }
        else { Cliente.nome = nomeCliente.value; }

        if (celularCliente.value == "") { Cliente.celular = "null"; }
        else { Cliente.celular = celularCliente.value; }

        if (obs1Cliente.value == "") { Cliente.obs1 = "null"; }
        else { Cliente.obs1 = obs1Cliente.value; }

        if (obs2Cliente.value == "") { Cliente.obs2 = "null"; }
        else { Cliente.obs2 = obs2Cliente.value; }

        Cliente.idoso = idosoCliente.value;
        Cliente.locomocao = locomocaoCliente.value;
        Cliente.exigente = exigenteCliente.value;
        Cliente.genero = generoCliente.value;

        let requisicao = new XMLHttpRequest();
        let recurso = "cadastro/cliente";
        requisicao.open("PUT", recurso, true);
        requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(JSON.stringify(Cliente));

        requisicao.onload = function() {
            CarregaDadosCliente(requisicao);

            if (clienteOK) {
                EscreveTexto("Servidor: o cadastro do cliente foi atualizado", "infocom");
                EscreveInfoCliente();
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Servidor: falha ao atualizar o cadastro do cliente", "infocom");
            }
        };

        requisicao.ontimeout = function(e) {
            EscreveTexto("O servidor não respondeu à requisição", "infocom");
            console.log("Erro: " + e);
        };

    }
}

//*********************************************************************************************************************
// Nome da função: CadastraCliente                                                                                    *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
//                                                                                                                    *
// Função: efetua o cadastro de um cliente. O servidor deve responder com os dados do cliente.                        *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function CadastraCliente() {

    if (confirm("Confirma o cadastro do cliente " + Cliente.nomeUsuario + "?")) {

        Cliente.nome = nomeCliente.value;
        Cliente.celular = celularCliente.value;

        if (obs1Cliente.value == "") { Cliente.obs1 = "null"; }
        else { Cliente.obs1 = obs1Cliente.value; }

        if (obs2Cliente.value == "") { Cliente.obs2 = "null"; }
        else { Cliente.obs2 = obs2Cliente.value; }

        Cliente.idoso = idosoCliente.value;
        Cliente.locomocao = locomocaoCliente.value;
        Cliente.exigente = exigenteCliente.value;
        Cliente.genero = generoCliente.value;

        let requisicao = new XMLHttpRequest();
        let recurso = "cadastro/cliente";
        requisicao.open("POST", recurso, true);
        requisicao.setRequestHeader("Content-Type", "application/json;charset=utf-8");
        requisicao.timeout = 2000;
        EscreveMsgEnvSrv();
        requisicao.send(JSON.stringify(Cliente));

        requisicao.onload = function() {
            CarregaDadosCliente(requisicao);
            if (clienteOK) {
                EscreveTexto("Servidor: o cliente foi cadastrado", "infocom");
                EscreveInfoCliente();
                Atualiza = true;
            }
            else {
                LimpaCamposInfo();
                EscreveTexto("Servidor: falha ao cadastrar o cliente", "infocom");
                Atualiza = false;
              }
        };

        requisicao.ontimeout = function(e) {
            EscreveTexto("O servidor não respondeu à requisição", "infocom");
            console.log("Erro: " + e);
        };
    }
}

//*********************************************************************************************************************
// Nome da função: ExcluiCadastroCliente                                                                              *
//                                                                                                                    *
// Data: 10/10/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Exclui. A função envia para o servidor uma        *
//         solicitação de exclusão de um cadastro de cliente. O servidor deve responder com os dados do cliente       *
//         mostrando que não está mais cadastrado.                                                                    *
//                                                                                                                    *
// Entrada: não tem                                                                                                   *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function ExcluiCadastroCliente() {

    LimpaCamposInfo();

    if (nomeUsuarioCliente.value == "") {
        EscreveTexto("Entre com o nome de usuário do cliente e verifique", "infocom");
    }
    else {
        if (clienteOK) {
            Cliente.nomeUsuario = nomeUsuarioCliente.value;

            if (confirm("Confirma a exclusão do cadastro do cliente " + Cliente.nomeUsuario + "?")) {
                let requisicao = new XMLHttpRequest();
                let recurso = "cadastro/cliente/" + Cliente.nomeUsuario;
                requisicao.open("DELETE", recurso, true);
                requisicao.timeout = 2000;
                EscreveMsgEnvSrv();
                requisicao.send(null);

                requisicao.onload = function() {
                    CarregaDadosCliente(requisicao);
                    if (!clienteOK) {
                        EscreveTexto("Servidor: confirmada a exclusão do cadastro", "infocom");
                        EscreveInfoCliente();
                    }
                    else {
                        LimpaCamposInfo();
                        EscreveTexto("Servidor: Falha ao excluir o cliente", "infocom");
                    }
                };
            }
        }
        else {
            EscreveTexto("Antes de excluir, verifique o cliente", "infocom");
        }
    }
}

//*********************************************************************************************************************
// Nome da função: CarregaDadosCliente                                                                                *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
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
    ClienteRec.nomeUsuario = dadosJson.nomeUsuario;
    ClienteRec.nome = dadosJson.nome;
    ClienteRec.celular = dadosJson.celular;
    ClienteRec.obs1 = dadosJson.obs1;
    ClienteRec.obs2 = dadosJson.obs2;
    ClienteRec.idoso = dadosJson.idoso;
    ClienteRec.locomocao = dadosJson.locomocao;
    ClienteRec.exigente = dadosJson.exigente;
    ClienteRec.gerero = dadosJson.genero;
    ClienteRec.adminResp = dadosJson.adminResp;

    if (ClienteRec.nomeUsuario == Cliente.nomeUsuario) {
      clienteOK = true;
    }
    else {
      clienteOK = false;
    }
}

//*********************************************************************************************************************
// Nome da função: EscreveInfoCliente                                                                                 *
//                                                                                                                    *
// Data: 13/10/2021                                                                                                   *
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
        EscreveTexto("Nome de usuário: " + ClienteRec.nomeUsuario, "info1");
        EscreveTexto("Nome Completo: " + ClienteRec.nome, "info2");
        EscreveTexto("Celular: " + ClienteRec.celular, "info3");

        var sufixo = "o";
        if (Cliente.gerero == "Feminino") sufixo = "a";
        EscreveTexto("Idos" + sufixo + ": " + ClienteRec.idoso, "info4");
        EscreveTexto("Dificuldade de locomoção: " + ClienteRec.locomocao, "info5");
        EscreveTexto("Exigente: " + ClienteRec.exigente, "info6");
        EscreveTexto("Gênero: " + ClienteRec.gerero, "info7");
        EscreveTexto("Obs 1: " + ClienteRec.obs1, "info8");
        EscreveTexto("Obs 2: " + ClienteRec.obs2, "info9");
        EscreveTexto("Responsável pelo cadastro: " + ClienteRec.adminResp, "info10");
        EscreveTexto("Atualiza", "botaocadastra");
    }
    else {
        EscreveTexto("Cliente não cadastrado", "info1");
        EscreveTexto("Cadastra", "botaocadastra");
    }
}

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
