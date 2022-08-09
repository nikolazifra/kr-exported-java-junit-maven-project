package com.example.UserSuite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Duration;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RegistrationTest {

  private static final int MAX_WAIT = 60;
  private WebDriver driver;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(MAX_WAIT));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testChromeSession() {
      WebDriverManager.chromedriver().setup();
      WebDriver driver = new ChromeDriver();
      driver.quit();
  }

  private String getRandomEmail() {
    StringBuffer sb = new StringBuffer("nz-");
    sb.append(UUID.randomUUID());
    sb.append("@google.com");
    return sb.toString();
  }

  @Test
  public void testSuccessRegistrationCase() throws Exception {
    driver.get("https://www.smartr.me/public/sign-up");
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("Nikola");
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("Zifra");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys(getRandomEmail());
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("gTK6s-8f#!qUNte");
    driver.findElement(By.id("consent")).click();
    driver.findElement(By.name("formSubmitBtn")).click();
    assertTrue(isElementPresent(By.cssSelector("p.text--center.sign-up-page__email-confirmation")));
  }

  @Test
  public void testEmailInUseRegistrationCase() throws Exception {
    driver.get("https://www.smartr.me/public/sign-up");
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("Nikola");
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("Zifra");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("nikola.zifra@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("gTK6s-8f#!qUNte");
    driver.findElement(By.id("consent")).click();
    driver.findElement(By.name("formSubmitBtn")).click();
    assertTrue(isElementPresent(By.cssSelector("p.cp-form-error.sign-up-form-error")));
  }

  @Test
  public void testVerifyEmailCase() throws Exception {
    driver.get("https://mailtrap.io/");
    // use mail trap to verify e-mail
    // but it's out of scope of the assignment
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
