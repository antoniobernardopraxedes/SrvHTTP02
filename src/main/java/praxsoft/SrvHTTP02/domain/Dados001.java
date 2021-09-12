package praxsoft.SrvHTTP02.domain;

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
    // Data: 12/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML a partir das variáveis da classe Dados001                                          *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaXML(String comando) {

        // Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        String MsgXML = "";
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "04";

        IdNv1 = 1; // Grupo de 19 Variáveis de Informação GERAL
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERAL";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "21";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "COMCNV";
        MsgXMLArray[IdNv0][IdNv1][1][1] = "Normal";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "COMCNC";
        MsgXMLArray[IdNv0][IdNv1][2][1] = comcnc;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "COMUTR";
        MsgXMLArray[IdNv0][IdNv1][3][1] = comutr;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "COMCC1";
        MsgXMLArray[IdNv0][IdNv1][4][1] = comcc1;
        MsgXMLArray[IdNv0][IdNv1][5][0] = "COMCC2";
        MsgXMLArray[IdNv0][IdNv1][5][1] = comcc2;
        MsgXMLArray[IdNv0][IdNv1][6][0] = "CLK";
        MsgXMLArray[IdNv0][IdNv1][6][1] = clk;
        MsgXMLArray[IdNv0][IdNv1][7][0] = "DATA";
        MsgXMLArray[IdNv0][IdNv1][7][1] = data;
        MsgXMLArray[IdNv0][IdNv1][8][0] = "CMDEX";
        MsgXMLArray[IdNv0][IdNv1][8][1] = comando; //cmdex;
        MsgXMLArray[IdNv0][IdNv1][9][0] = "MDOP";
        MsgXMLArray[IdNv0][IdNv1][9][1] = mdop;
        MsgXMLArray[IdNv0][IdNv1][10][0] = "MDCOM";
        MsgXMLArray[IdNv0][IdNv1][10][1] = mdcom;
        MsgXMLArray[IdNv0][IdNv1][11][0] = "MDCT1";
        MsgXMLArray[IdNv0][IdNv1][11][1] = mdct1;
        MsgXMLArray[IdNv0][IdNv1][12][0] = "MDCT234";
        MsgXMLArray[IdNv0][IdNv1][12][1] =mdct234;
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ENCG1";
        MsgXMLArray[IdNv0][IdNv1][13][1] = encg1;
        MsgXMLArray[IdNv0][IdNv1][14][0] = "ENCG2";
        MsgXMLArray[IdNv0][IdNv1][14][1] = encg2;
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ENCG3";
        MsgXMLArray[IdNv0][IdNv1][15][1] = encg3;
        MsgXMLArray[IdNv0][IdNv1][16][0] = "ICG3";
        MsgXMLArray[IdNv0][IdNv1][16][1] = icg3;
        MsgXMLArray[IdNv0][IdNv1][17][0] = "VBAT";
        MsgXMLArray[IdNv0][IdNv1][17][1] = vbat;
        MsgXMLArray[IdNv0][IdNv1][18][0] = "VREDE";
        MsgXMLArray[IdNv0][IdNv1][18][1] = vrede;
        MsgXMLArray[IdNv0][IdNv1][19][0] = "ESTVRD";
        MsgXMLArray[IdNv0][IdNv1][19][1] = estvrd;
        MsgXMLArray[IdNv0][IdNv1][20][0] = "TBAT";
        MsgXMLArray[IdNv0][IdNv1][20][1] = tbat;
        MsgXMLArray[IdNv0][IdNv1][21][0] = "SDBAT";
        MsgXMLArray[IdNv0][IdNv1][21][1] = sdbat;

        IdNv1 = 2; // Grupo de 07 Variáveis de Informação da Bomba do Poço e da Caixa Azul
        MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "07";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCXAZ";
        MsgXMLArray[IdNv0][IdNv1][1][1] = estcxaz;
        MsgXMLArray[IdNv0][IdNv1][2][0] = "NIVCXAZ";
        MsgXMLArray[IdNv0][IdNv1][2][1] = nivcxaz;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTBMB";
        MsgXMLArray[IdNv0][IdNv1][3][1] = estbmb;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "ESTDJB";
        MsgXMLArray[IdNv0][IdNv1][4][1] = estdjb;
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ESTDJRB";
        MsgXMLArray[IdNv0][IdNv1][5][1] = estdjrb;
        MsgXMLArray[IdNv0][IdNv1][6][0] = "ENBMB";
        MsgXMLArray[IdNv0][IdNv1][6][1] = enbmb;
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TMPBL";
        MsgXMLArray[IdNv0][IdNv1][7][1] = tmpbl;

        IdNv1 = 3; // Grupo de 18 Variáveis de Informação da Geração Solar e do Consumo
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "18";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "VP12";
        MsgXMLArray[IdNv0][IdNv1][1][1] = vp12;
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IS12";
        MsgXMLArray[IdNv0][IdNv1][2][1] = is12;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ISCC1";
        MsgXMLArray[IdNv0][IdNv1][3][1] = iscc1;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "WSCC1";
        MsgXMLArray[IdNv0][IdNv1][4][1] = wscc1;
        MsgXMLArray[IdNv0][IdNv1][5][0] = "SDCC1";
        MsgXMLArray[IdNv0][IdNv1][5][1] = sdcc1;
        MsgXMLArray[IdNv0][IdNv1][6][0] = "VP34";
        MsgXMLArray[IdNv0][IdNv1][6][1] = vp34;
        MsgXMLArray[IdNv0][IdNv1][7][0] = "IS34";
        MsgXMLArray[IdNv0][IdNv1][7][1] = is34;
        MsgXMLArray[IdNv0][IdNv1][8][0] = "ISCC2";
        MsgXMLArray[IdNv0][IdNv1][8][1] = iscc2;
        MsgXMLArray[IdNv0][IdNv1][9][0] = "WSCC2";
        MsgXMLArray[IdNv0][IdNv1][9][1] = wscc2;
        MsgXMLArray[IdNv0][IdNv1][10][0] = "SDCC2";
        MsgXMLArray[IdNv0][IdNv1][10][1] = sdcc2;
        MsgXMLArray[IdNv0][IdNv1][11][0] = "ITOTGER";
        MsgXMLArray[IdNv0][IdNv1][11][1] = itotger;
        MsgXMLArray[IdNv0][IdNv1][12][0] = "WTOTGER";
        MsgXMLArray[IdNv0][IdNv1][12][1] = wtotger;
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ITOTCG";
        MsgXMLArray[IdNv0][IdNv1][13][1] = itotcg;
        MsgXMLArray[IdNv0][IdNv1][14][0] = "WTOTCG";
        MsgXMLArray[IdNv0][IdNv1][14][1] = wtotcg;
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ESTFT1";
        MsgXMLArray[IdNv0][IdNv1][15][1] = estft1;
        MsgXMLArray[IdNv0][IdNv1][16][0] = "ESTFT2";
        MsgXMLArray[IdNv0][IdNv1][16][1] = estft2;
        MsgXMLArray[IdNv0][IdNv1][17][0] = "ICIRCC";
        MsgXMLArray[IdNv0][IdNv1][17][1] = icircc;
        MsgXMLArray[IdNv0][IdNv1][18][0] = "WCIRCC";
        MsgXMLArray[IdNv0][IdNv1][18][1] = wcircc;

        IdNv1 = 4; // Grupo de 20 Variáveis de Informação dos Inversores 1 e 2
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "20";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTIV2";
        MsgXMLArray[IdNv0][IdNv1][1][1] = estiv2;
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IEIV2";
        MsgXMLArray[IdNv0][IdNv1][2][1] = ieiv2;
        MsgXMLArray[IdNv0][IdNv1][3][0] = "WEIV2";
        MsgXMLArray[IdNv0][IdNv1][3][1] = weiv2;
        MsgXMLArray[IdNv0][IdNv1][4][0] = "VSIV2";
        MsgXMLArray[IdNv0][IdNv1][4][1] = vsiv2;
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ISIV2";
        MsgXMLArray[IdNv0][IdNv1][5][1] = isiv2;
        MsgXMLArray[IdNv0][IdNv1][6][0] = "WSIV2";
        MsgXMLArray[IdNv0][IdNv1][6][1] = wsiv2;
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TDIV2";
        MsgXMLArray[IdNv0][IdNv1][7][1] = tdiv2;
        MsgXMLArray[IdNv0][IdNv1][8][0] = "TTIV2";
        MsgXMLArray[IdNv0][IdNv1][8][1] = ttiv2;
        MsgXMLArray[IdNv0][IdNv1][9][0] = "EFIV2";
        MsgXMLArray[IdNv0][IdNv1][9][1] = efiv2;
        MsgXMLArray[IdNv0][IdNv1][10][0] = "SDIV2";
        MsgXMLArray[IdNv0][IdNv1][10][1] = sdiv2;

        MsgXMLArray[IdNv0][IdNv1][11][0] = "ESTIV1";
        MsgXMLArray[IdNv0][IdNv1][11][1] = estiv1;
        MsgXMLArray[IdNv0][IdNv1][12][0] = "IEIV1";
        MsgXMLArray[IdNv0][IdNv1][12][1] = ieiv1;
        MsgXMLArray[IdNv0][IdNv1][13][0] = "WEIV1";
        MsgXMLArray[IdNv0][IdNv1][13][1] = weiv1;
        MsgXMLArray[IdNv0][IdNv1][14][0] = "VSIV1";
        MsgXMLArray[IdNv0][IdNv1][14][1] = vsiv1;
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ISIV1";
        MsgXMLArray[IdNv0][IdNv1][15][1] = isiv1;
        MsgXMLArray[IdNv0][IdNv1][16][0] = "WSIV1";
        MsgXMLArray[IdNv0][IdNv1][16][1] = wsiv1;
        MsgXMLArray[IdNv0][IdNv1][17][0] = "TDIV1";
        MsgXMLArray[IdNv0][IdNv1][17][1] = tdiv1;
        MsgXMLArray[IdNv0][IdNv1][18][0] = "TTIV1";
        MsgXMLArray[IdNv0][IdNv1][18][1] = ttiv1;
        MsgXMLArray[IdNv0][IdNv1][19][0] = "EFIV1";
        MsgXMLArray[IdNv0][IdNv1][19][1] = efiv1;
        MsgXMLArray[IdNv0][IdNv1][20][0] = "SDIV1";
        MsgXMLArray[IdNv0][IdNv1][20][1] = sdiv1;

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = StringXML(MsgXMLArray) + " ";
        return(MsgXML);

    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLFalha()                                                                                 *
    //	                                                                                                               *
    // Data: 10/01/2020                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML indicando falha                                                                    *
    // Entrada: int: 0 = COMCNC = "----------" / 1 = COMCNC = "Falha"                                                  *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    public static String MontaXMLFalha(int Opcao) {

        // Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "04";

        IdNv1 = 1; // Grupo de 19 Variáveis de Informação GERAL
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERAL";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "21";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "COMCNV";
        MsgXMLArray[IdNv0][IdNv1][1][1] = "Falha";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "COMCNC";
        if (Opcao == 0) {
            MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
        }
        else {
            MsgXMLArray[IdNv0][IdNv1][2][1] = "Falha";
        }
        MsgXMLArray[IdNv0][IdNv1][3][0] = "COMUTR";
        MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "COMCC1";
        MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "COMCC2";
        MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "CLK";
        MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "DATA";
        MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "CMDEX";
        MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][9][0] = "MDOP";
        MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][10][0] = "MDCOM";
        MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][11][0] = "MDCT1";
        MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][12][0] = "MDCT234";
        MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ENCG1";
        MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][14][0] = "ENCG2";
        MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ENCG3";
        MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][16][0] = "ICG3";
        MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][17][0] = "VBAT";
        MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][18][0] = "VREDE";
        MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][19][0] = "ESTVRD";
        MsgXMLArray[IdNv0][IdNv1][19][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][20][0] = "TBAT";
        MsgXMLArray[IdNv0][IdNv1][20][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][21][0] = "SDBAT";
        MsgXMLArray[IdNv0][IdNv1][21][1] = "----------";

        IdNv1 = 2; // Grupo de 07 Variáveis de Informação da Bomba do Poço e da Caixa Azul
        MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "07";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCXAZ";
        MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "NIVCXAZ";
        MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTBMB";
        MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "ESTDJB";
        MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ESTDJRB";
        MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "ENBMB";
        MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TMPBL";
        MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";

        IdNv1 = 3; // Grupo de 18 Variáveis de Informação da Geração Solar e do Consumo
        MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "18";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "VP12";
        MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IS12";
        MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][3][0] = "ISCC1";
        MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "WSCC1";
        MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "SDCC1";
        MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "VP34";
        MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "IS34";
        MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "ISCC2";
        MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][9][0] = "WSCC2";
        MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][10][0] = "SDCC2";
        MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][11][0] = "ITOTGER";
        MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][12][0] = "WTOTGER";
        MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][13][0] = "ITOTCG";
        MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][14][0] = "WTOTCG";
        MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ESTFT1";
        MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][16][0] = "ESTFT2";
        MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][17][0] = "ICIRCC";
        MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][18][0] = "WCIRCC";
        MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";

        IdNv1 = 4; // Grupo de 20 Variáveis de Informação dos Inversores 1 e 2
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "20";

        MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTIV2";
        MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][2][0] = "IEIV2";
        MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][3][0] = "WEIV2";
        MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][4][0] = "VSIV2";
        MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][5][0] = "ISIV2";
        MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][6][0] = "WSIV2";
        MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][7][0] = "TDIV2";
        MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][8][0] = "TTIV2";
        MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][9][0] = "EFIV2";
        MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][10][0] = "SDIV2";
        MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";

        MsgXMLArray[IdNv0][IdNv1][11][0] = "ESTIV1";
        MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][12][0] = "IEIV1";
        MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][13][0] = "WEIV1";
        MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][14][0] = "VSIV1";
        MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][15][0] = "ISIV1";
        MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][16][0] = "WSIV1";
        MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][17][0] = "TDIV1";
        MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][18][0] = "TTIV1";
        MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][19][0] = "EFIV1";
        MsgXMLArray[IdNv0][IdNv1][19][1] = "----------";
        MsgXMLArray[IdNv0][IdNv1][20][0] = "SDIV1";
        MsgXMLArray[IdNv0][IdNv1][20][1] = "----------";

        // Retorna a Mensagem XML completa em formato de String
        return(StringXML(MsgXMLArray));

    }

    //******************************************************************************************************************
    //                                                                                                                 *
    // Nome do Método: StringXML()                                                                                     *
    //	                                                                                                               *
    // Funcao: monta uma String com a mensagem XML de resposta inserindo o valor das variáveis                         *
    //                                                                                                                 *
    // Entrada: array String com as Tags dos Níveis 0, 1 e 2 e os valores das variáveis de supervisão                  *
    //                                                                                                                 *
    // Saida: String com a mensagem XML                                                                                *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    private static String StringXML(String MsgXMLArray[][][][]) {
        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";         // Imprime a Tag de Nivel 0

        char Dezena = MsgXMLArray[0][0][0][1].charAt(0);
        char Unidade = MsgXMLArray[0][0][0][1].charAt(1);

        // Obtem o Numero de Tags de Nivel 1
        int NmTagNv1 = TwoCharToInt(Dezena, Unidade);

        // Repete até imprimir todas as Tags de Nível 1 e Nível 2
        for (int i = 1; i <= NmTagNv1; i++) {

            // Imprime a Tag de Nivel 1 de Início do Grupo
            MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";
            char DzNumVar = MsgXMLArray[0][i][0][1].charAt(0);
            char UnNumVar = MsgXMLArray[0][i][0][1].charAt(1);

            // Obtém o Número de Variáveis do Grupo
            int NumVar = TwoCharToInt(DzNumVar, UnNumVar);

            // Repeta até imprimir todas as Tags de Nível 2 e suas variáveis
            for (int j = 1; j <= NumVar; j++) {

                // Imprime as Tags de Nível 2 e os Valores das Variáveis
                MsgXML = MsgXML + "    <"+MsgXMLArray[0][i][j][0]+">"
                         + MsgXMLArray[0][i][j][1]
                         + "</"+MsgXMLArray[0][i][j][0]+">\n";
            }

            // Imprime a Tag de Nivel 1 de Fim de Grupo
            MsgXML = MsgXML + "  </" + MsgXMLArray[0][i][0][0] + ">\n";
        }

        // Imprime a Tag de Nivel 0 de Fim
        MsgXML = MsgXML + "</" + MsgXMLArray[0][0][0][0] + ">\n";

        return(MsgXML);

    }

    //*****************************************************************************************************************
    // Nome do Método: CharToByte                                                                                     *
    //                                                                                                                *
    // Funcao: converte um caracter numerico em um valor numerico de 0 a 9                                            *
    // Entrada: caracter: '0' a '9'                                                                                   *
    // Saida: byte (valor numerico de 0 a 9)                                                                          *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static int CharToByte(char caracter) {
        byte Num = 10;
        switch (caracter) {
            case '0': Num = 0;
                break;
            case '1': Num = 1;
                break;
            case '2': Num = 2;
                break;
            case '3': Num = 3;
                break;
            case '4': Num = 4;
                break;
            case '5': Num = 5;
                break;
            case '6': Num = 6;
                break;
            case '7': Num = 7;
                break;
            case '8': Num = 8;
                break;
            case '9': Num = 9;
                break;
        }
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome do Método: TwoCharToInt                                                                                  *
    //                                                                                                                *
    // Funcao: converte dois caracteres numericos em um valor numerico de 00 a 99                                     *
    // Entrada: caracteres dezena e unidade ('0' a '9')                                                               *
    // Saida: int (valor numerico de 00 a 99)                                                                        *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    static int TwoCharToInt(char Ch10, char Ch1) {
        int Num = 10*CharToByte(Ch10) + CharToByte(Ch1);
        return (Num);
    }

}
