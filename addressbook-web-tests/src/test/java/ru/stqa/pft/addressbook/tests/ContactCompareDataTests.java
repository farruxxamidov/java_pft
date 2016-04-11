package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Farrukh on 08-Apr-16.
 */
public class ContactCompareDataTests extends TestBase {

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
              withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("tester@gmail.com"));
    }
  }

  @Test(enabled = true)
  public void testContactComparisonData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFIOFromEditForm(contact);
    ContactData contactInfoFromDetailsForm = app.contact().infoFromDetailsForm(contact);
    assertThat(contactInfoFromDetailsForm.getAllData(), equalTo(mergeData(contactInfoFromEditForm)));
  }

  private String mergeData(ContactData contact) {
    return Arrays.asList(contact.getFio(), contact.getAddress(), contact.getHomePhone(),
            contact.getMobilePhone(), contact.getWorkPhone(), contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n")); //склейка
  }

}
