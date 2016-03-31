package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Farrukh on 09-Mar-16.
 */
public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().create(new GroupData().withName("test1"));
      }
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstName("Farrukh").withLastName("Khamidov").
              withAddress("Uzbekistan, Tashkent, Bobur street, 4/1").
              withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("tester@gmail.com").withGroup("test1"));
    }
  }


  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("xFarrukh").withLastName("xKhamidov")
            .withAddress("Uzbekistan, Tashkent, Bobur street, 4/1")
            .withHomePhone("x111").withMobilePhone("x222").withWorkPhone("x333").withEmail("xtester@gmail.com");
    app.contact().modify(contact);
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
