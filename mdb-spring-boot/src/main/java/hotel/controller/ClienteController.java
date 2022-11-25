package hotel.controller;

import hotel.model.ClienteItem;
import hotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public List<ClienteItem> Get() {
        return clienteRepository.findAll();
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClienteItem> GetById(@PathVariable(value = "id") String id)
    {
        Optional<ClienteItem> cliente = clienteRepository.findById(id);
        if(cliente.isPresent())
            return new ResponseEntity<ClienteItem>(cliente.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/cliente", method =  RequestMethod.POST)
    public ClienteItem Post(@Validated @RequestBody ClienteItem clienteItem)
    {
        return clienteRepository.save(clienteItem);
    }

    @RequestMapping(value = "/cliente/trocarNome/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<ClienteItem> Put(@PathVariable(value = "id") String id, @Validated @RequestBody ClienteItem newCliente)
    {
        Optional<ClienteItem> oldCliente = clienteRepository.findById(id);
        if(oldCliente.isPresent()){
            ClienteItem clienteItem = oldCliente.get();
            clienteItem.setname(newCliente.getname());
            clienteRepository.save(clienteItem);
            return new ResponseEntity<ClienteItem>(clienteItem, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") String id)
    {
        Optional<ClienteItem> clienteItem = clienteRepository.findById(id);
        if(clienteItem.isPresent()){
            clienteRepository.delete(clienteItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}