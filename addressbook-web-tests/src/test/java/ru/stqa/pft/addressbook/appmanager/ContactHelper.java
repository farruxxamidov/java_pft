package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Farrukh on 06-Mar-16.
 */
public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    //click(By.name("submit"));
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
//    attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    acceptAlert();
    goToHomePage();
  }

  public void selectContact(int index) {
    index += 2;
    click(By.xpath("html/body/div/div[4]/form[2]/table/tbody/tr[" + index + "]/td[1]/input"));
    //click(By.xpath("html/body/div/div[4]/form[2]/table/tbody/tr[2]/td[1]/input"));
  }

  public void submitContactModification() {
    click(By.name("update"));
    contactCache = null;
  }

  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    goToHomePage();
  }

  private void goToHomePage() {
    if (!isElementPresent(By.id("maintable"))) {
      click(By.linkText("home"));
    }
  }


  public boolean isThereAContact() {
    return isElementPresent(By.xpath("html/body/div/div[4]/form[2]/table/tbody/tr[2]/td[1]/input"));
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    contactCache = null;
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, true);
    submitContactModification();
    contactCache = null;
    goToHomePage();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allPhones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      contactCache.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname)
              .withAllPhones(allPhones).withAddress(address).withAllEmails(allEmails));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.cssSelector("textarea[name='address']")).getAttribute("value");
    String email1 = wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
    String email2 = wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
    String email3 = wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).
            withAddress(address).withEmail(email1).withEmail2(email2).withEmail3(email3).
            withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  public void initContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();

//    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
//    WebElement row = checkbox.findElement(By.xpath("./../.."));
//    List<WebElement> cells = row.findElements(By.tagName("td"));
//    cells.get(7).findElement(By.tagName("a")).click();

//    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();

//    wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();

  }

  public ContactData infoFIOFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String fio = firstname.concat(" ").concat(lastname);
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.cssSelector("textarea[name='address']")).getAttribute("value").concat("\n");
    String email1 = wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
    String email2 = wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
    String email3 = wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");

    if (!homePhone.equals("")) {
      homePhone = "H: " + homePhone;
    }
    if (!homePhone.equals("") && mobilePhone.equals("") && workPhone.equals("")) {
      homePhone = "H: " + homePhone + "\n";
    }
    if (!mobilePhone.equals("") && !workPhone.equals("")) {
      mobilePhone = "M: " + mobilePhone;
    }
    if (!mobilePhone.equals("") && workPhone.equals("")) {
      mobilePhone = "M: " + mobilePhone + "\n";
    }
    if (!workPhone.equals("")) {
      workPhone = "W: " + workPhone + "\n";
    }

    wd.navigate().back();
    initContactDetailsById(contact.getId());
    if (email1.contains("www")) {
      String domain1 = wd.findElement(By.xpath("//div[@id='content']//a[2]")).getText();
      email1 = email1.concat(" (").concat(domain1).concat(")");
    }
    if (email2.contains("www")) {
      String domain2 = wd.findElement(By.xpath("//div[@id='content']//a[4]")).getText();
      email2 = email2.concat(" (").concat(domain2).concat(")");
    }
    if (email2.contains("www")) {
      String domain3 = wd.findElement(By.xpath("//div[@id='content']//a[6]")).getText();
      email3 = email3.concat(" (").concat(domain3).concat(")");
    }
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFio(fio).withAddress(address).
            withEmail(email1).withEmail2(email2).withEmail3(email3).withHomePhone(homePhone).withMobilePhone(mobilePhone).
            withWorkPhone(workPhone);
  }


  public ContactData infoFromDetailsForm(ContactData contact) {
    initContactDetailsById(contact.getId());
    String allData = wd.findElement(By.xpath("//div[@id='content']")).getText();
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withAllData(allData);
  }

  private void initContactDetailsById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
  }

  public void selectGroupForAdding(String group) {
    new Select(wd.findElement(By.name("to_group")))
            .selectByVisibleText(group);
  }

  public void addToGroup() {
    click(By.name("add"));
  }


}
