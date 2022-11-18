package hotel.controller;

import hotel.model.ProdutoItem;
import hotel.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @RequestMapping(value = "/produto", method = RequestMethod.GET)
    public List<ProdutoItem> Get() {
        return produtoRepository.findAll();
    }

    @RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoItem> GetById(@PathVariable(value = "id") String id)
    {
        Optional<ProdutoItem> produto = produtoRepository.findById(id);
        if(produto.isPresent())
            return new ResponseEntity<ProdutoItem>(produto.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/produto", method =  RequestMethod.POST)
    public ProdutoItem Post(@Validated @RequestBody ProdutoItem produtoItem)
    {
        return produtoRepository.save(produtoItem);
    }

    @RequestMapping(value = "/produto/alterarValor/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<ProdutoItem> Put(@PathVariable(value = "id") String id, @Validated @RequestBody ProdutoItem newProduto)
    {
        Optional<ProdutoItem> oldProduto = produtoRepository.findById(id);
        if(oldProduto.isPresent()){
            ProdutoItem produtoItem = oldProduto.get();
            produtoItem.setValor(newProduto.getValor());
            produtoRepository.save(produtoItem);
            return new ResponseEntity<ProdutoItem>(produtoItem, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") String id)
    {
        Optional<ProdutoItem> produtoItem = produtoRepository.findById(id);
        if(produtoItem.isPresent()){
            produtoRepository.delete(produtoItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}