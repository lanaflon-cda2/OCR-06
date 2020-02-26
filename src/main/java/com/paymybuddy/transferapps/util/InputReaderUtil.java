package com.paymybuddy.transferapps.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Scanner;
@Repository
public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in, "UTF-8");
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");

    public int readInt(String select) {
        while (true) {
            System.out.println(select);
            try {
                int input = Integer.parseInt(scan.nextLine());
                return input;
            } catch (Exception e) {
                logger.error("Error while reading user input from Shell", e);
                System.out.println("Error reading input. Please enter valid number for proceeding further");
            }
        }
    }

    public double readDouble(String select) {
        while (true) {
            System.out.println(select);
            try {
                double input = Integer.parseInt(scan.nextLine());
                return input;
            } catch (Exception e) {

                logger.error("Error while reading user input from Shell", e);
                System.out.println("Error reading input. Please enter valid number for proceeding further");
            }
        }
    }

    public String readString(String select) {
        String input = null;
        while (input == null) {
            System.out.println(select);
            input = scan.nextLine();
            if(input.length() > 100){
                System.out.println("Your statement is too long, please write something shorter");
                input = null;
            }
        }
        return input;
    }
}
