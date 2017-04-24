/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddpms.util;

public class CharacterUtil {

    public static String removeNull(Object data) {
        return (data == null ? "" : String.valueOf(data));
    }

    public static int removeNullTo(Object data, int to) {
        try {
            if (data == null || data == "") {
                return to;
            } else {
                return Integer.parseInt(String.valueOf(data));
            }            
        } catch (Exception e) {            
            return 0;
        }
    }

    public static String removeNullTo(Object data, String to) {
        if (data == null) {
            return to;
        } else {
            return String.valueOf(data);
        }
    }
}
