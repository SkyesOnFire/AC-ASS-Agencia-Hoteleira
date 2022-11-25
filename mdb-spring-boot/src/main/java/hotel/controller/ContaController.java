package hotel.controller;

import hotel.model.ContaItem;
import hotel.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContaController {
    @Autowired
    private ContaRepository contaRepository;

    @RequestMapping(value = "/conta", method = RequestMethod.GET)
    public List<ContaItem> Get() {
        return contaRepository.findAll();
    }

    @RequestMapping(value = "/conta/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContaItem> GetById(@PathVariable(value = "id") String id)
    {
        Optional<ContaItem> conta = contaRepository.findById(id);
        if(conta.isPresent())
            return new ResponseEntity<ContaItem>(conta.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/conta", method =  RequestMethod.POST)
    public ContaItem Post(@Validated @RequestBody ContaItem contaItem)
    {
        return contaRepository.save(contaItem);
    }

    @RequestMapping(value = "/conta/confirmacaoPagamento/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<ContaItem> Put(@PathVariable(value = "id") String id, @Validated @RequestBody ContaItem newContaItem)
    {
        Optional<ContaItem> oldConta = contaRepository.findById(id);
        if(oldConta.isPresent()){
            ContaItem contaItem = oldConta.get();
            contaItem.setPago(newContaItem.isPago());
            contaRepository.save(contaItem);
            return new ResponseEntity<ContaItem>(contaItem, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/conta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") String id)
    {
        Optional<ContaItem> contaItem = contaRepository.findById(id);
        if(contaItem.isPresent()){
            contaRepository.delete(contaItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}