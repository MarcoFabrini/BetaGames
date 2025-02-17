package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.EditorsDTO;
import com.betagames.model.Editors;
import com.betagames.repository.IEditorsRepository;
import com.betagames.request.EditorsRequest;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IServiceMessagesService;

import static com.betagames.utility.Utilities.buildEditorsDTO;

/**
 *
 * @author FabriniMarco
 */
@Service
public class EditorsImplementation implements IEditorsService {

    @Autowired
    IServiceMessagesService serviceMessagesService;
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
            throw new Exception(serviceMessagesService.getMessage("editors-Present"));

        editors = editorsRepository.findByWebsite(req.getWebsite());
        if (editors.isPresent())
            throw new Exception(serviceMessagesService.getMessage("editorsWeb-Present"));

        Editors e = new Editors();
        e.setName(req.getName());
        e.setWebsite(req.getWebsite());

        editorsRepository.save(e);
    }// create

    @Override
    public void update(EditorsRequest req) throws Exception {
        Optional<Editors> editors = editorsRepository.findById(req.getId());
        if (!editors.isPresent())
            throw new Exception(serviceMessagesService.getMessage("editor-noPresent"));

        if (editorsRepository.findByNameAndIdNot(req.getName(), req.getId()).isPresent())
            throw new Exception(serviceMessagesService.getMessage("editors-Present"));

        if (editorsRepository.findByWebsiteAndIdNot(req.getWebsite(), req.getId()).isPresent())
            throw new Exception(serviceMessagesService.getMessage("editorsWeb-Present"));

        editors.get().setName(req.getName());
        editors.get().setWebsite(req.getWebsite());

        editorsRepository.save(editors.get());
    }// update

    @Override
    public void delete(EditorsRequest req) throws Exception {
        Optional<Editors> editors = editorsRepository.findById(req.getId());
        if (!editors.isPresent())
            throw new Exception(serviceMessagesService.getMessage("editor-noPresent"));

        editorsRepository.delete(editors.get());
    }// delete

}// class
