package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarModel;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.CarModelService;
import dk.ek.bilabonnement2026.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarModelService carModelService;

    @GetMapping("/cars/create")
    public String showCreateCarForm(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        List<CarModel> carModels = carModelService.getAllCarModels();
        model.addAttribute("carModels",carModels);
        return "createCar";
    }

    @PostMapping("/cars/create")
    public String createCar(@RequestParam("carModelId") int carModelId,
                            @RequestParam("vinNumber") String vinNumber,
                            @RequestParam("licensePlate") String licensePlate,
                            @RequestParam("colour") String colour,
                            @RequestParam("status") String status,
                            @RequestParam("monthlyPrice") double monthlyPrice,
                            Model model){
        Car car = new Car(carModelId,vinNumber,licensePlate,monthlyPrice,status,colour);

        try{
            carService.createCar(car);
            return "redirect:/cars";
        }catch (IllegalArgumentException e){
            model.addAttribute("fejl", e.getMessage());
            List<CarModel> carModels = carModelService.getAllCarModels();
            model.addAttribute("carModels", carModels);
            return "createCar";
        }
    }

    @GetMapping("/dashboard")
    public String showCarDashboard(@RequestParam(required = false) String status, HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }

        ArrayList<CarOverview> list;

        if (status == null || status.isBlank()) {
            list = carService.findCarsWithDetails();
        } else {
            list = carService.findCarsWithDetailsByStatus(status);
        }

        model.addAttribute("carList", list);

        return "car-dashboard";
    }
}
