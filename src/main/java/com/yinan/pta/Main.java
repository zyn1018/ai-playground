package com.yinan.pta;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    class SameStructureStrChecker {
        int checkValid(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return 1;
            }
            if (s1 != null && s2 == null || s1 == null && s2 != null) {
                return 0;
            }
            if (s1.length() != s2.length()) {
                return 0;
            }
            Map<Character, Character> cMap = new HashMap<>();
            for (int i = 0; i < s1.length(); i++) {
                Character c = cMap.get(s1.charAt(i));
                if (c != null) {
                    if (c != s2.charAt(i)) {
                        return 0;
                    }
                } else {
                    if (cMap.values().contains(s2.charAt(i))) {
                        return 0;
                    }
                    cMap.put(s1.charAt(i), s2.charAt(i));
                }
            }
            return 1;
        }
    }

    public static void main(String[] args) {
        Main.SameStructureStrChecker checker = new Main().new SameStructureStrChecker();
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] sArray = s.split(",");
        System.out.println(checker.checkValid(sArray[0], sArray[1]));
    }
}
