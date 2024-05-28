package ma.imane.orderservice.web;

import ma.imane.orderservice.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserRestController {
    Keycloak keycloak = Keycloak.getInstance(
            "http://localhost:8080",
            "e-shop-realm",
            "admin",
            "admin",
            "eshop-client");

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        try {
            UsersResource usersResource = keycloak.realm("e-shop-realm").users();
            UserResource userResource = usersResource.get(id);
            UserRepresentation userRepresentation = userResource.toRepresentation();

            User user = new User();
            BeanUtils.copyProperties(userRepresentation, user);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get user by id");
        }
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        try {


            keycloak.tokenManager().getAccessToken();
            UsersResource usersResource = keycloak.realm("e-shop-realm").users();
            List<UserRepresentation> userRepresentations = usersResource.list();
            List<User> users = new ArrayList<>();

            for (UserRepresentation userRepresentation : userRepresentations) {
                User user = new User();
                // Map attributes from UserRepresentation to User
                BeanUtils.copyProperties(userRepresentation, user);
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception and return an empty list or null as needed
            return null;
        }
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        try {
            UsersResource usersResource = keycloak.realm("e-shop-realm").users();
            usersResource.get(id).remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete user");
        }
    }

}
