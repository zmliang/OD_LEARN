package com.bkg.weitong;

import static com.bkg.weitong.License.PRIVATE_KEY;

import java.util.HashMap;

import ocxbwjmjmibzbnjyh.SMUtil;

public class ServerTest {

    private static boolean isFirst = false;

    private static final boolean interepted = true;

    private static final String RN = "303132333435363738394142434445467A3147B6219D2D85FF38BD32FCD1AB04";

    static {
        SMUtil.setLicense(License.LICENSE);
    }

    public static void main(String[] args) {
        //String miwenclient = "11e925f74e72862f9355212d33a4a197544e7d05e30583fab9e750f542aaa18ed94c72b0143df2af4f56a9b2d3876ada24496d4b289294aeede5cb3e3743ecbe3b8d605e3e89f97a004a531ad433b893c2f112f76f92bab8259146ac354ba8f01e9471b9018708955c017460ad92a2d67f8ecbbd15ce3b56fcbe762049a97f6b5e123e82f478e09d8ebe72130411074904e35a1f98102495270416b86765ce61894b0f43ce10274e59e95a6895b28615df40fd4c295dafaac2c6d96c8ff9a62d659426922bceb249eea9f7092e2c88299644601c5f2587ac51859ddb52fa249c0bd49b32ee37724cfa4168f0ddbe8762c994fceed7fc4885288add44d1327a23bca9926ebde657b34806e38e1cce82f6008b5e65c7e517a9a748c9d604081d9f3509f497e512892a1208fb67f429ba56b74ce8107ff9451e90bdfa968c913ddca6ded1f2c691e7ea7da8f02a151450c85328bcb124bd4eca0a87013f326128a3c99f6962260d0b7ffb8aca776248b027664db65e8903796150262bebde7cdfcd6e96c84be892a3e6987adcacc87e6e7c720e51a763470e329cbaffe67fea5e7cf40f54ffb1a6f84b4eeef31ecf01f18e7dd81e293a82f171d628fa236861fe8af1d900b0be201e5ac52661f30937983ae667baa29a51945a027d2268d634461211aea9280e13a15c52636de35de746547a7f80b5eb1ab09eda7b1b9201401ecaa8220f60d0da7bfa48679e9c71df19d233195aa078db371a902548f973a93b1c45860d8010e334d1d2c896e57939e2e3658898069f4d1603aad3c1c30239e91b";
        SMUtil smutil = new SMUtil();

        responseToClient("this is server response",RN,smutil);

        if (interepted){
            return;
        }


        //
        //todo 来自客户端的加密数据
        String miwenclient = "4BE2952F4BF246D8E96F97BC5AE6034015442090CE4213A632AD6897594F5D38028512A055A9A1FCC181249D610939E44A42482B29B7358C7D4EFAA1BEAFAE574779F71B8AB5811D5901D30F14C0DAE3F87D2CD809498F9B2DEFFBFEBA577DBD1F3FF51F3EAB27D0572A1BCBE4ED5E95D0A13C417C011605BE3BA7FFAEA78DD13EDFE4F476DBAABC4CFA0A0C7D49B74AFDFF0BD3B8D7D3E1F861D4A61438D585DEBFB2840BD5135E492758A296D3DE5D8323B82D0CFB834A7BF2660292FBE1D4AE11CBC9DE1476AB4612AB0FCA103F24D93A5876FE272241AA5128EC54E65DCE";

        // 截取前256位是rnpie
        String rnpie = miwenclient.substring(0, 256);
        // 剩下的是密文信息
        String censtr = miwenclient.substring(256);
        String rn = "";
        int pubid = 2;
        String encoding = "utf-8";
        // 解密rnpie得到rn，实际集成此步骤需要发给加密机解密
        rn = smutil.decryptSM2(PRIVATE_KEY, "04" + rnpie, 0);
        System.out.println("rn:" + rn);

        if (isFirst){
            // 服务端解密客户端报文校验Hmac
            HashMap<String, Object> hmsde;
            hmsde = smutil.sDecryptByECBAnCheckHmac(rn, censtr, encoding);
            HashMap<String, String> sm4ecbde = null;
            if ("0".equals(hmsde.get("code"))) {
                sm4ecbde = (HashMap<String, String>) hmsde.get("message");
            }
            System.out.println("解密后的数据："+sm4ecbde);
        }

        handleClientRequest(rn,smutil);

       // responseToClient("this is server response",rn,smutil);
    }


    /**
     * 服务器处理客户端的请求，数据里没带RNPie
     * @param rn
     * @param util
     */
    private static void handleClientRequest(String rn,SMUtil util) {
        String req = "3EDFE4F476DBAABC4CFA0A0C7D49B74A615F5BAEAD709CC2F832DEB25692248584141EA8F356AA27690A30AC298FF7038323B82D0CFB834A9BBE95B03946B8CD7AC148193DB6569C9B9DC87DDD4A1E800A9EE879722AAEAFAA5128EC54E65DCE";
        HashMap ret = util.sDecryptByECBAnCheckHmac(rn,req,"utf-8");
        if ("0".equals(ret.get("code"))) {
            System.out.println("解密后的："+ret.get("message"));
        }

    }


    /**
     * 给客户端响应数据，加密后的数据
     * @param message
     * @param rn
     * @param util
     */
    private static void responseToClient(String message, String rn,SMUtil util) {
        HashMap rep = util.sEncryptByECBAndHmac(rn,message,"utf-8");
        if ("0".equals(rep.get("code"))) {
            System.out.println("返回给客户端的数据："+rep.get("message"));
        }
    }


}
