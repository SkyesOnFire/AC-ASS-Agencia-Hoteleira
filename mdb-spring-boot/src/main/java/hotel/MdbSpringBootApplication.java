package hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotel.Enums.Cargo;
import hotel.Enums.Nacionalidade;
import hotel.Enums.Tipo;
import hotel.model.*;
import hotel.repository.QuartoRepository;
import hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import hotel.repository.CustomItemRepository;
import hotel.repository.ClienteRepository;

@SpringBootApplication
@EnableMongoRepositories
public class MdbSpringBootApplication implements CommandLineRunner{

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	QuartoRepository quartoRepository;
	@Autowired
	ReservaRepository reservaRepository;

	@Autowired
	CustomItemRepository customRepo;

	List<ProdutoItem> produtoItems = new ArrayList<ProdutoItem>();
	List<ClienteItem> clienteItems = new ArrayList<ClienteItem>();
	List<FuncionarioItem> funcionarioItems = new ArrayList<FuncionarioItem>();
	List<HotelItem> hotelItems = new ArrayList<HotelItem>();
	List<QuartoItem> quartoItems = new ArrayList<QuartoItem>();
	List<ReservaItem> reservaItems = new ArrayList<ReservaItem>();
	List<ContaItem> contaItems = new ArrayList<ContaItem>();

	public void run(String... args) throws ParseException {

		// Clean up any previous data
		clienteRepository.deleteAll(); // Doesn't delete the collection

		System.out.println("-------------CREATE ALL HOTEL ITEMS-------------------------------\n");

		populateInitialItems();

		System.out.println("\n----------------SHOW ALL CLIENT ITEMS---------------------------\n");

		showAllClientItems();

		System.out.println("\n--------------GET CLIENT ITEM BY NAME-----------------------------------\n");

		getClientItemByName("Pedro Lucho");

		System.out.println("\n-----------GET ALL QUARTOS---------------------------------\n");

		getAllQuartos();

//		System.out.println("\n-----------UPDATE COUNTRY OF ALL CLIENTS ----------------\n");
//
//		updateClientsCountry("Brazil");

//		System.out.println("\n-----------UPDATE ADDRESS OF A CLIENT ITEM------------------------\n");
//
//		updateClientAddress("Teilor Miotto", "Imaculada Conceicao");

		System.out.println("\n----------DELETE A CLIENT ITEM----------------------------------\n");

		deleteClientItem("4");

		System.out.println("\n------------FINAL COUNT OF CLIENT ITEMS-------------------------\n");

		findCountOfAllClients();

		System.out.println("\n-------------------THANK YOU---------------------------");

	}

	// CRUD operations

	//CREATE
	void populateInitialItems() throws ParseException {
		System.out.println("Data creation started...");

		HotelItem hotelAghahthas = new HotelItem("1", "HOTEL AGHAHTHAS", "Rua das Christies 9001", "154 243 534");
		FuncionarioItem julioFuncionario = new FuncionarioItem("1","Julio", Cargo.CAMAREIRO, hotelAghahthas);
		FuncionarioItem ricardoFuncionario = new FuncionarioItem("2","Ricardo", Cargo.FAXINEIRO, hotelAghahthas);

		clienteRepository.save(new ClienteItem("1", "Teilor Miotto", "Rua 1", Nacionalidade.BRASILEIRO, "tm@email.com", "12345678", hotelAghahthas));
		clienteRepository.save(new ClienteItem("2", "Pedro Lucho", "Rua 2", Nacionalidade.BRASILEIRO, "pl@email.com", "12345678", hotelAghahthas));
		clienteRepository.save(new ClienteItem("3", "Duda Grein", "Rua 3", Nacionalidade.BRASILEIRO, "dg@email.com", "12345678", hotelAghahthas));
		clienteRepository.save(new ClienteItem("4", "Agatha Christie", "Rua 4", Nacionalidade.BRASILEIRO, "ac@email.com", "12345678", hotelAghahthas));

		quartoRepository.save(new QuartoItem("1", 101, Tipo.SOLTEIRO, (long) 169.90, false, hotelAghahthas));
		quartoRepository.save(new QuartoItem("2", 105, Tipo.SOLTEIRO, (long) 169.90, false, hotelAghahthas));
		quartoRepository.save(new QuartoItem("3", 201, Tipo.CASAL, (long) 229.90, false, hotelAghahthas));
		quartoRepository.save(new QuartoItem("4", 401, Tipo.TRIPLO, (long) 529.90, false, hotelAghahthas));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		reservaRepository.save(new ReservaItem("1", Tipo.SOLTEIRO, simpleDateFormat.parse("25/11/2022"), simpleDateFormat.parse("05/12/2022"),
				quartoRepository.findQuartoItemById("2"), clienteRepository.findClienteItemByName("Teilor Miotto"), ricardoFuncionario));

		System.out.println("Data creation complete...");
	}

	// READ
	// 1. Show all the data
	 public void showAllClientItems() {

		 clienteItems = clienteRepository.findAll();

		 clienteItems.forEach(item -> System.out.println(getClientDetails(item)));
	 }

	 // 2. Get item by name
	 public void getClientItemByName(String name) {
		 System.out.println("Getting item by name: " + name);
		 ClienteItem item = clienteRepository.findClienteItemByName(name);
		 System.out.println(getClientDetails(item));
	 }

	 public void getAllQuartos() {
		 System.out.println("Getting all quartos: ");
		 List<QuartoItem> list = quartoRepository.findAll();

		 list.forEach(item -> System.out.println("ID: " + item.getId() + ", Numero: " + item.getNumero() +
				 ", Tipo: " + item.getTipo() + ", Valor: " + item.getValor() + ", Adaptado: " + item.isAdaptado() +
				 ", Hotel Vinculado: " + item.getHotelItem()));
	 }

	 // 4. Get count of documents in the collection
	 public void findCountOfAllClients() {
		 long count = clienteRepository.count();
		 System.out.println("Number of documents in the collection = " + count);
	 }

	 // UPDATE APPROACH 1: Using MongoRepository
	 public void updateClientsCountry(String country) {

		 // Change to this new value
		 String newCountry = "Brazil";

		 // Find all the items with the category
		 List<ClienteItem> list = clienteRepository.findAll(country);

		 list.forEach(item -> {
			 // Update the category in each document
			 item.setaddress(newCountry);
		 });

		 // Save all the items in database
		 List<ClienteItem> itemsUpdated = clienteRepository.saveAll(list);

		 if(itemsUpdated != null)
			 System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
	 }

	 // UPDATE APPROACH 2: Using MongoTemplate
	 public void updateClientAddress(String name, String address) {
		 System.out.println("Updating address for " + name);
		 customRepo.updateItemAddress(name, address);
	 }

	 // DELETE
	 public void deleteClientItem(String id) {
		 clienteRepository.deleteById(id);
		 System.out.println("Item with id " + id + " deleted...");
	 }
	 // Print details in readable form

	 public String getClientDetails(ClienteItem item) {

		 System.out.println(
		 "Client Id: " + item.getId() +
		 ", \nClient Name: " + item.getname() +
		 ", \nClient Address: " + item.getaddress() +
		 ", \nClient Email: " + item.getEmail() +
		 ", \nClient Nacionality: " + item.getNacionalidade() +
		 ", \nClient Phone: " + item.getphone()
		 );

		 return "";
	 }
}

