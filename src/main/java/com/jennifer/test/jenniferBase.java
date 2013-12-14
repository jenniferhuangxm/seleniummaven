package com.jennifer.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class jenniferBase extends Assert {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@Before
	public void init(){
		driver=new FirefoxDriver();
		wait=new WebDriverWait(driver, 10);
	}
	
	//�õ����еȴ����Ƶ�WebElement(�ԡ���Ԫ��+�ȴ����ķ�װ)
	public WebElement e(By by){
		try{
			waitUntilElementExist(by);
			return driver.findElement(by);
		}catch (Exception e) {
			fail("Can't find element: ["+by+"]");
		}
		return null;
	}
	//��ɶ�Ԫ�صġ�����+�ȴ�(ֱ��Ԫ�ش���)+��֤+��� �������ķ�װ
	public void clickAndWait(By by){
		e(by).click();		
	}
	
	//��ɶ�Ԫ�ص� ������+�ȴ�+��֤�����ԡ� �ķ�װ
	public void waitUntilElementExist(By by){
		ElementExistOrNot isTrue=new ElementExistOrNot(by, "yes");
		try{
			wait.until(isTrue);
		}catch (Exception e){
			fail("Element [" + by + "] should presents!");
		}
	}
	//��ɶ�Ԫ�صġ�����+�ȴ�+��֤�������ԡ��ķ�װ
	public void waitUntilElementNotExist(By by){
		ElementExistOrNot isTrue=new ElementExistOrNot(by, "no");
		try{
			wait.until(isTrue);
		}catch (Exception e) {
			fail("Element [" + by + "] should not presents!");
		}
	}
	
	//��ɶ�Ԫ�صġ�����+�ȴ�+��֤��text�����ԡ��ķ�װ
	public void waitUntilElementEqualsText(By by,String s){
		waitUntilElementExist(by);
		ElementEqualsOrNot isTrue=new ElementEqualsOrNot(by, "yes", s, "text");
		try{
			wait.until(isTrue);
		}catch (Exception e) {
			fail("Element ["+by+"] text should equals"+s+"but not it equals"+e(by).getText());
		}
	}
	//��ɶ�Ԫ�صġ�����+�ȴ�+��֤��value�����ԡ��ķ�װ
	public void waitUntilElementEqualsValue(By by,String s){
		waitUntilElementExist(by);
		ElementEqualsOrNot isTrue=new ElementEqualsOrNot(by, "yes", s, "value");
		try{
			wait.until(isTrue);
		}catch (Exception e) {
			fail("Element ["+by+"] value should equals:"+s+"but not it equals:"+e(by).getAttribute("value"));
		}
	}
	
	@After
	public void tearDown(){
		driver.quit();
	}
}

class ElementExistOrNot implements Function<WebDriver, Boolean>{//��һ������Ϊapply�����Ĳ������ͣ��ڶ�������Ϊapply�����ķ�������
	private By by;
	private String sign;
	
	public ElementExistOrNot(By by, String sign) {
		this.by = by;
		this.sign = sign;
	}
	public Boolean apply(WebDriver driver) {
		try{
			WebElement e=driver.findElement(by);
			if(sign.equals("yes")){
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			if(sign.equals("yes")){
				return false;
			}else{
				return true;
			}
		}
	}
}
class ElementEqualsOrNot implements Function<WebDriver,Boolean>{
	private By by;
	private String sign;
	private String s;
	private String textOrValue;
	
	public ElementEqualsOrNot(By by, String sign, String s, String textOrValue) {
		this.by = by;
		this.sign = sign;
		this.s = s;
		this.textOrValue = textOrValue;
	}

	public Boolean apply(WebDriver driver) {
		WebElement e=driver.findElement(by);
		if(sign.equals("yes")){ //ȷ��������
			if(textOrValue.equals("text")){  //ȷ��text�Ĵ�����
				return e.getText().equals(s);
			}else{  //ȷ��value�Ĵ�����
				return e.getAttribute("value").equals(s);
			}
		}else{//ȷ����������
			if(textOrValue.equals("text")){
				return !e.getText().equals(s);
			}else{
				return !e.getAttribute("value").equals(s);
			}
		}
		
	}
}