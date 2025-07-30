package com.yinan.util.sm4;

public class DecryptTest {

    public static void main(String[] args) {
        //设置待加密的文本
        String cipher = "dtSXdcPYJ0McQOoRWr3ecDsXG9azuYNLIp63FWdMfecLZ/m7SwukqVFHaY1GJ4ikqtdQ/3EgTb1SZn2D3Mg0BkpOzJGWVdj7TNEH8Inh44XnUTdZNuS40potiaVFRzr4pFJyJgcg8ZzjKgfdwCny3UF8G6jbgmcWxdq/ZZVe26UINef4hkrhOTPbBOeKWuK9XRYfbmP2p85sPDic4wMw9tnwGK82WqwGdysOM2ek43+DJRh9p0wIQtqQxHaPwWFA4Y4QXZyyvAX6kQvXa826WJnSQ+PLZE3wAcVSWeN5OSRBEzxA+OR9rIvzdd/9YJrdKoZ06rHePXUVN8XUmLYjrHgH35KCvssZQMmEojTf1aDZbd4zJW4LnZBrebZy9pmsEYLsVyk6Fhw4O5xhEZuAZPC7gIg7+GFwbCVmtAx5B+j3suWZSf7+iJFCQw+9LiLra0Gf7lNay9YMAec1vgfeELvAW24TGmofg2I1csHFlp8QBgmXNpieZXFoqQ4spxj6JoJ38pgOOnvp9KTzZFCaiH6UDc60jPKGAErtdHMWK7Y94wov4Sy3TuxGOLyumcjrs62c/GWFuemODwez/S3QTJb6clbkKEiUCr8HRMddNG7WIrBEEV+GeENdp4p/wekdOVAAfd0TpRUNAF9SEIqXlRwnf6MDhynuHUJPb5l2aLV2Jz1ton1pdCAUESOxgDVUvJ0uURuOuaUNwmeDuU07xWqM/4GwpmWtnRuuQZ0+TlFJffDnZj6irlN4rw9humLgL3kZjlL/64Eg3o4lfr4LxZ9GYSI0/SPO/1Y508Rs8OFFg2WWMTu4LtTbgZ8RfPCuaWSIxgERgADTwny4uG71/XVvIHbQSPIB/FIhtkCgQeunP45w0+JkNe3ehjM4rRgObRPWvEAlypSZsiDRPVsLC9y9GEcuRGnys1Cfh7xQS/yzKK7HZ36tLZzGHkuEmmM1";
        SM4Utils sm4 = new SM4Utils();
        //设置 密钥 16长度的字符
        sm4.setSecretKey("a7b4gSdFWc9b62l1");
        //设置 向量 16长度的字符
        sm4.setIv("mlue3fu15bd3Ycg1");
        //声明密钥和向量是否是32长度的十六进制的字符串，如果true则需要设置密钥向量都是十六进制的32长度字符串。Util.byteToHex("b7b3gSMFWd9a67i1".getBytes())
        sm4.setHexString(false);

        System.out.println(sm4.decryptDataCBC(cipher));
    }
}
