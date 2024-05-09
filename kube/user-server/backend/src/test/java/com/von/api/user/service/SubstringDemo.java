package com.von.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.von.api.common.lambda.StreamOf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.*;

@ExtendWith(MockitoExtension.class)
public class SubstringDemo {

    @Test
    public void 문자열_분할() throws Exception {
        String str = "a,b,c";
        String data = new StringBuilder()
                .append(str)
                .append(",d,e,f")
                .toString();

        String[] arr = data.split(",");
        assertThat(arr.length).isEqualTo(6);

    }

    @Test
    public void 주민번호로_성별_구분() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd"); // M은 Month m은 minute
        String formattedDate = now.format(formatter);
        // System.out.println(formattedDate);

        // String human1 = "970301-1";
        // String human2 = "950301-2";
        // String human3 = "020701-3";
        // String human4 = "020301-4";
        // String human5 = "920301-5";
        // String human6 = "980301-6";
        // String human7 = "050301-7";
        // String human8 = "050301-8";
        // String human9 = "980301-0";

        // String allhuman = new StringBuilder()
        // .append(human1 + ",")
        // .append(human2 + ",")
        // .append(human3 + ",")
        // .append(human4 + ",")
        // .append(human5 + ",")
        // .append(human6 + ",")
        // .append(human7 + ",")
        // .append(human8 + ",")
        // .append(human9 + ",")
        // .toString();

        // String[] arr = allhuman.split(",");
        String[] arr = {
                "971201-1",
                "950301-2",
                "020701-3",
                "020301-4",
                "920301-5",
                "980301-6",
                "050301-7",
                "050301-8",
                "980301-0"
        };

        assertThat(arr.length).isEqualTo(9);

        for (String str : arr) {
            String[] arr1 = str.split("-");
            int age = nai(arr1[0], formattedDate, arr1[1]);
            String gender = getGenderNewSwitch(arr1[1]);
            System.out.println(age + gender);

            // if (arr1[1].equals("1") || arr1[1].equals("3")) {
            // System.out.println(age + "남자");
            // } else if (arr1[1].equals("2") || arr1[1].equals("4")) {
            // System.out.println(age + "여자");
            // } else {
            // System.out.println(age + "외국인");
            // }
        }

    }

    public int nai(String birthdate, String formattedDate, String gender) {

        int birthyy = Integer.parseInt(birthdate.substring(0, 2));
        int birthmm = Integer.parseInt(birthdate.substring(2, 4));
        int birthdd = Integer.parseInt(birthdate.substring(4, 6));
        int nowyy = Integer.parseInt(formattedDate.substring(0, 2));
        int nowmm = Integer.parseInt(formattedDate.substring(2, 4));
        int nowdd = Integer.parseInt(formattedDate.substring(4, 6));

        nowyy += 2000;

        // if (gender.equals("1") || gender.equals("2")) {
        // birthyy += 1900;
        // } else if (gender.equals("3") || gender.equals("4")) {
        // birthyy += 2000;
        // } else if (gender.equals("5") || gender.equals("6")) {
        // birthyy += 1900;
        // } else if (gender.equals("7") || gender.equals("8")) {
        // birthyy += 2000;
        // } else{
        // birthyy += 1800;
        // }

        switch (gender) {
            case "1", "2", "5", "6" ->
                birthyy += 1900;
            case "3", "4", "7", "8" ->
                birthyy += 2000;
            default ->
                birthyy += 1800;
        }

        int age = nowyy - birthyy;

        // if(birthmm > nowmm){
        // age --;
        // }else if (birthmm == nowmm){
        // if(birthdd>nowdd || birthdd == nowdd){
        // age --;
        // }
        // }

        if (birthmm > nowmm || (birthmm == nowmm && birthdd >= nowdd)) {
            age--;
        }

        return age;

    }

    private String getGenderNewSwitch(String ssn) {

        return switch (ssn.charAt(0)) {
            case '1', '3', '9' -> " 남성";
            case '2', '4', '0' -> " 여성";
            case '5', '7' -> " 외국인 남성";
            case '6', '8' -> " 외국인 여성";
            default -> " Error";

        };

    }

    @Test
    public void getAgeUsingLambda() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // M은 Month m은 minute
        String formattedDate = now.format(formatter);
        int fullyear = Integer.parseInt(formattedDate);

        String ssn2 = "920425-1";

        int s = Stream.of(ssn2)
                .map(ssn -> Integer.parseInt(ssn.substring(0, 2)))
                .map(birthyy -> switch (ssn2.charAt(7)) {
                    case '1', '2', '5', '6' -> birthyy += 1900;
                    case '3', '4', '7', '8' -> birthyy += 2000;
                    default -> birthyy += 1800;
                })
                .map(i -> i * 10000)
                .map(i -> i + Integer.parseInt(formattedDate.substring(2, 6)))
                .map(i -> (fullyear - i) / 10000)//
                .findFirst()
                .get();


        assertThat(s).isEqualTo(31);

    }

}
