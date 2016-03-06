package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupDeletetionTests extends TestBase {

    @Test
    public void testGroupDeletetion() {
        gotoGroupPage();
        selectGroup();
        deleteSelectedGroups();
        returnToGroupPage();
    }

}
