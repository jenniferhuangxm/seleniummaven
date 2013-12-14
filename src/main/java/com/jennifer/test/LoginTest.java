package com.jennifer.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginTest extends jenniferBase{
	@Test
	public void test() throws Exception{
		driver.get("http://www.google.com.hk/");
		clickAndWait(By.id("gbi4s1"));
		waitUntilElementEqualsText(By.id("link-signup"), "创建帐户");
		e(By.id("Email")).sendKeys("jenniferhuangzf@gmail.com");
		e(By.id("Passwd")).sendKeys("8226669hzf*");
		clickAndWait(By.id("signIn"));
		waitUntilElementEqualsText(By.id("gbi4t"), "黄志芬");
		
	}

}
