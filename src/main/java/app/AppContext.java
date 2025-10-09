package app;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import repository.implement.CustomerRepositoryImplement;
import repository.implement.FoodRepositoryImplement;
import repository.implement.GuestTableRepositoryImplement;
import repository.implement.IngredientRepositoryImplement;
import repository.implement.InventoryRepositoryImplement;
import repository.implement.InvoiceRepositoryImplement;
import repository.implement.OrderRepositoryImplement;
import repository.implement.UserRepositoryImplement;
import repository.implement.WarehouseRepositoryImplement;
import repository.interfaceRepo.CustomerRepository;
import repository.interfaceRepo.FoodRepository;
import repository.interfaceRepo.GuestTableRepository;
import repository.interfaceRepo.IngredientRepository;
import repository.interfaceRepo.InventoryRepository;
import repository.interfaceRepo.InvoiceRepository;
import repository.interfaceRepo.OrderRepository;
import repository.interfaceRepo.UserRepository;
import repository.interfaceRepo.WareHouseRepository;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class AppContext {
    // --- Repository ---
    static UserRepository userRepository = new UserRepositoryImplement();
    static CustomerRepository customerRepository = new CustomerRepositoryImplement();
    static FoodRepository foodRepository = new FoodRepositoryImplement();
    static GuestTableRepository guestTableRepository = new GuestTableRepositoryImplement();
    static IngredientRepository ingredientRepository = new IngredientRepositoryImplement();
    static InventoryRepository inventoryTransactionRepository = new InventoryRepositoryImplement();
    static InvoiceRepository invoiceRepository = new InvoiceRepositoryImplement();
    static OrderRepository orderRepository = new OrderRepositoryImplement();
    static WareHouseRepository wareHouseRepository = new WarehouseRepositoryImplement();

    // --- Nếu sau này có Service ---
    // public static final UserService userService = new UserService(userRepository);

    // --- Constructor private để không ai tạo thêm instance ---
    private AppContext() {
    }

    // --- Nếu cần reset session hoặc cleanup (ít khi dùng) ---
    public static void close() {
        System.out.println("🔒 Đóng context, giải phóng tài nguyên (nếu có).");
    }
}
