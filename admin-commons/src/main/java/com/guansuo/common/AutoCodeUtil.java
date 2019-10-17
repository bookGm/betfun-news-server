package com.guansuo.common;

import java.text.NumberFormat;

public class AutoCodeUtil {

    public static String getDigitCode(int digit, int code){
        return String.format("%0"+digit+"d", code+1);
    }

    /**
     * 获取规则编码
     * @param digit 位数
     * @param code 编码
     * @param pcode 父编码
     * @return
     */
    public static String getDigitCode(int digit,String code,String pcode){
        boolean codeEmp=StringUtil.isBlank(code);
        code=codeEmp?"0":code;
        if(StringUtil.isBlank(pcode)||"0".equals(pcode)){
            return getDigitCode(digit,Integer.parseInt(code));
        }
        if("0".equals(code)){
            return pcode+getDigitCode(digit,0);
        }
        return pcode+getDigitCode(digit,Integer.parseInt(code.substring(code.length()-digit)));
    }
}
