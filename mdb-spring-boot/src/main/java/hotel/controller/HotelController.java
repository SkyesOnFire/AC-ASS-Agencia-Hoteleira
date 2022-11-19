package hotel.controller;

import hotel.model.HotelItem;
import hotel.model.HotelItem;
import hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public List<HotelItem> Get() {
        return hotelRepository.findAll();
    }

    @RequestMapping(value = "/hotel/{id}", method = RequestMethod.GET)
    public ResponseEntity<HotelItem> GetById(@PathVariable(value = "id") String id)
    {
        Optional<HotelItem> hotel = hotelRepository.findById(id);
        if(hotel.isPresent())
            return new ResponseEntity<HotelItem>(hotel.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/hotel", method =  RequestMethod.POST)
    public HotelItem Post(@Validated @RequestBody HotelItem hotelItem)
    {
        return hotelRepository.save(hotelItem);
    }

    @RequestMapping(value = "/hotel/trocarNome/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<HotelItem> Put(@PathVariable(value = "id") String id, @Validated @RequestBody HotelItem newHotel)
    {
        Optional<HotelItem> oldHotel = hotelRepository.findById(id);
        if(oldHotel.isPresent()){
            HotelItem hotelItem = oldHotel.get();
            hotelItem.setNome(newHotel.getNome());
            hotelRepository.save(hotelItem);
            return new ResponseEntity<HotelItem>(hotelItem, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/hotel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") String id)
    {
        Optional<HotelItem> hotelItem = hotelRepository.findById(id);
        if(hotelItem.isPresent()){
            hotelRepository.delete(hotelItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}