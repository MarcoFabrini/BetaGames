package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.EditorsDTO;
import com.betagames.model.Editors;
import com.betagames.repository.IEditorsRepository;
import com.betagames.request.EditorsRequest;
import com.betagames.service.interfaces.IEditorsService;
import static com.betagames.utility.Utilities.buildEditorsDTO;

/**
 *
 * @author Fabrini Marco
 */
@Service
public class EditorsImplementation implements IEditorsService {

    @Autowired
    Logger log;
    @Autowired
    IEditorsRepository editorsRepository;

    @Override
    public List<EditorsDTO> list() throws Exception {
        List<Editors> listEditors = editorsRepository.findAll();
        return buildEditorsDTO(listEditors);
    }// list

    @Override
    public List<EditorsDTO> searchByTyping(Integer id, String name, String website) throws Exception {
        List<Editors> listEditors = editorsRepository.searchByTyping(id, name, website);

        return listEditors.stream()
                .map(e -> new EditorsDTO(e.getId(), e.getName(), e.getWebsite()))
                .collect(Collectors.toList());
    }// searchByTyping

    @Override
    public void create(EditorsRequest req) throws Exception {
        Optional<Editors> editors = editorsRepository.findByName(req.getName());
        if (editors.isPresent())
            throw new Exception("This editor name is already present");

        editors = editorsRepository.findByWebsite(req.getWebsite());
        if (editors.isPresent())
            throw new Exception("This editor website is already present");

        Editors e = new Editors();
        e.setName(req.getName());
        e.setWebsite(req.getWebsite());

        editorsRepository.save(e);
    }// create

    @Override
    public void update(EditorsRequest req) throws Exception {
        Optional<Editors> editors = editorsRepository.findById(req.getId());
        if (!editors.isPresent())
            throw new Exception("Editor not found");

        Optional<Editors> ed = editorsRepository.findByName(req.getName());
        if (ed.isPresent())
            throw new Exception("This editor is already present");

        ed = editorsRepository.findByWebsite(req.getWebsite());
        if (ed.isPresent())
            throw new Exception("This editor website is already present");

        editors.get().setName(req.getName());
        editors.get().setWebsite(req.getWebsite());

        editorsRepository.save(editors.get());
    }// update

    @Override
    public void delete(EditorsRequest req) throws Exception {
        Optional<Editors> editors = editorsRepository.findById(req.getId());
        if (!editors.isPresent())
            throw new Exception("Editor not found");

        editorsRepository.delete(editors.get());
    }// delete

}// class
