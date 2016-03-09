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
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("xFarrukh", "xKhamidov", "xUzbekistan, xTashkent, " +
            "xBobur street, 4/1", "x1111111", "x2222222", "x3333333", "xtester@gmail.com"));
    app.getContactHelper().submitContactModification();
  }

}
