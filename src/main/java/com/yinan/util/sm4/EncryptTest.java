package com.yinan.util.sm4;

public class EncryptTest {
    public static void main(String[] args) {
        System.out.println("CBC模式-BASE64处理");

        //设置待加密的文本
        String plainText = "{\"applyUser\":{\"mobile\":\"13210179169\",\"name\":\"刘淑红\"},\"orderId\":\"1693881044202\",\"requestId\":1693893453264,\"applySku\":{\"applySkuReason\":{\"reason\":\"已拒收\",\"reasonDesc\":\"已拒收\"},\"count\":\"1\",\"originalPrice\":111.1099999999999994315658113919198513031005859375,\"packageId\":\"5007802087\",\"skuId\":\"1003000\",\"subtotalPrice\":72}}";
        SM4Utils sm4 = new SM4Utils();
        //设置 密钥 16长度的字符
        sm4.setSecretKey("a7b4gSdFWc9b62l1");
        //设置 向量 16长度的字符
        sm4.setIv("mlue3fu15bd3Ycg1");
        //声明密钥和向量是否是32长度的十六进制的字符串，如果true则需要设置密钥向量都是十六进制的32长度字符串。Util.byteToHex("b7b3gSMFWd9a67i1".getBytes())
        sm4.setHexString(false);

        //进行加密
        long encryptStartTime = System.currentTimeMillis();
        String cipherText = sm4.encryptDataCBC(plainText);
        System.out
            .println("sm4密文: " + cipherText + ", cost = " + (System.currentTimeMillis() - encryptStartTime) + "ms");
        System.out.println("");

        //进行解密
        long decryptStartTime = System.currentTimeMillis();
        plainText = sm4.decryptDataCBC(cipherText);
        System.out
            .println("sm4明文: " + plainText + ", cost = " + (System.currentTimeMillis() - decryptStartTime) + "ms");


    }
}
