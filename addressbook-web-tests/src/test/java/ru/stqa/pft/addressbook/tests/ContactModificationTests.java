package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Farrukh on 09-Mar-16.
 */
public class ContactModificationTests extends TestBase{

  @Test(enabled = false)
  public void testContactModification() {
    app.goTo().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.goTo().goToContactPage();
      app.getContactHelper().createContact(new ContactData("Farrukh", "Khamidov", "Uzbekistan, Tashkent, Bobur street, 4/1", "1111111", "2222222", "3333333", "tester@gmail.com", "test1"));
      app.goTo().gotoHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactModification(before.size() - 1);
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "xFarrukh", "xKhamidov", "xUzbekistan, xTashkent, " +
            "xBobur street, 4/1", "x1111111", "x2222222", "x3333333", "xtester@gmail.com", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.goTo().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }

}
