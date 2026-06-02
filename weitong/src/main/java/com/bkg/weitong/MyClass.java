package com.bkg.weitong;


import static com.bkg.weitong.License.PRIVATE_KEY;

import java.util.HashMap;

import ocxbwjmjmibzbnjyh.GetRandom;

import ocxbwjmjmibzbnjyh.SMUtil;

public class MyClass {

    static {
        SMUtil.setLicense(License.LICENSE);
    }

    private static final String encryptedContent = "FEA5BF0737FD1BA8D790AE9ABBD4BD45F1DBBC8A95B232A54314D5B53FB83B02A3EA44A864D34560A96B74430A7401647DD64DB46CD872BE507BB638965A83ABA03FBE3B2AF209745F67C8BC03BBF971FC3796AAA1E747D197FF2605348ACDC74E6F721D152BB4B6F126BDC047021CCDD55C1A2F7FBB5343FFC444E7264C2DC1B8113BDD8D27822D6CC3DA9360585098F74465B10F6B49D5F937D504F443996A79A7E549055076DB5D290042231EDD28425D8299BCE08E45EF3B31B1F1E26AA70A868A6D7DDE031B90FD915993B366F1DEBFB2840BD5135E492758A296D3DE5D8323B82D0CFB834A45135FB567B52862BFB05905D8ABAACA556053D205E5DDEB44994544D352B8AFAA5128EC54E65DCE";

    private static final String payload = "049e4c92dea78493f058b888a4460937e92474a71519506c3bde7a34311b40fd1db2767df0952d79cbfe5ee39592099c252644c04f3a71552fb5fa9bea0bb163cd4d34c60e955596f66840621b10b298599991a0cd6ef28147b80c97fedf6022e8869fca5c4ab67bacf59088e61407f4ad";
    public static void main(String[] args) {
        System.out.println("开始解密");
        SMUtil util = new SMUtil();
        try {

            //MzUzNTMzMzkzMDM4NDM0MTM0MzAzMzMxMzE0NTQyMzY0MTM4NDMzMDMzMzgzNTM5MzQzMTQ1MzczMzMzMzAzM2dEJQXwm/Xe1gAUV+gm3RHj5iJlGml77TijUlbk675cCywvHF/J8NkaAvk/gpOWNWaHrJekB/Rzl/76jrEhXBYD5q71BW1Idxy8bqm9SEAp
            //MzMzNzMzNDQzODM5NDY0NTQyMzAzMTQyMzkzNDM4MzczMzQ0NDE0MTMwNDIzMzM2NDIzMjQyMzI0NDQ2NDQzNf/K+N0OXPhvQBRMbCZHVqJNlKQAmdRuWwulUhMMcc0StfJJHmft3oGXRhXnjNpgqsX+mzYEkWhTWhDHrxVYRS7uk+59wRGDSOPIEktrlRax
            //"MzczMjM4NDYzNTMzMzczMjQ1MzE0NDMyMzg0MjMxNDMzNzM0MzQzNDM5MzI0NjQ1MzU0MTMxMzkzMzQyMzk0NDJe1jOuNrwFTPUVwTR8EpPsVgPYjbuLf6RbV1MrrU3w9rzBJix/0nMsYrUPEfmrCec3aQHEzGdoSm+S7ldb8ALKJ24zP5qGfIcWhlou3Fgr"

//            //1.生成随机数R0
//            String r0= "A8CFC07488DDA38241BB78726F246840";//GetRandom.generateStringSec(32);
//
//            //2.服务端加密随机数R0
//            HashMap encryptedR0 = util.sEncryptServerRand(r0);
//            System.out.println("加密后的："+encryptedR0.get("code")+"---"+encryptedR0.get("message"));


            //3.客户端解密随机数R0
            String  sm2Dec = util.decryptSM2(PRIVATE_KEY,"",0);

            //4.解密报文
            HashMap ret = util.sDecryptByECBAnCheckHmac(sm2Dec,payload,"utf-8");
            System.out.println("解密后的报文："+ret.get("code")+"---"+ret.get("message"));

            /*
            String randomKey = util.decryptSM2(privateKey,payload,0);
            System.out.println("解密后的："+randomKey);

            HashMap ret ;


            //String randomKey = "90837746202127620225439666943472";

            ret = util.cDecryptByECBAnCheckHmacNoC(randomKey,encryptedContent);
            if (ret!= null) {
                System.out.println("1解密后的："+ret.get("message"));
            }
            ret = util.cDecryptByECBAnCheckHmac(randomKey,encryptedContent);
            if (ret!= null) {
                System.out.println("7解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.cDecryptByCBCAnCheckHmacNoC(randomKey,encryptedContent);
            if (ret!= null) {
                System.out.println("8解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.cDecryptByCBCAnCheckHmac(randomKey,encryptedContent);
            if (ret!= null) {
                System.out.println("9解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.sDecryptByECBAnCheckHmac(randomKey,encryptedContent,"utf-8");
            if (ret!= null) {
                System.out.println("10解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.sDecryptByCBCAnCheckHmac(randomKey,encryptedContent,"utf-8");
            if (ret!= null) {
                System.out.println("11解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.sDecryptByECBAnCheckHmacNoC(randomKey,encryptedContent,"utf-8");
            if (ret!= null) {
                System.out.println("12解密后的："+ret.get("code")+"---"+ret.get("message"));
            }
            ret = util.sDecryptByCBCAnCheckHmacNoC(randomKey,encryptedContent,"utf-8");
            if (ret!= null) {
                System.out.println("13解密后的："+ret.get("code")+"---"+ret.get("message"));
            }

            */

        }catch (Exception e){
            e.printStackTrace();
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
//        if ("0".equals(hmsde.get("code"))) {
//            sm4ecbde = (HashMap<String, String>) hmsde.get("message");
//
//
//
}