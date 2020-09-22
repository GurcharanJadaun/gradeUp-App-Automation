package pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import HelperClass.ScrollHelper;
import HelperClass.WaitHelper;
import HelperClass.WaitParams;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PreviousPapers {
	AppiumDriver<MobileElement> driver;
	Map<String, String> xpath;
	Map<String, Object> scrollMap = new HashMap<String, Object>();
	Map<String, Integer> reportOnSubmission = new HashMap<String, Integer>();
	WaitHelper wait = new WaitHelper();

	public PreviousPapers(AppiumDriver<MobileElement> driver, Map<String, String> xpath) {
		this.driver = driver;
		this.xpath = xpath;
	}

	private boolean scrollToTest(By locator) {
		boolean result = false;

		scrollMap.put("numberOfScrolls", 1);
		scrollMap.put("targetElement", locator);
		scrollMap.put("scrollDir", "Down");
		result = wait.placeElementOnTop(driver, scrollMap);
		return result;
	}

	public boolean startTest() {
		boolean result = false;
		WaitParams param = new WaitParams();
		param.setParamters(5, xpath.get("startTestButton"));
		By testButton = By.xpath(xpath.get("testRowWithStartButton"));
		if (scrollToTest(testButton)) {
			result = wait.waitAndClick(driver, param);
		}
		else {
System.out.println("scrollToTest failed");
		}
		return result;
	}

	private String prepareXpath(String xpath, String replace, String replaceWith) {
		String val = xpath.replace(replace, replaceWith);
		return val;
	}

	public boolean attemptTest(int numberOfQuestions) {
		boolean result = false;
		String pathForQuestions, pathForAnswerSet, pathForAnswerOption;
		WaitParams param = new WaitParams();
		int option = 1;

		pathForQuestions = xpath.get("testQuestions");
		pathForAnswerSet = xpath.get("answerOptionSet");
		pathForAnswerOption = xpath.get("answerOptions");
		String path = prepareXpath(xpath.get("testQuestions"), "$number", "1");
		param.setParamters(25, path);

		ScrollHelper scroll = new ScrollHelper(driver);
		if (wait.presenceOfElementInDom(driver, param)) {
			int topOfPage = driver.findElement(By.xpath(xpath.get("questionIndex"))).getSize().height + 10;
			int startPoint = 0;
			for (int i = 1; i <= numberOfQuestions; i++) {
				try {
					path = prepareXpath(pathForQuestions, "$number", String.valueOf(i));
					startPoint = driver.findElement(By.xpath(path)).getLocation().getY();
					scroll.placeElementOnTop(topOfPage, startPoint);
					startPoint = driver.findElement(By.xpath(path)).findElement(By.xpath(pathForAnswerSet))
							.getLocation().getY();
					scroll.placeElementOnTop(topOfPage, startPoint);
					option = i % 5;
					path = prepareXpath(pathForAnswerOption, "$option", String.valueOf(option));
					param.setXpath(path);
					wait.waitAndClick(driver, param);
					Thread.sleep(200);
					result = true;
				} catch (Exception ex) {
					System.out.println("Exception on iteration " + i + " :" + ex.getMessage());
				}
			}
		}
		return result;

	}

	private int extractNumberFromString(String text) {
		int num = -1;

		String t = String.valueOf(text.indexOf("(") + 1);
		try {
			num = Integer.parseInt(t);
		} catch (Exception ex) {
			System.out.println("Exception while parsing String >>" + t);
			num = -1;
		}
		return num;
	}

	public boolean FinishTest() {
		boolean result = false;
		String text;

		WaitParams param = new WaitParams();
		param.setParamters(5, xpath.get("questionIndex"));
		if (wait.waitAndClick(driver, param)) {
			param.setXpath(xpath.get("submissionCorrect"));
			if (wait.presenceOfElementInDom(driver, param)) {
				text = driver.findElement(By.xpath(param.getXpath())).getAttribute("text");
				reportOnSubmission.put("Correct", extractNumberFromString(text));
				text = "";
			}

			param.setXpath(xpath.get("submissionWrong"));
			if (wait.presenceOfElementInDom(driver, param)) {
				text = driver.findElement(By.xpath(param.getXpath())).getAttribute("text");
				reportOnSubmission.put("Wrong", extractNumberFromString(text));
				text = "";
			}

			param.setXpath(xpath.get("submissionUnattempted"));
			if (wait.presenceOfElementInDom(driver, param)) {
				text = driver.findElement(By.xpath(param.getXpath())).getAttribute("text");
				reportOnSubmission.put("Unattempted", extractNumberFromString(text));
				text = "";
			}
			scrollMap.put("numberOfScrolls", 3);
			scrollMap.put("targetElement", (By) By.xpath(xpath.get("endTestButton")));
			scrollMap.put("scrollDir", "Down");
			if (wait.placeElementOnTop(driver, scrollMap)) {
				param.setXpath(xpath.get("endTestButton"));
				WaitParams expectedField = new WaitParams();
				expectedField.setParamters(5, xpath.get("submitConfirmation"));
				result = wait.clickAndWait(driver, param, expectedField);
			}
		}
		return result;

	}

	public boolean submitTest() {
		boolean result = false;
		WaitParams fieldToBeClicked = new WaitParams();
		WaitParams expectedField = new WaitParams();
		fieldToBeClicked.setParamters(10, xpath.get("submitTest"));
		expectedField.setParamters(10, xpath.get("resultPageScore"));

		result = wait.clickAndWait(driver, fieldToBeClicked, expectedField);
		return result;
	}

	public boolean selectAndAttemptExam() {
		boolean result = false;

		if (startTest()) {
			if (attemptTest(10)) {
				if (FinishTest()) {
					result = submitTest();
				}
			} else {
				WaitParams param = new WaitParams();
				param.setParamters(5, xpath.get("reattemptTest"));
				if (wait.waitAndClick(driver, param)) {
					if (attemptTest(10)) {
						if (FinishTest()) {
							result = submitTest();
						}
					}
				}
			}
		}
		return result;
	}
}
