package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().createContact(new ContactData("Farrukh", "Khamidov", "Uzbekistan, Tashkent, Bobur street, 4/1", "1111111", "2222222", "3333333", "tester@gmail.com", "test1"), true);
    }

}
