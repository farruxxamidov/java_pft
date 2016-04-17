package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

/**
 * Created by Farrukh on 17-Apr-16.
 */
public class RegistrationTests extends TestBase {

  @Test
  public void testRegistration() {
    app.registration().start("user1", "user@localhost.localdomain");
  }
}
