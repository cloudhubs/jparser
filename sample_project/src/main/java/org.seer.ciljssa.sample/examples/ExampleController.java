package org.seer.ciljssa.sample.examples;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * Basic example controller for annotation / role testing
 */

@RestController(value = "example")
public class ExampleController {

    private String[] users;

    public ExampleController() {
        this.users = new String[128]; // System supports 128 users
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    @RolesAllowed({"user"})
    public String exampleHandshake() {
        return "Hello from ExampleController";
    }

    @PostMapping(value = "/alter_users")
    @RolesAllowed({"moderator"})
    public String[] alterUsers(@RequestBody String[] users) {
        this.users = users;
        return this.users;
    }

    @PostMapping(value = "/alter_user")
    @RolesAllowed({"moderator"})
    public String[] alterUser(@RequestBody String user, int index) {
        if (index < users.length) {
            this.users[index] = user;
            return this.users;
        } else {
            return this.users;
        }
    }

    @PostMapping(value = "/delete_user")
    @RolesAllowed({"admin"})
    public String[] deleteUser(@RequestBody int index) {
        if (index < users.length) {
            this.users[index] = "[deleted]";
            return this.users;
        } else {
            return this.users;
        }
    }

}
