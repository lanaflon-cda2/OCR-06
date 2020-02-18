package com.paymybuddy.transferapps.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

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
}
