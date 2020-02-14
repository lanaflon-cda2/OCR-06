package com.paymybuddy.transferapps.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputReaderUtil {

	private static Scanner scan = new Scanner(System.in, "UTF-8");
	private static final Logger logger = LogManager.getLogger("InputReaderUtil");

	public int readSelection() {
		try {
			int input = Integer.parseInt(scan.nextLine());
			return input;
		} catch (Exception e) {

			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid number for proceeding further");
			throw e;
		}
	}

	public String readVehicleRegistrationNumber() {
		try {
			String vehicleRegNumber = scan.nextLine();
			if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
				throw new IllegalArgumentException("Invalid input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
			throw e;
		}
	}
}
