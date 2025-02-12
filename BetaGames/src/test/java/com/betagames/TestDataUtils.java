package com.betagames;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.model.Editors;
import com.betagames.model.Games;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.IEditorsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.repository.IRolesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.EditorsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@Component
public class TestDataUtils {

    @Autowired
    IRolesRepository roleR;
    @Autowired
    IRolesService roleS;

    @Autowired
    IUsersRepository userR;
    @Autowired
    IUsersService userS;

    @Autowired
    IEditorsRepository editorR;

    @Autowired
    IGamesRepository gamesR;
    @Autowired
    IGamesService gamesS;

    @Transactional
    public Roles createRoleTest(String name) {
        RolesRequest rolesRequest = new RolesRequest();
        // Roles role = new Roles();
        rolesRequest.setName("User");
        // role.setName(name);

        return null;
    }

    @Transactional
    public Users createUserTest(String username, String email, String pwd) {
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPwd(pwd);
        user.setActive(true);

        return userR.save(user);
    }

    // "name" : "ciao",
    // "website" : "www.ciao.com"
    // @Transactional
    // public Editors createEditorsTest(String username, String email, String pwd){
    // Editors editor = new Users();
    // editor.setUsername(username);
    // editor.setEmail(email);
    // editor.setPwd(pwd);
    // editor.setActive(true);

    // return editorR.save(editor);
    // }

    @Transactional
    public Games createGamesTest(
            String name,
            String description,
            Date date,
            Integer maxGameTime,
            Integer minGameTime,
            Integer maxPlayerNumber,
            Integer minPlayerNumber,
            Integer minAge,
            Double price,
            Integer stockQuantity,
            Editors editor) {
        Games game = new Games();
        game.setName(name);
        game.setDescription(description);
        game.setDate(date);
        game.setMaxGameTime(maxGameTime);
        game.setMinGameTime(minGameTime);
        game.setMaxPlayerNumber(maxPlayerNumber);
        game.setMinPlayerNumber(minPlayerNumber);
        game.setMinAge(minAge);
        game.setPrice(price);
        game.setStockQuantity(stockQuantity);
        game.setEditor(editor);

        return gamesR.save(game);
    }

    @Transactional
    public EditorsRequest createEditorsRequestTest(String name, String website){
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setName(name);
        editorsRequest.setWebsite(website);

        return editorsRequest;
    }
    
}
