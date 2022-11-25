package hotel.repository;

import hotel.model.ContaItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface ContaRepository extends MongoRepository<ContaItem, String> {
	
	@Query("{name:'?0'}")
	ContaItem findContaItemByName(String nome);

//	@Query("{quarto:'?0'}")
//	QuartoItem

	@Query(value="{name:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<ContaItem> findAll(String category);
	
	public long count();

	//	  private String nome;
	//    private String endereco;
	//    private Nacionalidade nacionalidade;
	//    private String email;
	//    private String telefone;
	//    private HotelItem hotelItem;

}