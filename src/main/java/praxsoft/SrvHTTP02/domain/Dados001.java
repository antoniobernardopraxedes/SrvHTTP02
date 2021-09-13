package praxsoft.SrvHTTP02.domain;

import praxsoft.SrvHTTP02.services.SupService;

//**********************************************************************************************************************
// Nome da Classe: Dados001                                                                                            *
//	                                                                                                                   *
// Data: 13/09/2021                                                                                                    *
//                                                                                                                     *
// Funções: contém todos os atributos e métodos referentes ao Sistema de Supervisão, Controle e Monitoramento de       *
//          condição da Usina Solar Fotovoltaica. Os atributos referem-se às variáveis do sistema, que podem ser       *
//          atualizadas de duas maneiras: em modo local (Intranet) pelo método LeEstMedsPayload; em modo remoto        *
//          (nuvem) através de mensagem em formato Json enviada pelo software Atualizador.                             *
//                                                                                                                     *
//**********************************************************************************************************************
//
public class Dados001 {

    // Estados de Comunicação (5 Variáveis)
    private static String comcnv;
    private static String comcnc;
    private static String comutr;
    private static String comcc1;
    private static String comcc2;

    // Informação geral (16 Variáveis)
    private static String clk;
    private static String data;
    private static String cmdex;
    private static String mdop;
    private static String mdcom;
    private static String mdct1;
    private static String mdct234;
    private static String encg1;
    private static String encg2;
    private static String encg3;
    private static String icg3;
    private static String vbat;
    private static String vrede;
    private static String estvrd;
    private static String tbat;
    private static String sdbat;

    // Estados e Medidas da Caixa d'água e da Bomba (7 Variáveis)
    private static String estcxaz;
    private static String nivcxaz;
    private static String estbmb;
    private static String estdjb;
    private static String estdjrb;
    private static String enbmb;
    private static String tmpbl;

    // Geração Solar e Consumo (18 Variáveis)
    private static String vp12;
    private static String is12;
    private static String iscc1;
    private static String wscc1;
    private static String sdcc1;
    private static String vp34;
    private static String is34;
    private static String iscc2;
    private static String wscc2;
    private static String sdcc2;
    private static String itotger;
    private static String wtotger;
    private static String itotcg;
    private static String wtotcg;
    private static String estft1;
    private static String estft2;
    private static String icircc;
    private static String wcircc;

    // Inversor 2 (10 Variáveis)
    private static String estiv2;
    private static String ieiv2;
    private static String weiv2;
    private static String isiv2;
    private static String vsiv2;
    private static String wsiv2;
    private static String tdiv2;
    private static String ttiv2;
    private static String efiv2;
    private static String sdiv2;

    // Inversor 1 (10 Variáveis)
    private static String estiv1;
    private static String ieiv1;
    private static String weiv1;
    private static String isiv1;
    private static String vsiv1;
    private static String wsiv1;
    private static String tdiv1;
    private static String ttiv1;
    private static String efiv1;
    private static String sdiv1;

    public String getClk() {
        return clk;
    }

    public void setClk(String clk) {
        this.clk = clk;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCmdex() {
        return cmdex;
    }

    public void setCmdex(String cmdex) {
        this.cmdex = cmdex;
    }

    public String getMdop() {
        return mdop;
    }

    public void setMdop(String mdop) {
        this.mdop = mdop;
    }

    public String getMdcom() {
        return mdcom;
    }

    public void setMdcom(String mdcom) {
        this.mdcom = mdcom;
    }

    public String getMdct1() {
        return mdct1;
    }

    public void setMdct1(String mdct1) {
        this.mdct1 = mdct1;
    }

    public String getMdct234() {
        return mdct234;
    }

    public void setMdct234(String mdct234) {
        this.mdct234 = mdct234;
    }

    public String getEncg1() {
        return encg1;
    }

    public void setEncg1(String encg1) {
        this.encg1 = encg1;
    }

    public String getEncg2() {
        return encg2;
    }

    public void setEncg2(String encg2) {
        this.encg2 = encg2;
    }

    public String getEncg3() {
        return encg3;
    }

    public void setEncg3(String encg3) {
        this.encg3 = encg3;
    }

    public String getIcg3() {
        return icg3;
    }

    public void setIcg3(String icg3) {
        this.icg3 = icg3;
    }

    public String getVbat() {
        return vbat;
    }

    public void setVbat(String vbat) {
        this.vbat = vbat;
    }

    public String getVrede() {
        return vrede;
    }

    public void setVrede(String vrede) {
        this.vrede = vrede;
    }

    public String getEstvrd() {
        return estvrd;
    }

    public void setEstvrd(String estvrd) {
        this.estvrd = estvrd;
    }

    public String getTbat() {
        return tbat;
    }

    public void setTbat(String tbat) {
        this.tbat = tbat;
    }

    public String getSdbat() {
        return sdbat;
    }

    public void setSdbat(String sdbat) {
        this.sdbat = sdbat;
    }

    public String getComcnv() {
        return comcnv;
    }

    public void setComcnv(String comcnv) {
        this.comcnv = comcnv;
    }

    public String getComcnc() {
        return comcnc;
    }

    public void setComcnc(String comcnc) {
        this.comcnc = comcnc;
    }

    public String getComutr() {
        return comutr;
    }

    public void setComutr(String comutr) {
        this.comutr = comutr;
    }

    public String getComcc1() {
        return comcc1;
    }

    public void setComcc1(String comcc1) {
        this.comcc1 = comcc1;
    }

    public String getComcc2() {
        return comcc2;
    }

    public void setComcc2(String comcc2) {
        this.comcc2 = comcc2;
    }

    public String getEstcxaz() {
        return estcxaz;
    }

    public void setEstcxaz(String estcxaz) {
        this.estcxaz = estcxaz;
    }

    public String getNivcxaz() {
        return nivcxaz;
    }

    public void setNivcxaz(String nivcxaz) {
        this.nivcxaz = nivcxaz;
    }

    public String getEstbmb() {
        return estbmb;
    }

    public void setEstbmb(String estbmb) {
        this.estbmb = estbmb;
    }

    public String getEstdjb() {
        return estdjb;
    }

    public void setEstdjb(String estdjb) {
        this.estdjb = estdjb;
    }

    public String getEstdjrb() {
        return estdjrb;
    }

    public void setEstdjrb(String estdjrb) {
        this.estdjrb = estdjrb;
    }

    public String getEnbmb() {
        return enbmb;
    }

    public void setEnbmb(String enbmb) {
        this.enbmb = enbmb;
    }

    public String getTmpbl() {
        return tmpbl;
    }

    public void setTmpbl(String tmpbl) {
        this.tmpbl = tmpbl;
    }

    public String getVp12() {
        return vp12;
    }

    public void setVp12(String vp12) {
        this.vp12 = vp12;
    }

    public String getIs12() {
        return is12;
    }

    public void setIs12(String is12) {
        this.is12 = is12;
    }

    public String getIscc1() {
        return iscc1;
    }

    public void setIscc1(String iscc1) {
        this.iscc1 = iscc1;
    }

    public String getWscc1() {
        return wscc1;
    }

    public void setWscc1(String wscc1) {
        this.wscc1 = wscc1;
    }

    public String getSdcc1() {
        return sdcc1;
    }

    public void setSdcc1(String sdcc1) {
        this.sdcc1 = sdcc1;
    }

    public String getVp34() {
        return vp34;
    }

    public void setVp34(String vp34) {
        this.vp34 = vp34;
    }

    public String getIs34() {
        return is34;
    }

    public void setIs34(String is34) {
        this.is34 = is34;
    }

    public String getIscc2() {
        return iscc2;
    }

    public void setIscc2(String iscc2) {
        this.iscc2 = iscc2;
    }

    public String getWscc2() {
        return wscc2;
    }

    public void setWscc2(String wscc2) {
        this.wscc2 = wscc2;
    }

    public String getSdcc2() {
        return sdcc2;
    }

    public void setSdcc2(String sdcc2) {
        this.sdcc2 = sdcc2;
    }

    public String getItotger() {
        return itotger;
    }

    public void setItotger(String itotger) {
        this.itotger = itotger;
    }

    public String getWtotger() {
        return wtotger;
    }

    public void setWtotger(String wtotger) {
        this.wtotger = wtotger;
    }

    public String getItotcg() {
        return itotcg;
    }

    public void setItotcg(String itotcg) {
        this.itotcg = itotcg;
    }

    public String getWtotcg() {
        return wtotcg;
    }

    public void setWtotcg(String wtotcg) {
        this.wtotcg = wtotcg;
    }

    public String getEstft1() {
        return estft1;
    }

    public void setEstft1(String estft1) {
        this.estft1 = estft1;
    }

    public String getEstft2() {
        return estft2;
    }

    public void setEstft2(String estft2) {
        this.estft2 = estft2;
    }

    public String getIcircc() {
        return icircc;
    }

    public void setIcircc(String icircc) {
        this.icircc = icircc;
    }

    public String getWcircc() {
        return wcircc;
    }

    public void setWcircc(String wcircc) {
        this.wcircc = wcircc;
    }

    public String getEstiv2() {
        return estiv2;
    }

    public void setEstiv2(String estiv2) {
        this.estiv2 = estiv2;
    }

    public String getIeiv2() {
        return ieiv2;
    }

    public void setIeiv2(String ieiv2) {
        this.ieiv2 = ieiv2;
    }

    public String getWeiv2() {
        return weiv2;
    }

    public void setWeiv2(String weiv2) {
        this.weiv2 = weiv2;
    }

    public String getVsiv2() {
        return vsiv2;
    }

    public void setVsiv2(String vsiv2) {
        this.vsiv2 = vsiv2;
    }

    public String getIsiv2() {
        return isiv2;
    }

    public void setIsiv2(String isiv2) {
        this.isiv2 = isiv2;
    }

    public String getWsiv2() {
        return wsiv2;
    }

    public void setWsiv2(String wsiv2) {
        this.wsiv2 = wsiv2;
    }

    public String getTdiv2() {
        return tdiv2;
    }

    public void setTdiv2(String tdiv2) {
        this.tdiv2 = tdiv2;
    }

    public String getTtiv2() {
        return ttiv2;
    }

    public void setTtiv2(String ttiv2) {
        this.ttiv2 = ttiv2;
    }

    public String getEfiv2() {
        return efiv2;
    }

    public void setEfiv2(String efiv2) {
        this.efiv2 = efiv2;
    }

    public String getSdiv2() {
        return sdiv2;
    }

    public void setSdiv2(String sdiv2) {
        this.sdiv2 = sdiv2;
    }

    public String getEstiv1() {
        return estiv1;
    }

    public void setEstiv1(String estiv1) {
        this.estiv1 = estiv1;
    }

    public String getIeiv1() {
        return ieiv1;
    }

    public void setIeiv1(String ieiv1) {
        this.ieiv1 = ieiv1;
    }

    public String getWeiv1() {
        return weiv1;
    }

    public void setWeiv1(String weiv1) {
        this.weiv1 = weiv1;
    }

    public String getVsiv1() {
        return vsiv1;
    }

    public void setVsiv1(String vsiv1) {
        this.vsiv1 = vsiv1;
    }

    public String getIsiv1() {
        return isiv1;
    }

    public void setIsiv1(String isiv1) {
        this.isiv1 = isiv1;
    }

    public String getWsiv1() {
        return wsiv1;
    }

    public void setWsiv1(String wsiv1) {
        this.wsiv1 = wsiv1;
    }

    public String getTdiv1() {
        return tdiv1;
    }

    public void setTdiv1(String tdiv1) {
        this.tdiv1 = tdiv1;
    }

    public String getTtiv1() {
        return ttiv1;
    }

    public void setTtiv1(String ttiv1) {
        this.ttiv1 = ttiv1;
    }

    public String getEfiv1() {
        return efiv1;
    }

    public void setEfiv1(String efiv1) {
        this.efiv1 = efiv1;
    }

    public String getSdiv1() {
        return sdiv1;
    }

    public void setSdiv1(String sdiv1) {
        this.sdiv1 = sdiv1;
    }


    //******************************************************************************************************************
    // Nome do Método: MontaXML()                                                                                      *
    //	                                                                                                               *
    // Data: 13/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML a partir das variáveis da classe Dados001                                          *
    //                                                                                                                 *
    // Entrada: a string com o comando recebido do navegador e a flag normal: se normal = true monta a mensagem XML    *
    //           normalmente. Se normal = false indica falha e monta a mensagem XML com os campos Value = ----------   *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static java.lang.String MontaXML(String comando, boolean normal) {

        // Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        String MsgXML = "";
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "04";

        IdNv1 = 1; // Grupo 1: 19 Variáveis de Informação GERAL
        int i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERAL";    // Carrega a Tag do Grupo 0
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("COMCNV", "Normal", normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("COMCNC", comcnc, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("COMUTR", comutr, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("COMCC1", comcc1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("COMCC2", comcc2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("CLK", clk, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("DATA", data, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("CMDEX", comando, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("MDOP", mdop, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("MDCOM", mdcom, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("MDCT1", mdct1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("MDCT234", mdct234, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ENCG1", encg1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ENCG2", encg2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ENCG3", encg3, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ICG3", icg3, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VBAT", vbat, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VREDE", vrede, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTVRD", estvrd, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TBAT", tbat, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("SDBAT", sdbat, normal);

        // Carrega o número de elementos do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][0][1] = SupService.IntToStr2(i);

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 2: Variáveis de Informação da Bomba do Poço e da Caixa Azul
        IdNv1 = 2;
        i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "07";
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTCXAZ", estcxaz, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("NIVCXAZ", nivcxaz, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTBMB", estbmb, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTDJB", estdjb, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTDJRB", estdjrb, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ENBMB", enbmb, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TMPBL", tmpbl, normal);

        // Carrega o número de elementos do Grupo 2
        MsgXMLArray[IdNv0][IdNv1][0][1] = SupService.IntToStr2(i);  // Carrega o número de elementos do Grupo 1

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 3: 18 Variáveis de Informação da Geração Solar e do Consumo
        IdNv1 = 3;
        i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VP12", vp12, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("IS12", is12, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ISCC1", iscc1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WSCC1", wscc1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("SDCC1", sdcc1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VP34", vp34, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("IS34", is34, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ISCC2", iscc2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WSCC2", wscc2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("SDCC2", sdcc2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ITOTGER", itotger, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WTOTGER", wtotger, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ITOTCG", itotcg, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WTOTCG", wtotcg, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTFT1", estft1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTFT2", estft2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ICIRCC", icircc, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WCIRCC", wcircc, normal);

        // Carrega o número de elementos do Grupo 3
        MsgXMLArray[IdNv0][IdNv1][0][1] = SupService.IntToStr2(i);

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 4: 20 Variáveis de Informação dos Inversores 1 e 2
        IdNv1 = 4;
        i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";

        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTIV2", estiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("IEIV2", ieiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WEIV2", weiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VSIV2", vsiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ISIV2", isiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WSIV2", wsiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TDIV2", tdiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TTIV2", ttiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("EFIV2", efiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("SDIV2", sdiv2, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ESTIV1", estiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("IEIV1", ieiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WEIV1", weiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("VSIV1", vsiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("ISIV1", isiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WSIV1", wsiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TDIV1", tdiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("TTIV1", ttiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("EFIV1", efiv1, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = SupService.EntTagValue("WCIRCC", sdiv1, normal);

        // Carrega o número de elementos do Grupo 4
        MsgXMLArray[IdNv0][IdNv1][0][1] = SupService.IntToStr2(i);

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = SupService.StringXML(MsgXMLArray);
        return(MsgXML);
    }

    //*****************************************************************************************************************
    // Nome do Método: LeEstMedsPayload()                                                                             *
    //                                                                                                                *
    // Data: 13/09/2021                                                                                               *
    //                                                                                                                *
    // Funcao: em operação local (Intranet), lê as informações dos estados e medidas recebidas do Concentrador        *
    //          usando mensagem em formato CoAP-OSA-CBM e carrega nas variáveis (atributos da classe).                *
    //                                                                                                                *
    // Medidas (64): bytes 160 a 288 - 2 bytes por medida                                                             *
    //                                                                                                                *
    // Entrada: array de bytes com a mensagem binária recebida em protocolo CoAP/UDP.                                 *
    //                                                                                                                *
    // Saida: não tem                                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    public static void LeEstMedsPayload(byte[] MsgRecCoAP) {

        int TamMsgSrv = MsgRecCoAP.length;

        // Lê as Informações de Data e Hora
        byte Hora = MsgRecCoAP[21] ;
        byte Minuto = MsgRecCoAP[22];
        byte Segundo = MsgRecCoAP[23];
        byte Dia = MsgRecCoAP[24];
        byte Mes = MsgRecCoAP[25];
        byte Ano = MsgRecCoAP[26];

        // Lê os estados de comunicação
        boolean EstComUTR = MsgRecCoAP[27] > 0;
        boolean EstComCC1 = MsgRecCoAP[28] > 0;
        boolean EstComCC2 = MsgRecCoAP[29] > 0;
        boolean EstCom1 = MsgRecCoAP[30] > 0;

        // Le os Estados Digitais
        boolean DJEINV1 = MsgRecCoAP[37] > 0;
        boolean EstRede = MsgRecCoAP[42] > 0;
        boolean MdOp = MsgRecCoAP[43] > 0;
        boolean MdCom = MsgRecCoAP[44] > 0;
        boolean MdCtrl1 = MsgRecCoAP[55] > 0;
        boolean MdCtrl = MsgRecCoAP[45] > 0;
        boolean Carga1 = MsgRecCoAP[46] > 0;
        boolean Carga2 = MsgRecCoAP[47] > 0;
        boolean Carga3 = MsgRecCoAP[48] > 0;
        boolean Carga4 = MsgRecCoAP[49] > 0;
        boolean HabCom = MsgRecCoAP[50] > 0;
        boolean EstadoInversor1 = MsgRecCoAP[51] > 0;
        boolean EstadoInversor2 = MsgRecCoAP[52] > 0;
        boolean EstadoCarga3 = MsgRecCoAP[53] > 0;
        boolean CDBat = MsgRecCoAP[68] > 0;
        boolean FonteCC2Ligada = MsgRecCoAP[71] > 0;
        boolean FonteCC1Ligada = MsgRecCoAP[73] > 0;

        // Lê os estados da Caixa Azul e da Bomba
        byte EstadoCxAz = MsgRecCoAP[72];           // O estado da Caixa Azul tem 5 valores
        boolean CircBoia = MsgRecCoAP[38] > 0;
        boolean BoiaCxAzul = MsgRecCoAP[39] > 0;
        boolean CxAzNvBx = MsgRecCoAP[69] > 0;
        boolean EdCxAzCheia = MsgRecCoAP[70] > 0;
        boolean CircBomba = MsgRecCoAP[40] > 0;
        boolean AlRedeBomba = MsgRecCoAP[41] > 0;
        boolean BombaLigada = MsgRecCoAP[54] > 0;

        // Le os Alarmes
        boolean FalhaIv1 = MsgRecCoAP[56] > 0;
        boolean SubTensaoInv1 = MsgRecCoAP[57] > 0;
        boolean SobreTensaoInv1 = MsgRecCoAP[58] > 0;
        boolean SobreTempDrInv1 = MsgRecCoAP[59] > 0;
        boolean SobreTempTrInv1 = MsgRecCoAP[60] > 0;
        boolean DjAbIv1 = MsgRecCoAP[61] > 0;
        boolean FalhaIv2 = MsgRecCoAP[62] > 0;
        boolean SubTensaoInv2 = MsgRecCoAP[63] > 0;
        boolean SobreTensaoInv2 = MsgRecCoAP[64] > 0;
        boolean SobreTempDrInv2 = MsgRecCoAP[65] > 0;
        boolean SobreTempTrInv2 = MsgRecCoAP[66] > 0;
        boolean DjAbIv2 = MsgRecCoAP[67] > 0;
        boolean SobreCorrInv1 = MsgRecCoAP[74] > 0;
        boolean SobreCorrInv2 = MsgRecCoAP[75] > 0;

        // Carrega as variaveis com os valores das saidas digitais
        int k = 112;
        boolean  CT1Inv = MsgRecCoAP[k] > 0;
        boolean  Iv1Lig = MsgRecCoAP[k + 1] > 0;
        boolean  CT3Inv = MsgRecCoAP[k + 2] > 0;
        boolean  Iv2Lig = MsgRecCoAP[k + 10] > 0;
        boolean  EstFonteCC = MsgRecCoAP[k + 16] > 0;
        boolean  CT2Inv = MsgRecCoAP[k + 17] > 0;

        // Le as Medidas da mensagem recebida do Concentrador Arduino Mega (medidas 0 a 47)
        k = 160;
        int[] Med = new int[256];
        for (byte i = 0; i < 48; i++){
            Med[i] = SupService.DoisBytesInt(MsgRecCoAP[k], MsgRecCoAP[k + 1]);
            k = k + 2;
        }

        // Carrega as medidas lidas do Concentrador Arduino Mega nas variaveis
        int VBat = Med[0];           // Tensão do Banco de Baterias
        int VMBat = Med[16];         // Tensão Média Estendida do Banco de Baterias
        int VRede = Med[5];          // Tensão da Rede
        int Icarga3 = Med[14];       // Corrente Carga 3 (Geladeira)
        int ICircCC = Med[3];        // Corrente Total dos Circuitos CC
        int IFonteCC = Med[11];      // Corrente de Saída da Fonte CC

        int TmpBmbLig = Med[17];     // Tempo da Bomba Ligada
        int TmpCxAzNvBx = Med[46];   // Tempo da Caixa Azul em Nivel Baixo

        // Leitura e Cálculo das Medidas referentes à Geração e Consumo
        int VP12 = Med[18];          // 0x3100 - PV array voltage 1
        int IS12 = Med[19];          // 0x3101 - PV array current 1
        int WS12 = Med[20];          // 0x3102 - PV array power 1
        int VBat1 = Med[21];         // 0x3104 - Battery voltage 1
        int ISCC1 = Med[22];         // 0x3105 - Battery charging current 1
        int WSCC1 = Med[23];         // 0x3106 - Battery charging power 1
        int TBat =  Med[24];         // 0x3110 - Battery Temperature 1

        int VP34 = Med[26];          // 0x3100 - PV array voltage 2
        int IS34 = Med[27];          // 0x3101 - PV array current 2
        int WS34 = Med[28];          // 0x3102 - PV array power 2
        int VBat2 = Med[29];         // 0x3104 - Battery voltage 2
        int ISCC2 = Med[30];         // 0x3105 - Battery charging current 2
        int WSCC2 = Med[31];         // 0x3106 - Battery charging power 2

        // Leitura e Cálculo das Medidas referentes ao Inversor 1
        int IEIv1 = Med[12];         		    // Corrente de Entrada do Inversor 1
        int WEIv1 = (VBat * IEIv1) / 100;		// Potência de Entrada do Inversor 1
        int VSIv1 = Med[4];          			// Tensão de Saída do Inversor 1
        int ISInv1 = (7 * Med[10]) / 10;        // Corrente de Saída do Inversor 1
        int WSInv1 = (VSIv1 * ISInv1) / 1000;	// Potencia de Saida do Inversor 1
        int TDInv1 = Med[8];         			// Temperatura do Driver do Inversor 1
        int TTInv1 = Med[9];         			// Temperatura do Transformador do Inversor 1
        int EfIv1 = SupService.CalcEficienciaInversor(WEIv1, WSInv1);
        int SDIv1 = 0;

        // Leitura e Cálculo das Medidas referentes ao Inversor 2
        double IEInversor2 = 838 * Med[15];
        int IEIv2 = (int)(IEInversor2 / 1000);		// Corrente de Entrada do Inversor 2
        int WEIv2 = (VBat * IEIv2) / 100;         	// Potencia de Entrada do Inversor 2
        int VSIv2 = Med[6];          				// Tensão de Saída do Inversor 2
        int ISInv2 = Med[13];
        int WSInv2 = (VSIv2 * ISInv2) / 1000;      	// Potencia de Saida do Inversor 2
        int TDInv2 = Med[2];         				// Temperatura do Driver do Inversor 2
        int TTInv2 = Med[7];         				// Temperatura do Transformador do Inversor 2
        int EfIv2 = SupService.CalcEficienciaInversor(WEIv2, WSInv2);
        int SDIv2 = 0;

        int ITotGer = Med[33];       				// Corrente Total Gerada
        int WCircCC = Med[35];       				// Potencia Consumida pelos Circuitos de 24Vcc
        int WFonteCC = Med[36];      				// Potencia Fornecida pela Fonte 24Vcc
        int IBat = Med[37];          				// Corrente de Carga ou Descarga do Banco de Baterias
        int WBat = (VBat * IBat) / 100;				// Potência de Carga/Descarga do Banco de Baterias
        ITotGer = ISCC1 + ISCC2;				    // Corrente Total Gerada
        int WTotGer = WSCC1 + WSCC2;				// Potência Total Gerada
        int ITotCg = IEIv1 + IEIv2 + (ICircCC/10);	// Corrente Total Consumida pelas Cargas
        int WTotCg =  WEIv1 + WEIv2 + WCircCC;		// Potência Total Consumida pelas Cargas

        int SDCC1 = 0;
        int SDCC2 = 0;
        int SDBat = 0;

        //--------------------------------------------------------------------------------------------------------------
        //                   Carrega os valores das variáveis nos atributos em formato String
        //--------------------------------------------------------------------------------------------------------------

        // Estados de Comunicacao
        comcnc = SupService.EstadoSimples(EstCom1, "Normal", "Falha");
        comutr = SupService.EstadoSimples(EstComUTR, "Normal", "Falha");
        comcc1 = SupService.EstadoSimples(EstComCC1, "Normal", "Falha");
        comcc2 = SupService.EstadoSimples(EstComCC2, "Normal", "Falha");

        // Hora e Data da UTR
        clk = SupService.ImpHora(Hora, Minuto, Segundo);
        data = SupService.ImpData(Dia, Mes, Ano);

        // Estados Gerais
        mdop = SupService.EstadoSimples(MdOp, "Normal", "Economia");
        mdcom = SupService.EstadoSimples(MdCom, "Remoto", "Local");
        mdct1 = SupService.EstadoSimples(EstComCC2, "Automatico", "Manual");
        mdct234 = SupService.EstadoSimples(MdCtrl, "Automatico", "Manual");

        // Estado da Fonte de Energia das Cargas 1, 2, 3 e 4
        encg1 = SupService.EstFonteEnergia(CT2Inv, Carga1, "Inversor 2", "Rede", "Rede (Hab)");
        encg2 = SupService.EstFonteEnergia(CT1Inv, Carga2, "Inversor 2", "Rede", "Rede (Hab)");
        encg3 = SupService.EstFonteEnergia(CT3Inv, Carga3, "Inversor 2", "Rede", "Rede (Hab)");
        enbmb = SupService.EstFonteEnergia(Iv1Lig, Carga4, "Inversor 1", "Rede", "Rede (Hab)");

        // Medida da Corrente da Carga 3 (Geladeira)
        icg3 = SupService.FrmAna3(Icarga3);

        // Medidas do Banco de Baterias
        vbat = SupService.FrmAna(VBat);
        tbat = SupService.FrmAna(TBat);
        sdbat = SupService.FrmAnaInt(SDBat);

        // Medida e Estado da Tensão da Rede
        vrede = SupService.FrmAna(VRede);
        estvrd = SupService.EstadoRede(EstRede, VRede, 19000);

        // Estados e Medidas da Caixa d'Água e da Bomba
        estcxaz = SupService.EstadoCaixaAzul(EstadoCxAz);
        nivcxaz = SupService.NivelCaixaAzul(EstadoCxAz);
        estbmb = SupService.EstadoSimples(CircBomba, "Ligada", "Desligada");
        estdjb = SupService.EstadoSimples(CircBoia, "Ligado", "Desligado");
        estdjrb = SupService.EstadoDepRede(EstRede, AlRedeBomba, "Ligado", "Desligado");
        tmpbl = SupService.FormAnaHora(TmpBmbLig);

        // Medidas da Geração Solar e Consumo
        vp12 = SupService.FrmAna(VP12);
        is12 = SupService.FrmAna(IS12);
        iscc1 = SupService.FrmAna(ISCC1);
        wscc1 = SupService.FrmAna(WSCC1);
        sdcc1 = SupService.FrmAnaInt(SDCC1);

        vp34 = SupService.FrmAna(VP34);
        is34 = SupService.FrmAna(IS34);
        iscc2 = SupService.FrmAna(ISCC2);
        wscc2 = SupService.FrmAna(WSCC2);
        sdcc2 = SupService.FrmAnaInt(SDCC2);

        itotger = SupService.FrmAna(ITotGer);
        wtotger = SupService.FrmAna(WTotGer);
        itotcg = SupService.FrmAna(ITotCg);
        wtotcg = SupService.FrmAna(WTotCg);

        // Estado das Fontes CC1 e CC2
        estft1 = SupService.EstadoDepRede(EstRede, FonteCC1Ligada, "Ligada", "Desligada");
        estft2 = SupService.EstadoDepRede(EstRede, FonteCC2Ligada, "Ligada", "Desligada");

        // Medidas dos Circuitos de Corrente Continua
        icircc = SupService.FrmAna3(ICircCC);
        wcircc = SupService.FrmAna(WCircCC);

        // Estados e Medidas do Inversor 2
        estiv2 = SupService.EstadoSimples(Iv2Lig, "Ligado", "Desligado");
        ieiv2 = SupService.FrmAna(IEIv2);
        weiv2 = SupService.FrmAna(WEIv2);
        vsiv2 = SupService.FrmAna(VSIv2);
        isiv2 = SupService.FrmAna(ISInv2);
        wsiv2 = SupService.FrmAna(WSInv2);
        tdiv2 = SupService.FrmAna(TDInv2);
        ttiv2 = SupService.FrmAna(TTInv2);
        efiv2 = SupService.FrmAna(EfIv2);
        sdiv2 = SupService.FrmAna(SDIv2);

        // Estados e Medidas do Inversor 2
        estiv1 = SupService.EstadoSimples(Iv1Lig, "Ligado", "Desligado");
        ieiv1 = SupService.FrmAna(IEIv1);
        weiv1 = SupService.FrmAna(WEIv1);
        vsiv1 = SupService.FrmAna(VSIv1);
        isiv1 = SupService.FrmAna(ISInv1);
        wsiv1 = SupService.FrmAna(WSInv1);
        tdiv1 = SupService.FrmAna(TDInv1);
        ttiv1 = SupService.FrmAna(TTInv1);
        efiv1 = SupService.FrmAna(EfIv1);
        sdiv1 = SupService.FrmAna(SDIv1);
    }
}
