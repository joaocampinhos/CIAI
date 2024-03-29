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
import hotelr.model.Hotel;
import hotelr.model.Manager;
import hotelr.repository.HotelRepository;
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
public class HotelControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Autowired
	HotelRepository hotels;

	@Autowired
	ManagerRepository managers;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}


	@Test
	public void testIndex() throws Exception {
		mvc.perform(get("/hotels")).andExpect(status().isOk())
				.andExpect(view().name("hotels/index"));
	}


	@Test
	public void testAddHotel() throws Exception {
		String hotelName = "Salgados";
		String hotelAddress = "Rua dos Salgados, 4, 20";
		String hotelCategory = "Alta Categoria";
		String hotelRating = "5";
		//Manager hotelManager = managers.findByName("O Chefe");
		mvc.perform(post("/hotels")
				.param("id", Integer.toString(0))
                .param("name", hotelName)
								.param("address", hotelAddress)
								.param("category", hotelCategory)
								.param("rating", hotelRating))
								//.param("manager", hotelManager)
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/hotels"));;

		Hotel hotel = hotels.findByName(hotelName);

		Assert.assertTrue(hotel != null);
	}

	@Test
	public void testGetOne() throws Exception {
		String hotelName = "Marriot";

		Hotel hotel = hotels.findByName(hotelName);

		mvc.perform(get("/hotels/"+hotel.getId()))
				.andExpect(view().name("hotels/show"));
	}

	@Test
	public void testModel() throws Exception {
		mvc.perform(get("/hotels"))
				.andExpect(model().attributeExists("hotels"));
	}
}
