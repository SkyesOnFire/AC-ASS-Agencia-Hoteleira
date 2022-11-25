package hotel.repository;

import hotel.model.ContaItem;
import hotel.model.FuncionarioItem;
import hotel.model.HotelItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface HotelRepository extends MongoRepository<HotelItem, String> {
	
	@Query("{name:'?0'}")
	HotelItem findHotelItemByName(String nome);

//	@Query("{quarto:'?0'}")
//	QuartoItem

	@Query(value="{name:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<HotelItem> findAll(String category);
	
	public long count();

	//	  private String nome;
	//    private String endereco;
	//    private Nacionalidade nacionalidade;
	//    private String email;
	//    private String telefone;
	//    private HotelItem hotelItem;

}