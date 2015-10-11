package hotel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hotelr.Application;
import hotelr.model.Manager;
import hotelr.repository.ManagerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class ManagerControllerTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Autowired
  ManagerRepository managers;

  @Before
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }


  @Test
  public void testIndex() throws Exception {
    mvc.perform(get("/managers")).andExpect(status().isOk())
        .andExpect(view().name("managers/index"));
  }


  @Test
  public void testAddManager() throws Exception {
    String managerName = "Based God";
    String managerEmail = "me@based.god";
    String managerPassword = "callmebasedgod";
    mvc.perform(post("/managers")
        .param("id", Integer.toString(0))
                .param("name", managerName)
                .param("email", managerEmail)
                .param("password", managerPassword))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/managers"));;

    Manager manager = managers.findByName(managerName);

    Assert.assertTrue(manager != null);
  }

  @Test
  public void testGetOne() throws Exception {
    String managerName = "O Chefe";

    Manager manager = managers.findByName(managerName);

    mvc.perform(get("/managers/"+manager.getId()))
        .andExpect(view().name("managers/show"));
  }

  @Test
  public void testModel() throws Exception {
    mvc.perform(get("/managers"))
        .andExpect(model().attributeExists("managers"));
  }
}
