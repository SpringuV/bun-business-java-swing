package app;

import repository.implement.CustomerRepositoryImplement;
import repository.implement.FoodRepositoryImplement;
import repository.implement.GuestTableRepositoryImplement;
import repository.implement.IngredientRepositoryImplement;
import repository.implement.InventoryRepositoryImplement;
import repository.implement.InvoiceRepositoryImplement;
import repository.implement.OrderRepositoryImplement;
import repository.implement.UserRepositoryImplement;
import repository.interfaceRepo.CustomerRepository;
import repository.interfaceRepo.FoodRepository;
import repository.interfaceRepo.GuestTableRepository;
import repository.interfaceRepo.IngredientRepository;
import repository.interfaceRepo.InventoryRepository;
import repository.interfaceRepo.InvoiceRepository;
import repository.interfaceRepo.OrderRepository;
import repository.interfaceRepo.UserRepository;


public class AppContext {
    // --- Repository ---
    public static final UserRepository userRepository = new UserRepositoryImplement();
    public static final CustomerRepository customerRepository = new CustomerRepositoryImplement();
    public static final FoodRepository foodRepository = new FoodRepositoryImplement();
    public static final GuestTableRepository guestTableRepository = new GuestTableRepositoryImplement();
    public static final IngredientRepository ingredientRepository = new IngredientRepositoryImplement();
    public static final InventoryRepository inventoryTransactionRepository = new InventoryRepositoryImplement();
    public static final InvoiceRepository invoiceRepository = new InvoiceRepositoryImplement();
    public static final OrderRepository orderRepository = new OrderRepositoryImplement();

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
