package com.boxever.air.seatarrange;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test suites for Seat Arrangement application
 * 
 * @author dkumar
 *
 */
public class SeatArrangementTest {

	@Test
	public void testBoxeverInputFile() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(100, seatArrangement.satisfiedPaxPercentage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBothDimensionsAreZero() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile1.txt";
		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOneOfTheDimesionIsZero() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile2.txt";
		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
	}

	@Test
	public void testSeatMapDimensionOneXOne() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile3.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output3.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(50, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testSeatMapDimensionTwoXOne() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile4.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output4.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(100, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testSeatMapDimensionOneXTwoWithWindowSeatPax()
			throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile5.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output5.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(50, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testSeatMapSquareDimWithPaxHasWindowPref() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile6.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output6.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(100, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testInvalidDataForPassengerAtLastRow() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile7.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output7.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(84, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testInvalidDataForPassengerInMiddle() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile8.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output8.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(84, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testForSeatMapWithSemiAssignedRows() throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile9.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output9.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(94, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testForEmptySeatsInSeatMapEqualsPaxGroupCapacity()
			throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile10.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output10.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(80, seatArrangement.satisfiedPaxPercentage());
	}

	@Test
	public void testForEmptySeatsInSeatMapEqualsPaxGroupCapacityForNextLine()
			throws IOException {
		String inputFilePath = "src/test/resources/input/inputFile11.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/test/resources/output/expected-Output11.txt"), "UTF-8");

		SeatArrangement seatArrangement = new SeatArrangement();
		String actualOutput = seatArrangement
				.arrangeSeatForPaxFromFile(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(83, seatArrangement.satisfiedPaxPercentage());
	}

}