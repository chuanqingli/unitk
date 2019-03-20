package unitk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**字节处理*/
public final class ByteUtil{

    private ByteUtil(){}
    private final static ByteUtil __instance = new ByteUtil();
    public static ByteUtil getInstance(){
        return __instance;
    }

//1)字节数组与整数间转换
    // 8位的byte[]数组转换成long型
    public long bytes8ToLong(byte[] mybytes){
        long tmp = (0xff & (long)mybytes[0]) << 56 | (0xff & (long)mybytes[1]) << 48
                | (0xff & (long)mybytes[2]) << 40 | (0xff & (long)mybytes[3]) << 32
                | (0xff & (long)mybytes[4]) << 24 | (0xff & (long)mybytes[5]) << 16
                | (0xff & (long)mybytes[6]) << 8 | 0xff & (long)mybytes[7];
        return tmp;
    }

    // long类型转化成8个字节
    public byte[] longToBytes8(long i){
        byte[] mybytes = new byte[8];
        mybytes[7] = (byte) (int) ((long) 255 & i);
        mybytes[6] = (byte) (int) (((long) 65280 & i) >> 8);
        mybytes[5] = (byte) (int) (((long) 0xff0000 & i) >> 16);
        mybytes[4] = (byte) (int) (((long) 0xff000000 & i) >> 24);
        int high = (int) (i >> 32);
        mybytes[3] = (byte) (0xff & high);
        mybytes[2] = (byte) ((0xff00 & high) >> 8);
        mybytes[1] = (byte) ((0xff0000 & high) >> 16);
        mybytes[0] = (byte) ((0xff000000 & high) >> 24);
        return mybytes;
    }

    //uint转化成4个字节的数组
    public byte[] intToBytes4(int i){
        byte[] mybytes = new byte[4];
        mybytes[3] = (byte) (0xff & i);
        mybytes[2] = (byte) ((0xff00 & i) >> 8);
        mybytes[1] = (byte) ((0xff0000 & i) >> 16);
        mybytes[0] = (byte) ((0xff000000 & i) >> 24);
        return mybytes;
    }

    // byte数组转化成uint类型
    public int bytes4ToInt(byte[] mybytes){
        int tmp = (0xff & mybytes[0]) << 24 | (0xff & mybytes[1]) << 16
                | (0xff & mybytes[2]) << 8 | 0xff & mybytes[3];
        return (int)tmp;
    }

    public byte[] shortToBytes2(short i){
        byte[] mybytes = new byte[2];
        mybytes[1] = (byte) (0xff & i);
        mybytes[0] = (byte) ((0xff00 & i) >> 8);
        return mybytes;
    }

    public short bytes2ToShort(byte[] mybytes){
        short tmp = (short)((0xff & mybytes[0]) << 8 | 0xff & mybytes[1]);
        return tmp;
    }

    public byte[] longToBytes4(long l){
        byte[] mybytes = new byte[4];

        mybytes[3] = (byte)(int)(255L & l);
        mybytes[2] = (byte)(int)((65280L & l) >> 8);
        mybytes[1] = (byte)(int)((0xff0000L & l) >> 16);
        mybytes[0] = (byte)(int)((0xffffffffff000000L & l) >> 24);
        return mybytes;
    }

    public long bytes4ToLong(byte[] abyte0){
        return (255L & (long)abyte0[0]) << 24 | (255L & (long)abyte0[1]) << 16 | (255L & (long)abyte0[2]) << 8 | 255L & (long)abyte0[3];
    }

//2)字节数组与base64字符串间转换
    public byte[] base64ToBytes(String s){
        return Base64Util.getInstance().decode(s.getBytes());
//		return Convert.FromBase64String(s);
    }

    public String bytesToBase64(byte[] b){
        return new String(Base64Util.getInstance().encode(b));
//		return Convert.ToBase64String(b);
    }

//3)由字节数组做md5后生成新的字节数组
    public byte[] getMd5(byte[] b) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持的算法",e);
        }

        msgDigest.update(b);
        return msgDigest.digest();
    }


//4)字节数组拷贝
    public void arraycopy(byte[] src, int srcPos,byte[] dest, int destPos, int length){
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

//5)字节数组与十六进制字符串间转换
    private static String[] HexCode = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9","A", "B", "C", "D", "E", "F"};

    public String ByteArrayToHexString(byte[] b){
        if(b==null||b.length<=0)return "";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(ByteToHexString(b[i]));
        }
        return result.toString();
    }

    public String ByteArrayToString(byte[] b){
        if(b==null||b.length<=0)return "";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(b[i]);
        }
        return result.toString();
    }

    public String ByteToHexString(byte b){
        int n = b;
        if (n < 0){
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }

    public byte[] hexStringToBytes(String str){
        if(str==null||str=="")return null;

        byte[] bytes = new byte[str.length()/2];
        for(int i=0;i<bytes.length;i++){
            String high = str.substring(i*2,i*2+1);
            String low = str.substring(i*2+1,i*2+2);
            bytes[i] = (byte) (hexStringToByte(high)*16 + hexStringToByte(low));
        }
        return bytes;
    }

    public int hexStringToByte(String str){
        int ret = 0;
        for(int i=0;i<HexCode.length;i++)
        {
            if(HexCode[i].equalsIgnoreCase(str))
                return i;
        }
        return ret;
    }

    public void printBytes(byte[] b){
        System.out.println(ByteArrayToHexString(b));
    }

//6)字节数组与字符串间转换
    //去除右补0字符
    public String doTrimEnd(String s){
        byte[] b = s.getBytes();
        byte[] a = new byte[b.length];

        for(int i=0;i<b.length;i++)
            a[i] = b[b.length-1];

        int cur = 0;
        byte selByte = a[0];

        while(selByte == 0x00 && cur < a.length)
            selByte = a[cur++];

        if(cur==0)return s;

        if(a.length == cur)return "";

        int trimLen = a.length - cur + 1;
        if(trimLen > s.length())return s;

        return s.substring(0,trimLen);
    }

    // 将字符串转化成特定长度的byte[]
    public byte[] getText(int idx, String value) {
        byte[] b1 = new byte[idx];

        byte[] b2 = value.getBytes();
        int i = 0;
        if (value != null || !value.equals("")) {
            while (i < b2.length && i < idx) {
                b1[i] = b2[i];
                i++;
            }
        }
        while (i < b1.length) {
            b1[i] = 0;
            i++;
        }
        return b1;
    }


    public byte[] stringToBytes(String inStr,String charsetName){
        try{
            return inStr.getBytes(charsetName);
        }catch(Exception e){
            e.printStackTrace();
            return inStr.getBytes();
        }
    }

}
