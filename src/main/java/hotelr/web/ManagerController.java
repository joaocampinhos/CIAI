package hotelr.web;

import hotelr.repository.*;
import hotelr.model.*;
import hotelr.exception.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * Mapping
 * GET  /managers             - the list of managers
 * GET  /managers/new         - the form to fill the data for a new manager
 * POST /managers             - creates a new manager
 * GET  /managers/{id}        - the manager with identifier {id}
 * GET  /managers/{id}/edit   - the form to edit the manager with identifier {id}
 * POST /managers/{id}        - update the manager with identifier {id}
 */

@Controller
@RequestMapping(value="/managers")
public class ManagerController {

  @Autowired
  ManagerRepository managers;

  // GET  /managers             - the list of hotels
  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("managers", managers.findAll());
    return "managers/index";
  }

  // GET  /managers.json            - the list of managers
  @RequestMapping(method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody Iterable<Manager> indexJSON(Model model) {
    return managers.findAll();
  }

  // GET  /managers/new         - the form to fill the data for a new manager
  @RequestMapping(value="/new", method=RequestMethod.GET)
  public String newManager(Model model) {
    model.addAttribute("manager", new Manager());
    return "managers/create";
  }

  // POST /managers             - creates a new manager
  @RequestMapping(method=RequestMethod.POST)
  public String saveIt(@ModelAttribute Manager manager, Model model) {
    managers.save(manager);
    model.addAttribute("manager", manager);
    return "redirect:/managers";
  }

  // GET  /managers/{id}        - the manager with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.GET)
  public String show(@PathVariable("id") long id, Model model) {
    Manager manager = managers.findOne(id);
    if( manager == null )
      throw new ManagerNotFoundException();
    model.addAttribute("manager", manager );
    return "managers/show";
  }

  // GET  /managers/{id}.json       - the manager with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody Manager showJSON(@PathVariable("id") long id, Model model) {
    Manager manager = managers.findOne(id);
    if( manager == null )
      throw new ManagerNotFoundException();
    return manager;
  }

  // GET  /managers/{id}/edit   - the form to edit the manager with identifier {id}
  @RequestMapping(value="{id}/edit", method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Model model) {
    model.addAttribute("manager", managers.findOne(id));
    return "managers/edit";
  }

  // POST /managers/{id}        - update the manager with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.POST)
  public String editSave(@PathVariable("id") long id, Manager manager, Model model) {
    managers.save(manager);
    return "redirect:/managers";
  }

}







