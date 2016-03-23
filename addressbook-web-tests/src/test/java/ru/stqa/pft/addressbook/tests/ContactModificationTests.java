package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Farrukh on 09-Mar-16.
 */
public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData("Farrukh", "Khamidov", "Uzbekistan, Tashkent, Bobur street, 4/1", "1111111", "2222222", "3333333", "tester@gmail.com", "test1"));
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("xFarrukh", "xKhamidov", "xUzbekistan, xTashkent, " +
            "xBobur street, 4/1", "x1111111", "x2222222", "x3333333", "xtester@gmail.com", null), false);
    app.getContactHelper().submitContactModification();
  }

}
