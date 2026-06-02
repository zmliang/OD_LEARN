package com.bkg.weitong;

import static com.bkg.weitong.License.LICENSE;

import java.util.HashMap;

import ocxbwjmjmibzbnjyh.SMUtil;

public class ClientTest {

    /**
     * 演示了CBC模式前后端协商R0、RC
     *
     * @author lkf
     *
     */
    static {
        SMUtil.setLicense(LICENSE);
    }

    public static void main(String[] args) {
        SMUtil smutil = new SMUtil();
        //todo 1. 加密R0,r0是由加密机生成的
        String r0 = "A9AC3F9F758FE94C4BD69A318315749F";
        //todo 2. 服务端加密r0
        HashMap<String, String> hm = smutil.sEncryptServerRand(r0);
        String enStr = "";
        if ("0".equals(hm.get("code"))) {
            enStr = hm.get("message");
            System.out.println("r0 miwen:" + enStr);
        } else {
            System.out.println(hm.get("code") + " : " + hm.get("message"));
        }
        //=================== -- ====================//

        //todo 3. 客户端解密r0密文
        HashMap<String, String> hmd = smutil.cDecryptR0EnStr(enStr);
        if ("0".equals(hmd.get("code"))) {
            System.out.println("r0 mingwen:" + hmd.get("message"));
        } else {
            System.out.println(hmd.get("code") + " : " + hmd.get("message"));
        }
        Integer pubid = 2;

        //todo 4. 客户端加密任意报文同时做Hmac协商RC
        String bwstr = "rc 协商完成 CBC";
        String encoding = "utf-8";
        HashMap<String, String> hmcen = smutil.cEncryptByCBCAndHmacInit(bwstr, encoding, pubid);
        String censtr = "";
        String rnpie = "";
        if ("0".equals(hmcen.get("code"))) {
            // rnpie需要给加密机解密得到rn
            rnpie = hmcen.get("rnpie");
            System.out.println("rnpie :" + rnpie);
            censtr = hmcen.get("message");
            // SM4密文和Hmac密文
            System.out.println("D|H:" + censtr);
        } else {
            System.out.println(hmcen.get("code") + " : " + hmcen.get("message"));
        }
        String rn = "";

        //==================
        //todo 5. 解密rnpie得到rn，实际集成此步骤需要发给加密机解密
        rn = smutil.decryptSM2("747303B3A554909BFD87870197F8C5018E15F9713CD5303866DF436C190C3418", "04" + rnpie, 0);

        //todo 6. 服务端解密客户端报文校验Hmac
        HashMap<String, Object> hmsde = smutil.sDecryptByCBCAnCheckHmac(rn,
                censtr, encoding);
        HashMap<String, String> sm4ecbde = null;
        if ("0".equals(hmsde.get("code"))) {
            sm4ecbde = (HashMap<String, String>) hmsde.get("message");
            String destr = sm4ecbde.get("val");
            System.out.println("decrypt str:" + destr);
        } else {
            System.out.println(hmsde.get("code") + " : " + hmsde.get("message"));
        }

    }


//    public static void main(String[] args) {
//        // Android或iOS客户端加密的带rnpie的报文密文
//        String miwenclient = "11e925f74e72862f9355212d33a4a197544e7d05e30583fab9e750f542aaa18ed94c72b0143df2af4f56a9b2d3876ada24496d4b289294aeede5cb3e3743ecbe3b8d605e3e89f97a004a531ad433b893c2f112f76f92bab8259146ac354ba8f01e9471b9018708955c017460ad92a2d67f8ecbbd15ce3b56fcbe762049a97f6b5e123e82f478e09d8ebe72130411074904e35a1f98102495270416b86765ce61894b0f43ce10274e59e95a6895b28615df40fd4c295dafaac2c6d96c8ff9a62d659426922bceb249eea9f7092e2c88299644601c5f2587ac51859ddb52fa249c0bd49b32ee37724cfa4168f0ddbe8762c994fceed7fc4885288add44d1327a23bca9926ebde657b34806e38e1cce82f6008b5e65c7e517a9a748c9d604081d9f3509f497e512892a1208fb67f429ba56b74ce8107ff9451e90bdfa968c913ddca6ded1f2c691e7ea7da8f02a151450c85328bcb124bd4eca0a87013f326128a3c99f6962260d0b7ffb8aca776248b027664db65e8903796150262bebde7cdfcd6e96c84be892a3e6987adcacc87e6e7c720e51a763470e329cbaffe67fea5e7cf40f54ffb1a6f84b4eeef31ecf01f18e7dd81e293a82f171d628fa236861fe8af1d900b0be201e5ac52661f30937983ae667baa29a51945a027d2268d634461211aea9280e13a15c52636de35de746547a7f80b5eb1ab09eda7b1b9201401ecaa8220f60d0da7bfa48679e9c71df19d233195aa078db371a902548f973a93b1c45860d8010e334d1d2c896e57939e2e3658898069f4d1603aad3c1c30239e91b";
//        // 截取前256位是rnpie
//        String rnpie = miwenclient.substring(0, 256);
//        // 剩下的是密文信息
//        String censtr = miwenclient.substring(256);
//        String rn = "";
//        int pubid = 2;
//        String encoding = "utf-8";
//        SMUtil smutil = new SMUtil();
//        // 解密rnpie得到rn，实际集成此步骤需要发给加密机解密
//        if (pubid == 1) {// 加密机，此处应发给加密机解密。
//            rn = smutil.decryptSM2("xxxxxxxxxxxxx", "04" + rnpie, 0);
//        } else {
//            rn = smutil
//                    .decryptSM2(
//                            "EE3DBAAFCB39E4FE07E780E088B054671106C44A42C7A09A286B4BF571063EDB",
//                            "04" + rnpie, 0);
//        }
//        System.out.println("rn:" + rn);
//        // 服务端解密客户端报文校验Hmac
//        HashMap<String, Object> hmsde = smutil.sDecryptByCBCAnCheckHmac(rn,
//                censtr, encoding);
//        HashMap<String, String> sm4ecbde = null;
////        if ("0".equals(hmsde.get("code"))) {
////            sm4ecbde = (HashMap<String, String>) hmsde.get("message");
////
////
////}
//    }
}
