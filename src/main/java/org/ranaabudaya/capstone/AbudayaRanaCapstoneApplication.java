package org.ranaabudaya.capstone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Role;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.AdminRepository;
import org.ranaabudaya.capstone.service.AdminService;
import org.ranaabudaya.capstone.service.RoleService;
import org.ranaabudaya.capstone.service.ServicesService;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class AbudayaRanaCapstoneApplication implements CommandLineRunner {
	@Autowired
   RoleService roleService;

	@Autowired
	AdminService adminService;
	@Autowired
	ServicesService servicesService;


	@Autowired
	UserService userService;
	@Autowired
	AdminRepository adminRepository;
	public static void main(String[] args) {
		SpringApplication.run(AbudayaRanaCapstoneApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		initRoles();
		initUsers();
	}

	private void initRoles()
	{  if(roleService.getAllRoles().isEmpty()) {
		roleService.saveRole(new Role("ROLE_ADMIN"));
		roleService.saveRole(new Role("ROLE_CLEANER"));
		roleService.saveRole(new Role("ROLE_CLIENT"));
	   }
	}

	private void initUsers()
	{
//		Login 'test@gmail.com'
//		Password '1234'
		User user = userService.findUserByEmail("test@gmail.com");
		if(user ==null) {
			UserDTO userDTO = new UserDTO("rana", "rana", "abudaya", "test@gmail.com", "70170112345", "98201", "1234",
					"1234", "2900 grand ave", "Everett", "wa");
			userDTO.setRoleName("ROLE_ADMIN");
			int id = userService.create(userDTO);
			User usernew = userService.findById(id).get();
			Admin admin =new Admin();
			admin.setUser(usernew);
			System.out.println(admin);
			adminRepository.save(admin);
		}

		if(servicesService.getAllActiveServices().isEmpty()){
			ServicesDTO servicesDTO = new ServicesDTO();
			servicesDTO.setActive(true);
			servicesDTO.setName("Window Cleaning");
			servicesDTO.setPrice(10);
			servicesDTO.setDescription("Interior and exterior window cleaning\n" +
					"Dirt and grime removal\n" +
					"Sill and track cleaning\n" +
					"Use of eco-friendly solutions\n" +
					"Enhancement of window longevity and clarity");
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			servicesDTO.setDescription(servicesDTO.getDescription().replace("\n", "**"));
			Services service = modelMapper.map(servicesDTO, Services.class);
			servicesService.saveService(service);

			ServicesDTO servicesDTO2 = new ServicesDTO();
			servicesDTO2.setActive(true);
			servicesDTO2.setName("Regular Cleaning");
			servicesDTO2.setPrice(12);
			servicesDTO2.setDescription("Cleaning supplies: including vacuum\n" +
					"Kitchen: dishes, microwave, appliance exteriors\n" +
					"Bedroom: making bed, folding laundry\n" +
					"Bathroom: toilet, sink, shower, mirrors, folding, hanging towels\n" +
					"Every Room: organizing, dusting, vacuuming");
			modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			servicesDTO2.setDescription(servicesDTO2.getDescription().replace("\n", "**"));
			Services service2 = modelMapper.map(servicesDTO2, Services.class);
			servicesService.saveService(service2);

			ServicesDTO servicesDTO3 = new ServicesDTO();
			servicesDTO3.setActive(true);
			servicesDTO3.setName("Deep Cleaning");
			servicesDTO3.setPrice(15);
			servicesDTO3.setDescription("Regular Cleaning PLUS\n" +
					"Wash + fold laundry\n" +
					"Inside fridge and oven\n" +
					"Inside cabinets\n" +
					"Interior windows\n" +
					"Interior walls");
			modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			servicesDTO3.setDescription(servicesDTO3.getDescription().replace("\n", "**"));
			Services service3 = modelMapper.map(servicesDTO3, Services.class);
			servicesService.saveService(service3);



		}

	}
}
