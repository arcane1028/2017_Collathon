package com.naver.naverspeech.collathon;

/**
 * Created by YunDongHyeon on 2017-10-26.
 */

public class GameScore {
    public int[] parse(char input) {
        char sample;
        int[] charParsed = new int[3];
        sample = input;
        sample = (char) (sample - 0xac00);
        charParsed[2] = sample % 28;
        charParsed[1] = ((sample - charParsed[2]) / 28) % 21;
        charParsed[0] = (((sample - charParsed[2]) / 28) - charParsed[1]) / 21;
        return charParsed;
    }

    public int parseWord(String orign,String input) {
        int[] orignParse,inputParse;
        int equalCount=0;
        for (int i = 0; i < input.length(); i++) {
            orignParse=parse(orign.charAt(i));
            inputParse=parse(input.charAt(i));
            for(int j =0;j<orignParse.length;j++) {
                if(orignParse[j]==inputParse[j])
                    equalCount++;
            }
        }
        return equalCount;
    }

    public double parseSentence(String orign,String input) {
        int totalLength=0,equalCount=0;
        String[] inputSub = input.split(" ");
        String[] orignSub = orign.split(" ");
        if(inputSub.length!=orignSub.length) {
            return -1;
        }
        for (int i = 0; i < inputSub.length; i++) {
            totalLength+=3*inputSub[i].length();
            equalCount+=parseWord(orignSub[i],inputSub[i]);
        }
        System.out.println(totalLength);
        System.out.println(equalCount);
        double result=equalCount/totalLength;
        return (double)equalCount/(double)totalLength*100;
    }
}
