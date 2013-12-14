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
	
	//得到带有等待机制的WebElement(对“找元素+等待”的封装)
	public WebElement e(By by){
		try{
			waitUntilElementExist(by);
			return driver.findElement(by);
		}catch (Exception e) {
			fail("Can't find element: ["+by+"]");
		}
		return null;
	}
	//完成对元素的“查找+等待(直到元素存在)+验证+点击 操作”的封装
	public void clickAndWait(By by){
		e(by).click();		
	}
	
	//完成对元素的 ”查找+等待+验证存在性“ 的封装
	public void waitUntilElementExist(By by){
		ElementExistOrNot isTrue=new ElementExistOrNot(by, "yes");
		try{
			wait.until(isTrue);
		}catch (Exception e){
			fail("Element [" + by + "] should presents!");
		}
	}
	//完成对元素的“查找+等待+验证不存在性”的封装
	public void waitUntilElementNotExist(By by){
		ElementExistOrNot isTrue=new ElementExistOrNot(by, "no");
		try{
			wait.until(isTrue);
		}catch (Exception e) {
			fail("Element [" + by + "] should not presents!");
		}
	}
	
	//完成对元素的“查找+等待+验证其text存在性”的封装
	public void waitUntilElementEqualsText(By by,String s){
		waitUntilElementExist(by);
		ElementEqualsOrNot isTrue=new ElementEqualsOrNot(by, "yes", s, "text");
		try{
			wait.until(isTrue);
		}catch (Exception e) {
			fail("Element ["+by+"] text should equals"+s+"but not it equals"+e(by).getText());
		}
	}
	//完成对元素的“查找+等待+验证其value存在性”的封装
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

class ElementExistOrNot implements Function<WebDriver, Boolean>{//第一个参数为apply方法的参数类型，第二个参数为apply方法的返回类型
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
		if(sign.equals("yes")){ //确定存在性
			if(textOrValue.equals("text")){  //确定text的存在性
				return e.getText().equals(s);
			}else{  //确定value的存在性
				return e.getAttribute("value").equals(s);
			}
		}else{//确定不存在性
			if(textOrValue.equals("text")){
				return !e.getText().equals(s);
			}else{
				return !e.getAttribute("value").equals(s);
			}
		}
		
	}
}