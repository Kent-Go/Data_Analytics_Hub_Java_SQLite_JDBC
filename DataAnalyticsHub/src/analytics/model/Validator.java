/*

 * Validator.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import analytics.model.exceptions.*;

/**
 * 
 * The Validator class provide validation logics for user and post related
 * inputs entered by users in Data Analytics Hub application.
 */
public class Validator {
    /**
     * The method to check if input is empty to throw user-defined
     * EmptyContentException
     * 
     * @param content The string to be validate
     */
    public String checkInputEmpty(String input, String inputField) throws EmptyInputException {
	if (input.isEmpty()) {
	    throw new EmptyInputException(inputField);
	}
	return input;
    }

    /**
     * The method to check if input did not exceed 6 character length to throw
     * user-defined InvalidPasswordLengthException
     * 
     * @param input The string to be check
     */
    public void checkPasswordLength(String input) throws InvalidPasswordLengthException {
	if (input.length() < 6) {
	    throw new InvalidPasswordLengthException();
	}
    }

    /**
     * The method to read user's post ID input and call readInputNonNegativeInt to
     * validate the parsed integer format and check if post ID already existed in
     * database in order to return the non-exist post ID integer.
     * 
     * @param text The text to be print to prompt user's input
     * @return the the non-exist post ID integer input
     * @throws ExistedPostIDException
     * @throws EmptyInputException
     * @throws InvalidNegativeIntegerException
     */
    public int readInputPostID(String input)
	    throws ExistedPostIDException, EmptyInputException, InvalidNegativeIntegerException {
	int postID = 0;
	try {
	    input = input.trim();
	    postID = readInputNonNegativeInt(input, "Post ID");
	    PostModel.getInstance().checkPostIDExist(postID);
	} catch (EmptyInputException inputEmptyError) {
	    throw inputEmptyError;
	} catch (NumberFormatException numberFormatError) {
	    throw numberFormatError;
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    throw integerNegativeError;
	} catch (ExistedPostIDException postIDExisted) {
	    throw postIDExisted;
	}

	return postID;
    }

    /**
     * The method to read post's content input and validate the content format in
     * order to return the valid content.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid post's content input
     * @throws EmptyInputException
     * @throws InvalidContentException
     */
    public String readInputContent(String input) throws EmptyInputException, InvalidContentException {
	try {
	    input = input.trim();
	    checkInputEmpty(input, "Content");
	    checkContentFormat(input);
	} catch (EmptyInputException inputEmptyError) {
	    throw inputEmptyError;
	} catch (InvalidContentException contentFormatError) {
	    throw new InvalidContentException();
	}
	return input;
    }

    public int readInputPositiveInt(String input, String inputField)
	    throws EmptyInputException, NumberFormatException, InvalidNonPositiveIntegerException {
	int intInput = 0;
	try {
	    input = input.trim();
	    checkInputEmpty(input, inputField);
	    intInput = Integer.parseInt(input);
	    checkPositiveIntegerFormat(intInput, inputField);
	} catch (EmptyInputException inputEmptyError) {
	    throw inputEmptyError;
	} catch (NumberFormatException numberFormatError) {
	    throw new NumberFormatException(String.format("%s input is invalid. Input must be an integer.", inputField));
	} catch (InvalidNonPositiveIntegerException integerNonPositiveError) {
	    throw integerNonPositiveError;
	}

	return intInput;
    }

    /**
     * The method to read post's date-time input and validate the date-time format
     * in order to return the valid date-time.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid post's date-time format
     * @throws EmptyInputException
     */
    public String readInputDateTime(String input) throws EmptyInputException, ParseException {
	// Note that SimpleDateFormat class and methods are adapted from:
	// https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	dateFormat.setLenient(false);
	try {
	    input = input.trim();
	    checkInputEmpty(input, "DateTime");
	    Date date = dateFormat.parse(input);
	    input = dateFormat.format(date);
	} catch (EmptyInputException inputEmptyError) {
	    throw inputEmptyError;
	} catch (ParseException parseError) {
	    throw new ParseException(input, 0);
	}
	return input;
    }

    /**
     * The method to read user's string input and validate the parsed integer format
     * in order to return the valid non-negative parsed integer.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid parsed non-negative integer input
     * @throws EmptyInputException
     * @throws InvalidNegativeIntegerException
     */
    public int readInputNonNegativeInt(String input, String inputField)
	    throws EmptyInputException, NumberFormatException, InvalidNegativeIntegerException {
	int intInput = 0;
	try {
	    checkInputEmpty(input, inputField);
	    intInput = Integer.parseInt(input);
	    checkNonNegativeIntegerFormat(intInput, inputField);
	} catch (EmptyInputException inputEmptyError) {
	    throw inputEmptyError;
	} catch (NumberFormatException numberFormatError) {
	    throw new NumberFormatException(String.format("%s input is invalid. Input must be an integer.", inputField));
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    throw integerNegativeError;
	}

	return intInput;
    }

    /**
     * The method to check if content contains "," to throw user-defined
     * InvalidContentException
     * 
     * @param content The string to be validate
     */
    public void checkContentFormat(String content) throws InvalidContentException {
	if (content.contains(",")) {
	    throw new InvalidContentException();
	}
    }

    /**
     * The method to check if integer is negative to throw user-defined
     * InvalidNegativeIntegerException
     * 
     * @param integer The integer to be validate
     */
    public void checkNonNegativeIntegerFormat(int integer, String inputField) throws InvalidNegativeIntegerException {
	if (integer < 0) {
	    throw new InvalidNegativeIntegerException(integer, inputField);
	}
    }

    /**
     * The method to check if integer is negative or zero to throw user-defined
     * InvalidNegativeIntegerException
     * 
     * @param integer The integer to be validate
     */
    public void checkPositiveIntegerFormat(int integer, String inputField) throws InvalidNonPositiveIntegerException {
	if (integer <= 0) {
	    throw new InvalidNonPositiveIntegerException(integer, inputField);
	}
    }
}
