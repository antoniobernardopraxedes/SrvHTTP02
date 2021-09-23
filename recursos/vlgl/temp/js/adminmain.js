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
var i = 0;
var grupo;
var recurso = "reserva";

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
    document.getElementById("nomeadmin").style.fontSize = "43px";
    document.getElementById("nomeadmin").style.paddingLeft = "20px";
    document.getElementById("info1").innerHTML = "                                  ";
    document.getElementById("info1").style.fontSize = "47px";
    document.getElementById("info1").style.paddingLeft = "20px";
}

//*********************************************************************************************************************
// Nome da função: VerificaClienteMesas                                                                               *
//                                                                                                                    *
// Data: 23/09/2021                                                                                                   *
//                                                                                                                    *
// Função: é chamada cada vez que o usuário Admin pressiona o botão Reservar. Esta função envia para o servidor o     *
//         nome de usuário do cliente  e a data da reserva. O servidor deve responder enviando duas informações:      *
//         1) Se o cliente está cadastrado; 2) O mapa de ocupação das mesas.                                          *
//                                                                                                                    *
// Entrada: string com o nome da mesa (A0 a A8 e B9 a B16)                                                            *
//                                                                                                                    *
// Saída: não tem                                                                                                     *
//*********************************************************************************************************************
//
function VerificaClienteMesas() {
    DataReserva = dataReserva.value;
    UserName = userName.value;
    NumPessoas = numPessoas.value;
    var codigo = "DataCliente";
    if (UserName == "") {
        codigo = "Data";
    }
    
    recurso = "reserva";
    xhttp.open("POST", recurso, false);
    try {
        xhttp.send(MontaMsgServ(codigo, UserName, DataReserva));
        var xmlRec = xhttp.responseXML;
        i = 0;
        grupo = xmlRec.getElementsByTagName("CLIENTE");
        Cliente = grupo[i].getElementsByTagName("NOME")[0].childNodes[0].nodeValue;
        
        if (Cliente != "null") {
            if (Cliente != "naocadastrado") {
                clienteOK = "true";
                document.getElementById("info1").innerHTML = "Cliente: " + Cliente;
                document.getElementById("info1").style.fontSize = "47px";
                document.getElementById("info1").style.paddingLeft = "20px";
                document.getElementById("info2").innerHTML = "Escolha a mesa:";
                document.getElementById("info2").style.fontSize = "47px";
                document.getElementById("info2").style.paddingLeft = "20px";
                
                LimpaCampoInfo3();
                //document.getElementById("info3").innerHTML = "                      ";
                //document.getElementById("info3").style.fontSize = "47px";
                //document.getElementById("info3").style.paddingLeft = "20px";
            }
            else {
                document.getElementById("info1").innerHTML = "Cliente não cadastrado";
                document.getElementById("info1").style.fontSize = "47px";
                document.getElementById("info1").style.paddingLeft = "20px";
                
                LimpaCampoInfo2();
                //document.getElementById("info2").innerHTML = "                      ";
                //document.getElementById("info2").style.fontSize = "47px";
                //document.getElementById("info2").style.paddingLeft = "20px";
                
                LimpaCampoInfo3();
                //document.getElementById("info3").innerHTML = "                      ";
                //document.getElementById("info3").style.fontSize = "47px";
                //document.getElementById("info3").style.paddingLeft = "20px";
            }
        }
        
            
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
        
        console.log("Mensagem POST enviada: " + UserName);
    } catch(err) {
        console.log("Erro " + err);
    }
}

function corMesa(mesa, habMesa) {
    if (habMesa == "livre") {
        document.getElementById(mesa).style.backgroundColor = "#33ff71";
    }
    else {
        document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
        document.getElementById(mesa).innerHTML = "Reservada";
    }
}

function LimpaCampoInfo2() {
    document.getElementById("info2").innerHTML = "                      ";
    document.getElementById("info2").style.fontSize = "47px";
    document.getElementById("info2").style.paddingLeft = "20px";
}

function LimpaCampoInfo3() {
    document.getElementById("info3").innerHTML = "                      ";
    document.getElementById("info3").style.fontSize = "47px";
    document.getElementById("info3").style.paddingLeft = "20px";
}

function MontaMsgServ(codigo, userName, dataReserva) {
    
    msgServ = "Codigo: " + codigo + "\n" +
              "UserName: " + userName + "\n" +
              "DataReserva: " + dataReserva + "\n" +
              "NumPessoas: " + NumPessoas;
              
    return msgServ;
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
function reservaMesa(mesa) {
    impMesa = mesa;
    if (clienteOK) {
        if (mesa == "A00") {
            impMesa = "Gazebo";
        }
        var msgConfirma = "Confirma a reserva da mesa " + impMesa + " para " + Cliente + " no dia " + DataReserva + "?";
        
        if (confirm(msgConfirma)) {
            
            var MsgConfirmacao = "Confirmada a reserva da mesa " + impMesa;
            document.getElementById("info2").innerHTML = MsgConfirmacao;
            document.getElementById("info2").style.fontSize = "47px";
            document.getElementById("info2").style.paddingLeft = "20px";
            MsgConfirmacao = "para " + NumPessoas + " pessoas no dia " + DataReserva;
            document.getElementById("info3").innerHTML = MsgConfirmacao;
            document.getElementById("info3").style.fontSize = "47px";
            document.getElementById("info3").style.paddingLeft = "20px";
            
            document.getElementById(mesa).style.backgroundColor = "#aeb6bf";
            document.getElementById(mesa).innerHTML = "Reservada";
        }
        else {
            document.getElementById("info1").innerHTML = "Por favor, escolha a mesa";
        }
    }
    
    function CancelaReserva() {
        //code
    }
}






