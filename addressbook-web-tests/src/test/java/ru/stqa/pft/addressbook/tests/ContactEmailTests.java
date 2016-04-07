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
 * Created by Farrukh on 07-Apr-16.
 */
public class ContactEmailTests extends TestBase {

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

  @Test
  public void testContactEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contactInfoFromEditForm) {
    return Arrays.asList(contactInfoFromEditForm.getEmail(), contactInfoFromEditForm.getEmail2(), contactInfoFromEditForm.getEmail3())
            .stream().filter((s) -> !s.equals("")) //фильтр пустых значений
            .map(ContactEmailTests::cleanedEmails)   //очистка от лишних символов
            .collect(Collectors.joining("\n")); //склеивание
  }

  public static String cleanedEmails(String email) {
    return email.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

}
