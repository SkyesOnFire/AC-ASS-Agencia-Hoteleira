package hotel.controller;

import hotel.model.FuncionarioItem;
import hotel.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @RequestMapping(value = "/funcionario", method = RequestMethod.GET)
    public List<FuncionarioItem> Get() {
        return funcionarioRepository.findAll();
    }

    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.GET)
    public ResponseEntity<FuncionarioItem> GetById(@PathVariable(value = "id") String id)
    {
        Optional<FuncionarioItem> funcionario = funcionarioRepository.findById(id);
        if(funcionario.isPresent())
            return new ResponseEntity<FuncionarioItem>(funcionario.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/funcionario", method =  RequestMethod.POST)
    public FuncionarioItem Post(@Validated @RequestBody FuncionarioItem funcionarioItem)
    {
        return funcionarioRepository.save(funcionarioItem);
    }

    @RequestMapping(value = "/funcionario/trocarCargo/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<FuncionarioItem> Put(@PathVariable(value = "id") String id, @Validated @RequestBody FuncionarioItem newFuncionarioItem)
    {
        Optional<FuncionarioItem> oldFuncionario = funcionarioRepository.findById(id);
        if(oldFuncionario.isPresent()){
            FuncionarioItem funcionarioItem = oldFuncionario.get();
            funcionarioItem.setCargo(funcionarioItem.getCargo());
            funcionarioRepository.save(funcionarioItem);
            return new ResponseEntity<FuncionarioItem>(funcionarioItem, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") String id)
    {
        Optional<FuncionarioItem> funcionarioItem = funcionarioRepository.findById(id);
        if(funcionarioItem.isPresent()){
            funcionarioRepository.delete(funcionarioItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}