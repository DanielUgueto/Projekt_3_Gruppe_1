package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.*;
import dk.ek.bilabonnement2026.service.CarBrandService;
import dk.ek.bilabonnement2026.service.CarModelService;
import dk.ek.bilabonnement2026.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarModelService carModelService;

    @Autowired
    CarBrandService carBrandService;

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
                            @RequestParam("registration_date") String registrationDate,
                            Model model){
        Car car = new Car(carModelId,vinNumber,licensePlate,monthlyPrice,status,colour,registrationDate);

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

    @GetMapping("/dashboard/car")
    public String showCarDashboard(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) Integer carId,
                                   @RequestParam(required = false) String vinNumber,
                                   HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }

        List<CarOverview> list;

        if (status == null || status.isBlank()) {
            list = carService.findCarsWithDetails();
        } else {
            list = carService.findCarsWithDetailsByStatus(status);
        }

        CarOverview selectedCar = null;

        if (carId != null) {
            for (CarOverview c : list){
                if (c.getCarId() == carId) {
                    selectedCar = c;
                    break;
                }
            }
            model.addAttribute("selectedCar", selectedCar);
        }

        if (vinNumber != null){
            for (CarOverview c : list){
                if (c.getVinNumber().equals(vinNumber)) {
                    selectedCar = c;
                    break;
                }
            }

            if (selectedCar == null){
                model.addAttribute("notFound", "Kunne ikke finde bil med matchende stelnummer!");
            }
            model.addAttribute("selectedCar", selectedCar);
        }

        model.addAttribute("employee", employee);
        model.addAttribute("carList", list);

        return "car-dashboard";
    }

    @PostMapping("/dashboard/car/remove")
    public String setCarStatusAsExpired(@RequestParam("carId") int carId,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")){
            return "redirect:/dashboard/car";
        }

        boolean statusUpdated = carService.changeCarStatusToExpired(carId);

        if (!statusUpdated) {
            redirectAttributes.addFlashAttribute("error", "Bilens status kunne ikke ændres til udgået!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Bilens status blev ændret til udgået.");
        }

        return "redirect:/dashboard/car?carId=" + carId;
    }

    @GetMapping("/dashboard/car/edit")
    public String getCarEditHTML(@RequestParam("carId") int carId, HttpSession session,
                                 Model model, RedirectAttributes redirectAttributes){

        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")){
            return "redirect:/dashboard/car";
        }

        Car car = carService.getCarByCarId(carId);
        if (car == null) {
            redirectAttributes.addFlashAttribute("carNotFound", "Bil kunne ikke findes i databasen!");
            return "redirect:/dashboard/car";
        }

        CarModel carModel = carModelService.getCarModelByCarModelId(car.getCarModelId());
        if (carModel == null) {
            redirectAttributes.addFlashAttribute("carModelNotFound", "Bil model kunne ikke findes i databasen!");
            return "redirect:/dashboard/car";
        }

        List<CarBrand> brands = carBrandService.getAllCarBrands();

        CarBrand carBrand = null;
        for (CarBrand brand : brands) {
            if (brand.getCarBrandId() == carModel.getCarBrandId()) {
                carBrand = brand;
                break;
            }
        }
        if (carBrand == null) {
            redirectAttributes.addFlashAttribute("carModelNotFound", "Bil mærket kunne ikke findes i databasen!");
            return "redirect:/dashboard/car";
        }


        model.addAttribute("brands", brands);
        model.addAttribute("car", car);
        model.addAttribute("carModel", carModel);
        model.addAttribute("carBrand", carBrand);

        return "edit-car";
    }

    @PostMapping("/dashboard/car/edit")
    public String editCar(@RequestParam("brandName") String brandName,
                          @RequestParam("modelName") String modelName,
                          @RequestParam("equipmentLevel") String equipmentLevel,
                          @RequestParam("fuelType") String fuelType,
                          @RequestParam("shiftGearType") String shiftGearType,
                          @RequestParam("vinNumber") String vinNumber,
                          @RequestParam("licensePlate") String licensePlate,
                          @RequestParam("monthlyPrice") double monthlyPrice,
                          @RequestParam("registrationDate") String registrationDate,
                          @RequestParam("colour") String colour,
                          @RequestParam("status") String status,
                          @RequestParam("carId") int carId,
                          @RequestParam("carModelId") int carModelId, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")){
            return "redirect:/dashboard/car";
        }

        List<CarBrand> brands = carBrandService.getAllCarBrands();
        Integer carBrandId = null;
        for (CarBrand brand : brands) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                carBrandId = brand.getCarBrandId();
                break;
            }
        }

        if (carBrandId == null) {

        }

        Car car = new Car(carId, carModelId, vinNumber, licensePlate, monthlyPrice, status, colour, registrationDate);
        CarModel carModel = new CarModel(carModelId, carBrandId, modelName, equipmentLevel, shiftGearType, fuelType);
        CarBrand carBrand = new CarBrand(carBrandId, brandName);



        return "redirect:/dashboard/car";
    }
}
