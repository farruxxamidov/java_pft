package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();

    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType()); // List<ContactData>.class
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();

    }


    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
//      Groups groups = app.db().groups();
//      File photo = new File("/src/test/resources/horse-riding.jpg");
//      ContactData newContact = new ContactData().withFirstName("Farrukh").withLastName("Khamidov")
//              .withAddress("Uzbekistan, Tashkent, Bobur street, 4/1").withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("tester@gmail.com").withPhoto(photo)
//              .inGroup(groups.iterator().next());

      Contacts before = app.db().contacts();
      app.contact().createContact(contact);
      app.goTo().homePage();
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      //Contacts after = app.contact().all();
      //assertThat(after.size(), equalTo(before.size() + 1));
      Contacts after = app.db().contacts();
      assertThat(after, equalTo(
              before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
      verifyContactListInUI();
    }



  @Test(enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/horse-riding.jpg");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }

}
