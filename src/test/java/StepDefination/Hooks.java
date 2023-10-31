package StepDefination;

import java.io.IOException;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class Hooks {

	@AfterStep
	public void AddScrrenShot(Scenario scenario) throws IOException {
	
		if (scenario.isFailed()) {
			Helper helper = Helper.getInstance();
			byte[] fileContent = helper.takeScreenshotFile();
			scenario.attach(fileContent, "image/png", "Image");
			
		}
	}
	}

