package hotel.repository;

import hotel.model.ContaItem;
import hotel.model.FuncionarioItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface FuncionarioRepository extends MongoRepository<FuncionarioItem, String> {
	
	@Query("{name:'?0'}")
	FuncionarioItem findFuncionarioItemByName(String nome);

//	@Query("{quarto:'?0'}")
//	QuartoItem

	@Query(value="{name:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<FuncionarioItem> findAll(String category);
	
	public long count();

	//	  private String nome;
	//    private String endereco;
	//    private Nacionalidade nacionalidade;
	//    private String email;
	//    private String telefone;
	//    private HotelItem hotelItem;

}
