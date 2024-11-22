package rsa.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{ //implementing retryAnalyzer for retry of flaky or probable failure test cases

	int count = 0;
	int maxTry = 1; //signifies how many time to retry
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (count<maxTry) {
			count++;
			return true;
		}
		
		return false;
	}

}
